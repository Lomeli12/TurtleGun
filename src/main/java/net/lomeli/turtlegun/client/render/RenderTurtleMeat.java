package net.lomeli.turtlegun.client.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidRegistry;

import net.lomeli.lomlib.client.render.RenderUtils;

import net.lomeli.turtlegun.item.ModItems;

public class RenderTurtleMeat extends Render {
    @Override
    public void doRender(Entity entity, double d0, double d1, double d2, float f0, float f1) {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) d0, (float) d1, (float) d2);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(0.5F, 0.5F, 0.5F);

        bindEntityTexture(entity);

        RenderUtils.applyColor(1f, 1f, 1f, 1f);

        renderIcon(Tessellator.instance, ModItems.turtleMeat.getIconFromDamage(0));

        RenderUtils.resetColor();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    private void renderIcon(Tessellator tessellator, IIcon icon) {
        if (icon == null)
            icon = FluidRegistry.WATER.getIcon();

        float f1 = icon.getMinU();
        float f2 = icon.getMaxU();
        float f3 = icon.getMinV();
        float f4 = icon.getMaxV();
        float f5 = 1.0F;
        float f6 = 0.5F;
        float f7 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f6, 0.0F - f7, 0.0D, f1, f4);
        tessellator.addVertexWithUV(f5 - f6, 0.0F - f7, 0.0D, f2, f4);
        tessellator.addVertexWithUV(f5 - f6, f5 - f7, 0.0D, f2, f3);
        tessellator.addVertexWithUV(0.0F - f6, f5 - f7, 0.0D, f1, f3);
        tessellator.draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return RenderUtils.ITEM_TEXTURE;
    }
}
