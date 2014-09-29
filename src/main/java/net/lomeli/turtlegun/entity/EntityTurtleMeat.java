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

    @Override
    protected void onImpact(MovingObjectPosition pos) {
        if (!this.worldObj.isRemote) {
            for (int i = 0; i < ModLibs.TURTLE_BOMB_SPAWN; i++) {
                EntityAggressiveTurtle turtle = new EntityAggressiveTurtle(worldObj);
                turtle.setPositionAndUpdate(pos.blockX, pos.blockY, pos.blockZ);
                turtle.motionX += (rand.nextFloat() * (rand.nextBoolean() ? 1 : -1));
                turtle.motionY += (rand.nextFloat() * (rand.nextBoolean() ? 1 : -1));
                turtle.motionZ += (rand.nextFloat() * (rand.nextBoolean() ? 1 : -1));
                if (this.getThrower() != null)
                    turtle.setSummoner(this.getThrower());
                this.worldObj.spawnEntityInWorld(turtle);
            }
        }
    }
}
