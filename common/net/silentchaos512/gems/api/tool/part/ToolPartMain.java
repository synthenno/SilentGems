package net.silentchaos512.gems.api.tool.part;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.gems.api.tool.ToolStats;
import net.silentchaos512.gems.init.ModItems;

public class ToolPartMain extends ToolPart {

  public ToolPartMain(String key, ItemStack craftingStack) {

    super(key, craftingStack);
  }

  public ToolPartMain(String key, ItemStack craftingStack, String oreName) {

    super(key, craftingStack, oreName);
  }

  @Override
  public final void applyStats(ToolStats stats) {

    stats.durability += getDurability();
    stats.harvestSpeed += getHarvestSpeed();
    stats.meleeDamage += getMeleeDamage();
    stats.magicDamage += getMagicDamage();
    stats.meleeSpeed += getMeleeSpeed();
    stats.enchantability += getEnchantability();
    stats.protection += getProtection();
    stats.harvestLevel = Math.max(getHarvestLevel(), stats.harvestLevel);
  }

  @Override
  public int getDurability() {

    return 0;
  }

  @Override
  public float getHarvestSpeed() {

    return 0;
  }

  @Override
  public int getHarvestLevel() {

    return 0;
  }

  @Override
  public float getMeleeDamage() {

    return 0;
  }

  @Override
  public float getMagicDamage() {

    return 0;
  }

  @Override
  public int getEnchantability() {

    return 0;
  }

  @Override
  public float getMeleeSpeed() {

    return 0;
  }

  @Override
  public float getProtection() {

    return 0;
  }

  public ItemStack getPreferredRod() {

    if (getRarity().ordinal() >= EnumRarity.RARE.ordinal()) {
      return ModItems.craftingMaterial.toolRodGold;
    } else {
      return new ItemStack(Items.STICK);
    }
  }
}
