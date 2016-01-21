package net.lomeli.turtlegun.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.lomeli.lomlib.client.render.ModelGenerator;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.client.handler.TickHandlerClient;
import net.lomeli.turtlegun.client.render.RenderTurtle;
import net.lomeli.turtlegun.core.Proxy;
import net.lomeli.turtlegun.entity.EntityTurtle;
import net.lomeli.turtlegun.item.ItemTurtleGun;
import net.lomeli.turtlegun.item.ModItems;

public class ClientProxy extends Proxy {
    @Override
    public void preInit() {
        super.preInit();

        ModelGenerator.addItemRender((ItemTurtleGun) ModItems.turtleGun);
    }

    @Override
    public void init() {
        super.init();

        registerModel(ModItems.turtleGun, 0, "turtleGun:turtleGun");
        registerModel(ModItems.turtleShell, 0, "turtleGun:turtleShell");
        registerModel(ModItems.gunParts, 0, "turtleGun:turtleGunPart");
        registerModel(ModItems.gunParts, 1, "turtleGun:turtleGunPart1");
        registerModel(ModItems.gunParts, 2, "turtleGun:turtleGunPart2");
        registerMetadataModel(ModItems.gunParts, new ResourceLocation("turtleGun:turtleGunPart"), new ResourceLocation("turtleGun:turtleGunPart1"), new ResourceLocation("turtleGun:turtleGunPart2"));

        MinecraftForge.EVENT_BUS.register(TurtleGun.modConfig);
        MinecraftForge.EVENT_BUS.register(new TickHandlerClient());
        RenderTurtle renderTurtle = new RenderTurtle();
        RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, renderTurtle);
    }

    private void registerMetadataModel(Item item, ResourceLocation... files) {
        ModelBakery.registerItemVariants(item, files);
    }

    private void registerModel(Item item, int metaData, String name) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, metaData, new ModelResourceLocation(name, "inventory"));
    }

    private void registerModel(Item item, ItemMeshDefinition mesh) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, mesh);
    }
}