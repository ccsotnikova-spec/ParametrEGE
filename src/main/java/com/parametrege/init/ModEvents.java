package com.parametrege.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEvents {
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player p = event.getEntity();
        p.displayClientMessage(Component.literal("=== PARAMETR EGE MOD v1.0.0 ==="), false);
        p.displayClientMessage(Component.literal("Mobs: Nuclear Hamster, Olga Borisovna, Yashchenko, Morgenshtern, Face"), false);
        p.displayClientMessage(Component.literal("Weapons: Sword 10900^89992 dmg, Portal Gun, Mega Parametr"), false);
    }
}