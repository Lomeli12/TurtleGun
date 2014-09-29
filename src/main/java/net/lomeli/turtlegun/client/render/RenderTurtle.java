package net.lomeli.turtlegun.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import net.lomeli.turtlegun.client.model.ModelTurtle;
import net.lomeli.turtlegun.entity.EntityAggressiveTurtle;
import net.lomeli.turtlegun.lib.ModLibs;

public class RenderTurtle extends RenderLiving {
    private final ResourceLocation turtleTexture = new ResourceLocation(ModLibs.MOD_ID + ":models/turtle.png");

    public RenderTurtle() {
        super(new ModelTurtle(), 1f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float tick) {
        super.preRenderCallback(entity, tick);
        if (entity instanceof EntityAggressiveTurtle)
            GL11.glColor4f(0.75f, 0f, 0f, 1f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return turtleTexture;
    }
}
