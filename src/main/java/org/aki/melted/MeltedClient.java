package org.aki.melted;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import org.aki.melted.common.PublicVars;

@Environment(EnvType.CLIENT)
public class MeltedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FluidRenderHandlerRegistry.INSTANCE.register(PublicVars.REFINED_WATER,
                SimpleFluidRenderHandler.coloredWater(0x00CCFF));

    }

}
