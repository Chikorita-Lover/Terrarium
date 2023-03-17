package com.chikoritalover.terrarium.entity;

import com.chikoritalover.terrarium.item.BoomerangItem;
import com.chikoritalover.terrarium.registry.TerrariumEntityType;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import com.chikoritalover.terrarium.registry.TerrariumSoundEvents;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BoomerangEntity extends PersistentProjectileEntity {
    private ItemStack boomerangStack = new ItemStack(TerrariumItems.BOOMERANG);
    public int boomerangAge;
    private float baseVelocity = 1.3F;
    public int preferredSlot;

    public BoomerangEntity(EntityType<? extends BoomerangEntity> entityType, World world) {
        super(entityType, world);
        this.setDamage(((BoomerangItem) this.boomerangStack.getItem()).attackDamage);
    }

    public BoomerangEntity(World world, LivingEntity owner, ItemStack stack) {
        super(TerrariumEntityType.BOOMERANG, owner, world);
        this.boomerangStack = stack.copy();
        this.baseVelocity = ((BoomerangItem) this.boomerangStack.getItem()).baseVelocity;

        EntityAttributeInstance attributeInstance = owner.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        attributeInstance.removeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID);
        attributeInstance.addTemporaryModifier(new EntityAttributeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ((BoomerangItem) this.boomerangStack.getItem()).attackDamage - 1.0, EntityAttributeModifier.Operation.ADDITION));
        this.setDamage(owner.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        attributeInstance.removeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID);

        if (owner.isSprinting()) {
            this.setPunch(1);
            owner.setSprinting(false);
        } else {
            this.setCritical(owner.fallDistance > 0.0f && !owner.isClimbing() && !owner.isTouchingWater());
        }
        this.setPos(this.getX(), owner.getBodyY(0.5), this.getZ());
    }

    @Override
    public void tick() {
        ++this.boomerangAge;
        Entity entity = this.getOwner();

        if (this.boomerangAge % 4 == 0) this.playSound(TerrariumSoundEvents.ENTITY_BOOMERANG_THROW, 0.1F, MathHelper.lerp(this.random.nextFloat(), 1.3F, 1.7F));

        if (this.isOwnerAlive()) {
            if (this.isReturning()) {
                this.setNoClip(true);
                this.setVelocity(entity.getPos().add(0.0, entity.getHeight() * 0.5, 0.0).subtract(this.getPos()).normalize().multiply(this.getModifiedVelocity()));
            }
        } else {
            this.setOwner(null);
        }

        if (!this.isOwnerAlive() || !this.isReturning())
            this.setVelocity(this.getVelocity().normalize().multiply(this.getModifiedVelocity()));

        super.tick();
    }

    public float getModifiedVelocity() {
        float f = 1.0F;
        if (this.isOwnerAlive()) f *= MathHelper.clamp(Math.abs(this.boomerangAge - 15) / 15.0F, MathHelper.EPSILON, 1.0F);
        if (this.isTouchingWater()) f *= this.getDragInWater();
        return this.baseVelocity * f;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.isOwner(entityHitResult.getEntity())) return;

        Entity entity = entityHitResult.getEntity();
        this.setCritical(this.isCritical() && entity instanceof LivingEntity);
        Entity entity2 = this.getOwner();
        float f = (float) this.getDamage();
        if (this.isCritical()) f *= 1.5F;
        float targetHealth = 0.0F;
        DamageSource damageSource = DamageSource.thrownProjectile(this, entity2 == null ? this : entity2);
        SoundEvent soundEvent = getHitSound();

        if (entity instanceof LivingEntity livingEntity) {
            targetHealth = livingEntity.getHealth();
        }

        if (f > 0.0F) {
            soundEvent = SoundEvents.ENTITY_PLAYER_ATTACK_STRONG;
            if (this.getPunch() > 0) {
                soundEvent = SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK;
            } else if (this.isCritical()) {
                soundEvent = SoundEvents.ENTITY_PLAYER_ATTACK_CRIT;
            }

            if (entity.damage(damageSource, f)) {
                if (entity.getType() == EntityType.ENDERMAN) {
                    return;
                }

                int i = this.getPunch();
                if (i > 0) {
                    float g = (float) Math.atan2(-this.getVelocity().getX(), this.getVelocity().getZ());

                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).takeKnockback(i * 0.5F, MathHelper.sin(g), -MathHelper.cos(g));
                    } else {
                        entity.addVelocity(-MathHelper.sin(g) * (float) i * 0.5f, 0.1, MathHelper.cos(g) * (float) i * 0.5f);
                    }
                }

                if (entity instanceof LivingEntity livingEntity) {
                    if (entity2 instanceof LivingEntity) {
                        EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                        EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
                    }

                    this.onHit(livingEntity);

                    if (this.getOwner() instanceof PlayerEntity) {
                        float g = targetHealth - livingEntity.getHealth();
                        if (g > 2.0F) {
                            int j = (int) (g * 0.5);
                            ((ServerWorld) this.world).spawnParticles(ParticleTypes.DAMAGE_INDICATOR, entity.getX(), entity.getBodyY(0.5), entity.getZ(), j, 0.1, 0.0, 0.1, 0.2);
                        }
                    }
                }

                if (this.boomerangStack.damage(1, this.random, entity2 instanceof ServerPlayerEntity serverPlayer ? serverPlayer : null)) {
                    this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                    if (entity2 instanceof PlayerEntity player) {
                        player.incrementStat(Stats.BROKEN.getOrCreateStat(this.boomerangStack.getItem()));
                    }
                    this.discard();
                }
            }
        }
        if (this.isOwnerAlive()) {
            this.returnToOwner();
        } else {
            this.dropAsItem();
        }
        this.playSound(soundEvent, 1.0F, 1.0F);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        System.out.println(this.world.getBlockState(blockHitResult.getBlockPos()).getBlock().getTranslationKey());
        if (this.isOwnerAlive() && !this.isReturning()) {
            this.returnToOwner();
        } else {
            this.dropAsItem();
        }
        super.onBlockHit(blockHitResult);
    }

    public void returnToOwner() {
        this.boomerangAge = 30;
        this.setCritical(false);
        this.setPunch(0);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.world.isClient && this.isOwner(player) && this.isReturning() && this.tryPickup(player)) {
            player.sendPickup(this, 1);
            player.getItemCooldownManager().set(TerrariumItems.BOOMERANG, 4);
            this.discard();
        }
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        if (!this.isReturning() || !this.isOwner(player)) return false;
        switch (this.pickupType) {
            case ALLOWED -> {
                if (preferredSlot == PlayerInventory.OFF_HAND_SLOT) {
                    if (player.getOffHandStack().isEmpty()) {
                        player.setStackInHand(Hand.OFF_HAND, this.asItemStack());
                        return true;
                    } else {
                        return player.getInventory().insertStack(this.asItemStack());
                    }
                } else {
                    return player.getInventory().getStack(preferredSlot).isEmpty() && player.getInventory().insertStack(preferredSlot, this.asItemStack()) || player.getInventory().insertStack(this.asItemStack());
                }
            }
            case CREATIVE_ONLY -> {
                return player.getAbilities().creativeMode;
            }
        }
        return false;
    }

    protected void dropAsItem() {
        if (!this.world.isClient && this.pickupType == PickupPermission.ALLOWED) {
            this.dropStack(this.asItemStack(), 0.1f);
        }
        this.discard();
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.baseVelocity = nbt.getFloat("BaseVelocity");
        if (nbt.contains("Boomerang", 10)) {
            this.boomerangStack = ItemStack.fromNbt(nbt.getCompound("Boomerang"));
        }
        this.boomerangAge = nbt.getInt("BoomerangAge");
        this.preferredSlot = nbt.getInt("PreferredSlot");
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("BaseVelocity", this.baseVelocity);
        nbt.put("Boomerang", this.boomerangStack.writeNbt(new NbtCompound()));
        nbt.putInt("BoomerangAge", this.boomerangAge);
        nbt.putInt("PreferredSlot", this.preferredSlot);
    }

    @Override
    public SoundEvent getHitSound() {
        return TerrariumSoundEvents.ENTITY_BOOMERANG_HIT;
    }

    public float getRotation(float tickDelta) {
        return (this.boomerangAge + tickDelta) * 45.0F;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    public boolean isReturning() {
        return this.boomerangAge >= 15 && this.isOwnerAlive();
    }

    @Override
    public ItemStack asItemStack() {
        return this.boomerangStack.copy();
    }
}
