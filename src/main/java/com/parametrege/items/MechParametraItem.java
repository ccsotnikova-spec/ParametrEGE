package com.parametrege.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class MechParametraItem extends SwordItem {
    public MechParametraItem() {
        super(Tiers.NETHERITE, 9999, -2.0F,
            new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurt(target.damageSources().magic(), Float.MAX_VALUE);
        if (target.level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                target.getX(), target.getY()+1, target.getZ(), 10, 2, 2, 2, 0.1);
            sl.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                target.getX(), target.getY(), target.getZ(), 50, 3, 3, 3, 0.5);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            AABB area = player.getBoundingBox().inflate(30.0);
            List<LivingEntity> ents = level.getEntitiesOfClass(LivingEntity.class, area, e -> e != player);
            for (LivingEntity e : ents) e.hurt(e.damageSources().magic(), Float.MAX_VALUE);
            player.displayClientMessage(Component.literal("PARAMETR EGE! Damage: 10900^89992")
                .withStyle(ChatFormatting.RED, ChatFormatting.BOLD), true);
            if (level instanceof ServerLevel sl)
                sl.sendParticles(ParticleTypes.DRAGON_BREATH, player.getX(), player.getY()+1, player.getZ(), 200, 15, 5, 15, 0.5);
        }
        player.getCooldowns().addCooldown(this, 100);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    @Override public boolean isFoil(ItemStack stack) { return true; }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tt, TooltipFlag flag) {
        tt.add(Component.literal("\u00a7c\u00a7l LEGENDARY SWORD OF PARAMETR EGE"));
        tt.add(Component.literal("\u00a76Damage: \u00a7c10900^89992"));
        tt.add(Component.literal("\u00a7eRMB: Area attack 30 blocks"));
    }
}