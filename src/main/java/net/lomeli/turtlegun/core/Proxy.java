package net.lomeli.turtlegun.core;

import net.minecraftforge.common.MinecraftForge;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.core.handler.EntityHandler;
import net.lomeli.turtlegun.entity.ModEntities;
import net.lomeli.turtlegun.item.ModItems;
import net.lomeli.turtlegun.lib.ModLibs;

public class Proxy {
    public void preInit() {
        TurtleGun.logger.logInfo("Pre Init");
        if (ModLibs.CHECK_FOR_UPDATES)
            new Thread(TurtleGun.versionChecker).start();
        TurtleGun.modConfig.loadConfig();

        ModItems.loadItems();
    }

    public void init() {
        TurtleGun.logger.logInfo("Init");
        ModEntities.loadEntities();
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
    }
}
