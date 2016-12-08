package net.lomeli.turtlegun.client.handler;

import net.minecraft.util.SoundEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.util.client.SoundUtil;

@SideOnly(Side.CLIENT)
public class SoundHandler {
    public static SoundEvent TURTLE_SONG;

    public static void registerSounds() {
        TURTLE_SONG = SoundUtil.INSTANCE.register("turtlegun:turtleSong");
    }
}
