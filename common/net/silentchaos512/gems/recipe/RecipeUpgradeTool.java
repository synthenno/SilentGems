package net.silentchaos512.gems.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.api.ITool;
import net.silentchaos512.gems.item.ItemTipUpgrade;
import net.silentchaos512.lib.recipe.RecipeBaseSL;
import net.silentchaos512.lib.util.StackHelper;


public class RecipeUpgradeTool extends RecipeBaseSL {

  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv) {

    ItemStack tool = StackHelper.empty();
    List<ItemStack> upgrades = new ArrayList<>();

    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      ItemStack stack = inv.getStackInSlot(i);
      if (StackHelper.isValid(stack)) {
        if (stack.getItem() instanceof ITool) {
          if (StackHelper.isValid(tool)) {
            return StackHelper.empty();
          }
          tool = stack;
        } else if (stack.getItem() instanceof ItemTipUpgrade) {
          upgrades.add(stack);
        }
      }
    }

    if (StackHelper.isEmpty(tool) || upgrades.isEmpty()) {
      return StackHelper.empty();
    }

    ItemStack result = StackHelper.safeCopy(tool);

    for (ItemStack upgrade : upgrades) {
      // TODO: Upgrade interface?
      ((ItemTipUpgrade) upgrade.getItem()).applyToTool(result, upgrade);
    }

    return result;
  }
}
