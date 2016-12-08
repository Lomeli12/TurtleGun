package net.lomeli.turtlegun.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;

import net.lomeli.lomlib.client.render.item.IItemRenderer;
import net.lomeli.lomlib.client.render.item.ItemRenderHandler;
import net.lomeli.lomlib.client.render.item.RenderType;
import net.lomeli.lomlib.test.client.ModelGun;
import net.lomeli.lomlib.util.client.RenderUtil;
import net.lomeli.lomlib.util.nbt.NBTUtil;
import net.lomeli.turtlegun.lib.ModLibs;

public class RenderGunModel implements IItemRenderer {
    private final ResourceLocation GUN_TEXTURE = new ResourceLocation(ModLibs.MOD_ID + ":textures/models/guntexture.png");
    private ModelGun model = new ModelGun();

    @Override
    public void renderFirstPerson(EnumHand hand, EnumHandSide handSide, float partialTicks, float swingProgress, float equipProgress, ItemStack stack) {
        boolean creative = NBTUtil.INSTANCE.getBoolean(stack, "isCreative");
        GlStateManager.pushMatrix();
        ItemRenderHandler.INSTANCE.transformEquipProgressFirstPerson(handSide, equipProgress);

        RenderUtil.INSTANCE.bindTexture(GUN_TEXTURE);

        RenderUtil.INSTANCE.applyColor(creative ? 0xCB15EB : 0xFFFFFF, creative ? 0.75f : 1f);
        if (handSide == EnumHandSide.RIGHT) {
            GlStateManager.translate(0.5f, 0.25f, -0.2f);
            GlStateManager.rotate(87.5F * 5, 0F, 1F, 0F);
            GlStateManager.rotate(90f, 0f, 0.5f, 0f);
            GlStateManager.rotate(180f, 1f, 0f, 0f);
        } else {
            GlStateManager.translate(-0.5f, 0.25f, -0.3f);
            GlStateManager.rotate(180f, 0f, 0f, 1f);
        }

        model.render(0.046875f);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderThirdPerson(EntityPlayer player, EnumHandSide side, ItemStack stack, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean creative = NBTUtil.INSTANCE.getBoolean(stack, "isCreative");
        GlStateManager.pushMatrix();
        RenderUtil.INSTANCE.applyColor(creative ? 0xCB15EB : 0xFFFFFF, creative ? 0.75f : 1f);
        GlStateManager.rotate(180f, 1f, 0f, 0f);
        GlStateManager.rotate(180f, 0f, 1f, 0f);
        GlStateManager.translate(0, -0.2f, -0.5f);
        RenderUtil.INSTANCE.bindTexture(GUN_TEXTURE);
        model.render(0.046875f);
        RenderUtil.INSTANCE.applyColor(1f, 1f, 1f, 1f);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean useRenderer(RenderType type, EnumHand hand, ItemStack stack) {
        return type == RenderType.FIRST_PERSON || type == RenderType.THIRD_PERSON;
    }
}
