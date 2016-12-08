package net.lomeli.turtlegun.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.turtlegun.item.ModItems;

public class TickHandlerClient {

    private boolean wasHeld;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.END)) {
            ItemStack stack = event.player.getHeldItemMainhand();
            ItemStack off = event.player.getHeldItemOffhand();
            if (stack != null && (!(event.player == Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) || !Minecraft.getMinecraft().inGameHasFocus)) {
                if (stack.getItem() == ModItems.turtleGun) {
                    if (off != null) event.player.resetActiveHand();
                    else if (event.player.getItemInUseCount() <= 0) {
                        wasHeld = true;
                        event.player.resetActiveHand();
                        event.player.setActiveHand(EnumHand.MAIN_HAND);
                    }
                } else if (wasHeld) {
                    wasHeld = false;
                    event.player.resetActiveHand();
                }
            }
        }
    }
}
