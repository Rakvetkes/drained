package org.aki.melted.mixture;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.listener.GameEventListener;
import org.aki.melted.PublicVars;
import org.aki.melted.limitedfluid.LimitedFluid;
import org.jetbrains.annotations.Nullable;

public class Mixture extends LimitedFluid implements BlockEntityProvider {

    protected boolean isFlowing;

    public Mixture(boolean isFlowing) {
        this.isFlowing = isFlowing;
    }

    /* Here are some silly minecraft-featured methods & classes. */

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getFlowing() || fluid == getStill();
    }

    @Override
    public Fluid getFlowing() {
        return PublicVars.FLOWING_MIXTURE;
    }

    @Override
    public Fluid getStill() {
        return PublicVars.STILL_MIXTURE;
    }

    @Override
    public Item getBucketItem() {
        return PublicVars.MIXTURE_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return PublicVars.MIXTURE.getDefaultState()
                .with(Properties.LEVEL_15, getBlockStateLevel(state))
                .with(ACTUAL_LEVEL, state.get(ACTUAL_LEVEL));
    }

    @Override
    public boolean isStill(FluidState state) {
        return !isFlowing;
    }



    /* TODO: The methods below are not realized yet. The return values should be computed from the fluid state. */

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

    }

    @Override
    protected float getBlastResistance() {
        return 1.0f;
    }

    @Override
    protected float getFlowPressure(World world, BlockPos pos, FluidState state, Direction direction, FluidState targetState) {
        switch (direction) {
            case UP: return 0.0f;
            case DOWN: return 1.0f;
            case NORTH: case SOUTH: case EAST: case WEST:
                return 0.2f;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }



    /* TODO: This part is unfinished. */
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return BlockEntityProvider.super.getTicker(world, state, type);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        return BlockEntityProvider.super.getGameEventListener(world, blockEntity);
    }
}
