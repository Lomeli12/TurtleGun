package net.lomeli.turtlegun;

import net.lomeli.turtlegun.core.Proxy;
import net.lomeli.turtlegun.core.handler.ConfigHandler;
import net.lomeli.turtlegun.core.handler.VersionChecker;
import net.lomeli.turtlegun.lib.ModLibs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.lomlib.util.LogHelper;

@Mod(modid = ModLibs.MOD_ID, name = ModLibs.MOD_NAME, version = ModLibs.VERSION, dependencies = "required-after:LomLib;", guiFactory = "net.lomeli.turtlegun.core.TurtleFactory")
public class TurtleGun {
    public static LogHelper logger;
    public static VersionChecker versionChecker;
    public static ConfigHandler configHandler;

    @SidedProxy(clientSide = ModLibs.CLIENT_PROXY, serverSide = ModLibs.PROXY)
    public static Proxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configHandler = new ConfigHandler(event.getSuggestedConfigurationFile());
        logger = new LogHelper(ModLibs.MOD_NAME);
        versionChecker = new VersionChecker();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInt(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}