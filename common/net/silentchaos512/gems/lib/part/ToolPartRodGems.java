package net.silentchaos512.gems.lib.part;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.tool.part.ToolPartRod;
import net.silentchaos512.gems.item.tool.ItemGemShield;
import net.silentchaos512.lib.registry.IRegistryObject;

public class ToolPartRodGems extends ToolPartRod {

  Map<String, ModelResourceLocation> modelMap = Maps.newHashMap();

  public final String rodName;
  public final int color;

  public ToolPartRodGems(String name, ItemStack stack, int color,
      float durabilityMulti, float harvestSpeedMulti, float meleeDamageMulti,
      float magicDamageMulti, float enchantabilityMulti) {

    super(SilentGems.RESOURCE_PREFIX + name, stack, durabilityMulti, harvestSpeedMulti,
        meleeDamageMulti, magicDamageMulti, enchantabilityMulti);
    this.rodName = name;
    this.color = color;
  }

  public ToolPartRodGems(String name, ItemStack stack, int color,
      String oreName, float durabilityMulti, float harvestSpeedMulti, float meleeDamageMulti,
      float magicDamageMulti, float enchantabilityMulti) {

    super(SilentGems.RESOURCE_PREFIX + name, stack, oreName, durabilityMulti, harvestSpeedMulti,
        meleeDamageMulti, magicDamageMulti, enchantabilityMulti);
    this.rodName = name;
    this.color = color;
  }

  @Override
  public ModelResourceLocation getModel(ItemStack tool, int frame) {

    String name = ((IRegistryObject) tool.getItem()).getName();
    name = SilentGems.RESOURCE_PREFIX + name + "/" + name + "_" + rodName;

    if (modelMap.containsKey(name)) {
      return modelMap.get(name);
    }

    name = name.toLowerCase();
    ModelResourceLocation model = new ModelResourceLocation(name, "inventory");
    modelMap.put(name, model);
    return model;
  }

  @Override
  public int getColor(ItemStack toolOrArmor) {

    //return color;
    return 0xFFFFFF;
  }
}
