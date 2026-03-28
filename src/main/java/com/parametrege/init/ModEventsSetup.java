package com.parametrege.init;

import com.parametrege.ParametrEgeMod;
import com.parametrege.entities.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ParametrEgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventsSetup {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.NUCLEAR_HAMSTER.get(), NuclearHamsterEntity.createAttributes().build());
        event.put(ModEntities.OLGA_BORISOVNA.get(), OlgaBorisovnaEntity.createAttributes().build());
        event.put(ModEntities.MORGENSHTERN.get(), MorgenshternEntity.createAttributes().build());
        event.put(ModEntities.FACE_RAPPER.get(), FaceRapperEntity.createAttributes().build());
        event.put(ModEntities.YASHCHENKO.get(), YashchenkoEntity.createAttributes().build());
    }
}