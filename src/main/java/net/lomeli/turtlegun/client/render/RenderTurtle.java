package net.lomeli.turtlegun.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import net.lomeli.turtlegun.client.model.ModelTurtle;
import net.lomeli.turtlegun.lib.ModLibs;

public class RenderTurtle extends RenderLiving {
    private final ResourceLocation turtleTexture = new ResourceLocation(ModLibs.MOD_ID + ":models/turtle.png");

    public RenderTurtle() {
        super(new ModelTurtle(), 1f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float tick) {
        super.preRenderCallback(entity, tick);
        //GL11.glScalef(0.7F, 0.7F, 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return turtleTexture;
    }
}
