package net.silentchaos512.gems.init;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.potion.PotionAntivenom;
import net.silentchaos512.gems.potion.PotionBlockTeleport;
import net.silentchaos512.gems.potion.PotionSlowFall;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModPotions implements IRegistrationHandler<Potion> {

  public static final Potion SLOW_FALL = new PotionSlowFall();
  public static final Potion ANTIVENOM = new PotionAntivenom();
  public static final Potion BLOCK_TELEPORT = new PotionBlockTeleport();

  @Override
  public void registerAll(SRegistry reg) {

    reg.registerPotion(SLOW_FALL, new ResourceLocation(SilentGems.RESOURCE_PREFIX + "slow_fall"));
    reg.registerPotion(ANTIVENOM, new ResourceLocation(SilentGems.RESOURCE_PREFIX + "antivenom"));
    reg.registerPotion(BLOCK_TELEPORT, new ResourceLocation(SilentGems.RESOURCE_PREFIX + "block_teleport"));
    MinecraftForge.EVENT_BUS.register(BLOCK_TELEPORT);
  }
}
