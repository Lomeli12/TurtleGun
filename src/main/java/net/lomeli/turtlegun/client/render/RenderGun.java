package net.lomeli.turtlegun.client.render;

import org.lwjgl.opengl.GL11;

import java.io.InputStream;

import net.minecraft.item.ItemStack;

import net.minecraftforge.client.IItemRenderer;

import net.lomeli.lomlib.client.render.RenderUtils;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.client.model.ModelTurtle;
import net.lomeli.turtlegun.client.techne.TC2Info;
import net.lomeli.turtlegun.client.techne.model.ModelTechne2;

public class RenderGun implements IItemRenderer {

    private ModelTechne2 gunModel;

    public RenderGun() {
        try {
            InputStream techneFile = TurtleGun.class.getResourceAsStream("../../../assets/turtlegun/models/ModelGun.tcn");
            TC2Info tc2Info = TC2Info.readTechneFile(techneFile);
            gunModel = new ModelTechne2(tc2Info);
        } catch (Exception e) {
            TurtleGun.logger.logError("Failed to load model!");
            e.printStackTrace();
        }
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (gunModel == null)
            return;
        GL11.glPushMatrix();

        RenderUtils.applyColor(1, 1, 1, 1);

        GL11.glRotatef(140, 0, 0, -1);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glTranslatef(0, -0.05f, type == ItemRenderType.EQUIPPED ? -1f : -0.5f);

        gunModel.render(true, 0.0625F);

        RenderUtils.resetColor();

        GL11.glPopMatrix();
    }
}
