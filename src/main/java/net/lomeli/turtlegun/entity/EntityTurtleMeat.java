package net.lomeli.turtlegun.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.lomeli.turtlegun.lib.ModLibs;

public class EntityTurtleMeat extends EntityThrowable {
    public EntityTurtleMeat(World world) {
        super(world);
    }

    public EntityTurtleMeat(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    public EntityTurtleMeat(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition pos) {
        if (!this.worldObj.isRemote && pos != null && pos.getBlockPos() != null) {
            for (int i = 0; i < ModLibs.TURTLE_BOMB_SPAWN; i++) {
                EntityAggressiveTurtle turtle = new EntityAggressiveTurtle(worldObj);
                if (turtle != null) {
                    turtle.setPositionAndUpdate(pos.getBlockPos().getX(), pos.getBlockPos().getY(), pos.getBlockPos().getZ());
                    turtle.motionX += rand.nextFloat() * (rand.nextBoolean() ? 1 : -1);
                    turtle.motionY += rand.nextFloat() * (rand.nextBoolean() ? 1 : -1);
                    turtle.motionZ += rand.nextFloat() * (rand.nextBoolean() ? 1 : -1);
                    if (this.getThrower() != null)
                        turtle.setSummoner(this.getThrower());
                    this.worldObj.spawnEntityInWorld(turtle);
                }
            }
        }
    }
}
