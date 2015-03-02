package net.lomeli.turtlegun.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemParts extends Item {
    public ItemParts() {
        super();
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(ModLibs.MOD_ID + ".gunParts");
        this.setCreativeTab(TurtleGun.turtleTab);
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 3; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
