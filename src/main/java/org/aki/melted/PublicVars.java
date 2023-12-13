package org.aki.melted;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import org.aki.melted.mixture.MixtureDescription;

public class PublicVars {

    public static FlowableFluid STILL_MIXTURE;
    public static FlowableFluid FLOWING_MIXTURE;

    public static Block MIXTURE;
    public static BlockEntityType<MixtureDescription> MIXTURE_DESCRIPTION;

    public static Item MIXTURE_BUCKET;

}
