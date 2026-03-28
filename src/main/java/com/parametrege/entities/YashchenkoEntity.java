package com.parametrege.entities;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.ChatFormatting;

public class YashchenkoEntity extends Monster {
    private final ServerBossEvent bossBar = new ServerBossEvent(
        Component.literal("YASHCHENKO I.V. - CREATOR OF EGE"), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_20);
    private int timer = 0, phase = 1;
    private static final String[] FRAZY = {"Task 18. Parameter.","Find all values of a...","EGE will be harder!","Variant 36!","Retake denied!","FIPI approves!","Min score: 39!"};
    public YashchenkoEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        setCustomName(Component.literal("Yashchenko I.V.")); setCustomNameVisible(true);
    }
    @Override protected void registerGoals() {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3D, false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 20.0F));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 1000).add(Attributes.ATTACK_DAMAGE, 30)
            .add(Attributes.MOVEMENT_SPEED, 0.38).add(Attributes.ARMOR, 30)
            .add(Attributes.ARMOR_TOUGHNESS, 15).add(Attributes.KNOCKBACK_RESISTANCE, 1)
            .add(Attributes.FOLLOW_RANGE, 80);
    }
    @Override public void tick() {
        super.tick(); timer++;
        bossBar.setProgress(getHealth() / getMaxHealth());
        float hp = getHealth() / getMaxHealth();
        if (hp < 0.2 && phase < 4) { phase = 4; getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60); getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.55); }
        else if (hp < 0.4 && phase < 3) { phase = 3; getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(45); }
        else if (hp < 0.7 && phase < 2) { phase = 2; getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(38); }
        if (timer % 120 == 0 && getTarget() instanceof Player p)
            p.displayClientMessage(Component.literal("Yashchenko: " + FRAZY[(int)(Math.random()*FRAZY.length)]), false);
        if (timer % 80 == 0 && getTarget() != null && !level().isClientSide) {
            AABB area = getBoundingBox().inflate(10 + phase * 5);
            for (Player p : level().getEntitiesOfClass(Player.class, area)) {
                p.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60 + phase * 20, phase - 1));
                if (phase >= 2) p.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, phase));
                if (phase >= 3) { p.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 1));
                    p.displayClientMessage(Component.literal("PARAMETR: For which a - 3 roots?"), false); }
                if (phase >= 4) { p.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
                    p.displayClientMessage(Component.literal("TASK 19: Prove n^2+n+1 not div by n-1"), false); }
                p.hurt(damageSources().magic(), 5.0F + phase * 8);
            }
            if (phase >= 3 && level() instanceof ServerLevel sl)
                for (int i = 0; i < phase * 3; i++)
                    sl.setBlock(blockPosition().offset((int)(Math.random()*10-5),(int)(Math.random()*3),(int)(Math.random()*10-5)), Blocks.COBWEB.defaultBlockState(), 3);
            if (phase >= 4 && level() instanceof ServerLevel sl)
                for (int i = 0; i < 10; i++)
                    sl.setBlock(blockPosition().offset((int)(Math.random()*16-8),10+(int)(Math.random()*5),(int)(Math.random()*16-8)), Blocks.LAVA.defaultBlockState(), 3);
        }
        if (level() instanceof ServerLevel sl && timer % 5 == 0) {
            sl.sendParticles(ParticleTypes.ENCHANT, getX(), getY()+1, getZ(), 10, 1, 2, 1, 0.5);
            if (phase >= 3) sl.sendParticles(ParticleTypes.DRAGON_BREATH, getX(), getY()+0.5, getZ(), 5, 1.5, 0.5, 1.5, 0.02);
            if (phase >= 4) sl.sendParticles(ParticleTypes.END_ROD, getX(), getY()+2, getZ(), 8, 2, 2, 2, 0.1);
        }
    }
    @Override public boolean hurt(DamageSource s, float a) {
        if (phase >= 4 && Math.random() < 0.3) heal(a * 0.5F);
        return super.hurt(s, a);
    }
    @Override public void startSeenByPlayer(ServerPlayer p) { super.startSeenByPlayer(p); bossBar.addPlayer(p); }
    @Override public void stopSeenByPlayer(ServerPlayer p) { super.stopSeenByPlayer(p); bossBar.removePlayer(p); }
    @Override public void die(DamageSource s) {
        if (s.getEntity() instanceof Player p) {
            p.displayClientMessage(Component.literal("YASHCHENKO DEFEATED! EGE CANCELLED!").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD), true);
            p.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 24000, 4));
            p.addEffect(new MobEffectInstance(MobEffects.LUCK, 24000, 9));
        }
        super.die(s);
    }
    @Override public boolean canChangeDimensions() { return false; }
}