package com.chikoritalover.terrarium.mixin;

import com.chikoritalover.terrarium.registry.TerrariumBlocks;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow public abstract BoatEntity.Type getVariant();

    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItem(CallbackInfoReturnable<Item> info) {
        if (this.getVariant() == ClassTinkerers.getEnum(BoatEntity.Type.class, "EBONWOOD")) info.setReturnValue(TerrariumItems.EBONWOOD_BOAT);
    }

    @Mixin(BoatEntity.Type.class)
    private static abstract class TypeMixin {
        @Shadow public abstract String getName();

        @Inject(method = "getBaseBlock", at = @At("RETURN"), cancellable = true)
        public void getBaseBlock(CallbackInfoReturnable<Block> cir) {
            if (Objects.equals(this.getName(), "ebonwood")) {
                cir.setReturnValue(TerrariumBlocks.EBONWOOD_PLANKS);
            }
        }
    }
}
