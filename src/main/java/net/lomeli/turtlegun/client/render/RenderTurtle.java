package net.lomeli.turtlegun.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import net.lomeli.turtlegun.client.model.ModelTurtle;
import net.lomeli.turtlegun.entity.EntityAggressiveTurtle;
import net.lomeli.turtlegun.lib.ModLibs;

public class RenderTurtle extends RenderLiving {
    private final ResourceLocation turtleTexture = new ResourceLocation(ModLibs.MOD_ID + ":models/turtle.png");
    private final ResourceLocation penguinTurtle = new ResourceLocation(ModLibs.MOD_ID + ":models/penguin.png");
    private final ResourceLocation jadedTurtle = new ResourceLocation(ModLibs.MOD_ID + ":models/jaded.png");

    public RenderTurtle() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTurtle(), 1f);
        shadowSize = 0.35f;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float tick) {
        super.preRenderCallback(entity, tick);
        if (entity instanceof EntityAggressiveTurtle)
            GlStateManager.color(0.75f, 0f, 0f, 1f);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float partialTicks) {
        if (entity.hasCustomName() && entity.getCustomNameTag().equals("Ghost Turtle")) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1, 1, 1, 0.3f);
        }
        super.doRender(entity, x, y, z, f, partialTicks);
        GlStateManager.color(1f, 1f, 1f, 1f);
        if (entity.hasCustomName() && entity.getCustomNameTag().equals("Ghost Turtle")) {
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntityLiving && entity.hasCustomName()) {
            if (entity.getCustomNameTag().equals("Penguin Turtle"))
                return penguinTurtle;
            if (entity.getCustomNameTag().equals("JadedTurtle"))
                return jadedTurtle;
        }
        return turtleTexture;
    }
}
