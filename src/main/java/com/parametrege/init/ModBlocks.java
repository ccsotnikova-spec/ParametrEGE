package com.parametrege.init;

import com.parametrege.ParametrEgeMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, ParametrEgeMod.MOD_ID);
    public static final RegistryObject<Block> MATH_STONE = BLOCKS.register("math_stone",
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> MATH_GRASS = BLOCKS.register("math_grass",
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.6F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> MATH_SAND = BLOCKS.register("math_sand",
        () -> new SandBlock(0x99CCFF, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> FORMULA_BLOCK = BLOCKS.register("formula_block",
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(2.0F).lightLevel(s -> 8).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> EGE_ORE = BLOCKS.register("ege_ore",
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.0F, 8.0F).requiresCorrectToolForDrops().lightLevel(s -> 5).sound(SoundType.STONE)));
    public static final RegistryObject<Block> MATH_PORTAL_FRAME = BLOCKS.register("math_portal_frame",
        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(50.0F, 1200.0F).lightLevel(s -> 12).sound(SoundType.STONE)));
    public static final RegistryObject<Block> MATH_PORTAL = BLOCKS.register("math_portal",
        () -> new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().lightLevel(s -> 15).strength(-1.0F).sound(SoundType.GLASS).noLootTable()));
    static {
        ModItems.ITEMS.register("math_stone", () -> new BlockItem(MATH_STONE.get(), new Item.Properties()));
        ModItems.ITEMS.register("math_grass", () -> new BlockItem(MATH_GRASS.get(), new Item.Properties()));
        ModItems.ITEMS.register("math_sand", () -> new BlockItem(MATH_SAND.get(), new Item.Properties()));
        ModItems.ITEMS.register("formula_block", () -> new BlockItem(FORMULA_BLOCK.get(), new Item.Properties()));
        ModItems.ITEMS.register("ege_ore", () -> new BlockItem(EGE_ORE.get(), new Item.Properties()));
        ModItems.ITEMS.register("math_portal_frame", () -> new BlockItem(MATH_PORTAL_FRAME.get(), new Item.Properties()));
    }
}