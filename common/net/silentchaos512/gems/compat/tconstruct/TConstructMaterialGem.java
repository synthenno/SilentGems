package net.silentchaos512.gems.compat.tconstruct;

import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.lib.EnumGem;
import net.silentchaos512.gems.lib.Names;
import slimeknights.tconstruct.library.materials.Material;

public class TConstructMaterialGem extends Material {

  EnumGem gem;
  boolean supercharged;

  public TConstructMaterialGem(EnumGem gem, boolean supercharged) {

    super("silentgems:" + gem.name().toLowerCase() + (supercharged ? "_super" : ""), gem.getColor());
    this.gem = gem;
    this.supercharged = supercharged;

//    setCraftable(true);
//    addCommonItems(tier == EnumMaterialTier.SUPER ? gem.getItemSuperOreName() : gem.getItemOreName());
//    addItem(tier == EnumMaterialTier.SUPER ? gem.getItemSuper() : gem.getItem(), VALUE_Gem, VALUE_Gem);
//    setRepresentativeItem(tier == EnumMaterialTier.SUPER ? gem.getItemSuper() : gem.getItem());
  }

  @Override
  public String getLocalizedName() {

    int meta = gem.ordinal();
    String itemName = supercharged ? Names.GEM_SUPER : Names.GEM;
    return SilentGems.localizationHelper.getLocalizedString("item", itemName + meta + ".name");
  }
}
