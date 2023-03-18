package com.chikoritalover.terrarium.mixin;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Inject(method = "getStrippedState", at = @At("HEAD"), cancellable = true)
    public void getStrippedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> info) {
        if (TerrariumBlocks.STRIPPED_BLOCKS.containsKey(state.getBlock())) {
            info.setReturnValue(Optional.ofNullable(TerrariumBlocks.STRIPPED_BLOCKS.get(state.getBlock())).map(block -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS))));
        }
    }
}
