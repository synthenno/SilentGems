package net.silentchaos512.gems.util;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.ITool;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.gems.lib.EnumGem;
import net.silentchaos512.lib.util.EntityHelper;

public class ToolRandomizer {

  public static final float SECOND_GEM_CHANCE = 0.7f;
  public static final float THIRD_GEM_CHANCE = 0.5f;
  public static final float SUPER_TIER_CHANCE = 0.33f;

  private static final String[] NAME_ADJECTIVES_BASE = { "an abstract", "an ancient",
      "a bite-sized", "a brave", "a convenient", "a creepy", "a dashing", "a defenestrated",
      "a disturbed", "a dizzy", "a fanatical", "a grieving", "a languid", "a mysterious",
      "a psychotic", "a quaint", "a questionable", "a quizzical", "a redundant", "a sentient",
      "a sturdy", "a tasteful", "a thoughtful", "an unbiased", "an unloved", };
  private static final String[] NAME_NOUNS_BASE = { "alien", "alpaca", "bench", "buffalo", "cake",
      "cat", "crate", "dime", "egg", "fish", "fork", "guitar", "hammer", "hatred", "key", "lamp",
      "mitten", "pair of dice", "pendulum", "pizza", "potato", "rice cooker", "rock", "shelf",
      "sock puppet", "spoon", "square", "storm", "surprise", "toaster", "toothbrush", "toy", "tree",
      "twig", "wheel" };

  public List<String> nameAdjectives = Lists.newArrayList(NAME_ADJECTIVES_BASE);
  public List<String> nameNouns = Lists.newArrayList(NAME_NOUNS_BASE);

  public static ToolRandomizer INSTANCE = new ToolRandomizer();

  private ToolRandomizer() {

    for (String name : EntityHelper.getEntityNameList()) {
      String entityName = "entity." + name + ".name";
      String localizedName = SilentGems.localizationHelper.getLocalizedString(entityName);
      if (!localizedName.endsWith(".name")) {
        nameNouns.add(localizedName.toLowerCase());
      }
    }
  }

  public ItemStack randomize(ItemStack tool) {

    return randomize(tool, SUPER_TIER_CHANCE);
  }

  public ItemStack randomize(ItemStack tool, float superChance) {

    if (!(tool.getItem() instanceof ITool) || !ToolHelper.hasNoConstruction(tool))
      return tool;

    ITool itool = (ITool) tool.getItem();

    // Regular or super?
    boolean superTier = SilentGems.random.nextFloat() < superChance;


    // Select head
    EnumGem gem = EnumGem.getRandom();
    ItemStack head = superTier ? gem.getItemSuper() : gem.getItem();

    // Select rod
    ItemStack rod;
    if (superTier) {
      ItemStack[] choices = new ItemStack[] { ModItems.craftingMaterial.toolRodGold,
          ModItems.craftingMaterial.toolRodSilver, ModItems.craftingMaterial.toolRodIron };
      rod = choices[SilentGems.random.nextInt(choices.length)];
    } else {
      ItemStack[] choices = new ItemStack[] { new ItemStack(Items.STICK), new ItemStack(Items.BONE),
          ModItems.craftingMaterial.toolRodIron };
      rod = choices[SilentGems.random.nextInt(choices.length)];
    }

    ItemStack temp = ToolHelper.constructTool(tool.getItem(), rod, head);

    // Set name
    String ownerName = nameAdjectives.get(SilentGems.random.nextInt(nameAdjectives.size())) + " "
        + nameNouns.get(SilentGems.random.nextInt(nameNouns.size()));
    ToolHelper.setOriginalOwner(temp, TextFormatting.AQUA + ownerName);

    tool.setTagCompound(temp.getTagCompound());

    return temp;
  }
}
