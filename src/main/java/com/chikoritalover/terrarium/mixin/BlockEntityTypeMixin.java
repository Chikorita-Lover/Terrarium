package com.chikoritalover.terrarium.mixin;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType$Builder;create(Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType$Builder;"))
    private static void create(Args args) {
        List<Block> blocks = new ArrayList<>(Arrays.stream(((Block[]) args.get(1))).toList());
        blocks.add(TerrariumBlocks.EBONWOOD_SIGN);
        blocks.add(TerrariumBlocks.EBONWOOD_WALL_SIGN);
        Block[] blocks2 = new Block[blocks.size()];
        for (int i = 0; i < blocks.size(); ++i) {
            blocks2[i] = blocks.get(0);
        }
        args.set(1, blocks2);
    }
}
