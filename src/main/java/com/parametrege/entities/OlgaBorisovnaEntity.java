package com.parametrege.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.ChatFormatting;

public class OlgaBorisovnaEntity extends Monster {
    private final ServerBossEvent bossBar = new ServerBossEvent(
        Component.literal("OLGA BORISOVNA"), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10);
    private int timer = 0, phase = 1;
    private static final String[] FRAZY = {"DNEVNIK NA STOL!","RODITELEY V SHKOLU!","DVOIKA!","ZVONOK DLYA UCHITELYA!","PARAMETR NA DOSKU!","POSLE UROKOV OSTAYUTSYA!"};
    public OlgaBorisovnaEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        setCustomName(Component.literal("Olga Borisovna")); setCustomNameVisible(true);
    }
    @Override protected void registerGoals() {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 16.0F));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 500).add(Attributes.ATTACK_DAMAGE, 25)
            .add(Attributes.MOVEMENT_SPEED, 0.35).add(Attributes.ARMOR, 20)
            .add(Attributes.ARMOR_TOUGHNESS, 10).add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
            .add(Attributes.FOLLOW_RANGE, 50);
    }
    @Override public void tick() {
        super.tick(); timer++;
        bossBar.setProgress(getHealth() / getMaxHealth());
        float hp = getHealth() / getMaxHealth();
        if (hp < 0.3 && phase < 3) { phase = 3; getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50); getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5); }
        else if (hp < 0.6 && phase < 2) { phase = 2; getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(35); }
        if (timer % 100 == 0 && getTarget() instanceof Player p)
            p.displayClientMessage(Component.literal("Olga Borisovna: " + FRAZY[(int)(Math.random()*FRAZY.length)]).withStyle(ChatFormatting.RED), false);
        if (timer % 60 == 0 && getTarget() != null && !level().isClientSide) {
            LivingEntity t = getTarget();
            t.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
            t.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            if (phase >= 2) t.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
            if (phase >= 3) t.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
            t.hurt(damageSources().magic(), 10.0F);
        }
        if (level() instanceof ServerLevel sl && timer % 3 == 0) {
            sl.sendParticles(ParticleTypes.ANGRY_VILLAGER, getX(), getY()+2, getZ(), 3, 0.5, 0.5, 0.5, 0);
            if (phase >= 2) sl.sendParticles(ParticleTypes.FLAME, getX(), getY()+1, getZ(), 5, 0.8, 1, 0.8, 0.05);
        }
    }
    @Override public void startSeenByPlayer(ServerPlayer p) { super.startSeenByPlayer(p); bossBar.addPlayer(p); }
    @Override public void stopSeenByPlayer(ServerPlayer p) { super.stopSeenByPlayer(p); bossBar.removePlayer(p); }
    @Override public void die(DamageSource s) {
        if (s.getEntity() instanceof Player p) {
            p.displayClientMessage(Component.literal("OLGA BORISOVNA DEFEATED! HOLIDAYS!").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD), true);
            p.addEffect(new MobEffectInstance(MobEffects.LUCK, 12000, 4));
        }
        super.die(s);
    }
    @Override public boolean canChangeDimensions() { return false; }
}