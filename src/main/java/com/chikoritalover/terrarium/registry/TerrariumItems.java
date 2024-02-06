package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TerrariumItems {
    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Terrarium.MODID, id), item);
    }

    public static void register() {
        registerFuels();
        registerItemGroups();

        CompostingChanceRegistry.INSTANCE.add(TerrariumBlocks.EBONWOOD_LEAVES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(TerrariumBlocks.EBONWOOD_SAPLING, 0.3F);
    }

    public static void registerFuels() {
        FuelRegistry.INSTANCE.add(TerrariumBlocks.EBONWOOD_FENCE, 300);
        FuelRegistry.INSTANCE.add(TerrariumBlocks.EBONWOOD_FENCE_GATE, 300);
    }

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addBefore(Blocks.BAMBOO_BLOCK, TerrariumBlocks.EBONWOOD_LOG, TerrariumBlocks.STRIPPED_EBONWOOD_LOG, TerrariumBlocks.EBONWOOD, TerrariumBlocks.STRIPPED_EBONWOOD, TerrariumBlocks.EBONWOOD_PLANKS, TerrariumBlocks.EBONWOOD_STAIRS, TerrariumBlocks.EBONWOOD_SLAB, TerrariumBlocks.EBONWOOD_FENCE, TerrariumBlocks.EBONWOOD_FENCE_GATE, TerrariumBlocks.EBONWOOD_DOOR, TerrariumBlocks.EBONWOOD_TRAPDOOR, TerrariumBlocks.EBONWOOD_PRESSURE_PLATE, TerrariumBlocks.EBONWOOD_BUTTON);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addBefore(Blocks.MUSHROOM_STEM, TerrariumBlocks.EBONWOOD_LOG);
            entries.addBefore(Blocks.AZALEA_LEAVES, TerrariumBlocks.EBONWOOD_LEAVES);
            entries.addBefore(Blocks.AZALEA, TerrariumBlocks.EBONWOOD_SAPLING);
        });
    }
}
