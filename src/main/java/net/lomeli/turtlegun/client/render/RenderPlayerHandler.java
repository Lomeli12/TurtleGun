package net.lomeli.turtlegun.client.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.util.RenderUtils;

import net.lomeli.turtlegun.item.ModItems;

public class RenderPlayerHandler {
    private boolean gunInHand, activate;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player != null && event.side == Side.CLIENT) {
            ItemStack stack = player.getCurrentEquippedItem();
            gunInHand = stack != null && stack.getItem() != null && stack.getItem() == ModItems.turtleGun;
            if (gunInHand) {
                RenderUtils.setPlayerUseCount(player, 1);
                activate = true;
            } else {
                if (activate) {
                    RenderUtils.setPlayerUseCount(player, 0);
                    activate = false;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerDropItem(ItemTossEvent event) {
        EntityPlayer player = event.player;
        if (player != null) {
            ItemStack droppedItem = event.entityItem.getEntityItem();
            if (droppedItem != null && droppedItem.getItem() != null && droppedItem.getItem() == ModItems.turtleGun) {
                if (activate) {
                    gunInHand = false;
                    RenderUtils.setPlayerUseCount(player, 0);
                }
            }
        }
    }
}
