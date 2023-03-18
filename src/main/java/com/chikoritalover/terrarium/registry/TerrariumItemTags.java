package com.chikoritalover.terrarium.registry;

import com.chikoritalover.terrarium.Terrarium;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerrariumItemTags {
    public static final TagKey<Item> EBONWOOD_LOGS = TagKey.of(Registry.ITEM_KEY, new Identifier(Terrarium.MODID, "ebonwood_logs"));
}
