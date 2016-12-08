package net.lomeli.turtlegun.item;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.client.models.IModelHolder;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ItemParts extends Item implements IModelHolder {
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

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    public String[] getVariants() {
        return new String[] {
                "turtleGun:turtleGunPart",
                "turtleGun:turtleGunPart1",
                "turtleGun:turtleGunPart2"
        };
    }
}
