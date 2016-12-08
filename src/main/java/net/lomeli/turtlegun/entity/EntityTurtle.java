package net.lomeli.turtlegun.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import net.lomeli.lomlib.util.nbt.NBTUtil;

import net.lomeli.turtlegun.item.ModItems;
import net.lomeli.turtlegun.lib.ModLibs;

public class EntityTurtle extends EntityAnimal {
    private static final DataParameter<Integer> TIME = EntityDataManager.createKey(EntityTurtle.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> EXPLOSIVE = EntityDataManager.createKey(EntityTurtle.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BOUNCY = EntityDataManager.createKey(EntityTurtle.class, DataSerializers.BOOLEAN);

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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EXPLOSIVE, false);
        this.dataManager.register(TIME, 0);
        this.dataManager.register(BOUNCY, false);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.worldObj.isRemote) {
            if (this.isRiding() && this.getRidingEntity().isSneaking())
                this.dismountEntity(this.getRidingEntity());
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
        return this.dataManager.get(TIME);
    }

    public void setTime(int time) {
        this.dataManager.set(TIME, time);
    }

    public int incrementTimer() {
        setTime(getTime() + 1);
        return getTime();
    }

    public boolean isGoingToExplode() {
        return this.dataManager.get(EXPLOSIVE);
    }

    public void startExplosionCountDown() {
        setState(true);
    }

    public void setState(boolean bool) {
        this.dataManager.set(EXPLOSIVE, bool);
    }

    public boolean bouncy() {
        return this.dataManager.get(BOUNCY);
    }

    public void setBouncy(boolean bool) {
        this.dataManager.set(BOUNCY, bool);
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
            NBTUtil.INSTANCE.setInteger(gun, "gunCoolDown", ModLibs.GUN_COOLDOWN);
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
        return stack != null && stack.getItem() != null && (stack.getItem() == Items.CARROT || stack.getItem() == Items.WHEAT_SEEDS);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (!worldObj.isRemote && player != null) {
            if (stack != null && stack.getItem() == Items.SPAWN_EGG) {
                EntityAgeable baby = this.createChild(this);
                if (baby != null) {
                    baby.setGrowingAge(-24000);
                    baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                    this.worldObj.spawnEntityInWorld(baby);

                    if (stack.hasDisplayName())
                        baby.setCustomNameTag(stack.getDisplayName());

                    if (!player.capabilities.isCreativeMode) {
                        --stack.stackSize;

                        if (stack.stackSize <= 0)
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                    return true;
                }
            }
            if (!player.isSneaking() && player.getRidingEntity() == null && (stack == null || stack.getItem() == null))
                this.startRiding(player);
        }
        return super.processInteract(player, hand, stack);
    }
}
