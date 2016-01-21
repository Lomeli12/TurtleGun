package net.lomeli.turtlegun.client.render;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.vecmath.Matrix4f;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.fml.client.FMLClientHandler;

import net.lomeli.lomlib.client.render.item.IItemRenderer;
import net.lomeli.lomlib.util.NBTUtil;
import net.lomeli.lomlib.util.RenderUtils;
import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.turtlegun.client.model.ModelGun;
import net.lomeli.turtlegun.lib.ModLibs;

@SuppressWarnings("deprecation")
public class RenderGunModel implements IItemRenderer {
    private static final ItemCameraTransforms cameraTransforms = new ItemCameraTransforms(
            new ItemTransformVec3f(new Vector3f(-2.5F, -100.0F, 90.0F), new Vector3f(0.0125F, 0.3125F, -0.075F), new Vector3f(0.55F, 0.55F, 0.55F)),
            new ItemTransformVec3f(new Vector3f(-8.0F, 42.0F, 6.0F), new Vector3f(-0.1F, 0.075F, 0.05F), new Vector3f(0.7F, 1.0F, 1.0F)),
            new ItemTransformVec3f(new Vector3f(0.0F, -90.0F, 0.0F), new Vector3f(0.0F, -0.1F, -0.05F), new Vector3f(0.7F, 0.7F, 0.7F)),
            new ItemTransformVec3f(new Vector3f(0.0F, -90.0F, 0.0F), new Vector3f(0.0F, -0.1F, -0.05F), new Vector3f(0.7F, 0.7F, 0.7F)),
            new ItemTransformVec3f(new Vector3f(0.0F, -90.0F, 0.0F), new Vector3f(0.0F, -0.1F, -0.05F), new Vector3f(0.7F, 0.7F, 0.7F)),
            new ItemTransformVec3f(new Vector3f(0.0F, -90.0F, 0.0F), new Vector3f(0.0F, -0.1F, -0.05F), new Vector3f(0.7F, 0.7F, 0.7F)));
    public EntityPlayer lastPlayer;
    private ModelGun model;
    private ResourceLocation texture;
    private ItemStack heldItem;
    private boolean creative;
    private ItemCameraTransforms.TransformType currentPerspective;

    public RenderGunModel() {
        model = new ModelGun();
        texture = ResourceUtil.getResource(ModLibs.MOD_ID, "models/guntexture.png");
    }

    @Override
    public void preRenderItem() {
        if (heldItem != null)
            creative = NBTUtil.getBoolean(heldItem, "isCreative");
    }

    @Override
    public void renderItem() {
        if (heldItem != null) {
            Minecraft mc = FMLClientHandler.instance().getClient();

            RenderUtils.bindTexture(texture);

            boolean isFirstPerson = (this.currentPerspective == ItemCameraTransforms.TransformType.FIRST_PERSON) && (this.lastPlayer == mc.thePlayer);
            boolean isItemRender = (this.currentPerspective == null) || (this.currentPerspective == ItemCameraTransforms.TransformType.GUI) || (this.currentPerspective == ItemCameraTransforms.TransformType.NONE);

            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.enableBlend();

            if (isFirstPerson) {
                if (mc.currentScreen == null) {
                    GlStateManager.scale(1.5f, 1.5f, 1.5f);
                    GlStateManager.rotate(50f, 0f, 1f, 0f);
                    GlStateManager.translate(0f, -0.3f, -0.45f);
                } else {
                    GlStateManager.scale(1.3f, 1.3f, 1.3f);
                    GlStateManager.rotate(50f, 0f, 1f, 0f);
                    GlStateManager.rotate(5f, 0f, 0f, 1f);
                    GlStateManager.rotate(20f, -1f, 0f, 0f);
                    GlStateManager.translate(-0.25f, 0f, -0.65f);
                }
            } else if (isItemRender) {
                if (this.currentPerspective != ItemCameraTransforms.TransformType.GUI) {
                    GlStateManager.translate(0f, -0.5f, 0f);
                    GlStateManager.scale(1.5, 1.5, 1.5);
                } else {
                    GlStateManager.rotate(90f, 0, 1f, 0);
                    GlStateManager.rotate(10f, 0, 0, 1f);
                    GlStateManager.scale(0.8, 0.8, 0.8);
                    GlStateManager.translate(0.05f, -0.2f, 0f);
                }
            } else {
                GlStateManager.scale(0.8f, 0.8f, 0.8f);
                GlStateManager.rotate(90f, 0f, 1f, 0f);
                GlStateManager.rotate(90f, -1f, 0f, 0f);
                GlStateManager.rotate(90f, 0f, 0f, 1f);
                GlStateManager.translate(0f, -0.4f, -0.5f);
            }
            if (creative)
                RenderUtils.applyColor(0xCB15EB, 0.75f);

            model.render(RenderUtils.magicNum);

            RenderUtils.applyColor(1f, 1f, 1f, 1f);

            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void postRenderItem() {
        this.lastPlayer = null;
        this.currentPerspective = null;
    }

    @Override
    public ItemCameraTransforms getCameraTransforms() {
        return cameraTransforms;
    }

    @Override
    public void handleBlockState(IBlockState state) {
    }

    @Override
    public void handleItemState(ItemStack stack) {
        this.heldItem = stack;
    }

    @Override
    public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, Pair<? extends IFlexibleBakedModel, Matrix4f> pair) {
        this.currentPerspective = cameraTransformType;
        return pair;
    }

    @Override
    public boolean useVanillaCameraTransform() {
        return true;
    }
}
