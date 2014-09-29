package net.lomeli.turtlegun.client;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.client.render.RenderGun;
import net.lomeli.turtlegun.client.render.RenderPlayerHandler;
import net.lomeli.turtlegun.client.render.RenderTurtle;
import net.lomeli.turtlegun.client.render.RenderTurtleMeat;
import net.lomeli.turtlegun.core.Proxy;
import net.lomeli.turtlegun.entity.EntityAggressiveTurtle;
import net.lomeli.turtlegun.entity.EntityTurtle;
import net.lomeli.turtlegun.entity.EntityTurtleMeat;
import net.lomeli.turtlegun.item.ModItems;

public class ClientProxy extends Proxy {
    @Override
    public void preInit() {
        super.preInit();
        if (TurtleGun.versionChecker.needUpdate())
            FMLCommonHandler.instance().bus().register(TurtleGun.versionChecker);
        getModels();
    }

    @Override
    public void init() {
        super.init();
        MinecraftForgeClient.registerItemRenderer(ModItems.turtleGun, new RenderGun());
        MinecraftForge.EVENT_BUS.register(new RenderPlayerHandler());
        RenderTurtle renderTurtle = new RenderTurtle();
        RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, renderTurtle);
        RenderingRegistry.registerEntityRenderingHandler(EntityAggressiveTurtle.class, renderTurtle);
        RenderingRegistry.registerEntityRenderingHandler(EntityTurtleMeat.class, new RenderTurtleMeat());
    }

    public void getModels() {
        ThreadModelDownloader downloader = new ThreadModelDownloader(TurtleGun.modelsFolder);
        downloader.preCheck();
        downloader.start();
    }
}
