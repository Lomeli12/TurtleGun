package net.lomeli.turtlegun.item;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.client.models.IModelHolder;
import net.lomeli.lomlib.util.LangUtil;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemTurtleShell extends Item implements IModelHolder {
    public ItemTurtleShell() {
        super();
        this.setCreativeTab(TurtleGun.turtleTab);
        this.setUnlocalizedName(ModLibs.MOD_ID.toLowerCase() + ".shell");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean something) {
        list.add(LangUtil.INSTANCE.translate("item.turtlegun.shell.subtext"));
    }

    @NotNull
    @Override
    public String[] getVariants() {
        return new String[]{"turtleGun:turtleShell"};
    }
}
