package net.lomeli.turtlegun.client;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.lomeli.lomlib.client.models.ModelHandler;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.client.handler.SoundHandler;
import net.lomeli.turtlegun.client.handler.TickHandlerClient;
import net.lomeli.turtlegun.client.render.RenderTurtle;
import net.lomeli.turtlegun.core.Proxy;
import net.lomeli.turtlegun.entity.EntityTurtle;
import net.lomeli.turtlegun.item.ModItems;

public class ClientProxy extends Proxy {
    @Override
    public void preInit() {
        super.preInit();
        ModelHandler.INSTANCE.registerModel(ModItems.gunParts);
        ModelHandler.INSTANCE.registerModel(ModItems.turtleGun);
        ModelHandler.INSTANCE.registerModel(ModItems.turtleShell);
        RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, RenderTurtle.TurtleRenderFactory.INSTANCE);
        //ModelGenerator.addItemRender((ItemTurtleGun) ModItems.turtleGun);
        SoundHandler.registerSounds();
    }

    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(TurtleGun.modConfig);
        MinecraftForge.EVENT_BUS.register(new TickHandlerClient());
    }
}