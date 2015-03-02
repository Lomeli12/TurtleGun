package net.lomeli.turtlegun.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import net.lomeli.lomlib.util.RenderUtils;
import net.lomeli.turtlegun.item.ModItems;

public class RenderTurtleMeat extends Render {
    public RenderTurtleMeat() {
        super(Minecraft.getMinecraft().getRenderManager());
    }

    @Override
    public void doRender(Entity entity, double d0, double d1, double d2, float f0, float f1) {
        GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        GlStateManager.enableRescaleNormal();
        float f2 = 0.5f;
        GlStateManager.scale(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
        TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModItems.turtleMeat);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        float f3 = textureatlassprite.getMinU();
        float f4 = textureatlassprite.getMaxU();
        float f5 = textureatlassprite.getMinV();
        float f6 = textureatlassprite.getMaxV();
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        worldrenderer.startDrawingQuads();
        worldrenderer.setNormal(0.0F, 1.0F, 0.0F);
        worldrenderer.addVertexWithUV((double) (0.0F - f8), (double) (0.0F - f9), 0.0D, (double) f3, (double) f6);
        worldrenderer.addVertexWithUV((double) (f7 - f8), (double) (0.0F - f9), 0.0D, (double) f4, (double) f6);
        worldrenderer.addVertexWithUV((double) (f7 - f8), (double) (1.0F - f9), 0.0D, (double) f4, (double) f5);
        worldrenderer.addVertexWithUV((double) (0.0F - f8), (double) (1.0F - f9), 0.0D, (double) f3, (double) f5);
        tessellator.draw();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return RenderUtils.TEXTURE_MAP;
    }
}
