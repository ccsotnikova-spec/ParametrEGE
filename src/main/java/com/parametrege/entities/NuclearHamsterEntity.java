package com.parametrege.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class NuclearHamsterEntity extends Monster {
    private int glowTick = 0;
    private boolean aboutToExplode = false;
    public NuclearHamsterEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setCustomName(Component.literal("Nuclear Hamster"));
        this.setCustomNameVisible(true);
    }
    @Override protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 100).add(Attributes.ATTACK_DAMAGE, 15)
            .add(Attributes.MOVEMENT_SPEED, 0.4).add(Attributes.ARMOR, 8);
    }
    @Override public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel sl && tickCount % 5 == 0)
            sl.sendParticles(ParticleTypes.HAPPY_VILLAGER, getX(), getY()+0.5, getZ(), 3, 0.3, 0.3, 0.3, 0);
        if (getHealth() < getMaxHealth()*0.3 && !aboutToExplode) { aboutToExplode = true; glowTick = 60; }
        if (aboutToExplode) {
            glowTick--;
            if (this.level() instanceof ServerLevel sl)
                sl.sendParticles(ParticleTypes.FLAME, getX(), getY()+0.5, getZ(), 10, 0.5, 0.5, 0.5, 0.1);
            if (glowTick <= 0) boom();
        }
    }
    private void boom() {
        if (!level().isClientSide) {
            level().explode(this, getX(), getY(), getZ(), 12.0F, Level.ExplosionInteraction.MOB);
            AABB a = getBoundingBox().inflate(20);
            for (LivingEntity e : level().getEntitiesOfClass(LivingEntity.class, a))
                e.hurt(damageSources().explosion(null, this), 50.0F);
            if (level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 30, 10, 10, 10, 1);
                for (int i = 0; i < 10; i++)
                    sl.sendParticles(ParticleTypes.LARGE_SMOKE, getX(), getY()+i*3, getZ(), 50, 3, 1, 3, 0.1);
            }
            discard();
        }
    }
    @Override public void die(DamageSource s) { boom(); super.die(s); }
}