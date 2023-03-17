package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import com.chikoritalover.terrarium.entity.BoomerangEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerrariumEntityType {
    public static final EntityType<BoomerangEntity> BOOMERANG = FabricEntityTypeBuilder.<BoomerangEntity>create(SpawnGroup.MISC, BoomerangEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).build();

    public static void register() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Terrarium.MODID, "boomerang"), BOOMERANG);
    }
}
