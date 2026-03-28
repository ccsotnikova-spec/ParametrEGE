package com.parametrege.init;

import com.parametrege.ParametrEgeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ParametrEgeMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> EGE_TAB = TABS.register("ege_tab",
        () -> CreativeModeTab.builder()
            .title(Component.literal("Parametr EGE"))
            .icon(() -> new ItemStack(ModItems.MECH_PARAMETRA.get()))
            .displayItems((params, output) -> {
                output.accept(ModItems.MECH_PARAMETRA.get());
                output.accept(ModItems.PORTAL_GUN.get());
                output.accept(ModItems.MEGA_PARAMETR.get());
                output.accept(ModItems.DVOIKA_WEAPON.get());
                output.accept(ModItems.SHPARGALKA.get());
                output.accept(ModItems.EGE_CRYSTAL.get());
                output.accept(ModItems.EGE_HELMET.get());
                output.accept(ModItems.EGE_CHESTPLATE.get());
                output.accept(ModItems.EGE_LEGGINGS.get());
                output.accept(ModItems.EGE_BOOTS.get());
                output.accept(ModBlocks.MATH_STONE.get());
                output.accept(ModBlocks.MATH_GRASS.get());
                output.accept(ModBlocks.FORMULA_BLOCK.get());
                output.accept(ModBlocks.EGE_ORE.get());
                output.accept(ModItems.NUCLEAR_HAMSTER_EGG.get());
                output.accept(ModItems.OLGA_BORISOVNA_EGG.get());
                output.accept(ModItems.MORGENSHTERN_EGG.get());
                output.accept(ModItems.FACE_EGG.get());
                output.accept(ModItems.YASHCHENKO_EGG.get());
            }).build());
}