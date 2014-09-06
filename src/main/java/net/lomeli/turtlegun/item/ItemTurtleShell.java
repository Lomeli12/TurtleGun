package net.lomeli.turtlegun.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemTurtleShell extends Item {
    public ItemTurtleShell() {
        super();
        this.setCreativeTab(TurtleGun.turtleTab);
        this.setUnlocalizedName(ModLibs.MOD_ID.toLowerCase() + ".shell");
        this.setTextureName(ModLibs.MOD_ID.toLowerCase() + ":turtleShell");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean something) {
        list.add(StatCollector.translateToLocal("item.turtlegun.shell.subtext"));
    }
}
