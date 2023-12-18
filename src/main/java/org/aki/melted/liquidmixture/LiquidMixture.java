package org.aki.melted.liquidmixture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.aki.melted.limitedfluid.LimitedFluid;

public class LiquidMixture extends LimitedFluid {

    @Override
    public float getFlowPressure(World world, BlockPos pos, FluidState state, Direction direction, FluidState targetState) {
        LiquidMixtureBlockEntity be = (LiquidMixtureBlockEntity) world.getBlockEntity(pos);
        // TODO: calculate flow pressure.
        return 0;
    }

    @Override
    public Block getBlock() {
        return null;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

    }

    @Override
    public Item getBucketItem() {
        return null;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 0;
    }

    @Override
    protected float getBlastResistance() {
        return 0;
    }

}
