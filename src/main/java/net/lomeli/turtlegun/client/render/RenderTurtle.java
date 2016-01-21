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
import net.lomeli.turtlegun.entity.EntityTurtle;
import net.lomeli.turtlegun.lib.ModLibs;

public class RenderTurtle extends RenderLiving {
    private final ResourceLocation turtleTexture = new ResourceLocation(ModLibs.MOD_ID + ":models/turtle.png");
    private final ResourceLocation penguinTurtle = new ResourceLocation(ModLibs.MOD_ID + ":models/penguin.png");
    private final ResourceLocation jadedTurtle = new ResourceLocation(ModLibs.MOD_ID + ":models/jaded.png");

    public RenderTurtle() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTurtle(), 1f);
        shadowSize = 0.3f;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float tick) {
        super.preRenderCallback(entity, tick);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float partialTicks) {
        if (entity instanceof EntityTurtle) {
            if (entity.hasCustomName() && entity.getCustomNameTag().equals("Ghost Turtle")) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color(1, 1, 1, 0.3f);
            }
            GlStateManager.pushMatrix();
            super.doRender((EntityTurtle) entity, x, y, z, f, partialTicks);
            GlStateManager.color(1f, 1f, 1f, 1f);
            GlStateManager.popMatrix();
            if (entity.hasCustomName() && entity.getCustomNameTag().equals("Ghost Turtle")) {
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
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
