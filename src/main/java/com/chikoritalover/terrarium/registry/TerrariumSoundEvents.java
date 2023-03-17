package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerrariumSoundEvents {
    public static final SoundEvent ENTITY_BOOMERANG_HIT = register("entity.boomerang.hit");
    public static final SoundEvent ENTITY_BOOMERANG_THROW = register("entity.boomerang.throw");

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, new Identifier(Terrarium.MODID, id), new SoundEvent(new Identifier(Terrarium.MODID, id)));
    }

    public static void register() {}
}
