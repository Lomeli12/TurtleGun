package net.lomeli.turtlegun.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import net.lomeli.lomlib.client.BasicItemMesh;
import net.lomeli.lomlib.client.models.RendererRegistry;

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
    }

    @Override
    public void init() {
        super.init();

        registerModel(ModItems.turtleGun, new BasicItemMesh("turtleGun:turtleGun"));
        registerModel(ModItems.turtleShell, 0, "turtleGun:turtleShell");
        registerModel(ModItems.turtleMeat, 0, "turtleGun:turtleMeat");
        registerModel(ModItems.gunParts, 0, "turtleGun:turtleGunPart");
        registerModel(ModItems.gunParts, 1, "turtleGun:turtleGunPart1");
        registerModel(ModItems.gunParts, 2, "turtleGun:turtleGunPart2");
        registerMetadataModel(ModItems.gunParts, "turtleGun:turtleGunPart", "turtleGun:turtleGunPart1", "turtleGun:turtleGunPart2");

        RenderPlayerHandler handler = new RenderPlayerHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
        FMLCommonHandler.instance().bus().register(TurtleGun.configHandler);

        RenderTurtle renderTurtle = new RenderTurtle();
        RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, renderTurtle);
        RenderingRegistry.registerEntityRenderingHandler(EntityAggressiveTurtle.class, renderTurtle);
        RenderingRegistry.registerEntityRenderingHandler(EntityTurtleMeat.class, new RenderTurtleMeat());
        RendererRegistry.instance().addItemRenderer(new RenderGun(), ModItems.turtleGun);
    }

    private void registerMetadataModel(Item item, String... files) {
        ModelBakery.addVariantName(item, files);
    }

    private void registerModel(Item item, int metaData, String name) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, metaData, new ModelResourceLocation(name, "inventory"));
    }

    private void registerModel(Item item, ItemMeshDefinition mesh) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, mesh);
    }
}