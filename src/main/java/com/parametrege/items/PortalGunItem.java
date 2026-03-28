package com.parametrege.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class PortalGunItem extends Item {
    public PortalGunItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            HitResult hit = player.pick(100, 0, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult bhr = (BlockHitResult) hit;
                BlockPos pos = bhr.getBlockPos();
                for (int y = 0; y < 5; y++) {
                    for (int x = -1; x <= 1; x++) {
                        BlockPos p = pos.offset(x, y, 0);
                        if (x == -1 || x == 1 || y == 0 || y == 4)
                            level.setBlock(p, Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
                        else
                            level.setBlock(p, Blocks.NETHER_PORTAL.defaultBlockState(), 3);
                    }
                }
                player.displayClientMessage(Component.literal("PORTAL CREATED!").withStyle(ChatFormatting.LIGHT_PURPLE), true);
            } else {
                double lx = player.getLookAngle().x, ly = player.getLookAngle().y, lz = player.getLookAngle().z;
                sp.teleportTo(player.getX()+lx*50, Math.max(player.getY()+ly*50, -60), player.getZ()+lz*50);
                player.displayClientMessage(Component.literal("TELEPORTED!").withStyle(ChatFormatting.AQUA), true);
            }
        }
        player.getCooldowns().addCooldown(this, 60);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    @Override public boolean isFoil(ItemStack stack) { return true; }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tt, TooltipFlag flag) {
        tt.add(Component.literal("\u00a7dPORTAL GUN"));
    }
}