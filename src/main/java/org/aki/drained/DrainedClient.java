package org.aki.drained;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import org.aki.drained.common.PublicVars;

@Environment(EnvType.CLIENT)
public class DrainedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FluidRenderHandlerRegistry.INSTANCE.register(PublicVars.REFINED_WATER,
                SimpleFluidRenderHandler.coloredWater(0xDDEEFF));

    }

}
