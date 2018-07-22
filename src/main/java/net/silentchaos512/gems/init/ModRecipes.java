package net.silentchaos512.gems.init;

import net.minecraft.item.crafting.IRecipe;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModRecipes implements IRegistrationHandler<IRecipe> {

    public static final boolean ADD_SOUL_RECIPES = true;

    @Override
    public void registerAll(SRegistry reg) {

//        RecipeMaker recipes = reg.recipes;

        // Chaos Essence creation.
//    SilentGemsAPI.addAltarRecipe(ModItems.craftingMaterial.chaosEssence,
//        ModItems.craftingMaterial.getStack(Names.CHAOS_ESSENCE_SHARD, 4), 240000,
//        new ItemStack(Items.DIAMOND));

        // Ender Essence creation.
//    SilentGemsAPI.addAltarRecipe(ModItems.craftingMaterial.enderEssence,
//        ModItems.craftingMaterial.getStack(Names.ENDER_ESSENCE_SHARD, 4), 240000,
//        ModItems.craftingMaterial.enderSlimeBall);

        // Light <--> Dark gem conversion.
//    ItemStack slimeBall = new ItemStack(Items.SLIME_BALL);
//    ItemStack magmaCream = new ItemStack(Items.MAGMA_CREAM);
//    ItemStack enderSlime = ModItems.craftingMaterial.enderSlimeBall;
//    for (int i = 0; i < 16; ++i) {
//      EnumGem classic = EnumGem.values()[i];
//      EnumGem dark = EnumGem.values()[i + 16];
//      EnumGem light = EnumGem.values()[i + 32];
//      ItemStack classicShards = new ItemStack(ModItems.gemShard, 6, classic.ordinal());
//      ItemStack darkShards = new ItemStack(ModItems.gemShard, 6, dark.ordinal());
//      ItemStack lightShards = new ItemStack(ModItems.gemShard, 6, light.ordinal());
//      SilentGemsAPI.addAltarRecipe(darkShards, classic.getItem(), 80000, magmaCream);
//      SilentGemsAPI.addAltarRecipe(classicShards, dark.getItem(), 80000, slimeBall);
//      SilentGemsAPI.addAltarRecipe(lightShards, classic.getItem(), 80000, enderSlime);
//      SilentGemsAPI.addAltarRecipe(classicShards, light.getItem(), 80000, slimeBall);
//    }

//        recipes.addCustomRecipe("apply_enchantment_token", new RecipeApplyEnchantmentToken());
//        recipes.addCustomRecipe("chaos_gem_upgrade", new RecipeChaosGemUpgrade());
//        recipes.addCustomRecipe("name_plate_use", new RecipeNamePlate());
//        recipes.addCustomRecipe("holding_gem_set_block", new RecipeHoldingGemSetBlock());
//        if (ADD_SOUL_RECIPES) {
//            recipes.addCustomRecipe("craft_tool_soul", new RecipeToolSoul());
//            recipes.addCustomRecipe("apply_tool_soul", new RecipeApplyToolSoul());
//        }
    }
}
