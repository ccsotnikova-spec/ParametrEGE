package com.parametrege.init;

import com.parametrege.ParametrEgeMod;
import com.parametrege.entities.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ParametrEgeMod.MOD_ID);

    public static final RegistryObject<EntityType<NuclearHamsterEntity>> NUCLEAR_HAMSTER =
        ENTITIES.register("nuclear_hamster", () ->
            EntityType.Builder.<NuclearHamsterEntity>of(NuclearHamsterEntity::new, MobCategory.MONSTER)
                .sized(0.8F, 0.6F).clientTrackingRange(10).build("nuclear_hamster"));

    public static final RegistryObject<EntityType<OlgaBorisovnaEntity>> OLGA_BORISOVNA =
        ENTITIES.register("olga_borisovna", () ->
            EntityType.Builder.<OlgaBorisovnaEntity>of(OlgaBorisovnaEntity::new, MobCategory.MONSTER)
                .sized(0.8F, 2.2F).clientTrackingRange(16).build("olga_borisovna"));

    public static final RegistryObject<EntityType<MorgenshternEntity>> MORGENSHTERN =
        ENTITIES.register("morgenshtern", () ->
            EntityType.Builder.<MorgenshternEntity>of(MorgenshternEntity::new, MobCategory.CREATURE)
                .sized(0.6F, 1.9F).clientTrackingRange(12).build("morgenshtern"));

    public static final RegistryObject<EntityType<FaceRapperEntity>> FACE_RAPPER =
        ENTITIES.register("face_rapper", () ->
            EntityType.Builder.<FaceRapperEntity>of(FaceRapperEntity::new, MobCategory.CREATURE)
                .sized(0.6F, 1.9F).clientTrackingRange(12).build("face_rapper"));

    public static final RegistryObject<EntityType<YashchenkoEntity>> YASHCHENKO =
        ENTITIES.register("yashchenko", () ->
            EntityType.Builder.<YashchenkoEntity>of(YashchenkoEntity::new, MobCategory.MONSTER)
                .sized(1.0F, 2.5F).clientTrackingRange(20).fireImmune().build("yashchenko"));
}