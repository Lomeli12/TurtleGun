package net.lomeli.turtlegun.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.lomeli.turtlegun.item.ModItems;
import net.lomeli.turtlegun.lib.ModLibs;

public class CreativeTurtle extends CreativeTabs {

    public CreativeTurtle() {
        super(ModLibs.MOD_ID + ".tab");
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.turtleShell;
    }
}
