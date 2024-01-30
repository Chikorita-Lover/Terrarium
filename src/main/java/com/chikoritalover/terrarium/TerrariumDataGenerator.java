package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.registry.TerrariumBlockFamilies;
import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import com.chikoritalover.terrarium.registry.TerrariumItemTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.resource.featuretoggle.FeatureFlags;

import java.util.function.Consumer;

public class TerrariumDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(TerrariumLootTableGenerator::new);
        pack.addProvider(TerrariumModelGenerator::new);
        pack.addProvider(TerrariumRecipeGenerator::new);
    }

    private static class TerrariumLootTableGenerator extends FabricBlockLootTableProvider {
        public TerrariumLootTableGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generate() {
            this.addDrop(TerrariumBlocks.EBONWOOD_PLANKS);
            this.addDrop(TerrariumBlocks.EBONWOOD_SLAB, slabDrops(TerrariumBlocks.EBONWOOD_SLAB));
            this.addDrop(TerrariumBlocks.EBONWOOD_STAIRS);
            this.addDrop(TerrariumBlocks.EBONWOOD_LOG);
            this.addDrop(TerrariumBlocks.STRIPPED_EBONWOOD_LOG);
            this.addDrop(TerrariumBlocks.EBONWOOD);
            this.addDrop(TerrariumBlocks.STRIPPED_EBONWOOD);

            this.addDrop(TerrariumBlocks.EBONWOOD_FENCE);
            this.addDrop(TerrariumBlocks.EBONWOOD_SIGN);

            this.addDrop(TerrariumBlocks.EBONWOOD_BUTTON);
            this.addDrop(TerrariumBlocks.EBONWOOD_PRESSURE_PLATE);
            this.addDrop(TerrariumBlocks.EBONWOOD_DOOR, doorDrops(TerrariumBlocks.EBONWOOD_DOOR));
            this.addDrop(TerrariumBlocks.EBONWOOD_TRAPDOOR);
            this.addDrop(TerrariumBlocks.EBONWOOD_FENCE_GATE);
        }
    }

    private static class TerrariumModelGenerator extends FabricModelProvider {
        private TerrariumModelGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            TerrariumBlockFamilies.getFamilies().filter(BlockFamily::shouldGenerateModels).forEach((family) -> blockStateModelGenerator.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family));

            blockStateModelGenerator.registerLog(TerrariumBlocks.EBONWOOD_LOG).log(TerrariumBlocks.EBONWOOD_LOG).wood(TerrariumBlocks.EBONWOOD);
            blockStateModelGenerator.registerLog(TerrariumBlocks.STRIPPED_EBONWOOD_LOG).log(TerrariumBlocks.STRIPPED_EBONWOOD_LOG).wood(TerrariumBlocks.STRIPPED_EBONWOOD);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        }
    }

    private static class TerrariumRecipeGenerator extends FabricRecipeProvider {
        private TerrariumRecipeGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> exporter) {
            TerrariumBlockFamilies.getFamilies().filter(family -> family.shouldGenerateRecipes(FeatureFlags.DEFAULT_ENABLED_FEATURES)).forEach(family -> RecipeProvider.generateFamily(exporter, family));

            RecipeProvider.offerPlanksRecipe(exporter, TerrariumBlocks.EBONWOOD_PLANKS, TerrariumItemTags.EBONWOOD_LOGS, 4);
            RecipeProvider.offerBarkBlockRecipe(exporter, TerrariumBlocks.EBONWOOD, TerrariumBlocks.EBONWOOD_LOG);
            RecipeProvider.offerBarkBlockRecipe(exporter, TerrariumBlocks.STRIPPED_EBONWOOD, TerrariumBlocks.STRIPPED_EBONWOOD_LOG);
        }
    }
}
