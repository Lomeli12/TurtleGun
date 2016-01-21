package net.lomeli.turtlegun.client.config;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.fml.client.config.GuiConfig;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class TurtleGuiConfig extends GuiConfig {
    public TurtleGuiConfig(GuiScreen parent) {
        super(parent, TurtleGun.modConfig.getConfigElements(), ModLibs.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(TurtleGun.modConfig.getTitle()));
    }
}
