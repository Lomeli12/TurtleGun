package net.lomeli.turtlegun.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import net.lomeli.lomlib.util.items.ItemUtil;

public class ModItems {
    public static Item turtleGun, turtleShell, gunParts;

    public static void loadItems() {
        turtleGun = new ItemTurtleGun();
        ItemUtil.INSTANCE.registerItem(turtleGun, "turtleGun");

        turtleShell = new ItemTurtleShell();
        ItemUtil.INSTANCE.registerItem(turtleShell, "turtleShell");

        gunParts = new ItemParts();
        ItemUtil.INSTANCE.registerItem(gunParts, "turtleGunPart");

        OreDictionary.registerOre("shellTurtle", turtleShell);

        addShaped(gunParts, "  L", "ISW", "LW ", 'W', "plankWood", 'L', "logWood", 'S', "stickWood", 'I', "ingotIron");
        addShaped(new ItemStack(gunParts, 1, 1), "IGI", "IRI", "TLT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "shellTurtle", 'G', Items.GUNPOWDER, 'L', Blocks.LEVER);
        addShaped(new ItemStack(gunParts, 1, 2), "III", "  D", "LLI", 'I', "ingotIron", 'L', Items.LEATHER, 'D', Blocks.DISPENSER);
        GameRegistry.addRecipe(new ShapelessOreRecipe(turtleGun, "shellTurtle", new ItemStack(gunParts, 1, 0), new ItemStack(gunParts, 1, 1), new ItemStack(gunParts, 1, 2)));
    }

    private static void addShaped(Object stack, Object... items) {
        if (stack instanceof ItemStack)
            GameRegistry.addRecipe(new ShapedOreRecipe((ItemStack) stack, true, items));
        if (stack instanceof Item)
            GameRegistry.addRecipe(new ShapedOreRecipe((Item) stack, true, items));
    }
}
