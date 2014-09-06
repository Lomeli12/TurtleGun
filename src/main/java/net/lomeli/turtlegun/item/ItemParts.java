package net.lomeli.turtlegun.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemParts extends Item {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public ItemParts() {
        super();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(ModLibs.MOD_ID + ".gunParts");
        this.setCreativeTab(TurtleGun.turtleTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        iconArray = new IIcon[3];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = register.registerIcon(ModLibs.MOD_ID + ":parts" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return par1 < iconArray.length ? iconArray[par1] : iconArray[0];
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < iconArray.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
