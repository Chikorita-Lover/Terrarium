package com.chikoritalover.terrarium.mixin;

import com.chikoritalover.terrarium.registry.TerrariumBoatType;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public class BoatEntityMixin {
    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItem(CallbackInfoReturnable<Item> info) {
        BoatEntity boat = BoatEntity.class.cast(this);

        if (boat.getBoatType() == TerrariumBoatType.EBONWOOD) info.setReturnValue(TerrariumItems.EBONWOOD_BOAT);
    }
}
