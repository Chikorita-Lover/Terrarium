package com.chikoritalover.terrarium.mixin;

import com.chikoritalover.terrarium.registry.TerrariumBoatType;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public class ChestBoatEntityMixin {
    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItem(CallbackInfoReturnable<Item> info) {
        ChestBoatEntity chestBoat = ChestBoatEntity.class.cast(this);

        if (chestBoat.getBoatType() == TerrariumBoatType.EBONWOOD) info.setReturnValue(TerrariumItems.EBONWOOD_CHEST_BOAT);
    }
}
