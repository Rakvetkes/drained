package org.aki.melted;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import org.aki.melted.mixture.MixtureRenderHandler;

@Environment(EnvType.CLIENT)
public class MeltedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FluidRenderHandlerRegistry.INSTANCE.register(PublicVars.STILL_MIXTURE, PublicVars.FLOWING_MIXTURE, new MixtureRenderHandler());

    }

}
