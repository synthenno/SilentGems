package net.silentchaos512.gems.lib.part;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.IArmor;
import net.silentchaos512.gems.api.tool.part.ToolPartMain;
import net.silentchaos512.gems.item.ToolRenderHelper;
import net.silentchaos512.gems.item.tool.ItemGemBow;
import net.silentchaos512.gems.item.tool.ItemGemShield;
import net.silentchaos512.gems.lib.EnumGem;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.lib.registry.IRegistryObject;

public class ToolPartGem extends ToolPartMain {

  EnumGem gem;
  boolean supercharged;
  Map<String, ModelResourceLocation> modelMap = Maps.newHashMap();

  public ToolPartGem(EnumGem gem, boolean supercharged) {

    super(SilentGems.RESOURCE_PREFIX + gem.name().toLowerCase() + (supercharged ? "_super" : ""), supercharged ? gem.getItemSuper() : gem.getItem());
    this.craftingOreDictName = supercharged ? gem.getItemSuperOreName() : gem.getItemOreName();
    this.gem = gem;
    this.supercharged = supercharged;
  }

  public EnumGem getGem() {

    return gem;
  }

  @Override
  public int getColor(ItemStack toolOrArmor) {

    Item item = toolOrArmor.getItem();
    if (item instanceof IArmor || item instanceof ItemGemShield || item instanceof ItemGemBow)
      return gem.getColor();
    else
      return gem.ordinal() > 15 ? ToolRenderHelper.DARK_GEM_SHADE : 0xFFFFFF;
  }

  @Override
  public String getDisplayName(ItemStack stack) {

    String itemName = supercharged ? Names.GEM_SUPER : Names.GEM;
    return SilentGems.localizationHelper.getLocalizedString("item", itemName + (stack.getItemDamage()) + ".name");
  }

  @Override
  public ModelResourceLocation getModel(ItemStack tool, int frame) {

    String name = ((IRegistryObject) tool.getItem()).getName();
    name = SilentGems.RESOURCE_PREFIX + name + "/" + name + "_head";
    String frameNum = frame == 3 ? "_3" : "";

    if (modelMap.containsKey(name)) {
      return modelMap.get(name);
    }

    name = name.toLowerCase();
    ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
    modelMap.put(name, model);
    return model;
  }

  @Override
  public int getDurability() {

    return gem.getDurability(supercharged);
  }

  @Override
  public float getHarvestSpeed() {

    return gem.getMiningSpeed(supercharged);
  }

  @Override
  public int getHarvestLevel() {

    // TODO: Configs!
    return supercharged ? 4 : 2;
  }

  @Override
  public float getMeleeDamage() {

    return gem.getMeleeDamage(supercharged);
  }

  @Override
  public float getMagicDamage() {

    return gem.getMagicDamage(supercharged);
  }

  @Override
  public int getEnchantability() {

    return gem.getEnchantability(supercharged);
  }

  @Override
  public float getMeleeSpeed() {

    return gem.getMeleeSpeed(supercharged);
  }

  @Override
  public float getProtection() {

    return gem.getProtection(supercharged);
  }
}
