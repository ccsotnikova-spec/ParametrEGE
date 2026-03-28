package com.parametrege.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class ShpargalkaItem extends Item {
    private static final String[] FORMULAS = {"sin2+cos2=1","(a+b)2=a2+2ab+b2","D=b2-4ac","S=pi*r2","E=mc2"};
    public ShpargalkaItem() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(16));
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            String f = FORMULAS[(int)(Math.random()*FORMULAS.length)];
            player.displayClientMessage(Component.literal("SHPARGALKA: "+f).withStyle(ChatFormatting.YELLOW), false);
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 600, 2));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1));
            if (Math.random() < 0.1) {
                player.displayClientMessage(Component.literal("CAUGHT BY OLGA BORISOVNA!").withStyle(ChatFormatting.DARK_RED), true);
                player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 1200, 4));
            }
            player.getItemInHand(hand).shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tt, TooltipFlag flag) {
        tt.add(Component.literal("\u00a7eSHPARGALKA - 10% chance caught"));
    }
}