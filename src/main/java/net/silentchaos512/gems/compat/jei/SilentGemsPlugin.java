package net.silentchaos512.gems.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.init.ModBlocks;

@JEIPlugin
public class SilentGemsPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelper;

    @Override
    public void register(IModRegistry reg) {

        jeiHelper = reg.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelper.getGuiHelper();

        doItemBlacklist(jeiHelper.getIngredientBlacklist());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
    }

    private void doItemBlacklist(IIngredientBlacklist list) {

        // Hide certain blocks/items
        list.addIngredientToBlacklist(new ItemStack(ModBlocks.fluffyPuffPlant));
    }

    @Override
    public void registerIngredients(IModIngredientRegistration arg0) {

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry reg) {
    }
}
