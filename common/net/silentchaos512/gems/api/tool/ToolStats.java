package net.silentchaos512.gems.api.tool;

import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.api.ITool;
import net.silentchaos512.gems.api.tool.part.ToolPart;

public final class ToolStats {

  private final ToolPart[] parts;

  public final ItemStack tool;
  public float durability = 0f;
  public float harvestSpeed = 0f;
  public float meleeDamage = 0f;
  public float magicDamage = 0f;
  public float meleeSpeed = 0f;
  public float enchantability = 0f;
  public float protection = 0f;
  public int harvestLevel = 0;

  public ToolStats(ItemStack tool, ToolPart[] parts) {

    this.tool = tool;
    this.parts = parts;
  }

  public ToolStats calculate() {

    if (parts.length == 0)
      return this;

    for (ToolPart part : parts) {
      if (part != null) {
        part.applyStats(this);
      }
    }

    // Tool class multipliers
    if (tool.getItem() instanceof ITool) {
      ITool itool = (ITool) tool.getItem();
      durability *= itool.getDurabilityMultiplier();
      harvestSpeed *= itool.getHarvestSpeedMultiplier();
    }

    return this;
  }
}
