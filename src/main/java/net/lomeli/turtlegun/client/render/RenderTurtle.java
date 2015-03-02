package net.lomeli.turtlegun.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
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

    public RenderTurtle() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTurtle(), 1f);
        shadowSize = 0.35f;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float tick) {
        super.preRenderCallback(entity, tick);
        if (entity instanceof EntityAggressiveTurtle)
            GL11.glColor4f(0.75f, 0f, 0f, 1f);
        if (entity.getDisplayName().getUnformattedText().equalsIgnoreCase("JadedTurtle"))
            GL11.glColor4f(1f, 0f, 1f, 1f);
    }

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof EntityLiving) {
            if (entity.hasCustomName() && entity.getCustomNameTag().equals("Penguin Turtle"))
                return penguinTurtle;
        }
        return turtleTexture;
    }
}
