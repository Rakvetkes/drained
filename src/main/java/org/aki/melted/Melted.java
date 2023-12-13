package org.aki.melted;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.aki.melted.limitedfluid.LimitedFluidBlock;
import org.aki.melted.mixture.Mixture;
import org.aki.melted.mixture.MixtureBlock;
import org.aki.melted.mixture.MixtureDescription;

public class Melted implements ModInitializer {

    @Override
    public void onInitialize() {

        Debug.initialize();

        PublicVars.STILL_MIXTURE = Registry.register(Registries.FLUID,
                new Identifier("melted", "still_mixture"),
                new Mixture(false));
        PublicVars.FLOWING_MIXTURE = Registry.register(Registries.FLUID,
                new Identifier("melted", "flowing_mixture"),
                new Mixture(true));
        PublicVars.MIXTURE = Registry.register(Registries.BLOCK,
                new Identifier("melted", "mixture"),
                new MixtureBlock(FabricBlockSettings.copy(Blocks.WATER)));
        PublicVars.MIXTURE_BUCKET = Registry.register(Registries.ITEM,
                new Identifier("melted", "mixture_bucket"),
                new BucketItem(PublicVars.STILL_MIXTURE, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        PublicVars.MIXTURE_DESCRIPTION = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier("melted", "mixture_description"),
                FabricBlockEntityTypeBuilder.create(MixtureDescription::new, PublicVars.MIXTURE).build());

    }

}
