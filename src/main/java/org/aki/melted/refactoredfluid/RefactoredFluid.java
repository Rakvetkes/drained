package org.aki.melted.refactoredfluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.aki.melted.common.reactor.ReactorManager;

public abstract class RefactoredFluid extends FlowableFluid {

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
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Deprecated @Override
    protected FluidState getUpdatedState(World world, BlockPos pos, BlockState state) {
        return super.getUpdatedState(world, pos, state);
    }

    @Deprecated @Override
    protected void tryFlow(World world, BlockPos pos, FluidState state) {
        super.tryFlow(world, pos, state);
    }



    /* Reserved features. Though I'm not willing to use them. */

    private RefactoredFluid refStill, refFlowing;
    private boolean isFlowing;

    protected RefactoredFluid(boolean isFlowing) {
        this.isFlowing = isFlowing;
        setDefaultState(getDefaultState().with(LEVEL, 8));
        ReactorManager.INSTANCE.register(this, this::exchange);
        ReactorManager.INSTANCE.registerExchangeable(this, Fluids.EMPTY, this::exchange);
    }

    public void copyReferences(FluidPackage<? extends RefactoredFluid> fluidPackage) {
        refStill = fluidPackage.refStill;
        refFlowing = fluidPackage.refFlowing;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getFlowing() || fluid == getStill();
    }

    @Deprecated @Override
    public boolean isStill(FluidState state) {
        return !isFlowing;
    }

    @Override
    public Fluid getStill() {
        return refStill;
    }

    @Override
    public Fluid getFlowing() {
        return refFlowing;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        super.appendProperties(builder);
        builder.add(LEVEL);
    }

    @Override
    public int getLevel(FluidState state) {
        return state.get(LEVEL);
    }



    /* Rewritten and supporting methods. */

    @Override
    public void onScheduledTick(World world, BlockPos pos, FluidState state) {
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = blockState.getFluidState();
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            BlockState targetState = world.getBlockState(blockPos);
            FluidState targetFluidState = targetState.getFluidState();
            // never mind what that fluid is. just fuck it up.
            if (this.canFlow(world, pos, blockState, direction, blockPos, targetState, Fluids.EMPTY.getDefaultState(), this)) {
                exchange(world, pos, blockState, fluidState, direction, targetState, targetFluidState);
            }
            blockState = world.getBlockState(pos);
            fluidState = blockState.getFluidState();
        }
        if (fluidState != state) {
            world.scheduleFluidTick(pos, fluidState.getFluid(), getNextTickDelay(world, pos, state, fluidState));
            // world.updateNeighborsAlways(pos, currentState.getBlockState().getBlock());
        }
    }

    protected void exchange(World world, BlockPos pos, BlockState state, FluidState fluidState, Direction direction, BlockState targetState, FluidState targetFluidState) {
        ReactorManager.INSTANCE.call(world, pos, direction);
    }

    @Override
    public void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        // go to hell if u want this to be protected.
        super.flow(world, pos, state, direction, fluidState);
    }

    abstract public void exchange(World world, BlockPos pos, Direction direction);

    abstract public Block getBlock();

}
