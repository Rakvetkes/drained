package org.aki.melted.liquidmixture;

import org.aki.melted.limitedfluid.LimitedFluid;
import org.aki.melted.limitedfluid.LimitedFluidBlockWithEntity;

public class LiquidMixtureBlock extends LimitedFluidBlockWithEntity {

    public LiquidMixtureBlock(LimitedFluid fluid, Settings settings) {
        super(LiquidMixtureBlockEntity::new, fluid, settings);
    }

}
