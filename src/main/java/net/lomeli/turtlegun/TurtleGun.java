package net.lomeli.turtlegun;

import java.io.File;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.lomlib.core.config.ModConfig;
import net.lomeli.lomlib.core.version.VersionChecker;
import net.lomeli.lomlib.util.LogUtil;

import net.lomeli.turtlegun.core.CreativeTurtle;
import net.lomeli.turtlegun.core.Proxy;
import net.lomeli.turtlegun.lib.ModLibs;

@Mod(modid = ModLibs.MOD_ID, name = ModLibs.MOD_NAME, version = ModLibs.VERSION, dependencies = "required-after:lomlib;", guiFactory = "net.lomeli.turtlegun.client.config.TurtleFactory")
public class TurtleGun {
    public static CreativeTurtle turtleTab = new CreativeTurtle();
    public static LogUtil logger;
    public static VersionChecker versionChecker;
    public static ModConfig modConfig;
    public static File modelsFolder;

    @Mod.Instance(ModLibs.MOD_ID)
    public static TurtleGun instance;

    @SidedProxy(clientSide = ModLibs.CLIENT_PROXY, serverSide = ModLibs.PROXY)
    public static Proxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modelsFolder = new File(event.getModConfigurationDirectory().getParent(), "/" + ModLibs.MOD_ID);
        modConfig = new ModConfig(ModLibs.MOD_ID, event.getSuggestedConfigurationFile(), ModLibs.class);
        logger = new LogUtil(ModLibs.MOD_NAME);
        versionChecker = new VersionChecker(ModLibs.UPDATE_JSON, ModLibs.MOD_ID, ModLibs.MOD_NAME, ModLibs.MAJOR, ModLibs.MINOR, ModLibs.REVISION);
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }
}
