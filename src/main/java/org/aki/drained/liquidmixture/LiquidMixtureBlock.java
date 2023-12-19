package org.aki.drained.liquidmixture;

import org.aki.drained.limitedfluid.LimitedFluid;
import org.aki.drained.limitedfluid.LimitedFluidBlockWithEntity;

public class LiquidMixtureBlock extends LimitedFluidBlockWithEntity {

    public LiquidMixtureBlock(LimitedFluid fluid, Settings settings) {
        super(LiquidMixtureBlockEntity::new, fluid, settings);
    }

}
