package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public class TerrariumBlocks {
    public static final Block EBONWOOD_PLANKS = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F, 3.0F));
    public static final Block EBONWOOD_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F, 3.0F));
    public static final Block EBONWOOD_STAIRS = new StairsBlock(EBONWOOD_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(EBONWOOD_PLANKS));
    public static final Block EBONWOOD_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_LIGHT_BLUE : MapColor.TERRACOTTA_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F));
    public static final Block STRIPPED_EBONWOOD_LOG = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F));
    public static final Block EBONWOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F));
    public static final Block STRIPPED_EBONWOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_LIGHT_BLUE).sounds(BlockSoundGroup.WOOD).strength(2.0F));

    public static final Block EBONWOOD_FENCE = new FenceBlock(AbstractBlock.Settings.copy(EBONWOOD_PLANKS));
    public static final Block EBONWOOD_SIGN = new SignBlock(AbstractBlock.Settings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), TerrariumSignType.EBONWOOD);
    public static final Block EBONWOOD_WALL_SIGN = new WallSignBlock(AbstractBlock.Settings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD), TerrariumSignType.EBONWOOD);

    public static final Block EBONWOOD_BUTTON = new WoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().sounds(BlockSoundGroup.WOOD).strength(0.5F));
    public static final Block EBONWOOD_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, EBONWOOD_PLANKS.getDefaultMapColor()).noCollision().sounds(BlockSoundGroup.WOOD).strength(0.5F));
    public static final Block EBONWOOD_DOOR = new DoorBlock(AbstractBlock.Settings.of(Material.WOOD, EBONWOOD_PLANKS.getDefaultMapColor()).nonOpaque().sounds(BlockSoundGroup.WOOD).strength(3.0F));
    public static final Block EBONWOOD_TRAPDOOR = new TrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, EBONWOOD_PLANKS.getDefaultMapColor()).nonOpaque().sounds(BlockSoundGroup.WOOD).strength(3.0F));
    public static final Block EBONWOOD_FENCE_GATE = new FenceGateBlock(AbstractBlock.Settings.copy(EBONWOOD_PLANKS));

    public static final Map<Block, Block> STRIPPED_BLOCKS = new ImmutableMap.Builder<Block, Block>().put(EBONWOOD, STRIPPED_EBONWOOD).put(EBONWOOD_LOG, STRIPPED_EBONWOOD_LOG).build();

    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_planks"), EBONWOOD_PLANKS);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_slab"), EBONWOOD_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_stairs"), EBONWOOD_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_log"), EBONWOOD_LOG);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "stripped_ebonwood_log"), STRIPPED_EBONWOOD_LOG);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood"), EBONWOOD);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "stripped_ebonwood"), STRIPPED_EBONWOOD);

        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_fence"), EBONWOOD_FENCE);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_sign"), EBONWOOD_SIGN);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_wall_sign"), EBONWOOD_WALL_SIGN);

        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_button"), EBONWOOD_BUTTON);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_pressure_plate"), EBONWOOD_PRESSURE_PLATE);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_door"), EBONWOOD_DOOR);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_trapdoor"), EBONWOOD_TRAPDOOR);
        Registry.register(Registry.BLOCK, new Identifier(Terrarium.MODID, "ebonwood_fence_gate"), EBONWOOD_FENCE_GATE);
    }
}
