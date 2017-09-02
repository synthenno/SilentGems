package net.silentchaos512.gems.lib.soul;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.IArmor;
import net.silentchaos512.gems.api.tool.ToolStats;
import net.silentchaos512.gems.client.key.KeyTracker;
import net.silentchaos512.gems.item.ItemSoulGem;
import net.silentchaos512.gems.item.ItemToolSoul;
import net.silentchaos512.gems.util.ToolHelper;
import net.silentchaos512.lib.util.ChatHelper;
import net.silentchaos512.lib.util.LocalizationHelper;

public class ToolSoul {

  public static final float XP_FACTOR_KILLS = 0.4f;
  public static final float XP_FACTOR_BLOCK_MINED = 1.0f;
  public static final int AP_START = 10;
  public static final int AP_PER_LEVEL = 2;
  public static final int AP_REGEN_DELAY = 600;

  static final int BASE_XP = 30;
  static final float XP_CURVE_FACTOR = 2.5f;

  // Experience and levels
  int xp = 0;
  int level = 1;

  // Elements
  EnumSoulElement element1, element2 = EnumSoulElement.NONE;

  // Personality
  // TODO

  // Skills
  int actionPoints = 10;
  Map<SoulSkill, Integer> skills = new LinkedHashMap<>();

  // =================
  // = XP and Levels =
  // =================

  public void addXp(int amount, ItemStack tool, EntityPlayer player) {

    xp += amount * 10;
    if (xp >= getXpToNextLevel()) {
      levelUp(tool, player);
    }
  }

  public int getXp() {

    return xp;
  }

  public int getXpToNextLevel() {

    int target = level + 1;
    return BASE_XP * (int) Math.pow(target, XP_CURVE_FACTOR);
  }

  public int getLevel() {

    return level;
  }

  protected void levelUp(ItemStack tool, EntityPlayer player) {

    ++level;
    String line = String.format("Your %s is now level %d!", tool.getDisplayName(), level);
    ChatHelper.sendMessage(player, line);
    player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP,
        SoundCategory.PLAYERS, 1.0f, 1.0f);

    addActionPoints(AP_PER_LEVEL);

    // Learn new skill?
    SoulSkill toLearn = SoulSkill.selectSkillToLearn(this, tool);
    if (toLearn != null) {
      addOrLevelSkill(toLearn, player);
    }

    ToolHelper.recalculateStats(tool);

    // Save soul to NBT
    ToolHelper.setSoul(tool, this);
  }

  // ======================
  // = Action Points (AP) =
  // ======================

  public int getActionPoints() {

    return actionPoints;
  }

  public void addActionPoints(int amount) {

    actionPoints = MathHelper.clamp(actionPoints + amount, 0, getMaxActionPoints());
  }

  public int getMaxActionPoints() {

    return AP_START + AP_PER_LEVEL * (level - 1);
  }

  // ============
  // = Elements =
  // ============

  public EnumSoulElement getPrimaryElement() {

    return element1;
  }

  public EnumSoulElement getSecondaryElement() {

    return element2;
  }

  // ==========
  // = Skills =
  // ==========

  public void addOrLevelSkill(SoulSkill skill, EntityPlayer player) {

    if (skills.containsKey(skill)) {
      int level = skills.get(skill);
      if (level < skill.maxLevel) {
        ++level;
        skills.put(skill, level);
        if (player != null) {
          ChatHelper.sendMessage(player, "Skill level up: " + skill.getLocalizedName(level));
        }
      }
    } else {
      skills.put(skill, 1);
      if (player != null) {
        ChatHelper.sendMessage(player, "Learned skill: " + skill.getLocalizedName(1));
      }
    }
  }

  public boolean hasSkill(SoulSkill skill) {

    return skills.containsKey(skill);
  }

  public void addInformation(ItemStack stack, World world, List<String> list, boolean advanced) {

    LocalizationHelper loc = SilentGems.localizationHelper;

    // Level, XP, AP
    list.add(loc.getMiscText("ToolSoul.level", level, xp, getXpToNextLevel()));
    list.add(loc.getMiscText("ToolSoul.actionPoints", actionPoints, getMaxActionPoints()));

    if (KeyTracker.isControlDown() || stack.getItem() instanceof ItemToolSoul) {
      // Display elements.
      String e1 = element1.getDisplayName();
      String e2 = element2.getDisplayName();
      String elements = e1 + (e2.equalsIgnoreCase("none") ? "" : ", " + e2);
      list.add(loc.getMiscText("ToolSoul.elements", elements));
    }
    if (KeyTracker.isControlDown()) {
      // Display skills.
      for (Entry<SoulSkill, Integer> entry : skills.entrySet()) {
        SoulSkill skill = entry.getKey();
        int level = entry.getValue();
        list.add("  " + skill.getLocalizedName(level));
      }
    } else {
      list.add(TextFormatting.GOLD + loc.getMiscText("Tooltip.keyForSkills"));
    }
  }

  public void applyToStats(ToolStats stats) {

    for (Entry<SoulSkill, Integer> entry : skills.entrySet()) {
      SoulSkill skill = entry.getKey();
      int level = entry.getValue();
      skill.applyToStats(stats, level);
    }
  }

  public static ToolSoul construct(ItemSoulGem.Soul... souls) {

    // Soul weight map
    Map<EnumSoulElement, Integer> elements = new HashMap<>();
    for (ItemSoulGem.Soul soul : souls) {
      int current = elements.containsKey(soul.element1) ? elements.get(soul.element1) : 0;
      elements.put(soul.element1, current + 5);
      if (soul.element2 != EnumSoulElement.NONE) {
        current = elements.containsKey(soul.element2) ? elements.get(soul.element2) : 0;
        elements.put(soul.element2, current + 3);
      }
    }

    // Highest weight becomes element 1, second becomes element 2.
    ToolSoul toolSoul = new ToolSoul();
    // Primary
    toolSoul.element1 = selectHighestWeight(elements);
    elements.remove(toolSoul.element1);
    // Secondary (if any are left)
    if (!elements.isEmpty()) {
      toolSoul.element2 = selectHighestWeight(elements);
    }

    return toolSoul;
  }

  private static EnumSoulElement selectHighestWeight(Map<EnumSoulElement, Integer> elements) {

    EnumSoulElement element = EnumSoulElement.NONE;
    int highestWeight = 0;

    for (Entry<EnumSoulElement, Integer> entry : elements.entrySet()) {
      EnumSoulElement elementInMap = entry.getKey();
      int weightInMap = entry.getValue();
      if (weightInMap > highestWeight
          || (weightInMap == highestWeight && elementInMap.weight > element.weight)) {
        // This element takes priority over the previous best match.
        element = entry.getKey();
        highestWeight = entry.getValue();
      }
    }

    return element;
  }

  // Variables used by updateTick
  int ticksExisted = 0;
  // Salting update delays by tool soul to give appearance of randomness.
  final int apRegenSalt = SilentGems.random.nextInt(AP_REGEN_DELAY / 2);
  final int skillActivateSalt = SilentGems.random.nextInt(SoulSkill.SKILL_ACTIVATE_DELAY / 2);

  public void updateTick(ItemStack tool, EntityPlayer player) {

    ++ticksExisted;
    // Regen action points
    int regenDelay = AP_REGEN_DELAY;
    if (SilentGems.BUILD_NUM == 0) {
      regenDelay /= 10;
    }
    if ((ticksExisted + apRegenSalt) % regenDelay == 0) {
      addActionPoints(1);
    }

    // Try to activate skills?
    boolean isInHand = player.getHeldItemMainhand() == tool || player.getHeldItemOffhand() == tool;
    boolean isArmor = tool.getItem() instanceof IArmor;
    int time = ticksExisted + skillActivateSalt;
    if (isInHand || isArmor) {
      for (SoulSkill skill : skills.keySet()) {
        if (time % skill.getActivateDelay() == 0) {
          if (skill.activate(this, tool, player)) {
            addActionPoints(-skill.apCost);
            // TODO: Chat message?
            ChatHelper.sendMessage(player, "Activated skill " + skill.id);
            // Only one skill use per activate cycle.
            break;
          }
        }
      }
    }
  }

  public static ToolSoul readFromNBT(NBTTagCompound tags) {

    ToolSoul soul = new ToolSoul();

    String e1 = tags.getString("element1");
    String e2 = tags.getString("element2");
    for (EnumSoulElement element : EnumSoulElement.values()) {
      if (element.name().equalsIgnoreCase(e1)) {
        soul.element1 = element;
      } else if (element.name().equalsIgnoreCase(e2)) {
        soul.element2 = element;
      }
    }

    soul.xp = tags.getInteger("xp");
    soul.level = tags.getInteger("level");
    soul.actionPoints = tags.getInteger("ap");

    // Load skills
    soul.skills.clear();
    NBTTagList tagList = tags.getTagList("skills", 10);
    for (int i = 0; i < tagList.tagCount(); ++i) {
      NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
      SoulSkill skill = SoulSkill.getById(tagCompound.getString("id"));
      if (skill != null) {
        int level = tagCompound.getShort("level");
        soul.skills.put(skill, level);
        SilentGems.logHelper.debug("  Skill: " + skill.getLocalizedName(level));
      } else {
        SilentGems.logHelper.debug("  No skill with id " + tagCompound.getString("id") + "!");
      }
    }

    return soul;
  }

  public void writeToNBT(NBTTagCompound tags) {

    tags.setString("element1", this.element1.name());
    tags.setString("element2", this.element2.name());
    tags.setInteger("xp", xp);
    tags.setInteger("level", level);
    tags.setInteger("ap", actionPoints);

    // Save skills
    NBTTagList tagList = new NBTTagList();
    for (Entry<SoulSkill, Integer> entry : skills.entrySet()) {
      SoulSkill skill = entry.getKey();
      int level = entry.getValue();
      NBTTagCompound tagCompound = new NBTTagCompound();
      tagCompound.setString("id", skill.id);
      tagCompound.setShort("level", (short) level);
      tagList.appendTag(tagCompound);
    }
    tags.setTag("skills", tagList);
  }

  @Override
  public String toString() {

    return "ToolSoul{" + "Level: " + level + ", XP: " + xp + ", Elements: {" + element1.name()
        + ", " + element2.name() + "}" + "}";
  }
}
