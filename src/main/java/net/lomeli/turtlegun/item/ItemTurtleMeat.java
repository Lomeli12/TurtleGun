package net.lomeli.turtlegun.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.entity.EntityTurtleMeat;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemTurtleMeat extends ItemFood {
    public ItemTurtleMeat() {
        super(3, 0.6f, false);
        this.setCreativeTab(TurtleGun.turtleTab);
        this.setUnlocalizedName(ModLibs.MOD_ID.toLowerCase() + ".meat");
        this.setTextureName(ModLibs.MOD_ID.toLowerCase() + ":turtleMeat");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player != null && player.isSneaking()) {
            if (!player.capabilities.isCreativeMode)
                stack.stackSize--;
            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
            world.playSoundAtEntity(player, "random.fuse", 1.0F, 0.5F);
            if (!world.isRemote)
                world.spawnEntityInWorld(new EntityTurtleMeat(world, player));
            return stack;
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean something) {
        list.add(StatCollector.translateToLocal("item.turtlegun.meat.subtext_p1"));
        list.add(StatCollector.translateToLocal("item.turtlegun.meat.subtext_p2"));
        list.add(StatCollector.translateToLocal("item.turtlegun.meat.subtext_p3"));
    }
}
