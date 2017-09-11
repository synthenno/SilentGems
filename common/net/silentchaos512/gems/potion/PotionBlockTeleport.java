package net.silentchaos512.gems.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.gems.SilentGems;

public class PotionBlockTeleport extends Potion {

  public PotionBlockTeleport() {
    super(true, 0x0);
    setPotionName("effect." + SilentGems.RESOURCE_PREFIX + "block_teleport");
  }

  @SubscribeEvent
  public void onEntityTeleport(EnderTeleportEvent event) {

    if (event.getEntityLiving() != null && event.getEntityLiving().isPotionActive(this)) {
      event.setCanceled(true);
    }
  }
}
