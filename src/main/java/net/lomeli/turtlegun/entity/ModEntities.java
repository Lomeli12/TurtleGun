package net.lomeli.turtlegun.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;

import net.minecraftforge.common.BiomeDictionary;

import cpw.mods.fml.common.registry.EntityRegistry;

import net.lomeli.turtlegun.lib.ModLibs;

public class ModEntities {
    public static void loadEntities() {
        registerEntity(EntityTurtle.class, "turtlegun.turtle", 0x91671D, 894731);
        addOverWorldSpawn(EntityTurtle.class, ModLibs.SPAWN_RATE, ModLibs.PACK_SIZE_MIN, ModLibs.PACK_SIZE_MAX, EnumCreatureType.creature, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.MUSHROOM, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.RIVER, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WATER, BiomeDictionary.Type.WET);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID);
        if (bkEggColor != 99999 && fgEggColor != 99999)
            EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, bkEggColor, fgEggColor));
    }

    private static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max, EnumCreatureType type, BiomeDictionary.Type... biomeTypes) {
        for (int i = 0; i < biomeTypes.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, type, BiomeDictionary.getBiomesForType(biomeTypes[i]));
        }
    }
}
