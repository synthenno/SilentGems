package net.silentchaos512.gems.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.api.ITool;
import net.silentchaos512.gems.api.tool.part.ToolPart;
import net.silentchaos512.gems.api.tool.part.ToolPartMain;
import net.silentchaos512.gems.api.tool.part.ToolPartRegistry;
import net.silentchaos512.gems.api.tool.part.ToolPartRod;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.lib.recipe.RecipeBaseSL;
import net.silentchaos512.lib.util.StackHelper;

public class RecipeCraftTool extends RecipeBaseSL {

  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv) {

    Item item = null;

    if (ModItems.paxel.recipe.matches(inv, null)) {
      item = ModItems.paxel;
    } else if (ModItems.scepter.recipe.matches(inv, null)) {
      item = ModItems.scepter;
    } else if (ModItems.tomahawk.recipe.matches(inv, null)) {
      item = ModItems.tomahawk;
    } else if (ModItems.katana.recipe.matches(inv, null)) {
      item = ModItems.katana;
    } else if (ModItems.pickaxe.recipe.matches(inv, null)) {
      item = ModItems.pickaxe;
    } else if (ModItems.sickle.recipe.matches(inv, null)) {
      item = ModItems.sickle;
    } else if (ModItems.axe.recipe.matches(inv, null)) {
      item = ModItems.axe;
    } else if (ModItems.sword.recipe.matches(inv, null)) {
      item = ModItems.sword;
    } else if (ModItems.hoe.recipe.matches(inv, null)) {
      item = ModItems.hoe;
    } else if (ModItems.dagger.recipe.matches(inv, null)) {
      item = ModItems.dagger;
    } else if (ModItems.shovel.recipe.matches(inv, null)) {
      item = ModItems.shovel;
    }

    if (item != null) {
      ItemStack head = getHead(inv);
      ItemStack rod = getRod(inv);
      return ((ITool) item).constructTool(rod, head);
    }

    return StackHelper.empty();
  }

  protected ItemStack getHead(InventoryCrafting inv) {

    ToolPart first = null;
    ItemStack ret = StackHelper.empty();

    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      ItemStack stack = inv.getStackInSlot(i);
      if (StackHelper.isValid(stack)) {
        ToolPart part = ToolPartRegistry.fromStack(stack);
        if (part instanceof ToolPartMain) {
          if (first == null) {
            first = part;
            ret = stack;
          } else if (first != part) {
            return null;
          }
        }
      }
    }

    return ret;
  }

  protected ItemStack getRod(InventoryCrafting inv) {

    ToolPart first = null;
    ItemStack ret = StackHelper.empty();

    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      ItemStack stack = inv.getStackInSlot(i);
      if (StackHelper.isValid(stack)) {
        ToolPart part = ToolPartRegistry.fromStack(stack);
        if (part instanceof ToolPartRod) {
          if (first == null) {
            first = part;
            ret = stack;
          } else if (first != part) {
            return null;
          }
        }
      }
    }

    return ret;
  }
}
