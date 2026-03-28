package com.parametrege.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FaceRapperEntity extends PathfinderMob {
    private int rapTimer = 0;
    private static final String[][] BARS = {
        {"Ya ustal ya ustal","Mne po kayfu","Gou gou gou!","Bro my letim vysoko!"},
        {"Moy gorod zasypaet","Ya na studii","Face v dele","Underground navsegda!"},
        {"Burger ya yem burger","Mne vsyo ravno","Ya Face ya chitayu rep","Mne ne nuzhen respekt"}
    };
    public FaceRapperEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setCustomName(Component.literal("FACE")); setCustomNameVisible(true);
    }
    @Override protected void registerGoals() {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 10.0F));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 150).add(Attributes.MOVEMENT_SPEED, 0.33).add(Attributes.ARMOR, 5);
    }
    @Override public void tick() {
        super.tick(); rapTimer++;
        if (rapTimer % 100 == 0 && !level().isClientSide) {
            int si = (rapTimer / 400) % BARS.length;
            int li = (rapTimer / 100) % BARS[si].length;
            for (Player p : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(15))) {
                p.displayClientMessage(Component.literal("Face: " + BARS[si][li]), false);
                p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 80, 0));
            }
        }
        if (level() instanceof ServerLevel sl && rapTimer % 15 == 0)
            sl.sendParticles(ParticleTypes.NOTE, getX(), getY()+2.5, getZ(), 1, 0.5, 0.2, 0.5, 0);
    }
    @Override protected InteractionResult mobInteract(Player p, InteractionHand h) {
        if (!level().isClientSide) {
            p.displayClientMessage(Component.literal("Face: Yo bro, listen."), false);
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 2));
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }
    @Override public boolean removeWhenFarAway(double d) { return false; }
}