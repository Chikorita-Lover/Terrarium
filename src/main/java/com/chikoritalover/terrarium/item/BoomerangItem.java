package com.chikoritalover.terrarium.item;

import com.chikoritalover.terrarium.entity.BoomerangEntity;
import com.chikoritalover.terrarium.registry.TerrariumEntityType;
import com.chikoritalover.terrarium.registry.TerrariumSoundEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BoomerangItem extends Item {
    public final boolean isEnchanted;
    public final float attackDamage;
    public final float baseVelocity;

    public BoomerangItem(boolean isEnchanted, float attackDamage, float baseVelocity, Settings settings) {
        super(settings);
        this.isEnchanted = isEnchanted;
        this.attackDamage = attackDamage;
        this.baseVelocity = baseVelocity;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 4);
        if (!world.isClient) {
            BoomerangEntity boomerang = new BoomerangEntity(world, user, itemStack);
            boomerang.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 1.0F);
            boomerang.preferredSlot = hand == Hand.OFF_HAND ? PlayerInventory.OFF_HAND_SLOT : user.getInventory().selectedSlot;
            if (user.getAbilities().creativeMode) {
                boomerang.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            user.addExhaustion(0.1F);
            world.spawnEntity(boomerang);
            world.playSoundFromEntity(null, boomerang, TerrariumSoundEvents.ENTITY_BOOMERANG_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            user.getInventory().removeOne(itemStack);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BoomerangItem boomerangItem = (BoomerangItem) stack.getItem();
        float f = boomerangItem.attackDamage;

        if (f == 0) return;

        Object[] damageTooltip = new Object[]{ItemStack.MODIFIER_FORMAT.format(f), Text.translatable("attribute.name.generic.attack_damage")};

        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("item.modifiers.thrown").formatted(Formatting.GRAY));
        tooltip.add(Text.literal(" ").append(Text.translatable("attribute.modifier.equals.0", damageTooltip).formatted(Formatting.DARK_GREEN)));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getItem() instanceof BoomerangItem boomerangItem && boomerangItem.isEnchanted || super.hasGlint(stack);
    }
}
