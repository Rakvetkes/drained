package org.aki.melted.mixture;

import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public class MixtureRenderHandler extends SimpleFluidRenderHandler {

    public MixtureRenderHandler() {
        super(WATER_STILL, WATER_FLOWING, WATER_OVERLAY, 0);
    }

    /* TODO: This should be computed from the fluid state. */
    @Override
    public int getFluidColor(@Nullable BlockRenderView view, @Nullable BlockPos pos, FluidState state) {
        return 0xff8c00;
    }
}
