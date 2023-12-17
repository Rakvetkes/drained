package org.aki.melted.refinedwater;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.aki.melted.common.PublicVars;
import org.aki.melted.limitedfluid.LimitedFluid;

public class RefinedWater extends LimitedFluid {

    public RefinedWater(/*boolean isFlowing*/) {
        super(/*isFlowing*/);
    }



    /* Here are some silly minecraft-featured methods & classes. */

    @Override
    public Item getBucketItem() {
        return PublicVars.REFINED_WATER_BUCKET;
    }

    @Override
    public Block getBlock() {
        return PublicVars.REFINED_WATER_BLOCK;
    }




    /* Behaviour. */

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected float getBlastResistance() {
        return 100.0f;
    }

    @Override
    public float getFlowPressure(World world, BlockPos pos, FluidState state, Direction direction, FluidState targetState) {
        switch (direction) {
            case UP: return -1.0f;
            case DOWN: return 1.0f;
            case NORTH: case SOUTH: case EAST: case WEST:
                return 0.8f;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getTickRate(WorldView world) {
        return 1;
    }

}
