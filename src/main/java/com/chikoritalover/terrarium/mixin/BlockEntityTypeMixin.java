package com.chikoritalover.terrarium.mixin;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @ModifyArg(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=sign")), at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType$Builder;create(Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType$Builder;"), index = 1)
    private static Block[] create(Block[] blocks) {
        List<Block> list = new ArrayList<>(Arrays.stream(blocks).toList());
        list.add(TerrariumBlocks.EBONWOOD_SIGN);
        list.add(TerrariumBlocks.EBONWOOD_WALL_SIGN);
        return list.toArray(new Block[0]);
    }
}
