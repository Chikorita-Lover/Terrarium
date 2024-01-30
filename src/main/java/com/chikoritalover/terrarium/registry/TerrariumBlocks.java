package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class TerrariumBlocks {
    public static final Block EBONWOOD_LOG = registerBlockWithItem("ebonwood_log", new PillarBlock(FabricBlockSettings.create().burnable().mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_LIGHT_BLUE : MapColor.TERRACOTTA_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F)));
    public static final Block STRIPPED_EBONWOOD_LOG = registerBlockWithItem("stripped_ebonwood_log", new PillarBlock(FabricBlockSettings.create().burnable().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F)));
    public static final Block EBONWOOD = registerBlockWithItem("ebonwood", new PillarBlock(FabricBlockSettings.create().burnable().mapColor(MapColor.TERRACOTTA_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F)));
    public static final Block STRIPPED_EBONWOOD = registerBlockWithItem("stripped_ebonwood", new PillarBlock(FabricBlockSettings.create().burnable().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F)));
    public static final Block EBONWOOD_PLANKS = registerBlockWithItem("ebonwood_planks", new Block(FabricBlockSettings.create().burnable().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F, 3.0F)));
    public static final Block EBONWOOD_STAIRS = registerBlockWithItem("ebonwood_stairs", new StairsBlock(EBONWOOD_PLANKS.getDefaultState(), FabricBlockSettings.copy(EBONWOOD_PLANKS)));
    public static final Block EBONWOOD_SLAB = registerBlockWithItem("ebonwood_slab", new SlabBlock(FabricBlockSettings.create().burnable().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F, 3.0F)));
    public static final Block EBONWOOD_FENCE = registerBlockWithItem("ebonwood_fence", new FenceBlock(FabricBlockSettings.copy(EBONWOOD_PLANKS)));
    public static final Block EBONWOOD_FENCE_GATE = registerBlockWithItem("ebonwood_fence_gate", new FenceGateBlock(FabricBlockSettings.copy(EBONWOOD_PLANKS), Terrarium.EBONWOOD_WOOD_TYPE));
    public static final Block EBONWOOD_DOOR = registerBlockWithItem("ebonwood_door", new DoorBlock(FabricBlockSettings.create().mapColor(EBONWOOD_PLANKS.getDefaultMapColor()).nonOpaque().sounds(BlockSoundGroup.WOOD).strength(3.0F), Terrarium.EBONWOOD_BLOCK_SET));
    public static final Block EBONWOOD_TRAPDOOR = registerBlockWithItem("ebonwood_trapdoor", new TrapdoorBlock(FabricBlockSettings.create().mapColor(EBONWOOD_PLANKS.getDefaultMapColor()).nonOpaque().sounds(BlockSoundGroup.WOOD).strength(3.0F), Terrarium.EBONWOOD_BLOCK_SET));
    public static final Block EBONWOOD_PRESSURE_PLATE = registerBlockWithItem("ebonwood_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.create().mapColor(EBONWOOD_PLANKS.getDefaultMapColor()).noCollision().sounds(BlockSoundGroup.WOOD).strength(0.5F), Terrarium.EBONWOOD_BLOCK_SET));
    public static final Block EBONWOOD_BUTTON = registerBlockWithItem("ebonwood_button", new ButtonBlock(FabricBlockSettings.create().noCollision().strength(0.5F), Terrarium.EBONWOOD_BLOCK_SET, 30, true));

    public static final Block EBONWOOD_SIGN = register("ebonwood_sign", new SignBlock(FabricBlockSettings.create().noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), Terrarium.EBONWOOD_WOOD_TYPE));
    public static final Block EBONWOOD_WALL_SIGN = register("ebonwood_wall_sign", new WallSignBlock(FabricBlockSettings.create().noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), Terrarium.EBONWOOD_WOOD_TYPE));

    private static Block registerBlockWithItem(String id, Block block) {
        Block block2 = register(id, block);
        BlockItem blockItem = new BlockItem(block2, new FabricItemSettings());
        blockItem.appendBlocks(Item.BLOCK_ITEMS, blockItem);
        Registry.register(Registries.ITEM, new Identifier(Terrarium.MODID, id), blockItem);
        return block2;
    }

    private static Block register(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Terrarium.MODID, id), block);
    }

    public static void register() {
        registerFlammableBlocks();
        registerStrippableBlockPairs();
    }

    private static void registerFlammableBlocks() {
        FlammableBlockRegistry.getDefaultInstance().add(EBONWOOD_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(EBONWOOD_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(EBONWOOD_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(EBONWOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_EBONWOOD_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(EBONWOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_EBONWOOD, 5, 5);

        FlammableBlockRegistry.getDefaultInstance().add(EBONWOOD_FENCE, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(EBONWOOD_FENCE_GATE, 5, 20);
    }

    private static void registerStrippableBlockPairs() {
        StrippableBlockRegistry.register(EBONWOOD, STRIPPED_EBONWOOD);
        StrippableBlockRegistry.register(EBONWOOD_LOG, STRIPPED_EBONWOOD_LOG);
    }
}
