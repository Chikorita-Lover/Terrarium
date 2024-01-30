package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TerrariumItemTags {
    public static final TagKey<Item> EBONWOOD_LOGS = TagKey.of(RegistryKeys.ITEM, new Identifier(Terrarium.MODID, "ebonwood_logs"));
}
