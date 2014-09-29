package net.lomeli.turtlegun.core.handler;

import java.io.File;

import net.minecraft.util.StatCollector;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ConfigHandler {
    private Configuration config;

    public ConfigHandler(File file) {
        this.config = new Configuration(file);
    }

    public Configuration getConfig() {
        return config;
    }

    public void updateConfig() {
        String cat = Configuration.CATEGORY_GENERAL;

        ModLibs.GUN_COOLDOWN = setGetInt(cat, "gunCoolDown", ModLibs.GUN_COOLDOWN, 10, Integer.MAX_VALUE, "config.turtlegun.cooldown");
        ModLibs.TURTLE_COUNTDOWN = setGetInt(cat, "turtleCountDown", ModLibs.TURTLE_COUNTDOWN, 50, Integer.MAX_VALUE, "config.turtlegun.timer");

        ModLibs.ALLOW_NATURAL_SPAWN = setGetBool(cat, "allowSpawn", ModLibs.ALLOW_NATURAL_SPAWN, "config.turtlegun.allowSpawn");
        ModLibs.SPAWN_RATE = setGetInt(cat, "spawnRate", ModLibs.SPAWN_RATE, 1, Integer.MAX_VALUE, "config.turtlegun.spawnRate");
        ModLibs.PACK_SIZE_MIN = setGetInt(cat, "packSizeMin", ModLibs.PACK_SIZE_MIN, 1, Integer.MAX_VALUE, "config.turtlegun.packSizeMin");
        ModLibs.PACK_SIZE_MAX = setGetInt(cat, "packSizeMax", ModLibs.PACK_SIZE_MAX, 1, Integer.MAX_VALUE, "config.turtlegun.packSizeMax");

        ModLibs.GUN_DROP_RATE = setGetInt(cat, "gunDropRate", ModLibs.GUN_DROP_RATE, 0, 1000, "config.turtlegun.gunDropRate");
        ModLibs.TURTLE_BOMB_SPAWN = setGetInt(cat, "turtleBombSpawn", ModLibs.TURTLE_BOMB_SPAWN, 1, 1000, "config.turtlegun.turtleBombSpawn");

        if (this.config.hasChanged())
            this.config.save();
    }

    private int setGetInt(String cat, String tag, int baseValue, int min, int max, String comment) {
        return config.getInt(tag, cat, baseValue, 0, 100, StatCollector.translateToLocal(comment));
    }

    private boolean setGetBool(String cat, String tag, boolean baseValue, String comment) {
        return config.getBoolean(tag, cat, baseValue, StatCollector.translateToLocal(comment));
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.modID.equalsIgnoreCase(ModLibs.MOD_ID.toLowerCase()))
            TurtleGun.configHandler.updateConfig();
    }
}
