package net.silentchaos512.gems.api.tool.part;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.tool.ToolStats;
import net.silentchaos512.gems.config.GemsConfig;

/**
 * Represents a part that tools can be made from. Add-ons should not extend this class! Extend one of the subclasses
 * instead.
 * 
 * ToolPartMain is for heads and rod decoration. ToolPartGrip is rod attachments like wool. ToolPartRod is for rods.
 * ToolPartTip is for tips.
 * 
 * ToolPartRegistry is where tool parts are registered. See net.silentchaos512.gems.lib.part.ModParts for examples.
 * 
 * @author SilentChaos512
 *
 */
public abstract class ToolPart {

  /**
   * The unique ID for the part. I recommend prefixing it with your mod ID. Example: SilentGems:RodGold.
   */
  protected @Nonnull String key;
  /**
   * An item that represents the tool part. It's also the item that is used in crafting. For example, the items that
   * apply tip upgrades are the crafting stacks for those parts.
   * 
   * This variable may not be null. If you want multiple items to be used for this part, you may specify an ore
   * dictionary key to use.
   */
  protected @Nonnull ItemStack craftingStack;
  /**
   * The ore dictionary key to match for this part. This may be an empty string if you do not want to use the ore
   * dictionary.
   */
  protected @Nonnull String craftingOreDictName;

  public ToolPart(String key, ItemStack craftingStack) {

    this(key, craftingStack, "");
  }

  protected ToolPart(String key, ItemStack craftingStack, String craftingOreDictName) {

    this.key = key.toLowerCase();
    this.craftingStack = craftingStack;
    this.craftingOreDictName = craftingOreDictName;
  }

  public @Nonnull String getKey() {

    return key;
  }

  public @Nonnull ItemStack getCraftingStack() {

    return craftingStack;
  }

  public @Nonnull String getCraftingOreDictName() {

    return craftingOreDictName;
  }

  /**
   * Gets the color applied when rendering the part.
   * 
   * @param toolOrArmor
   *          The tool or armor stack being rendered.
   * @return
   */
  public int getColor(ItemStack toolOrArmor) {

    return 0xFFFFFF;
  }

  /**
   * Returns a display name for the part and given ItemStack. For ToolPartMain, this is used in constructing the tool's
   * name. Defaults to the stack's display name, which the player can set in an anvil.
   * 
   * @param partRep
   *          The ItemStack, typically the representative that is being used in crafting.
   * @return
   */
  public String getDisplayName(ItemStack partRep) {

    return partRep.getDisplayName();
  }

  /**
   * Gets the amount of durability to repair when decorating with this part.
   * 
   * @param toolOrArmor
   *          The tool or armor being decorating/repaired.
   * @param partRep
   *          The stack that represents the tool part.
   * @return The amount of durability to restore, usually a fraction of the tool's max.
   */
  @Deprecated
  public int getRepairAmount(ItemStack toolOrArmor, ItemStack partRep) {

    return 0;
  }

  @SideOnly(Side.CLIENT)
  public ModelResourceLocation getModel(ItemStack toolOrArmor, int frame) {

    return null;
  }

  public String getUnlocalizedName() {

    return key;
  }

  public boolean isBlacklisted(ItemStack partStack) {

    return GemsConfig.PART_BLACKLIST.contains(key);
  }

  public void applyStats(ToolStats stats) {

  }

  public abstract int getDurability();

  public abstract float getHarvestSpeed();

  public abstract int getHarvestLevel();

  public abstract float getMeleeDamage();

  public abstract float getMagicDamage();

  public abstract int getEnchantability();

  public abstract float getMeleeSpeed();

  // Only needed for armor.
  public abstract float getProtection();

  public EnumRarity getRarity() {

    return EnumRarity.COMMON;
  }
  
  @Override
  public String toString() {

    String str = "ToolPart{";
    str += "Key: " + getKey() + ", ";
    str += "CraftingStack: " + getCraftingStack() + ", ";
    str += "CraftingOreDictName: '" + getCraftingOreDictName() + "', ";
    str += "}";
    return str;
  }
}
