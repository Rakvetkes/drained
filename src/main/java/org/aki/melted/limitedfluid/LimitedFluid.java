package org.aki.melted.limitedfluid;

import com.sun.jna.platform.win32.OaIdl;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.aki.melted.common.reactor.ReactorManager;
import org.aki.melted.common.util.Pair;
import org.aki.melted.refactoredfluid.RefactoredFluid;

public abstract class LimitedFluid extends RefactoredFluid {

    /* Implementation of the limited fluid mechanism. */

    public static final int MAX_ACTUAL_LEVEL = 80;
    public static final IntProperty ACTUAL_LEVEL = IntProperty.of("actual_level", 0, MAX_ACTUAL_LEVEL);

    private boolean coefficientFeatureKey;

    public LimitedFluid() {
        super();
        coefficientFeatureKey = false;
        setDefaultState(getDefaultState().with(ACTUAL_LEVEL, MAX_ACTUAL_LEVEL));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        super.appendProperties(builder);
        builder.add(ACTUAL_LEVEL);
    }

    protected FluidState changeIntoTheSameFluid(FluidState state) {
        if (this.matchesType(state.getFluid())) {
            return state;
        } else if (state.isEmpty()) {
            return this.getDefaultState().with(ACTUAL_LEVEL, 0);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void exchange(World world, BlockPos pos, Direction direction) {
        int flag = 0;
        if (direction == Direction.DOWN) flag = 1;
        if (direction == Direction.UP) flag = 2;
        exchange(world, pos, direction, flag);
    }

    protected void exchange(World world, BlockPos pos, Direction direction, int flag) {
        BlockPos targetPos = pos.offset(direction);
        FluidState state = world.getFluidState(pos);
        FluidState targetState = world.getFluidState(targetPos);
        float coefficient = this.getCoefficient(world, pos, state, direction, targetState);
        state = changeIntoTheSameFluid(state);
        targetState = changeIntoTheSameFluid(targetState);
        Pair<FluidState, FluidState> res = exchange(state, targetState, coefficient, flag);
        this.flow(world, pos, world.getBlockState(pos), direction.getOpposite(), res.getLeft());
        this.flow(world, targetPos, world.getBlockState(targetPos), direction, res.getRight());
    }

    protected Pair<FluidState, FluidState> exchange(FluidState state, FluidState targetState, float coefficient, int flag) {
        int level1 = state.get(ACTUAL_LEVEL);
        int level2 = targetState.get(ACTUAL_LEVEL);
        int flow = (int) (1.0f * (level1 - level2) * coefficient);
        if (flag == 1) flow = (int) (1.0f * level1 * coefficient);
        if (flag == 2) flow = (int) (1.0f * level2 * coefficient);

        flow = Math.min(flow, level1);
        flow = Math.min(flow, MAX_ACTUAL_LEVEL - level2);
        flow = Math.max(flow, -level2);
        flow = Math.max(flow, -MAX_ACTUAL_LEVEL + level1);

        FluidState newLeft = updateLevel(state.with(ACTUAL_LEVEL, level1 - flow));
        FluidState newRight = updateLevel(targetState.with(ACTUAL_LEVEL, level2 + flow));
        return new Pair<>(newLeft, newRight);
    }

    protected float getCoefficient(World world, BlockPos pos, FluidState state, Direction direction, FluidState targetState) {
        if (coefficientFeatureKey) {
            return 1.0f;
        } else {
            return getFlowPressure(world, pos, state, direction, targetState);
        }
    }

    @Override
    protected boolean hasRandomTicks() {
        return true;
    }

    @Override
    protected void onRandomTick(World world, BlockPos pos, FluidState state, Random random) {
        coefficientFeatureKey = true;
        this.tryFlow(world, pos, state);
        coefficientFeatureKey = false;
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
    abstract public float getFlowPressure(World world, BlockPos pos, FluidState state, Direction direction, FluidState targetState);

    @Override
    public void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        if (!fluidState.isEmpty() || canClear(state)) {
            super.flow(world, pos, state, direction, fluidState);
        }
    }

    private boolean canClear(BlockState state) {
        return state.getFluidState().getLevel() != 0;
    }



    /* Something else. */

    @Override
    protected BlockState toBlockState(FluidState state) {
        return this.getBlock().getDefaultState()
                .with(Properties.LEVEL_15, getBlockStateLevel(state))
                .with(ACTUAL_LEVEL, state.get(ACTUAL_LEVEL));
    }

    public FluidState updateLevel(FluidState oldState) {
        int actualLevel = oldState.get(ACTUAL_LEVEL);
        if (actualLevel == 0) {
            return Fluids.EMPTY.getDefaultState();
        } else {
            return oldState.with(LEVEL, (int) Math.ceil((double) actualLevel / MAX_ACTUAL_LEVEL * 8.0d));
        }
    }

    @Override
    public float getHeight(FluidState state) {
        if (this.matchesType(state.getFluid())) {
            return (state.get(ACTUAL_LEVEL) / (float) MAX_ACTUAL_LEVEL);
        } else {
            return super.getHeight(state);
        }
    }

}
