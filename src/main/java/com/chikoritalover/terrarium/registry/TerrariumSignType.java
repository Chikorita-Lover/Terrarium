package com.chikoritalover.terrarium.registry;

import net.minecraft.util.SignType;

public class TerrariumSignType extends SignType {
    public static final SignType EBONWOOD = SignType.register(new TerrariumSignType("ebonwood"));

    protected TerrariumSignType(String name) {
        super(name);
    }
}
