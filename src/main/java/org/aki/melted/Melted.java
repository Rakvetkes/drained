package org.aki.melted;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.aki.melted.common.Debug;
import org.aki.melted.common.PublicVars;
import org.aki.melted.common.reactor.ReactorManager;
import org.aki.melted.limitedfluid.LimitedFluidBlock;
import org.aki.melted.liquidmixture.LiquidMixture;
import org.aki.melted.liquidmixture.LiquidMixtureBlock;
import org.aki.melted.liquidmixture.LiquidMixtureBlockEntity;
import org.aki.melted.refinedwater.RefinedWater;

public class Melted implements ModInitializer {

    @Override
    public void onInitialize() {

        Debug.initialize();

        PublicVars.REFINED_WATER = Registry.register(Registries.FLUID,
                new Identifier("melted", "refined_water"),
                new RefinedWater());
        PublicVars.REFINED_WATER_BLOCK = Registry.register(Registries.BLOCK,
                new Identifier("melted", "refined_water"),
                new LimitedFluidBlock(PublicVars.REFINED_WATER, FabricBlockSettings.copy(Blocks.WATER)));
        // TODO: stop using net.minecraft.Item.BucketItem.
        PublicVars.REFINED_WATER_BUCKET = Registry.register(Registries.ITEM,
                new Identifier("melted", "refined_water_bucket"),
                new BucketItem(PublicVars.REFINED_WATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

        ReactorManager.INSTANCE.register(Fluids.EMPTY, (world, pos, direction) -> {});

        PublicVars.LIQUID_MIXTURE = Registry.register(Registries.FLUID,
                new Identifier("melted", "liquid_mixture"),
                new LiquidMixture());
        PublicVars.LIQUID_MIXTURE_BLOCK = Registry.register(Registries.BLOCK,
                new Identifier("melted", "liquid_mixture"),
                new LiquidMixtureBlock(PublicVars.LIQUID_MIXTURE, FabricBlockSettings.copy(Blocks.WATER)));
        PublicVars.LIQUID_MIXTURE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier("melted", "liquid_mixture_block_entity"),
                FabricBlockEntityTypeBuilder.create(LiquidMixtureBlockEntity::new, PublicVars.LIQUID_MIXTURE_BLOCK).build());

    }

}
