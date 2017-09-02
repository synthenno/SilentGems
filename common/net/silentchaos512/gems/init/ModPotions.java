package net.silentchaos512.gems.init;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.potion.PotionAntivenom;
import net.silentchaos512.gems.potion.PotionSlowFall;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModPotions implements IRegistrationHandler<Potion> {

  public static final Potion SLOW_FALL = new PotionSlowFall();
  public static final Potion ANTIVENOM = new PotionAntivenom();

  @Override
  public void registerAll(SRegistry reg) {

    reg.registerPotion(SLOW_FALL, new ResourceLocation(SilentGems.RESOURCE_PREFIX + "slow_fall"));
    reg.registerPotion(ANTIVENOM, new ResourceLocation(SilentGems.RESOURCE_PREFIX + "antivenom"));
  }
}
