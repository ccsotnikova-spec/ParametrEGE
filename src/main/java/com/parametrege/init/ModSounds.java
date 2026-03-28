package com.parametrege.init;

import com.parametrege.ParametrEgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ParametrEgeMod.MOD_ID);
    public static final RegistryObject<SoundEvent> MORGENSHTERN_CADILLAC = reg("morgenshtern_cadillac");
    public static final RegistryObject<SoundEvent> OLGA_KRICHIT = reg("olga_krichit");
    public static final RegistryObject<SoundEvent> NUCLEAR_BOOM = reg("nuclear_boom");
    public static final RegistryObject<SoundEvent> MATH_AMBIENT = reg("math_ambient");
    private static RegistryObject<SoundEvent> reg(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParametrEgeMod.MOD_ID, name)));
    }
}