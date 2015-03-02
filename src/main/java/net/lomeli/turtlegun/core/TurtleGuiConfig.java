package net.lomeli.turtlegun.core;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import net.minecraftforge.fml.client.config.GuiConfig;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class TurtleGuiConfig extends GuiConfig {
    public TurtleGuiConfig(GuiScreen parent) {
        super(parent, new ConfigElement(TurtleGun.configHandler.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), ModLibs.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(TurtleGun.configHandler.getConfig().toString()));
    }
}
