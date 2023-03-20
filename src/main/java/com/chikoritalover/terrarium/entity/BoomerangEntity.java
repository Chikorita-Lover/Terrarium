package com.chikoritalover.terrarium.entity;

import com.chikoritalover.terrarium.item.BoomerangItem;
import com.chikoritalover.terrarium.registry.TerrariumEntityType;
import com.chikoritalover.terrarium.registry.TerrariumItems;
import com.chikoritalover.terrarium.registry.TerrariumSoundEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class BoomerangEntity extends ProjectileEntity {
    private float damage;
    public int boomerangAge;
    public int preferredSlot;
    public PersistentProjectileEntity.PickupPermission pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
    private ItemStack boomerangStack = new ItemStack(TerrariumItems.BOOMERANG);
    private float baseVelocity = 1.3F;
    private int knockback;
    private boolean isCritical;

    public BoomerangEntity(EntityType<? extends BoomerangEntity> entityType, World world) {
        super(entityType, world);
        this.damage = (((BoomerangItem) this.boomerangStack.getItem()).attackDamage);
    }

    public BoomerangEntity(World world, LivingEntity owner, ItemStack stack) {
        super(TerrariumEntityType.BOOMERANG, world);
        this.boomerangStack = stack.copy();
        this.baseVelocity = ((BoomerangItem) this.boomerangStack.getItem()).baseVelocity;

        this.setOwner(owner);
        if (owner instanceof PlayerEntity) {
            this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        }

        EntityAttributeInstance attributeInstance = owner.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        attributeInstance.removeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID);
        attributeInstance.addTemporaryModifier(new EntityAttributeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", ((BoomerangItem) this.boomerangStack.getItem()).attackDamage - 1.0, EntityAttributeModifier.Operation.ADDITION));
        this.damage = (float) (owner.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
        attributeInstance.removeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID);

        if (owner.isSprinting()) {
            this.knockback = 1;
            owner.setSprinting(false);
        } else {
            this.isCritical = owner.fallDistance > 0.0F && !owner.isClimbing() && !owner.isTouchingWater() && !owner.hasStatusEffect(StatusEffects.BLINDNESS) && !owner.hasVehicle();
        }
        this.setPos(owner.getX(), owner.getBodyY(0.75), owner.getZ());
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void tick() {
        ++this.boomerangAge;
        Entity owner = this.getOwner();

        if (this.boomerangAge % 4 == 0)
            this.playSound(TerrariumSoundEvents.ENTITY_BOOMERANG_THROW, 0.1F, MathHelper.lerp(this.random.nextFloat(), 1.3F, 1.7F));

        if (this.isOwnerAlive()) {
            if (this.isReturning()) {
                this.isCritical = false;
                this.knockback = 0;
                this.setVelocity(owner.getPos().add(0.0, owner.getHeight() * 0.5, 0.0).subtract(this.getPos()).normalize().multiply(this.getModifiedVelocity()));
                this.velocityDirty = true;
            }
        } else {
            this.setOwner(null);
        }

        if (!this.isOwnerAlive() || !this.isReturning()) {
            this.setVelocity(this.getVelocity().normalize().multiply(this.getModifiedVelocity()));
        }

        Vec3d vec3d2;
        super.tick();
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = vec3d.horizontalLength();
            this.setYaw((float) (MathHelper.atan2(vec3d.getX(), vec3d.getZ()) * MathHelper.DEGREES_PER_RADIAN));
            this.setPitch((float) (MathHelper.atan2(vec3d.getY(), d) * MathHelper.DEGREES_PER_RADIAN));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        Vec3d vec3d3 = this.getPos();
        HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vec3d2 = vec3d3.add(vec3d), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3d2 = hitResult.getPos();
        }

        EntityHitResult entityHitResult = ProjectileUtil.getEntityCollision(this.world, this, vec3d3, vec3d2, this.getBoundingBox().stretch(vec3d).expand(1.0), this::canHit);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
        }
        if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            owner = this.getOwner();
            if (entity instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).shouldDamagePlayer((PlayerEntity) entity)) {
                hitResult = null;
            }
        }
        if (hitResult != null && !this.isReturning()) {
            this.onCollision(hitResult);
        }

        double e = this.getVelocity().x;
        double f = this.getVelocity().y;
        double g = this.getVelocity().z;
        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        double l = this.getVelocity().horizontalLength();
        if (!this.isReturning()) {
            this.setYaw((float) (MathHelper.atan2(e, g) * MathHelper.DEGREES_PER_RADIAN));
            this.setPitch((float) (MathHelper.atan2(f, l) * MathHelper.DEGREES_PER_RADIAN));
        } else {
            this.setYaw((float) (MathHelper.atan2(-e, -g) * MathHelper.DEGREES_PER_RADIAN));
            this.setPitch((float) (MathHelper.atan2(-f, -l) * MathHelper.DEGREES_PER_RADIAN));
        }
        if (this.isTouchingWater()) {
            for (int o = 0; o < 2; ++o) {
                this.world.addParticle(ParticleTypes.BUBBLE, h + this.random.nextFloat() * 0.5 - 0.25, j + this.random.nextFloat() * 0.5 - 0.25, k + this.random.nextFloat() * 0.5 - 0.25, e, f, g);
            }
        }
        this.setPosition(h, j, k);
        this.checkBlockCollision();
    }

    public float getModifiedVelocity() {
        float f = 1.0F;
        if (this.isOwnerAlive())
            f *= MathHelper.clamp(Math.abs(this.boomerangAge - 15) / 15.0F, 1.0E-3F, 1.0F);
        if (this.isTouchingWater()) f *= 0.6F;
        return this.baseVelocity * f;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.isOwner(entityHitResult.getEntity())) return;

        Entity entity = entityHitResult.getEntity();
        this.isCritical = this.isCritical && entity instanceof LivingEntity;
        Entity entity2 = this.getOwner();
        float damage = this.damage;
        if (this.isCritical) damage *= 1.5F;
        float targetHealth = 0.0F;
        Vec3d vec3d = this.getPos();
        DamageSource damageSource = DamageSource.thrownProjectile(this, entity2 == null ? this : entity2);
        SoundEvent soundEvent = TerrariumSoundEvents.ENTITY_BOOMERANG_HIT;

        /* if (entity2 != null && this.isReturning()) {
            vec3d = entity2.getPos();
            entity2.setPosition(vec3d.add(vec3d.subtract(this.getPos())));
        } */

        if (entity instanceof LivingEntity livingEntity) {
            targetHealth = livingEntity.getHealth();
        }

        if (damage > 0.0F && entity.damage(damageSource, damage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            soundEvent = SoundEvents.ENTITY_PLAYER_ATTACK_STRONG;
            if (this.knockback > 0) {
                soundEvent = SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK;
            } else if (this.isCritical) {
                soundEvent = SoundEvents.ENTITY_PLAYER_ATTACK_CRIT;
            }

            int punch = this.knockback;
            if (punch > 0) {
                float g = (float) Math.atan2(-this.getVelocity().getX(), this.getVelocity().getZ());

                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).takeKnockback(punch * 0.5F, MathHelper.sin(g), -MathHelper.cos(g));
                } else {
                    entity.addVelocity(-MathHelper.sin(g) * (float) punch * 0.5f, 0.1, MathHelper.cos(g) * (float) punch * 0.5f);
                }
            }

            if (this.isCritical) {
                ((ServerWorld) this.world).getChunkManager().sendToNearbyPlayers(this, new EntityAnimationS2CPacket(entity, EntityAnimationS2CPacket.CRIT));
            }

            if (entity instanceof LivingEntity livingEntity) {
                if (entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
                }

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
        } else if (this.isReturning()) {
            soundEvent = null;
        }
        if (this.isOwnerAlive()) {
            this.returnToOwner();
        } else {
            this.dropAsItem();
        }
        // if (entity2 != null && this.isReturning()) entity2.setPosition(vec3d);
        if (soundEvent != null) this.playSound(soundEvent, 1.0F, 1.0F);
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
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        Vec3d vec3d2 = vec3d.normalize().multiply(0.05F);
        this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
        this.playSound(TerrariumSoundEvents.ENTITY_BOOMERANG_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    public void returnToOwner() {
        this.boomerangAge = 30;
        this.isCritical = false;
        this.knockback = 0;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.world.isClient && this.isOwner(player) && this.isReturning() && this.tryPickup(player)) {
            ((ServerWorld) this.world).getChunkManager().sendToOtherNearbyPlayers(this, new ItemPickupAnimationS2CPacket(this.getId(), player.getId(), 1));
            player.getItemCooldownManager().set(TerrariumItems.BOOMERANG, 4);
            this.discard();
        }
    }

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
        if (!this.world.isClient && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
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
        this.damage = nbt.getFloat("damage");
        this.knockback = nbt.getInt("knockback");
        this.isCritical = nbt.getBoolean("crit");
        this.pickupType = PersistentProjectileEntity.PickupPermission.fromOrdinal(nbt.getByte("pickup"));
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("BaseVelocity", this.baseVelocity);
        nbt.put("Boomerang", this.boomerangStack.writeNbt(new NbtCompound()));
        nbt.putInt("BoomerangAge", this.boomerangAge);
        nbt.putInt("PreferredSlot", this.preferredSlot);
        nbt.putFloat("damage", this.damage);
        nbt.putInt("knockback", this.knockback);
        nbt.putBoolean("crit", this.isCritical);
        nbt.putByte("pickup", (byte) this.pickupType.ordinal());
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

    public ItemStack asItemStack() {
        return this.boomerangStack.copy();
    }

    @Override
    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }
}
