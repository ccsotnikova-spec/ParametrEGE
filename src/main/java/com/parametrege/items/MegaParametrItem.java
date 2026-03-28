package com.parametrege.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class MegaParametrItem extends Item {
    public MegaParametrItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            player.displayClientMessage(Component.literal("MEGA PARAMETR! WORLD DESTRUCTION!")
                .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD), true);
            BlockPos center = player.blockPosition();
            for (int x = -50; x <= 50; x++)
                for (int y = -25; y <= 25; y++)
                    for (int z = -50; z <= 50; z++) {
                        double dist = Math.sqrt(x*x+y*y+z*z);
                        if (dist <= 50 && Math.random() < 0.3) {
                            BlockPos pos = center.offset(x,y,z);
                            if (!level.getBlockState(pos).isAir())
                                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
            if (level instanceof ServerLevel sl)
                sl.sendParticles(ParticleTypes.EXPLOSION_EMITTER, player.getX(), player.getY(), player.getZ(), 50, 30, 20, 30, 1);
            level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(100), e -> e != player)
                .forEach(e -> e.hurt(e.damageSources().magic(), Float.MAX_VALUE));
        }
        player.getCooldowns().addCooldown(this, 600);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }
    @Override public boolean isFoil(ItemStack stack) { return true; }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tt, TooltipFlag flag) {
        tt.add(Component.literal("\u00a74MEGA PARAMETR - DESTROYS EVERYTHING"));
    }
}