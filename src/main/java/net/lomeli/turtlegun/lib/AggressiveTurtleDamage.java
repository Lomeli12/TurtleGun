package net.lomeli.turtlegun.lib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

import net.lomeli.turtlegun.entity.EntityAggressiveTurtle;

public class AggressiveTurtleDamage extends DamageSource {
    private EntityAggressiveTurtle turtle;

    public AggressiveTurtleDamage(EntityAggressiveTurtle turtle) {
        super("aggressiveTurtle");
        this.turtle = turtle;
    }

    @Override
    public Entity getEntity() {
        return turtle;
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase target) {
        String base = String.format(translate("damage.turtle"), target.getCommandSenderName());
        if (turtle.getSummoner() == null)
            base += turtle.hasCustomNameTag() ? turtle.getCustomNameTag() : translate("damage.turtle.genetic");
        else
            base += String.format(translate("damage.turtle.summoned"), turtle.getSummoner().getCommandSenderName(), turtle.hasCustomNameTag() ? turtle.getCustomNameTag() : translate("entity.turtlegun.Angryturtle.name"));
        return new ChatComponentText(base);
    }

    private String translate(String unlocalized) {
        return StatCollector.translateToLocal(unlocalized);
    }
}
