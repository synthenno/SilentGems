package net.silentchaos512.gems.lib.part;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.tool.part.ToolPartMain;
import net.silentchaos512.gems.item.armor.ItemGemArmor;
import net.silentchaos512.gems.item.tool.ItemGemBow;
import net.silentchaos512.gems.lib.EnumGem;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.lib.registry.IRegistryObject;

public class ToolPartGem extends ToolPartMain {

  EnumGem gem;
  boolean supercharged;
  Map<String, ModelResourceLocation> modelMap = Maps.newHashMap();

  public ToolPartGem(EnumGem gem, boolean supercharged) {

    super(SilentGems.RESOURCE_PREFIX + gem.name().toLowerCase() + (supercharged ? "_super" : ""),
        supercharged ? gem.getItemSuper() : gem.getItem());
    this.craftingOreDictName = supercharged ? gem.getItemSuperOreName() : gem.getItemOreName();
    this.gem = gem;
    this.supercharged = supercharged;
  }

  public EnumGem getGem() {

    return gem;
  }

  @Override
  public int getColor(ItemStack toolOrArmor) {

    if (toolOrArmor.getItem() instanceof ItemGemBow || toolOrArmor.getItem() instanceof ItemGemArmor) {
      return gem.getColor();
    }
    // Most gem tools use a separate texture for each head.
    return 0xFFFFFF;
  }

  @Override
  public EnumRarity getRarity() {

    return supercharged ? EnumRarity.RARE : EnumRarity.UNCOMMON;
  }

  @Override
  public String getDisplayName(ItemStack stack) {

    String itemName = supercharged ? Names.GEM_SUPER : Names.GEM;
    return SilentGems.localizationHelper.getLocalizedString("item",
        itemName + (stack.getItemDamage()) + ".name");
  }

  @Override
  public ModelResourceLocation getModel(ItemStack tool, int frame) {

    String name = ((IRegistryObject) tool.getItem()).getName();
    name = SilentGems.RESOURCE_PREFIX + name + "/" + name;

    // Gem number
    if (!(tool.getItem() instanceof ItemGemBow || tool.getItem() instanceof ItemGemArmor)) {
      // Bows and armor apply color via a color handler.
      name += gem.ordinal();
    }

    // Frame number. Currently just used by bows. Only the 4th frame (3) is distinct for bow
    // bodies, but I'm adding additional models in case anyone wants to do a resource pack.
    // FIXME: Not working?
    String frameNum = frame > 0 ? "_" + frame : "";

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
