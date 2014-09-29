package net.lomeli.turtlegun.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item turtleGun, turtleShell, gunParts, turtleMeat;

    public static void loadItems() {
        turtleGun = new ItemTurtleGun();
        registerItem(turtleGun, "turtleGun");

        turtleShell = new ItemTurtleShell();
        registerItem(turtleShell, "turtleShell");

        gunParts = new ItemParts();
        registerItem(gunParts, "turtleGunPart");

        turtleMeat = new ItemTurtleMeat();
        registerItem(turtleMeat, "turtleMeat");

        OreDictionary.registerOre("shellTurtle", turtleShell);

        addShaped(gunParts, "  L", "ISW", "LW ", 'W', "plankWood", 'L', "logWood", 'S', "stickWood", 'I', "ingotIron");
        addShaped(new ItemStack(gunParts, 1, 1), "IGI", "IRI", "TLT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "shellTurtle", 'G', Items.gunpowder, 'L', Blocks.lever);
        addShaped(new ItemStack(gunParts, 1, 2), "III", "  D", "LLI", 'I', "ingotIron", 'L', Items.leather, 'D', Blocks.dispenser);
        addShaped(turtleMeat, "STS", "TDT", "STS", 'S', "shellTurtle", 'D', "gemDiamond", 'T', Blocks.tnt);
        GameRegistry.addRecipe(new ShapelessOreRecipe(turtleGun, "shellTurtle", new ItemStack(gunParts, 1, 0), new ItemStack(gunParts, 1, 1), new ItemStack(gunParts, 1, 2)));
    }

    private static void registerItem(Item item, String id) {
        GameRegistry.registerItem(item, id);
    }

    private static void addShaped(Object stack, Object... items) {
        if (stack instanceof ItemStack)
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack) stack, true, items));
        if (stack instanceof Item)
            GameRegistry.addRecipe(new ShapedOreRecipe((Item) stack, true, items));
    }
}
