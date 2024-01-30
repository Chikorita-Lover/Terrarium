package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TerrariumItems {
    public static final Item EBONWOOD_SIGN = register("ebonwood_sign", new SignItem(new Item.Settings().maxCount(16), TerrariumBlocks.EBONWOOD_SIGN, TerrariumBlocks.EBONWOOD_WALL_SIGN));

    public static Item register(Block block) {
        return register(new BlockItem(block, new Item.Settings()));
    }

    private static Item register(BlockItem item) {
        return register(item.getBlock(), item);
    }

    private static Item register(Block block, Item item) {
        return register(Registries.BLOCK.getId(block), item);
    }

    private static Item register(String id, Item item) {
        return register(new Identifier(Terrarium.MODID, id), item);
    }

    private static Item register(Identifier id, Item item) {
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, id, item);
    }

    public static void register() {
        registerFuels();
        registerItemGroups();
    }

    public static void registerFuels() {
        FuelRegistry.INSTANCE.add(TerrariumBlocks.EBONWOOD_FENCE, 300);
        FuelRegistry.INSTANCE.add(TerrariumBlocks.EBONWOOD_FENCE_GATE, 300);
    }

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addBefore(Blocks.CRIMSON_STEM, TerrariumBlocks.EBONWOOD_LOG, TerrariumBlocks.STRIPPED_EBONWOOD_LOG, TerrariumBlocks.EBONWOOD, TerrariumBlocks.STRIPPED_EBONWOOD, TerrariumBlocks.EBONWOOD_PLANKS, TerrariumBlocks.EBONWOOD_STAIRS, TerrariumBlocks.EBONWOOD_SLAB, TerrariumBlocks.EBONWOOD_FENCE, TerrariumBlocks.EBONWOOD_FENCE_GATE, TerrariumBlocks.EBONWOOD_DOOR, TerrariumBlocks.EBONWOOD_TRAPDOOR, TerrariumBlocks.EBONWOOD_PRESSURE_PLATE, TerrariumBlocks.EBONWOOD_BUTTON);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.addBefore(Items.CRIMSON_SIGN, TerrariumItems.EBONWOOD_SIGN);
        });
    }
}
