package org.aki.melted.limitedfluid;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.aki.melted.limitedfluid.LimitedFluid;
import org.aki.melted.limitedfluid.LimitedFluidBlock;
import org.jetbrains.annotations.Nullable;

public class LimitedFluidBlockWithEntity extends LimitedFluidBlock implements BlockEntityProvider {

    private final BlockEntityConstructor blockEntityConstructor;

    public LimitedFluidBlockWithEntity(BlockEntityConstructor blockEntityConstructor, LimitedFluid fluid, Settings settings) {
        super(fluid, settings);
        this.blockEntityConstructor = blockEntityConstructor;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityConstructor.getBlockEntity(pos, state);
    }

    public static interface BlockEntityConstructor {
        BlockEntity getBlockEntity(BlockPos pos, BlockState state);
    }

}
