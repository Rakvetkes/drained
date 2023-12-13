package org.aki.melted.mixture;

import net.minecraft.fluid.FlowableFluid;
import org.aki.melted.PublicVars;
import org.aki.melted.limitedfluid.LimitedFluidBlock;

public class MixtureBlock extends LimitedFluidBlock {

    public MixtureBlock(Settings settings) {
        super(PublicVars.STILL_MIXTURE, settings);
    }

}
