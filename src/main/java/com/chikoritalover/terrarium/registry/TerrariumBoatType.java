package com.chikoritalover.terrarium.registry;

import net.minecraft.entity.vehicle.BoatEntity;

public class TerrariumBoatType {
    static {
        BoatEntity.Type.values();
    }

    public static BoatEntity.Type EBONWOOD;
}
