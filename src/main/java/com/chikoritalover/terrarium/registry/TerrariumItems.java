package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import com.chikoritalover.terrarium.item.BoomerangItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerrariumItems {
    public static final Item BOOMERANG = new BoomerangItem(false, 5.0F, 1.3F, new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(131));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(Terrarium.MODID, "boomerang"), BOOMERANG);
    }
}
