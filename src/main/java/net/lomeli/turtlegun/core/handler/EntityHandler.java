package net.lomeli.turtlegun.core.handler;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.turtlegun.entity.EntityTurtle;

public class EntityHandler {
    public static String[] turtleNames = {"Yoshi", "Crash", "Shelly", "Why?!", "OMG TURTLE <3", "Leonardo", "Ghost Turtle",
            "Michelangelo", "Raphael", "Donatello", "I'm really a pig", "Mine", "Feed me!", "forsinain42", "Shelldon",
            "da haxor 4chan", "Penguin Turtle", "Mj11jM", "Cthulhu", "bill", "bob", "jeb", "rawr", "My name Jeff!",
            "Blue and Black", "White and Gold", "Puncake", "King of Sweden!", "Fernando", "Maes Hughes", "JadedTurtle"};

    @SubscribeEvent
    public void onSpawnEvent(LivingSpawnEvent event) {
        Random rand = event.getWorld().rand;
        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityTurtle) {
            if (rand.nextInt(100) < 15)
                event.getEntityLiving().setCustomNameTag(turtleNames[rand.nextInt(turtleNames.length)]);
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() != null && !event.getEntityLiving().worldObj.isRemote && event.getEntityLiving() instanceof EntityTurtle) {
            if (event.getEntityLiving().hasCustomName() && event.getEntityLiving().getCustomNameTag().equals("Maes Hughes") && EntityUtil.INSTANCE.damageFromPlayer(event.getSource())) {
                Entity entity = EntityUtil.INSTANCE.getSourceOfDamage(event.getSource());
                if (entity != null && entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    if (player != null) {
                        player.addChatComponentMessage(new TextComponentTranslation("damage.turtlegun.fma"));
                        player.worldObj.setRainStrength(player.worldObj.rand.nextInt(12000) + 12000);
                    }
                }
            }
        }
    }
}