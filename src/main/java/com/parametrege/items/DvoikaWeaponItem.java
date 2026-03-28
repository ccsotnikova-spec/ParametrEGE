package com.parametrege.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class DvoikaWeaponItem extends SwordItem {
    public DvoikaWeaponItem() {
        super(Tiers.DIAMOND, 20, -2.4F, new Item.Properties().rarity(Rarity.RARE));
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 4));
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3));
        target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
        target.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 600, 9));
        if (attacker instanceof Player p)
            p.displayClientMessage(Component.literal("DVOIKA!").withStyle(ChatFormatting.RED), true);
        return super.hurtEnemy(stack, target, attacker);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tt, TooltipFlag flag) {
        tt.add(Component.literal("\u00a7cDVOIKA - Applies debuffs"));
    }
}