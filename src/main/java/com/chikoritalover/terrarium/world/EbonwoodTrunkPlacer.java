package com.chikoritalover.terrarium.world;

import com.chikoritalover.terrarium.Terrarium;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class EbonwoodTrunkPlacer extends TrunkPlacer {
    public static final Codec<EbonwoodTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, (EbonwoodTrunkPlacer::new)));

    public EbonwoodTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    protected TrunkPlacerType<?> getType() {
        return Terrarium.EBONWOOD_TRUNK_PLACER;
    }

    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = new ArrayList<>(ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 0, false)));
        List<Direction> list2 = new ArrayList<>(Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal()).toList());

        setToDirt(world, replacer, random, startPos.down(), config);
        if (random.nextBoolean()) {
            this.getAndSetState(world, replacer, random, startPos.offset(Util.getRandom(list2, random)), config);
        }
        for (int i = 0; i < height; ++i) {
            this.getAndSetState(world, replacer, random, startPos.up(i), config);
        }
        for (int i = 0; i < random.nextBetween(1, 2); ++i) {
            Direction direction = Util.getRandom(list2, random);
            list2.remove(direction);
            int j = height - random.nextBetween(5, 6) - i * 2;
            BlockPos blockPos = startPos.up(j).offset(direction);
            this.getAndSetState(world, replacer, random, blockPos, config);
            if (random.nextBoolean()) {
                this.getAndSetState(world, replacer, random, blockPos.up().offset(direction), config);
                list.add(new FoliagePlacer.TreeNode(blockPos.up(2).offset(direction), -1, false));
            }
        }

        return list;
    }
}
