package org.aki.melted.limitedfluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.aki.melted.Debug;

public abstract class LimitedFluid extends FlowableFluid {

    /* Abandoned Vanilla features. */

    @Deprecated @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    @Deprecated @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Deprecated @Override
    protected int getFlowSpeed(WorldView world) {
        return 4; // I don't know what the function of this method is yet.
    }

    @Deprecated @Override
    protected FluidState getUpdatedState(World world, BlockPos pos, BlockState state) {
        return super.getUpdatedState(world, pos, state);
    }

    @Deprecated @Override
    protected void tryFlow(World world, BlockPos pos, FluidState state) {
        super.tryFlow(world, pos, state);
    }



    /* Implementation of the limited fluid mechanism. */

    public static final int MAX_ACTUAL_LEVEL = 80;
    public static final IntProperty ACTUAL_LEVEL = IntProperty.of("actual_level", 0, MAX_ACTUAL_LEVEL);

    public LimitedFluid() {
        setDefaultState(updateLevel(getDefaultState().with(ACTUAL_LEVEL, MAX_ACTUAL_LEVEL)));
    }

    protected FluidState updateLevel(FluidState oldState) {
        int actualLevel = oldState.get(ACTUAL_LEVEL);
        if (actualLevel == 0) {
            return Fluids.EMPTY.getDefaultState();
        } else {
            return oldState.with(LEVEL, (int) Math.ceil((double) actualLevel / MAX_ACTUAL_LEVEL * 8.0d));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        super.appendProperties(builder);
        builder.add(LEVEL, ACTUAL_LEVEL);
    }

    @Override
    public int getLevel(FluidState state) {
        return state.get(LEVEL);
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return this.matchesType(fluid);
    }

    @Override
    public void onScheduledTick(World world, BlockPos pos, FluidState state) {
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = blockState.getFluidState();
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            BlockState targetState = world.getBlockState(blockPos);
            FluidState targetFluidState = targetState.getFluidState();
            if (this.canFlow(world, pos, blockState, direction, blockPos, targetState, targetFluidState, this)) {
                limitedFluidFlow(world, pos, blockState, fluidState, direction, targetState, targetFluidState);
            }
            blockState = world.getBlockState(pos);
            fluidState = blockState.getFluidState();
        }
        if (fluidState != state) {
            world.scheduleFluidTick(pos, fluidState.getFluid(), getNextTickDelay(world, pos, state, fluidState));
            // world.updateNeighborsAlways(pos, currentState.getBlockState().getBlock());
        }
    }

    protected void limitedFluidFlow(World world, BlockPos pos, BlockState state, FluidState fluidState, Direction direction, BlockState targetState, FluidState targetFluidState) {
        BlockPos targetPos = pos.offset(direction);
        float coefficient = this.getFlowPressure(world, pos, fluidState, direction, targetFluidState);
        Pair<FluidState, FluidState> res = exchange(new Pair<>(fluidState, targetFluidState), coefficient,
                direction == Direction.UP || direction == Direction.DOWN);

        if (Debug.isDebugInfoEnabled) {
            System.out.println(String.format("Calculating a flow from (%d,%d,%d) to (%d,%d,%d). The source level is %d. " +
                            "The target level is %d. The flow in this tick is %d.", pos.getX(), pos.getY(), pos.getZ(),
                    targetPos.getX(), targetPos.getY(), targetPos.getZ(), this.getActualLevel(fluidState),
                    this.getActualLevel(targetFluidState), this.getActualLevel(fluidState) - this.getActualLevel(res.getLeft())));
        }

        this.flow(world, pos, state, direction.getOpposite(), res.getLeft());
        this.flow(world, targetPos, targetState, direction, res.getRight());
    }

    /* TODO: This method needs to be rewritten. */
    protected Pair<FluidState, FluidState> exchange(Pair<FluidState, FluidState> objects, float coefficient, boolean _featureKey) {
        FluidState left = changeIntoMixture(objects.getLeft());
        FluidState right = changeIntoMixture(objects.getRight());
        int level1 = left.get(ACTUAL_LEVEL), level2 = right.get(ACTUAL_LEVEL);
        int flow = (int) (1.0 * (level1 - (_featureKey ? 0 : level2)) * coefficient);

        flow = Math.min(flow, level1);
        flow = Math.min(flow, MAX_ACTUAL_LEVEL - level2);
        flow = Math.max(flow, -level2);
        flow = Math.max(flow, -MAX_ACTUAL_LEVEL + level1);

        return new Pair<>(this.updateLevel(left.with(ACTUAL_LEVEL, level1 - flow)),
                this.updateLevel(right.with(ACTUAL_LEVEL, level2 + flow)));
    }

    /* TODO: The type of the target fluid should be kept. */
    protected FluidState changeIntoMixture(FluidState state) {
        if (this.matchesType(state.getFluid())) {
            return state;
        } else {
            int level = (int) ((float) state.getLevel() / 8.0 * (float) MAX_ACTUAL_LEVEL);
            return this.getDefaultState().with(ACTUAL_LEVEL, level);
        }
    }

    @Override
    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        if (!fluidState.isEmpty() || canClear(state)) {
            super.flow(world, pos, state, direction, fluidState);
        }
    }

    private int getActualLevel(FluidState state) {
        return changeIntoMixture(state).get(ACTUAL_LEVEL);
    }

    private boolean canClear(BlockState state) {
        return state.getFluidState().getLevel() != 0;
    }

    /**
     * Returns a float number representing the coefficient used to calculate the precise effect of flows.
     * The sign indicates the direction: positive for outward, negative for inward.
     *
     * @param world The world object.
     * @param pos The position of the fluid block being updated.
     * @param direction The direction being considered.
     * @return The coefficient for flow calculations.
     */
    abstract protected float getFlowPressure(World world, BlockPos pos, FluidState state, Direction direction, FluidState targetState);



    /* Something else. */

    @Override
    public float getHeight(FluidState state) {
        if (this.matchesType(state.getFluid())) {
            return (float) state.get(ACTUAL_LEVEL) / (float) MAX_ACTUAL_LEVEL * (8.0f / 9.0f);
        } else {
            return super.getHeight(state);
        }
    }

}
