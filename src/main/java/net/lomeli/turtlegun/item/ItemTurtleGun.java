package net.lomeli.turtlegun.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.client.models.IModelHolder;
import net.lomeli.lomlib.client.render.item.IItemRenderer;
import net.lomeli.lomlib.client.render.item.ISpecialRender;
import net.lomeli.lomlib.util.LangUtil;
import net.lomeli.lomlib.util.client.SoundUtil;
import net.lomeli.lomlib.util.items.ItemUtil;
import net.lomeli.lomlib.util.nbt.NBTUtil;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.client.handler.SoundHandler;
import net.lomeli.turtlegun.client.render.RenderGunModel;
import net.lomeli.turtlegun.core.handler.EntityHandler;
import net.lomeli.turtlegun.entity.EntityTurtle;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemTurtleGun extends Item implements ISpecialRender, IModelHolder {
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
                    if (gunCoolDown == ModLibs.GUN_COOLDOWN && (ItemUtil.INSTANCE.hasItem(player.inventory, ModItems.turtleShell) || player.capabilities.isCreativeMode)) {
                        if (!player.capabilities.isCreativeMode)
                            ItemUtil.INSTANCE.consumeItem(player.inventory, ModItems.turtleShell);
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
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && hand == EnumHand.MAIN_HAND && player.getHeldItemOffhand() == null) {
            if (getGunCooldown(stack) <= 0) {
                setGunCoolDown(stack, NBTUtil.INSTANCE.getBoolean(stack, "isCreative") ? 1 : ModLibs.GUN_COOLDOWN);
                Vec3d look = player.getLookVec();
                EntityTurtle turtle = new EntityTurtle(world);
                turtle.setSprinting(true);
                turtle.setPosition(player.posX + look.xCoord, player.posY + look.yCoord + (player.getEyeHeight() / 2), player.posZ + look.zCoord);
                turtle.motionX = look.xCoord * 3;
                turtle.motionY = look.yCoord * 3;
                turtle.motionZ = look.zCoord * 3;
                turtle.setBouncy(player.isSneaking());
                turtle.startExplosionCountDown();
                if (NBTUtil.INSTANCE.getBoolean(stack, "isCreative"))
                    turtle.setCustomNameTag(EntityHandler.turtleNames[world.rand.nextInt(EntityHandler.turtleNames.length)]);
                world.spawnEntityInWorld(turtle);
                SoundUtil.INSTANCE.playSoundAtEntity(player, SoundHandler.TURTLE_SONG, SoundCategory.PLAYERS, 1f, 1f);
            }
        }
        return super.onItemRightClick(stack, world, player, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (getGunCooldown(stack) <= 0)
                return EnumActionResult.FAIL;
        }
        return super.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(itemIn));
        ItemStack creativeGun = new ItemStack(itemIn);

        NBTUtil.INSTANCE.setBoolean(creativeGun, "isCreative", true);
        subItems.add(creativeGun);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(LangUtil.INSTANCE.translate("item.turtlegun.gun.hands"));
        if (NBTUtil.INSTANCE.getBoolean(stack, "isCreative"))
            tooltip.add(LangUtil.INSTANCE.translate("item.turtlegun.gun.sub"));
        int cool = getGunCooldown(stack);
        if (cool > 0)
            tooltip.add(LangUtil.INSTANCE.translate(cool == ModLibs.GUN_COOLDOWN ? "item.turtlegun.gun.empty" : "item.turtlegun.gun.reloading"));
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
        return NBTUtil.INSTANCE.getInt(stack, "gunCoolDown");
    }

    private void setGunCoolDown(ItemStack stack, int cooldown) {
        NBTUtil.INSTANCE.setInteger(stack, "gunCoolDown", cooldown);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getGunCooldown(stack) != 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        return NBTUtil.INSTANCE.getBoolean(stack, "isCreative");
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{CreativeTabs.COMBAT, TurtleGun.turtleTab};
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasSpecialRenderer(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IItemRenderer getSpecialRenderer(ItemStack stack) {
        if (renderer == null) renderer = new RenderGunModel();
        return renderer;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean canItemSwing(ItemStack stack) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String[] getVariants() {
        return new String[]{"turtleGun:turtleGun"};
    }
}
