package com.parametrege.items;

import com.parametrege.ParametrEgeMod;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.EnumMap;

public class EgeArmorItem extends ArmorItem {
    private static final EnumMap<ArmorItem.Type, Integer> DEF =
        Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(Type.HELMET, 5); m.put(Type.CHESTPLATE, 9);
            m.put(Type.LEGGINGS, 7); m.put(Type.BOOTS, 4);
        });
    private static final ArmorMaterial MAT = new ArmorMaterial() {
        public int getDurabilityForType(ArmorItem.Type t) { return 5000; }
        public int getDefenseForType(ArmorItem.Type t) { return DEF.getOrDefault(t, 0); }
        public int getEnchantmentValue() { return 30; }
        public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_NETHERITE; }
        public Ingredient getRepairIngredient() { return Ingredient.of(Items.DIAMOND); }
        public String getName() { return ParametrEgeMod.MOD_ID + ":ege_armor"; }
        public float getToughness() { return 5.0F; }
        public float getKnockbackResistance() { return 0.3F; }
    };
    public EgeArmorItem(ArmorItem.Type type, Item.Properties props) {
        super(MAT, type, props.rarity(Rarity.EPIC).fireResistant());
    }
    @Override public boolean isFoil(ItemStack stack) { return true; }
}