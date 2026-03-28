package com.parametrege.init;

import com.parametrege.ParametrEgeMod;
import com.parametrege.items.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, ParametrEgeMod.MOD_ID);

    public static final RegistryObject<Item> MECH_PARAMETRA =
        ITEMS.register("mech_parametra", MechParametraItem::new);
    public static final RegistryObject<Item> PORTAL_GUN =
        ITEMS.register("portal_gun", PortalGunItem::new);
    public static final RegistryObject<Item> MEGA_PARAMETR =
        ITEMS.register("mega_parametr", MegaParametrItem::new);
    public static final RegistryObject<Item> DVOIKA_WEAPON =
        ITEMS.register("dvoika_weapon", DvoikaWeaponItem::new);
    public static final RegistryObject<Item> SHPARGALKA =
        ITEMS.register("shpargalka", ShpargalkaItem::new);
    public static final RegistryObject<Item> EGE_CRYSTAL =
        ITEMS.register("ege_crystal", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EGE_HELMET =
        ITEMS.register("ege_helmet", () -> new EgeArmorItem(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> EGE_CHESTPLATE =
        ITEMS.register("ege_chestplate", () -> new EgeArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> EGE_LEGGINGS =
        ITEMS.register("ege_leggings", () -> new EgeArmorItem(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> EGE_BOOTS =
        ITEMS.register("ege_boots", () -> new EgeArmorItem(ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> NUCLEAR_HAMSTER_EGG =
        ITEMS.register("nuclear_hamster_spawn_egg", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> OLGA_BORISOVNA_EGG =
        ITEMS.register("olga_borisovna_spawn_egg", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> MORGENSHTERN_EGG =
        ITEMS.register("morgenshtern_spawn_egg", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> FACE_EGG =
        ITEMS.register("face_spawn_egg", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> YASHCHENKO_EGG =
        ITEMS.register("yashchenko_spawn_egg", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
}