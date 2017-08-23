package net.silentchaos512.gems.item.tool;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.config.ConfigOptionToolClass;
import net.silentchaos512.gems.config.GemsConfig;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.gems.util.ToolHelper;
import net.silentchaos512.lib.registry.RecipeMaker;
import net.silentchaos512.lib.util.ItemHelper;
import net.silentchaos512.lib.util.StackHelper;

public class ItemGemKatana extends ItemGemSword {

  public ItemGemKatana() {

    super();
    setUnlocalizedName(SilentGems.RESOURCE_PREFIX + Names.KATANA);
  }

  public ConfigOptionToolClass getConfig() {

    return GemsConfig.katana;
  }

  @Override
  public ItemStack constructTool(ItemStack rod, ItemStack head) {

    if (getConfig().isDisabled)
      return StackHelper.empty();
    return ToolHelper.constructTool(this, rod, head);
  }

  @Override
  public ItemStack constructTool(boolean supercharged, ItemStack head) {

    if (getConfig().isDisabled)
      return StackHelper.empty();
    ItemStack rod = supercharged ? ModItems.craftingMaterial.toolRodGold : new ItemStack(Items.STICK);
    return ToolHelper.constructTool(this, rod, head);
  }

  @Override
  public float getMeleeDamageModifier() {

    return 2.0f;
  }

  @Override
  public float getMagicDamageModifier() {

    return 3.0f;
  }

  @Override
  public float getMeleeSpeedModifier() {

    return -2.2f;
  }

  @Override
  public float getDurabilityMultiplier() {

    return 0.75f;
  }

  @Override
  public void clGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {

    if (!ItemHelper.isInCreativeTab(item, tab))
      return;

    List<ItemStack> subItems = ToolHelper.getSubItems(item, 3);
    list.addAll(subItems);
  }

  @Override
  public void addRecipes(RecipeMaker recipes) {

    if (!getConfig().isDisabled) {
      recipe = ToolHelper.addExampleRecipe(this, "gg", "g ", "s ");
    }
  }

  @Override
  public String getName() {

    return Names.KATANA;
  }
}
