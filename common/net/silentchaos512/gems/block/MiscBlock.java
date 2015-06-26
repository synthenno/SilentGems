package net.silentchaos512.gems.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.silentchaos512.gems.configuration.Config;
import net.silentchaos512.gems.core.util.RecipeHelper;
import net.silentchaos512.gems.item.CraftingMaterial;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.gems.lib.Strings;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class MiscBlock extends BlockSG implements IFuelHandler {

  public final static String[] names = {
    Names.CHAOS_ESSENCE_BLOCK,
    Names.CHAOS_ESSENCE_BLOCK_REFINED,
    "reserved",
    Names.CHAOS_COAL_BLOCK
  };

  public MiscBlock() {

    super(Material.iron);
    icons = new IIcon[names.length];
    setHasSubtypes(true);
    setUnlocalizedName(Names.MISC_BLOCKS);
  }

  @Override
  public void addRecipes() {
    
    GameRegistry.registerFuelHandler(this);

    ItemStack chaosEssence = CraftingMaterial.getStack(Names.CHAOS_ESSENCE);
    ItemStack chaosEssenceRefined = CraftingMaterial.getStack(Names.CHAOS_ESSENCE_PLUS);
    ItemStack block = this.getStack(Names.CHAOS_ESSENCE_BLOCK);
    ItemStack blockRefined = this.getStack(Names.CHAOS_ESSENCE_BLOCK_REFINED);
    RecipeHelper.addCompressionRecipe(chaosEssence, block, 9);
    RecipeHelper.addCompressionRecipe(chaosEssenceRefined, blockRefined, 9);
  }
  
  @Override
  public int getBurnTime(ItemStack stack) {

    if (stack != null && stack.getItem() == Item.getItemFromBlock(this)
        && stack.getItemDamage() == getStack(Names.CHAOS_COAL_BLOCK).getItemDamage()) {
      return Config.chaosCoalBurnTime * 9;
    }
    return 0;
  }
  
  @Override
  public EnumRarity getRarity(ItemStack stack) {
    
    if (stack.getItemDamage() == 1) {
      return EnumRarity.rare;
    } else {
      return super.getRarity(stack);
    }
  }

  public static ItemStack getStack(String name) {

    for (int i = 0; i < names.length; ++i) {
      if (names[i].equals(name)) {
        return new ItemStack(ModBlocks.miscBlock, 1, i);
      }
    }

    return null;
  }

  public static ItemStack getStack(String name, int count) {

    for (int i = 0; i < names.length; ++i) {
      if (names[i].equals(name)) {
        return new ItemStack(ModBlocks.miscBlock, count, i);
      }
    }

    return null;
  }
  
  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < 4; ++i) {
      if (i != 2) { // TODO: Change later
        list.add(new ItemStack(this, 1, i));
      }
    }
  }

  @Override
  public void registerBlockIcons(IIconRegister iconRegister) {

    for (int i = 0; i < names.length; ++i) {
      icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + names[i]);
    }
  }
}