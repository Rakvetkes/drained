package org.aki.drained.limitedfluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateManager;

public class LimitedFluidBlock extends FluidBlock {

    public LimitedFluidBlock(LimitedFluid fluid, Settings settings) {
        super(fluid, settings);
        setDefaultState(getDefaultState().with(LEVEL, 15)
                .with(LimitedFluid.ACTUAL_LEVEL, LimitedFluid.MAX_ACTUAL_LEVEL));
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
