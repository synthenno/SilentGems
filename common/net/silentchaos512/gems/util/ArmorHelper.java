package net.silentchaos512.gems.util;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.silentchaos512.gems.api.tool.part.ToolPart;
import net.silentchaos512.gems.api.tool.part.ToolPartRegistry;
import net.silentchaos512.gems.item.ToolRenderHelper;
import net.silentchaos512.lib.util.StackHelper;

public class ArmorHelper {

  public static final int PROTECTION_CAP = 39;

  /*
   * NBT keys
   */

  public static final String NBT_ROOT_CONSTRUCTION = "SGConstruction";
  public static final String NBT_ROOT_DECORATION = "SGDecoration";
  public static final String NBT_ROOT_PROPERTIES = "SGProperties";
  public static final String NBT_ROOT_STATISTICS = "SGStatistics";

  // Stats
  public static final String NBT_PROP_DURABILITY = "Durability";
  public static final String NBT_PROP_PROTECTION = "Protection";
  public static final String NBT_PROP_ENCHANTABILITY = "Enchantability";

  // Statistics
  public static final String NBT_STATS_ORIGINAL_OWNER = "OriginalOwner";
  public static final String NBT_STATS_DAMAGE_TAKEN = "DamageTaken";

  public static void recalculateStats(ItemStack armor) {

    ToolPart part = getMaterial(armor);

    if (part == null) {
      return;
    }

    float sumDurability = 0f;
    float sumProtection = 0f;
    // TODO: Toughness?
    float sumEnchantability = 0f;

    Set<ToolPart> uniqueParts = Sets.newConcurrentHashSet();

    // Set render colors
//    ToolPart[] renderParts = getRenderParts(armor);
//    for (int i = 0; i < renderParts.length; ++i) {
//      if (renderParts[i] != null) {
//        setTagInt(armor, ToolRenderHelper.NBT_MODEL_INDEX, "Layer" + i + "Color",
//            renderParts[i].getColor(armor));
//      }
//    }

    // Stats
    float durability = part.getDurability();
    float protection = part.getProtection();
    float enchantability = part.getEnchantability();

    // Set NBT
    setTagInt(armor, NBT_ROOT_PROPERTIES, NBT_PROP_DURABILITY, (int) durability);
    setTagFloat(armor, NBT_ROOT_PROPERTIES, NBT_PROP_PROTECTION, protection);
    setTagInt(armor, NBT_ROOT_PROPERTIES, NBT_PROP_ENCHANTABILITY, (int) enchantability);
  }

  /*
   * Armor Properties
   */

  public static float getProtection(ItemStack armor) {

    if (isBroken(armor))
      return 0;

    return getTagFloat(armor, NBT_ROOT_PROPERTIES, NBT_PROP_PROTECTION);
  }

  public static int getItemEnchantability(ItemStack armor) {

    return getTagInt(armor, NBT_ROOT_PROPERTIES, NBT_PROP_ENCHANTABILITY);
  }

  public static int getMaxDamage(ItemStack armor) {

    return getTagInt(armor, NBT_ROOT_PROPERTIES, NBT_PROP_DURABILITY);
  }

  public static boolean isBroken(ItemStack armor) {

    return armor.getItemDamage() >= armor.getItem().getMaxDamage(armor);
  }

  // ==========================================================================
  // Armor construction and decoration
  // ==========================================================================

  public static ToolPart getMaterial(ItemStack armor) {

    return ToolHelper.getPartHead(armor);
  }

  public static ToolPart getRenderMaterial(ItemStack armor) {

    if (StackHelper.isEmpty(armor)) {
      return null;
    }
    // TODO
    return getMaterial(armor);
  }

  public static int getRenderColor(ItemStack armor) {

    return getTagInt(armor, ToolRenderHelper.NBT_MODEL_INDEX, "Color", 0xFFFFFF);
  }

  // public static String getRenderColorString(ItemStack stack)
  // {
  // StringBuilder toReturn = new StringBuilder();
  // int[] colors = getRenderColorList(stack);
  // for (int color : colors)
  // {
  // toReturn.append(color);
  // }
  // return toReturn.toString();
  // }

  public static ItemStack constructArmor(Item item, ItemStack material) {

    if (StackHelper.isEmpty(material)) {
      String str = "ArmorHelper.constructArmor: empty part! " + material;
      throw new IllegalArgumentException(str);
    }

    ItemStack result = new ItemStack(item);
    result.setTagCompound(new NBTTagCompound());

    // Construction
    ToolPart part;

    part = ToolPartRegistry.fromStack(material);
    setTagPart(result, ToolHelper.NBT_PART_HEAD, part);

    recalculateStats(result);

    return result;
  }

  // ==========================================================================
  // NBT helper methods
  // ==========================================================================

  private static NBTTagCompound getRootTag(ItemStack tool, String key) {

    if (key != null && !key.isEmpty()) {
      if (!tool.getTagCompound().hasKey(key)) {
        tool.getTagCompound().setTag(key, new NBTTagCompound());
      }
      return tool.getTagCompound().getCompoundTag(key);
    }
    return tool.getTagCompound();
  }

  private static void initRootTag(ItemStack tool) {

    if (!tool.hasTagCompound())
      tool.setTagCompound(new NBTTagCompound());
  }

  private static int getTagInt(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound())
      return 0;
    return getRootTag(tool, root).getInteger(name);
  }

  private static int getTagInt(ItemStack tool, String root, String name, int defaultValue) {

    if (!tool.hasTagCompound() || !getRootTag(tool, root).hasKey(name))
      return defaultValue;
    return getRootTag(tool, root).getInteger(name);
  }

  private static void setTagInt(ItemStack tool, String root, String name, int value) {

    initRootTag(tool);
    getRootTag(tool, root).setInteger(name, value);
  }

  private static float getTagFloat(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound())
      return 0.0f;
    return getRootTag(tool, root).getFloat(name);
  }

  private static void setTagFloat(ItemStack tool, String root, String name, float value) {

    initRootTag(tool);
    getRootTag(tool, root).setFloat(name, value);
  }

  private static boolean getTagBoolean(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound())
      return false;
    return getRootTag(tool, root).getBoolean(name);
  }

  private static void setTagBoolean(ItemStack tool, String root, String name, boolean value) {

    initRootTag(tool);
    getRootTag(tool, root).setBoolean(name, value);
  }

  private static String getTagString(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound())
      return "";
    return getRootTag(tool, root).getString(name);
  }

  private static void setTagString(ItemStack tool, String root, String name, String value) {

    initRootTag(tool);
    getRootTag(tool, root).setString(name, value);
  }

  private static void setTagPart(ItemStack tool, String name, ToolPart part) {

    initRootTag(tool);
    getRootTag(tool, NBT_ROOT_CONSTRUCTION).setString(name, part.getKey());
  }

  // --------------
  // Statistics NBT
  // --------------

  public static String getOriginalOwner(ItemStack tool) {

    return getTagString(tool, NBT_ROOT_STATISTICS, NBT_STATS_ORIGINAL_OWNER);
  }

  public static void setOriginalOwner(ItemStack tool, EntityPlayer player) {

    setOriginalOwner(tool, player.getName());
  }

  public static void setOriginalOwner(ItemStack tool, String name) {

    ToolHelper.setOriginalOwner(tool, name);
  }

  public static float getStatDamageTaken(ItemStack tool) {

    return getTagFloat(tool, NBT_ROOT_STATISTICS, NBT_STATS_DAMAGE_TAKEN);
  }

  public static void incrementStatDamageTaken(ItemStack tool, float amount) {

    setTagFloat(tool, NBT_ROOT_STATISTICS, NBT_STATS_DAMAGE_TAKEN,
        getStatDamageTaken(tool) + amount);
  }
}
