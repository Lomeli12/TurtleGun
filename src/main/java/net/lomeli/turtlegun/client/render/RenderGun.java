package net.lomeli.turtlegun.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.lomeli.lomlib.client.models.IItemRenderer;
import net.lomeli.lomlib.client.models.RenderType;
import net.lomeli.lomlib.util.RenderUtils;
import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.turtlegun.client.model.ModelGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class RenderGun implements IItemRenderer {
    private ModelGun model;
    private ResourceLocation texture;
    
    public RenderGun() {
        model = new ModelGun();
        texture = ResourceUtil.getResourceUtil(ModLibs.MOD_ID, "models/guntexture.png");
    }
    
    @Override
    public void renderItem(RenderType type, EntityLivingBase entity, ItemStack stack, float renderTick) {
        if (type == RenderType.THIRD_PERSON)
            renderThirdPerson();
        else if (type == RenderType.FIRST_PERSON)
            renderFirstPerson();
    }
    
    public void renderThirdPerson() {
        GlStateManager.pushMatrix();

        RenderUtils.applyColor(1, 1, 1, 1);
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.translate(0, -0.4f, -0.5f);

        ResourceUtil.bindTexture(texture);
        model.render(0.046875f);

        RenderUtils.resetColor();
        GlStateManager.popMatrix();
    }
    
    public void renderFirstPerson() {
        RenderUtils.applyColor(1, 1, 1, 1);
        GlStateManager.translate(0, 0.5, 0);
        GlStateManager.translate(-0.75, 0, 0);
        GlStateManager.rotate(180, 0, 0, 1);
        GlStateManager.rotate(180, 0, 1, 0);
        
        ResourceUtil.bindTexture(texture);
        model.render(0.046875f);

        RenderUtils.resetColor();
    }
    
}
