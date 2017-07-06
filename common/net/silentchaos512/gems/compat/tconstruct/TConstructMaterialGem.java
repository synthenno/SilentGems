package net.silentchaos512.gems.compat.tconstruct;

import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.lib.EnumMaterialTier;
import net.silentchaos512.gems.lib.EnumGem;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.materials.Material;

public class TConstructMaterialGem extends Material {

  EnumGem gem;
  EnumMaterialTier tier;

  public TConstructMaterialGem(EnumGem gem, EnumMaterialTier tier) {

    super("silentgems:" + gem.name().toLowerCase() + (tier == EnumMaterialTier.SUPER ? "_super" : ""), gem.getColor());
    this.gem = gem;
    this.tier = tier;

    ItemStack gemStack = tier == EnumMaterialTier.SUPER ? gem.getItemSuper() : gem.getItem();
    String gemOreDict = tier == EnumMaterialTier.SUPER ? gem.getItemSuperOreName() : gem.getItemOreName();
    setCraftable(true);
    addItem(gemStack, VALUE_Gem, VALUE_Gem);
    addItem(gemOreDict, VALUE_Gem, VALUE_Gem);
    setRepresentativeItem(gemStack);
  }

  @Override
  public String getLocalizedName() {

    int meta = gem.ordinal() + (tier == EnumMaterialTier.SUPER ? 32 : 0); 
    return SilentGems.localizationHelper.getLocalizedString("item", "Gem" + meta + ".name");
  }
}
