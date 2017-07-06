package net.silentchaos512.gems.item;

import java.util.Locale;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.lib.EnumGem;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.lib.item.ItemSL;

public class ItemGemSuper extends ItemSL {

  public ItemGemSuper() {

    super(EnumGem.values().length, SilentGems.MODID, Names.GEM_SUPER);
  }

  @Override
  public boolean hasEffect(ItemStack stack) {

    return true;
  }

  @Override
  public void getModels(Map<Integer, ModelResourceLocation> models) {

    int i;
    String fullName = getFullName().toLowerCase(Locale.US).replaceFirst("super$", "");

    for (i = 0; i < 16; ++i)
      models.put(i, new ModelResourceLocation(fullName + i, "inventory"));
    for (i = 0; i < 16; ++i)
      models.put(i + 16, new ModelResourceLocation(fullName + "dark" + i, "inventory"));
    for (i = 0; i < 16; ++i)
      models.put(i + 32, new ModelResourceLocation(fullName + "light" + i, "inventory"));
  }
}
