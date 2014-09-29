package net.lomeli.turtlegun.core.handler;

import java.util.Random;

import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.turtlegun.entity.EntityTurtle;

public class EntityHandler {
    private String[] turtleNames = {"Yoshi", "Crash", "Shelly", "Why?!", "OMG TURTLE <3", "Leonardo", "Michelangelo", "Raphael", "Donatello"};

    @SubscribeEvent
    public void onSpawnEvent(LivingSpawnEvent event) {
        Random rand = event.world.rand;
        if (event.entityLiving != null && event.entityLiving instanceof EntityTurtle) {
            if (rand.nextInt(100) < 5)
                ((EntityTurtle) event.entityLiving).setCustomNameTag(turtleNames[rand.nextInt(turtleNames.length)]);
        }
    }
}