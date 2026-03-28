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

public class MorgenshternEntity extends PathfinderMob {
    private int songTimer = 0, currentSong = 0;
    private static final String[][] SONGS = {
        {"Cadillac Cadillac v moey golove","Tebe nravitsya Bentley","No Cadillac luchshe","Sadis v moy Cadillac"},
        {"Ledentsy ty moi ledentsy","Sladkaya kak konfeta","Ty moy Lollipop detka","Ya kuplyu tebe magazin"},
        {"Ya kupil tebe rozy","Krasnye rozy belye rozy","Ty krasivaya kak rozy","Roses are red"},
        {"RATATATATA","Ya kak avtomat RATATATATA","Dengi kak iz pulemyota","RA-TA-TA-TA-TA!"},
        {"ICE na mne ICE","Brillianty ICE","Bling-bling moy stil","ICE ICE BABY"}
    };
    public MorgenshternEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        setCustomName(Component.literal("MORGENSHTERN")); setCustomNameVisible(true);
    }
    @Override protected void registerGoals() {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 12.0F));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 200).add(Attributes.MOVEMENT_SPEED, 0.35).add(Attributes.ARMOR, 10);
    }
    @Override public void tick() {
        super.tick(); songTimer++;
        if (songTimer % 80 == 0 && !level().isClientSide) {
            String[] song = SONGS[currentSong % SONGS.length];
            int line = (songTimer / 80) % song.length;
            for (Player p : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(20))) {
                p.displayClientMessage(Component.literal("Morgenshtern: " + song[line]), false);
                p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1));
                p.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0));
            }
            if (line == song.length - 1) currentSong++;
        }
        if (level() instanceof ServerLevel sl && songTimer % 10 == 0)
            sl.sendParticles(ParticleTypes.NOTE, getX(), getY()+2.2, getZ(), 3, 0.5, 0.3, 0.5, 0);
    }
    @Override protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide) {
            currentSong = (currentSong + 1) % SONGS.length;
            String[] names = {"Cadillac","Ledentsy","Rozy","Ratatatata","ICE"};
            player.displayClientMessage(Component.literal("Now playing: " + names[currentSong % names.length]), true);
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }
    @Override public boolean removeWhenFarAway(double d) { return false; }
}