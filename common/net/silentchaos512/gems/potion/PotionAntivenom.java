package net.silentchaos512.gems.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.silentchaos512.gems.SilentGems;

public class PotionAntivenom extends Potion {

  public PotionAntivenom() {

    super(false, 0xa8ef75);
    setPotionName("effect." + SilentGems.RESOURCE_PREFIX + "antivenom");
    setBeneficial();
  }

  @Override
  public void performEffect(EntityLivingBase entityLiving, int amplifier) {

    entityLiving.removePotionEffect(MobEffects.POISON);
  }

  @Override
  public boolean isReady(int duration, int amplifier) {

    return true;
  }
}
