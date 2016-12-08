package net.lomeli.turtlegun.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * turtle.tcn - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelTurtle extends ModelBase {
    public ModelRenderer shellBase;
    public ModelRenderer shellTop;
    public ModelRenderer head;
    public ModelRenderer leg0;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;

    public ModelTurtle() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.leg2 = new ModelRenderer(this, 0, 17);
        this.leg2.setRotationPoint(1.0F, 22.0F, 1.0F);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.leg3 = new ModelRenderer(this, 0, 17);
        this.leg3.setRotationPoint(-2.0F, 22.0F, -2.0F);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.leg0 = new ModelRenderer(this, 0, 17);
        this.leg0.setRotationPoint(1.0F, 22.0F, -2.0F);
        this.leg0.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.leg1 = new ModelRenderer(this, 0, 17);
        this.leg1.setRotationPoint(-2.0F, 22.0F, 1.0F);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.shellBase = new ModelRenderer(this, 0, 0);
        this.shellBase.setRotationPoint(-3.0F, 20.0F, -3.0F);
        this.shellBase.addBox(0.0F, 0.75F, 0.0F, 6, 2, 6, 0.0F);
        this.head = new ModelRenderer(this, 0, 13);
        this.head.setRotationPoint(0.0F, 22.0F, -3.0F);
        this.head.addBox(-1.0F, -2.0F, -2.0F, 2, 2, 2, 0.0F);
        this.shellTop = new ModelRenderer(this, 0, 8);
        this.shellTop.setRotationPoint(-2.0F, 19.0F, -2.0F);
        this.shellTop.addBox(0.0F, 0.75F, 0.0F, 4, 1, 4, 0.0F);
    }



    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg0.render(f5);
        this.leg1.render(f5);
        this.shellBase.render(f5);
        this.head.render(f5);
        this.shellTop.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void render(float f) {
        shellBase.render(f);
        shellTop.render(f);
        head.render(f);
        leg0.render(f);
        leg1.render(f);
        leg2.render(f);
        leg3.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.head.rotateAngleX = (f4 / (180F / (float) Math.PI)) / 2;
        this.head.rotateAngleY = (f3 / (180F / (float) Math.PI)) / 2;
        this.leg0.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}
