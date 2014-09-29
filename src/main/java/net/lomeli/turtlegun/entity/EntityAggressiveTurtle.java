package net.lomeli.turtlegun.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.lomeli.lomlib.util.EntityUtil;

import net.lomeli.turtlegun.lib.AggressiveTurtleDamage;

public class EntityAggressiveTurtle extends EntityMob {

    private EntityLivingBase summoner;
    private int wanderCooldown;

    public EntityAggressiveTurtle(World world) {
        super(world);
        this.setSize(0.5F, 0.4f);
        this.wanderCooldown = 200;
        this.summoner = null;
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.5D);
    }

    private float getTurtleStrength() {
        return (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float i) {
        if (damageSourceFromPlayer(source) || (source.getEntity() != null && source.getEntity() instanceof EntityLivingBase))
            return super.attackEntityFrom(source, i);
        return false;
    }

    private boolean damageSourceFromPlayer(DamageSource source) {
        return EntityUtil.wasEntityKilledByPlayer(source);
    }

    //I owe Vazkii 1 code for this.
    @Override
    protected void updateEntityActionState() {
        if (!this.worldObj.isRemote) {
            EntityLivingBase target = getAttackTarget();
            if (target != null) {
                if (target instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) target;
                    if (player.capabilities.isCreativeMode) {
                        this.setAttackTarget(null);
                        return;
                    }
                }
                double d0 = target.posX + target.width / 2 - this.posX;
                double d1 = target.posY + target.height / 2 - this.posY;
                double d2 = target.posZ + target.width / 2 - this.posZ;
                double d3 = Math.pow(d0, 2) + Math.pow(d1, 2) + Math.pow(d2, 2);

                float mod = 0.15F;

                this.motionX += d0 / d3 * mod;
                this.motionY += d1 / d3 * mod;
                this.motionZ += d2 / d3 * mod;

                if (Math.sqrt(d3) < 1F) {
                    target.attackEntityFrom(new AggressiveTurtleDamage(this), this.getTurtleStrength());
                    if (target.isDead) {
                        this.setAttackTarget(null);
                        this.motionX *= 0.9;
                        this.motionY *= 0;
                        this.motionZ *= 0.9;
                    } else {
                        this.motionX *= -1.25;
                        this.motionY *= -1.25;
                        this.motionZ *= -1.25;
                    }
                }
            } else {
                this.getNewTarget();
                if (getAttackTarget() == null)
                    this.wander();
            }
        }

        this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float) Math.PI;
    }

    public EntityLivingBase getSummoner() {
        return this.summoner;
    }

    public EntityAggressiveTurtle setSummoner(EntityLivingBase entityLivingBase) {
        this.summoner = entityLivingBase;
        return this;
    }

    public void wander() {
        if (--this.wanderCooldown <= 0) {
            double d0 = this.posX + (this.rand.nextInt(3) * (this.rand.nextBoolean() ? 1 : -1)) - this.posX;
            double d1 = this.posY + (this.rand.nextInt(3) * (this.rand.nextBoolean() ? 1 : -1)) - this.posY;
            double d2 = this.posZ + (this.rand.nextInt(3) * (this.rand.nextBoolean() ? 1 : -1)) - this.posZ;
            double d3 = Math.pow(d0, 2) + Math.pow(d1, 2) + Math.pow(d2, 2);

            float mod = 1.1F;

            this.motionX += d0 / d3 * mod;
            this.motionY += d1 / d3 * mod;
            this.motionZ += d2 / d3 * mod;
            this.wanderCooldown = 50;
        }
    }

    public void getNewTarget() {
        boolean targetSummoner = false;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(16D, 16D, 16));
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj != null && obj instanceof EntityLivingBase) {
                    EntityLivingBase entity = (EntityLivingBase) obj;
                    if (!(entity instanceof EntityAggressiveTurtle) && !(entity instanceof EntityTurtle)) {
                        if (entity instanceof EntityPlayer) {
                            EntityPlayer player = (EntityPlayer) entity;
                            if (player.capabilities.isCreativeMode)
                                continue;
                        }
                        if (this.summoner != null && entity == this.summoner) {
                            if (list.size() == 1 || i == (list.size() - 1))
                                targetSummoner = true;
                        } else {
                            if (!(entity instanceof EntityCreeper)) {
                                this.setAttackTarget(entity);
                                return;
                            }
                        }
                    }
                }
            }
        }
        if (this.summoner != null) {
            if (targetSummoner || (this.getAttackTarget() == null && this.getDistanceToEntity(this.summoner) < 32D))
                this.setAttackTarget(this.summoner);
        }
    }

    @Override
    protected void fall(float par1) {
    }

    @Override
    protected void updateFallState(double par1, boolean par3) {
    }

    @Override
    public void moveEntityWithHeading(float par1, float par2) {
        if (isInWater()) {
            moveFlying(par1, par2, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.800000011920929D;
            motionY *= 0.800000011920929D;
            motionZ *= 0.800000011920929D;
        } else if (handleLavaMovement()) {
            moveFlying(par1, par2, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
        } else {
            float f2 = 0.91F;

            if (onGround) {
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                f2 = block.slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            moveFlying(par1, par2, onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;

            if (onGround) {
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                f2 = block.slipperiness * 0.91F;
            }

            moveEntity(motionX, motionY, motionZ);
            motionX *= f2;
            motionY *= f2;
            motionZ *= f2;
        }

        prevLimbSwingAmount = limbSwingAmount;
        double d0 = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if (f4 > 1.0F)
            f4 = 1.0F;

        limbSwingAmount += (f4 - limbSwingAmount) * 0.4F;
        limbSwing += limbSwingAmount;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }
}
