package net.silentchaos512.gems.entity;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.silentchaos512.gems.config.GemsConfig;
import net.silentchaos512.lib.registry.SRegistry;

public class ModEntities {

    public static void init(SRegistry reg) {
        reg.registerEntity(EntityEnderSlime.class, "EnderSlime", 64, 4, false, 0x003333, 0xAA00AA);
        if (GemsConfig.ENDER_SLIME_SPAWN_WEIGHT > 0) {
            EntityRegistry.addSpawn(EntityEnderSlime.class, GemsConfig.ENDER_SLIME_SPAWN_WEIGHT,
                    GemsConfig.ENDER_SLIME_SPAWN_MIN, GemsConfig.ENDER_SLIME_SPAWN_MAX,
                    EnumCreatureType.MONSTER, Biomes.SKY);
        }
    }
}
