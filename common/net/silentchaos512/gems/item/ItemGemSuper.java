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
}
