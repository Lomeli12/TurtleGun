package net.lomeli.turtlegun.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.lomeli.turtlegun.entity.EntityTurtle;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemTurtleGun extends Item {
    public ItemTurtleGun() {
        super();
        this.setUnlocalizedName(ModLibs.MOD_ID.toLowerCase() + ".gun");
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setFull3D();
        this.setTextureName(ModLibs.MOD_ID.toLowerCase() + ":turtleGun");

    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int partialTick, boolean p_77663_5_) {
        if (!world.isRemote) {
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    if (stack.getItemDamage() == ModLibs.GUN_COOLDOWN && player.inventory.hasItem(ModItems.turtleShell)) {
                        if (!player.capabilities.isCreativeMode)
                            player.inventory.consumeInventoryItem(ModItems.turtleShell);
                        stack.setItemDamage(stack.getItemDamage() - 1);
                    }
                    if (stack.getItemDamage() < ModLibs.GUN_COOLDOWN && stack.getItemDamage() > 0 && world.getWorldTime() % 10 == 0)
                        stack.setItemDamage(stack.getItemDamage() - 1);
                } else {
                    if (stack.getItemDamage() < ModLibs.GUN_COOLDOWN && stack.getItemDamage() > 0 && world.getWorldTime() % 10 == 0)
                        stack.setItemDamage(stack.getItemDamage() - 1);
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (stack.getItemDamage() == 0) {
                stack.setItemDamage(ModLibs.GUN_COOLDOWN);
                Vec3 look = player.getLookVec();
                EntityTurtle turtle = new EntityTurtle(world);
                turtle.setSprinting(true);
                turtle.setPosition(player.posX + look.xCoord * 4.2, player.posY + look.yCoord + (player.getEyeHeight() / 2), player.posZ + look.zCoord * 4.2);
                turtle.motionX = look.xCoord * 1;
                turtle.motionY = look.yCoord * 1;
                turtle.motionZ = look.zCoord * 1;
                turtle.startExplosionCountDown();
                world.spawnEntityInWorld(turtle);
                world.playSoundAtEntity(player, ModLibs.MOD_ID + ":turtleSong", 1f, 1f);
            }
        }
        return stack;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return true;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.bow;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1d - ((double) (ModLibs.GUN_COOLDOWN - stack.getItemDamage()) / (double) ModLibs.GUN_COOLDOWN);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.getItemDamage() > 0;
    }


}