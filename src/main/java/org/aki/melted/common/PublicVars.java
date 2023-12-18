package org.aki.melted.common;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BucketItem;
import org.aki.melted.limitedfluid.LimitedFluidBlock;
import org.aki.melted.liquidmixture.LiquidMixture;
import org.aki.melted.liquidmixture.LiquidMixtureBlock;
import org.aki.melted.liquidmixture.LiquidMixtureBlockEntity;
import org.aki.melted.refinedwater.RefinedWater;

public class PublicVars {

    public static RefinedWater REFINED_WATER;
    public static LimitedFluidBlock REFINED_WATER_BLOCK;
    public static BucketItem REFINED_WATER_BUCKET;

    public static BlockEntityType<LiquidMixtureBlockEntity> LIQUID_MIXTURE_BLOCK_ENTITY;
    public static LiquidMixture LIQUID_MIXTURE;
    public static LiquidMixtureBlock LIQUID_MIXTURE_BLOCK;

}
