package net.lomeli.turtlegun.core;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;

import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.core.handler.EntityHandler;
import net.lomeli.turtlegun.entity.ModEntities;
import net.lomeli.turtlegun.item.ModItems;

public class Proxy {
    public void preInit() {
        TurtleGun.versionChecker.checkForUpdates();
        TurtleGun.configHandler.updateConfig();
        FMLCommonHandler.instance().bus().register(TurtleGun.configHandler);
        if (TurtleGun.versionChecker.needUpdate() && ModLoaded.isModInstalled("VersionChecker"))
            TurtleGun.versionChecker.sendMessage();

        ModItems.loadItems();
    }

    public void init() {
        ModEntities.loadEntities();
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
    }
}
