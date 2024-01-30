package com.chikoritalover.terrarium.mixin;

import com.chikoritalover.terrarium.registry.TerrariumItems;
import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoatEntity.class)
public abstract class ChestBoatEntityMixin extends BoatEntity {
    public ChestBoatEntityMixin(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "asItem", at = @At("HEAD"), cancellable = true)
    public void asItem(CallbackInfoReturnable<Item> info) {
        if (this.getVariant() == ClassTinkerers.getEnum(Type.class, "EBONWOOD")) info.setReturnValue(TerrariumItems.EBONWOOD_CHEST_BOAT);
    }
}
