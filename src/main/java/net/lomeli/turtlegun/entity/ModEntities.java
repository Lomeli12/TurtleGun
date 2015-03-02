package net.lomeli.turtlegun.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.turtlegun.TurtleGun;
import net.lomeli.turtlegun.lib.ModLibs;

public class ModEntities {
    public static void loadEntities() {
        int i = 0;
        EntityUtil.registerEntity(EntityTurtle.class, "turtlegun.turtle", TurtleGun.instance, 0x91671D, 894731, i++);
        EntityUtil.registerEntity(EntityAggressiveTurtle.class, "turtlegun.Angryturtle", TurtleGun.instance, 0x91671D, 0xFF0000, i++);
        EntityUtil.registerEntity(EntityTurtleMeat.class, "turtlegun.turtleBomb", TurtleGun.instance, 0, 0, i++, false);
        addOverWorldSpawn(EntityTurtle.class, ModLibs.SPAWN_RATE, ModLibs.PACK_SIZE_MIN, ModLibs.PACK_SIZE_MAX, EnumCreatureType.CREATURE, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.RIVER, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WATER, BiomeDictionary.Type.WET);
    }

    private static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max, EnumCreatureType type, BiomeDictionary.Type... biomeTypes) {
        for (int i = 0; i < biomeTypes.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, type, BiomeDictionary.getBiomesForType(biomeTypes[i]));
        }
    }
}
