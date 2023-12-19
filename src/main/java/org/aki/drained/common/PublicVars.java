package org.aki.drained.common;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BucketItem;
import org.aki.drained.limitedfluid.LimitedFluidBlock;
import org.aki.drained.liquidmixture.LiquidMixture;
import org.aki.drained.liquidmixture.LiquidMixtureBlock;
import org.aki.drained.liquidmixture.LiquidMixtureBlockEntity;
import org.aki.drained.refinedwater.RefinedWater;

public class PublicVars {

    public static RefinedWater REFINED_WATER;
    public static LimitedFluidBlock REFINED_WATER_BLOCK;
    public static BucketItem REFINED_WATER_BUCKET;

    public static BlockEntityType<LiquidMixtureBlockEntity> LIQUID_MIXTURE_BLOCK_ENTITY;
    public static LiquidMixture LIQUID_MIXTURE;
    public static LiquidMixtureBlock LIQUID_MIXTURE_BLOCK;

}
