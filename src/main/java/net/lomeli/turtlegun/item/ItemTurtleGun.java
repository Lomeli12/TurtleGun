package net.lomeli.turtlegun.item;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.client.render.item.IItemRenderer;
import net.lomeli.lomlib.client.render.item.ISpecialRender;
import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.client.render.RenderGunModel;
import net.lomeli.turtlegun.core.handler.EntityHandler;
import net.lomeli.turtlegun.entity.EntityTurtle;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemTurtleGun extends Item implements ISpecialRender {
    @SideOnly(Side.CLIENT)
    private RenderGunModel renderer;

    public ItemTurtleGun() {
        super();
        this.setUnlocalizedName(ModLibs.MOD_ID.toLowerCase() + ".gun");
        this.setMaxStackSize(1);
        this.setFull3D();
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int partialTick, boolean isSelected) {
        if (!world.isRemote) {
            int gunCoolDown = getGunCooldown(stack);
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    if (gunCoolDown == ModLibs.GUN_COOLDOWN && (player.inventory.hasItem(ModItems.turtleShell) || player.capabilities.isCreativeMode)) {
                        if (!player.capabilities.isCreativeMode)
                            player.inventory.consumeInventoryItem(ModItems.turtleShell);
                        setGunCoolDown(stack, gunCoolDown - 1);
                    }
                    if (gunCoolDown < ModLibs.GUN_COOLDOWN && gunCoolDown > 0 && world.getWorldTime() % 10 == 0)
                        setGunCoolDown(stack, gunCoolDown - 1);
                } else {
                    if (gunCoolDown < ModLibs.GUN_COOLDOWN && gunCoolDown > 0 && world.getWorldTime() % 10 == 0)
                        setGunCoolDown(stack, gunCoolDown - 1);
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (getGunCooldown(stack) <= 0) {
                setGunCoolDown(stack, NBTUtil.getBoolean(stack, "isCreative") ? 1 : ModLibs.GUN_COOLDOWN);
                Vec3 look = player.getLookVec();
                EntityTurtle turtle = new EntityTurtle(world);
                turtle.setSprinting(true);
                turtle.setPosition(player.posX + look.xCoord, player.posY + look.yCoord + (player.getEyeHeight() / 2), player.posZ + look.zCoord);
                turtle.motionX = look.xCoord * 3;
                turtle.motionY = look.yCoord * 3;
                turtle.motionZ = look.zCoord * 3;
                turtle.setBouncy(player.isSneaking());
                turtle.startExplosionCountDown();
                if (NBTUtil.getBoolean(stack, "isCreative"))
                    turtle.setCustomNameTag(EntityHandler.turtleNames[world.rand.nextInt(EntityHandler.turtleNames.length)]);
                world.spawnEntityInWorld(turtle);
                world.playSoundAtEntity(player, ModLibs.MOD_ID + ":turtleSong", 1f, 1f);
            }
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (getGunCooldown(stack) <= 0)
                return true;
        }
        return super.onItemUse(stack, playerIn, world, pos, side, hitX, hitY, hitZ);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(itemIn));
        ItemStack creativeGun = new ItemStack(itemIn);

        NBTUtil.setBoolean(creativeGun, "isCreative", true);
        subItems.add(creativeGun);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        if (NBTUtil.getBoolean(stack, "isCreative"))
            tooltip.add(StatCollector.translateToLocal("item.turtlegun.gun.sub"));
        int cool = getGunCooldown(stack);
        if (cool > 0)
            tooltip.add(StatCollector.translateToLocal(cool == ModLibs.GUN_COOLDOWN ? "item.turtlegun.gun.empty" : "item.turtlegun.gun.reloading"));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1d - ((double) (ModLibs.GUN_COOLDOWN - getGunCooldown(stack)) / (double) ModLibs.GUN_COOLDOWN);
    }

    private int getGunCooldown(ItemStack stack) {
        return NBTUtil.getInt(stack, "gunCoolDown");
    }

    private void setGunCoolDown(ItemStack stack, int cooldown) {
        NBTUtil.setInteger(stack, "gunCoolDown", cooldown);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getGunCooldown(stack) != 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
        this.renderer.lastPlayer = player;
        return super.getModel(stack, player, useRemaining);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        return NBTUtil.getBoolean(stack, "isCreative");
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{CreativeTabs.tabCombat, TurtleGun.turtleTab};
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IItemRenderer getRenderer() {
        if (renderer == null)
            renderer = new RenderGunModel();
        return renderer;
    }

    @Override
    public String resourceName() {
        return "turtleGun:turtleGun";
    }
}
