package net.lomeli.turtlegun.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTurtle extends ModelBase {
    ModelRenderer shellBase;
    ModelRenderer shellTop;
    ModelRenderer head;
    ModelRenderer leg0;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;

    public ModelTurtle() {
        textureWidth = 128;
        textureHeight = 64;

        shellBase = new ModelRenderer(this, 0, 0);
        shellBase.addBox(0F, 0.75F, 0F, 6, 2, 6);
        shellBase.setRotationPoint(-3F, 20F, -3F);
        shellBase.setTextureSize(128, 64);
        shellBase.mirror = true;
        setRotation(shellBase, 0F, 0F, 0F);
        shellTop = new ModelRenderer(this, 0, 8);
        shellTop.addBox(0F, 0.75F, 0F, 4, 1, 4);
        shellTop.setRotationPoint(-2F, 19F, -2F);
        shellTop.setTextureSize(128, 64);
        shellTop.mirror = true;
        setRotation(shellTop, 0F, 0F, 0F);
        head = new ModelRenderer(this, 0, 13);
        head.addBox(0F, 0F, 0F, 2, 2, 2);
        head.setRotationPoint(-1F, 20F, -5F);
        head.setTextureSize(128, 64);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        leg0 = new ModelRenderer(this, 0, 17);
        leg0.addBox(0F, 0F, 0F, 1, 2, 1);
        leg0.setRotationPoint(1F, 22F, -2F);
        leg0.setTextureSize(128, 64);
        leg0.mirror = true;
        setRotation(leg0, 0F, 0F, 0F);
        leg1 = new ModelRenderer(this, 0, 17);
        leg1.addBox(0F, 0F, 0F, 1, 2, 1);
        leg1.setRotationPoint(-2F, 22F, 1F);
        leg1.setTextureSize(128, 64);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 0, 17);
        leg2.addBox(0F, 0F, 0F, 1, 2, 1);
        leg2.setRotationPoint(1F, 22F, 1F);
        leg2.setTextureSize(128, 64);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 0, 17);
        leg3.addBox(0F, 0F, 0F, 1, 2, 1);
        leg3.setRotationPoint(-2F, 22F, -2F);
        leg3.setTextureSize(128, 64);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        shellBase.render(f5);
        shellTop.render(f5);
        head.render(f5);
        leg0.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
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
        this.head.rotateAngleX = f4 / (180F / (float)Math.PI);
        this.head.rotateAngleY = f3 / (180F / (float)Math.PI);
        //this.shellBase.rotateAngleX = ((float)Math.PI / 2F);
        //this.shellTop.rotateAngleX = ((float)Math.PI / 2F);
        this.leg0.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leg1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        this.leg3.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

}
