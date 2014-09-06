package net.lomeli.turtlegun.core.handler;

import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.Level;

import java.io.InputStreamReader;
import java.net.URL;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.turtlegun.lib.ModLibs;

import static cpw.mods.fml.relauncher.Side.CLIENT;

public class VersionChecker {
    private boolean needsUpdate, isDirect, doneTelling;
    private String updateJson = "https://raw.githubusercontent.com/Lomeli12/TurtleGun/master/update.json";
    private String version, downloadURL, changeLog;

    public void checkForUpdates() {
        needsUpdate = false;
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(new URL(updateJson).openStream()));

            if (reader != null) {
                int major = 0, minor = 0, revision = 0;
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("major"))
                        major = reader.nextInt();
                    else if (name.equals("minor"))
                        minor = reader.nextInt();
                    else if (name.equals("revision"))
                        revision = reader.nextInt();
                    else if (name.equals("downloadURL"))
                        downloadURL = reader.nextString();
                    else if (name.equals("direct"))
                        isDirect = reader.nextBoolean();
                    else if (name.equals("changeLog"))
                        changeLog = reader.nextString();
                }
                reader.endObject();
                if (major > ModLibs.MAJOR)
                    needsUpdate = true;
                else if (major == ModLibs.MAJOR) {
                    if (minor > ModLibs.MINOR)
                        needsUpdate = true;
                    else if (minor == ModLibs.MINOR) {
                        if (revision > ModLibs.REVISION)
                            needsUpdate = true;
                    }
                }

                if (needsUpdate) {
                    version = major + "." + minor + "." + revision;
                    FMLLog.log(Level.INFO, translate(ModLibs.UPDATE_MESSAGE + " " + downloadURL));
                    FMLLog.log(Level.INFO, translate(ModLibs.OLD_VERSION) + " " + ModLibs.VERSION);
                    FMLLog.log(Level.INFO, translate(ModLibs.NEW_VERSION) + " " + version);
                }
                reader.close();
            }
        } catch (Exception e) {
        }
    }

    public void sendMessage() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("modDisplayName", ModLibs.MOD_NAME);
        tag.setString("oldVersion", ModLibs.VERSION);
        tag.setString("newVersion", version);
        tag.setString("updateUrl", downloadURL);
        tag.setBoolean("isDirectLink", isDirect);
        tag.setString("changeLog", changeLog);
        FMLInterModComms.sendMessage("VersionChecker", "addUpdate", tag);
    }

    public boolean needUpdate() {
        return needsUpdate;
    }

    public String translate(String unlocalized) {
        return StatCollector.translateToLocal(unlocalized);
    }

    @SubscribeEvent
    @SideOnly(CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (needUpdate() && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !doneTelling) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (!version.isEmpty() && player != null) {
                player.addChatMessage(new ChatComponentTranslation(ModLibs.UPDATE_MESSAGE + " " +downloadURL));
                player.addChatMessage(new ChatComponentText(translate(ModLibs.OLD_VERSION) + " " + ModLibs.VERSION));
                player.addChatMessage(new ChatComponentText(translate(ModLibs.NEW_VERSION) + " " + version));
                doneTelling = true;
            }
        }
    }
}
