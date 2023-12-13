package org.aki.melted.limitedfluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateManager;

public class LimitedFluidBlock extends FluidBlock {

    public LimitedFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
        setDefaultState(getDefaultState().with(LimitedFluid.ACTUAL_LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LimitedFluid.ACTUAL_LEVEL);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        FluidState fluidState = super.getFluidState(state);
        return fluidState.with(LimitedFluid.ACTUAL_LEVEL, state.get(LimitedFluid.ACTUAL_LEVEL));
    }

}
