package com.chikoritalover.terrarium.world;

import com.chikoritalover.terrarium.Terrarium;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class EbonwoodFoliagePlacer extends FoliagePlacer {
    public static final Codec<EbonwoodFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return fillFoliagePlacerFields(instance).and(instance.group(Codec.intRange(0, 16).fieldOf("height").forGetter((foliagePlacer) -> foliagePlacer.height), Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter((foliagePlacer) -> foliagePlacer.hangingLeavesChance))).apply(instance, EbonwoodFoliagePlacer::new);});
    private final int height;
    private final float hangingLeavesChance;

    public EbonwoodFoliagePlacer(IntProvider radius, IntProvider offset, int height, float hangingLeavesChance) {
        super(radius, offset);
        this.height = height;
        this.hangingLeavesChance = hangingLeavesChance;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return Terrarium.EBONWOOD_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        for (int i = offset; i >= offset - foliageHeight - treeNode.getFoliageRadius(); --i) {
            int j = Math.max(radius + treeNode.getFoliageRadius() - 1, 0);
            if (i == offset) --j;
            if (i == offset - foliageHeight) {
                this.generateSquareWithHangingLeaves(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk(), this.hangingLeavesChance, 0.0F);
            } else {
                this.generateSquare(world, placer, random, config, treeNode.getCenter(), j, i, treeNode.isGiantTrunk());
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        boolean bl = dx == radius && dz == radius;
        // boolean bl2 = (dx == radius || dz == radius) && y == radius - 2;
        return bl && random.nextInt(4) < radius;
    }
}
