package net.lomeli.turtlegun.item;

import net.minecraft.item.Item;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item turtleGun, turtleShell;

    public static void loadItems() {
        turtleGun = new ItemTurtleGun();
        registerItem(turtleGun, "turtleGun");

        turtleShell = new ItemTurtleShell();
        registerItem(turtleShell, "turtleShell");

        OreDictionary.registerOre("shellTurtle", turtleShell);
    }

    private static void registerItem(Item item, String id) {
        GameRegistry.registerItem(item, id);
    }
}
