package com.chikoritalover.terrarium;

import com.chikoritalover.terrarium.registry.TerrariumBlockFamilies;
import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import com.chikoritalover.terrarium.registry.TerrariumItemTags;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TerrariumDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(TerrariumLootTableGenerator::new);
		fabricDataGenerator.addProvider(TerrariumModelGenerator::new);
		fabricDataGenerator.addProvider(TerrariumRecipeGenerator::new);
	}

	private static class TerrariumLootTableGenerator extends SimpleFabricLootTableProvider {
		public TerrariumLootTableGenerator(FabricDataGenerator dataGenerator) {
			super(dataGenerator, LootContextTypes.BLOCK);
		}

		@Override
		public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_PLANKS);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_SLAB, BlockLootTableGenerator.slabDrops(TerrariumBlocks.EBONWOOD_SLAB));
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_STAIRS);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_LOG);
			this.addDrop(biConsumer, TerrariumBlocks.STRIPPED_EBONWOOD_LOG);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD);
			this.addDrop(biConsumer, TerrariumBlocks.STRIPPED_EBONWOOD);

			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_FENCE);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_SIGN);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_WALL_SIGN, TerrariumItems.EBONWOOD_SIGN);

			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_BUTTON);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_PRESSURE_PLATE);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_DOOR, BlockLootTableGenerator.doorDrops(TerrariumBlocks.EBONWOOD_DOOR));
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_TRAPDOOR);
			this.addDrop(biConsumer, TerrariumBlocks.EBONWOOD_FENCE_GATE);
		}

		private void addDrop(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block, LootTable.Builder builder) {
			biConsumer.accept(block.getLootTableId(), builder);
		}

		private void addDrop(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block, ItemConvertible itemConvertible) {
			this.addDrop(biConsumer, block, BlockLootTableGenerator.drops(itemConvertible));
		}

		private void addDrop(BiConsumer<Identifier, LootTable.Builder> biConsumer, Block block) {
			this.addDrop(biConsumer, block, block);
		}
	}

	private static class TerrariumModelGenerator extends FabricModelProvider {
		private TerrariumModelGenerator(FabricDataGenerator generator) {
			super(generator);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			TerrariumBlockFamilies.getFamilies().filter(BlockFamily::shouldGenerateModels).forEach((family) -> blockStateModelGenerator.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family));

			blockStateModelGenerator.registerLog(TerrariumBlocks.EBONWOOD_LOG).log(TerrariumBlocks.EBONWOOD_LOG).wood(TerrariumBlocks.EBONWOOD);
			blockStateModelGenerator.registerLog(TerrariumBlocks.STRIPPED_EBONWOOD_LOG).log(TerrariumBlocks.STRIPPED_EBONWOOD_LOG).wood(TerrariumBlocks.STRIPPED_EBONWOOD);
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			itemModelGenerator.register(TerrariumItems.EBONWOOD_BOAT, Models.GENERATED);
			itemModelGenerator.register(TerrariumItems.EBONWOOD_CHEST_BOAT, Models.GENERATED);

			itemModelGenerator.register(TerrariumItems.BOOMERANG, Models.HANDHELD);
		}
	}

	private static class TerrariumRecipeGenerator extends FabricRecipeProvider {
		private TerrariumRecipeGenerator(FabricDataGenerator generator) {
			super(generator);
		}

		@Override
		protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
			TerrariumBlockFamilies.getFamilies().filter(BlockFamily::shouldGenerateRecipes).forEach(family -> RecipeProvider.generateFamily(exporter, family));

			RecipeProvider.offerPlanksRecipe(exporter, TerrariumBlocks.EBONWOOD_PLANKS, TerrariumItemTags.EBONWOOD_LOGS);
			RecipeProvider.offerBarkBlockRecipe(exporter, TerrariumBlocks.EBONWOOD, TerrariumBlocks.EBONWOOD_LOG);
			RecipeProvider.offerBarkBlockRecipe(exporter, TerrariumBlocks.STRIPPED_EBONWOOD, TerrariumBlocks.STRIPPED_EBONWOOD_LOG);

			RecipeProvider.offerBoatRecipe(exporter, TerrariumItems.EBONWOOD_BOAT, TerrariumBlocks.EBONWOOD_PLANKS);
			RecipeProvider.offerChestBoatRecipe(exporter, TerrariumItems.EBONWOOD_CHEST_BOAT, TerrariumItems.EBONWOOD_BOAT);
		}
	}
}
