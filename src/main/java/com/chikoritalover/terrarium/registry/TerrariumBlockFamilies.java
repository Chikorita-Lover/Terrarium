package com.chikoritalover.terrarium.registry;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.stream.Stream;

public class TerrariumBlockFamilies {
    private static final Map<Block, BlockFamily> BASE_BLOCKS_TO_FAMILIES = Maps.newHashMap();
    public static final BlockFamily EBONWOOD = register(TerrariumBlocks.EBONWOOD_PLANKS).button(TerrariumBlocks.EBONWOOD_BUTTON).fence(TerrariumBlocks.EBONWOOD_FENCE).fenceGate(TerrariumBlocks.EBONWOOD_FENCE_GATE).pressurePlate(TerrariumBlocks.EBONWOOD_PRESSURE_PLATE).slab(TerrariumBlocks.EBONWOOD_SLAB).stairs(TerrariumBlocks.EBONWOOD_STAIRS).door(TerrariumBlocks.EBONWOOD_DOOR).trapdoor(TerrariumBlocks.EBONWOOD_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();

    public static BlockFamily.Builder register(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockFamily = BASE_BLOCKS_TO_FAMILIES.put(baseBlock, builder.build());
        if (blockFamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + Registries.BLOCK.getId(baseBlock));
        }
        return builder;
    }

    public static Stream<BlockFamily> getFamilies() {
        return BASE_BLOCKS_TO_FAMILIES.values().stream();
    }
}
