package net.lomeli.turtlegun.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.turtlegun.item.ModItems;
import net.lomeli.turtlegun.lib.ModLibs;

public class EntityTurtle extends EntityAnimal {

    public EntityTurtle(World world) {
        super(world);
        this.setSize(0.5F, 0.4F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(17, new Byte((byte) 0));
        this.dataWatcher.addObject(18, new Integer(0));
        this.dataWatcher.addObject(19, new Byte((byte) 0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.worldObj.isRemote) {
            if (this.isRiding() && this.ridingEntity.isSneaking())
                this.dismountEntity(this.ridingEntity);
            if (isGoingToExplode()) {
                if (bouncy() && (this.isCollided || this.onGround)) {
                    this.motionX = (this.rand.nextFloat() * Math.sin(this.rand.nextFloat())) * (this.rand.nextInt(2) == 0 ? 1.25 : -1.25);
                    this.motionY = this.rand.nextFloat() * 1.5;
                    this.motionZ = (this.rand.nextFloat() * Math.cos(this.rand.nextFloat())) * (this.rand.nextInt(2) == 0 ? 1.25 : -1.25);
                }
                this.fallDistance = 0f;
                if (this.worldObj.getWorldTime() % 20 == 0) {
                    if (incrementTimer() >= ModLibs.TURTLE_COUNTDOWN) {
                        boolean flag = this.worldObj.getGameRules().getBoolean("mobGriefing");
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3, flag);
                        this.setDead();
                    }
                }
            } else {
                if (getTime() > 0)
                    setTime(0);
            }
        }
    }

    public int getTime() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void setTime(int time) {
        this.dataWatcher.updateObject(18, time);
    }

    public int incrementTimer() {
        setTime(getTime() + 1);
        return getTime();
    }

    public boolean isGoingToExplode() {
        return this.dataWatcher.getWatchableObjectByte(17) > 0;
    }

    public void startExplosionCountDown() {
        setState(true);
    }

    public void setState(boolean bool) {
        this.dataWatcher.updateObject(17, new Byte((byte) (bool ? 1 : 0)));
    }

    public boolean bouncy() {
        return this.dataWatcher.getWatchableObjectByte(19) > 0;
    }

    public void setBouncy(boolean b) {
        this.dataWatcher.updateObject(19, new Byte((byte) (b ? 1 : 0)));
    }

    @Override
    protected Item getDropItem() {
        return ModItems.turtleShell;
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if (this.rand.nextInt(1000) < ModLibs.GUN_DROP_RATE && !worldObj.isRemote) {
            ItemStack gun = new ItemStack(ModItems.turtleGun);
            NBTUtil.setInteger(gun, "gunCoolDown", ModLibs.GUN_COOLDOWN);
            this.entityDropItem(gun, 1f);
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isInWater() {
        return super.isInWater();
    }

    @Override
    public EntityAgeable createChild(EntityAgeable p_90011_1_) {
        return new EntityTurtle(worldObj);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("willExplode", isGoingToExplode());
        tagCompound.setInteger("explosionTimer", getTime());
        tagCompound.setBoolean("bouncy", bouncy());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound);
        setTime(tagCompound.getInteger("willExplode"));
        setState(tagCompound.getBoolean("explosionTimer"));
        setBouncy(tagCompound.getBoolean("bouncy"));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack != null && stack.getItem() != null && (stack.getItem() == Items.carrot || stack.getItem() == Items.wheat_seeds);
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if (!worldObj.isRemote && player != null) {
            ItemStack hand = player.getCurrentEquippedItem();
            if (hand != null && hand.getItem() == Items.spawn_egg) {
                EntityAgeable baby = this.createChild(this);
                if (baby != null) {
                    baby.setGrowingAge(-24000);
                    baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                    this.worldObj.spawnEntityInWorld(baby);

                    if (hand.hasDisplayName())
                        baby.setCustomNameTag(hand.getDisplayName());

                    if (!player.capabilities.isCreativeMode) {
                        --hand.stackSize;

                        if (hand.stackSize <= 0)
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                    return true;
                }
            }
            if (!player.isSneaking() && player.riddenByEntity == null && (hand == null || hand.getItem() == null))
                this.mountEntity(player);
        }
        return super.interact(player);
    }
}
