package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import com.chikoritalover.terrarium.item.BoomerangItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerrariumItems {
    public static final Item EBONWOOD_PLANKS = new BlockItem(TerrariumBlocks.EBONWOOD_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final Item EBONWOOD_SLAB = new BlockItem(TerrariumBlocks.EBONWOOD_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final Item EBONWOOD_STAIRS = new BlockItem(TerrariumBlocks.EBONWOOD_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final Item EBONWOOD_LOG = new BlockItem(TerrariumBlocks.EBONWOOD_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final Item STRIPPED_EBONWOOD_LOG = new BlockItem(TerrariumBlocks.STRIPPED_EBONWOOD_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final Item EBONWOOD = new BlockItem(TerrariumBlocks.EBONWOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final Item STRIPPED_EBONWOOD = new BlockItem(TerrariumBlocks.STRIPPED_EBONWOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final Item EBONWOOD_FENCE = new BlockItem(TerrariumBlocks.EBONWOOD_FENCE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final Item EBONWOOD_SIGN = new SignItem(new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16), TerrariumBlocks.EBONWOOD_SIGN, TerrariumBlocks.EBONWOOD_WALL_SIGN);

    public static final Item EBONWOOD_BUTTON = new BlockItem(TerrariumBlocks.EBONWOOD_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final Item EBONWOOD_PRESSURE_PLATE = new BlockItem(TerrariumBlocks.EBONWOOD_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final Item EBONWOOD_DOOR = new BlockItem(TerrariumBlocks.EBONWOOD_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final Item EBONWOOD_TRAPDOOR = new BlockItem(TerrariumBlocks.EBONWOOD_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final Item EBONWOOD_FENCE_GATE = new BlockItem(TerrariumBlocks.EBONWOOD_FENCE_GATE, new Item.Settings().group(ItemGroup.REDSTONE));

    public static final Item EBONWOOD_BOAT = new BoatItem(false, TerrariumBoatType.EBONWOOD, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final Item EBONWOOD_CHEST_BOAT = new BoatItem(true, TerrariumBoatType.EBONWOOD, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));

    public static final Item BOOMERANG = new BoomerangItem(false, 5.0F, 1.3F, new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(131));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_planks"), EBONWOOD_PLANKS);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_slab"), EBONWOOD_SLAB);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_stairs"), EBONWOOD_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_log"), EBONWOOD_LOG);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "stripped_ebonwood_log"), STRIPPED_EBONWOOD_LOG);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood"), EBONWOOD);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "stripped_ebonwood"), STRIPPED_EBONWOOD);

        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_fence"), EBONWOOD_FENCE);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_sign"), EBONWOOD_SIGN);

        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_button"), EBONWOOD_BUTTON);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_pressure_plate"), EBONWOOD_PRESSURE_PLATE);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_door"), EBONWOOD_DOOR);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_trapdoor"), EBONWOOD_TRAPDOOR);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_fence_gate"), EBONWOOD_FENCE_GATE);

        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_boat"), EBONWOOD_BOAT);
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "ebonwood_chest_boat"), EBONWOOD_CHEST_BOAT);

        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "boomerang"), BOOMERANG);
    }
}
