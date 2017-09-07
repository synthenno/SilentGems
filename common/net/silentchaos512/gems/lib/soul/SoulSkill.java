package net.silentchaos512.gems.lib.soul;

import static net.silentchaos512.gems.lib.soul.EnumSoulElement.ALIEN;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.EARTH;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.FIRE;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.FLORA;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.ICE;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.LIGHTNING;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.METAL;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.MONSTER;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.VENOM;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.WATER;
import static net.silentchaos512.gems.lib.soul.EnumSoulElement.WIND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.tool.ToolStats;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.gems.init.ModPotions;
import net.silentchaos512.gems.potion.PotionSlowFall;
import net.silentchaos512.gems.skills.ToolSkill;
import net.silentchaos512.gems.util.ToolHelper;
import net.silentchaos512.lib.util.ChatHelper;
import net.silentchaos512.lib.util.PlayerHelper;
import net.silentchaos512.lib.util.StackHelper;

public class SoulSkill {

  public static final int SKILL_ACTIVATE_DELAY = 200;

  static Map<String, SoulSkill> SKILL_LIST = new HashMap<>();
  static final float STAT_BOOST_MULTI = 0.1f;

  public static SoulSkill SUPER_SKILL;
  public static SoulSkill DURABILITY_BOOST;
  public static SoulSkill HARVEST_SPEED_BOOST;
  public static SoulSkill MELEE_DAMAGE_BOOST;
  public static SoulSkill MAGIC_DAMAGE_BOOST;
  public static SoulSkill WARM;
  public static SoulSkill CHILL;
  public static SoulSkill ANTIVENOM;
  public static SoulSkill SLOW_FALL;
  public static SoulSkill CROP_GROWTH;
  public static SoulSkill WALL_CLIMB;
  public static SoulSkill COFFEE_POT;

  public static void init() {

    //@formatter:off
    SUPER_SKILL = new SoulSkill("super_skill", 1, 0, 5, 0.0);
    DURABILITY_BOOST = new SoulSkill("durability_boost", 5, 0, 0, 0.0, EARTH, METAL);
    HARVEST_SPEED_BOOST = new SoulSkill("harvest_speed_boost", 5, 0, 0, 0.0, WIND, LIGHTNING);
    MELEE_DAMAGE_BOOST = new SoulSkill("melee_damage_boost", 5, 0, 0, 0.0, FIRE, VENOM);
    MAGIC_DAMAGE_BOOST = new SoulSkill("magic_damage_boost", 5, 0, 0, 0.0, WATER, ICE);
    WARM = new SoulSkill("warm", 1, 10, 3, -6.0, FIRE, METAL)
        .setFavorWeightMulti(0.5f);
    CHILL = new SoulSkill("chill", 1, 10, 3, -6.0, WATER, ICE)
        .setFavorWeightMulti(0.5f);
    ANTIVENOM = new SoulSkill("antivenom", 1, 5, 4, -5.0, VENOM, FLORA)
        .setFavorWeightMulti(0.5f);
    SLOW_FALL = new SoulSkill("slow_fall", 1, 2, 10, -5.0, WIND, ALIEN)
        .setActivateDelay(1)
        .setFavorWeightMulti(0.25f)
        .lockToFavoredElements();
    CROP_GROWTH = new SoulSkill("crop_growth", 3, 1, 4, -6.0, FLORA)
        .setActivateDelay(300)
        .setFavorWeightMulti(0.75f)
        .lockToFavoredElements();
    WALL_CLIMB = new SoulSkill("wall_climb", 1, 1, 6, -7.0, METAL, MONSTER)
        .setFavorWeightMulti(0.25f)
        .lockToFavoredElements();
    COFFEE_POT = new SoulSkill("coffee_pot", 1, 4, 13, -8.0, FLORA, EARTH)
        .setFavorWeightMulti(0.25f);
    //@formatter:on
  }

  /** Unique String ID */
  public final String id;
  /** The maximum level the skill can level up to. */
  public final int maxLevel;
  /** The number of action points required to activate this skill. */
  public final int apCost;
  /** The level at which this skill is most likely to be learned (0 means no preference) */
  public final int medianXpLevel;
  /** Additional weight applied when selecting a skill to learn. */
  public final double weightDiff;
  /**
   * If not empty, souls with matching elements are more likely to learn this skill. Souls without one of the favored
   * elements are less likely to learn the skill.
   */
  public final EnumSoulElement[] favoredElements;

  protected int activateDelay = SKILL_ACTIVATE_DELAY;
  protected double favorWeightMulti = 1.0;
  protected boolean lockedToFavoredElements = false;

  public SoulSkill(String id, int maxLevel, int apCost, int medianXpLevel, double weightDiff,
      EnumSoulElement... favoredElements) {

    this.id = id;
    this.maxLevel = maxLevel;
    this.apCost = apCost;
    this.medianXpLevel = medianXpLevel;
    this.weightDiff = weightDiff;
    this.favoredElements = favoredElements;

    // Randomize activate delays a bit.
    this.activateDelay = 160 + SilentGems.random.nextInt(81);

    SKILL_LIST.put(id, this);
  }

  public boolean activate(ToolSoul soul, ItemStack tool, EntityPlayer player, int level) {

    if (soul.getActionPoints() < this.apCost) {
      return false;
    }

    if (this == WARM) {
      // TODO: Soul Skill "Warm" (TAN)
    } else if (this == CHILL) {
      // TODO: Soul Skill "Chill" (TAN)
    } else if (this == ANTIVENOM) {
      // Antivenom
      if (player.isPotionActive(MobEffects.POISON)) {
        player.removePotionEffect(MobEffects.POISON);
        player.addPotionEffect(new PotionEffect(ModPotions.ANTIVENOM, 300));
        player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_BREWING_STAND_BREW,
            SoundCategory.PLAYERS, 0.9f, 1.5f);
        return true;
      }
    } else if (this == SLOW_FALL) {
      // Slow Fall
      if (player.motionY < PotionSlowFall.SLOW_FALL_SPEED && player.fallDistance > 2.5f) {
        player.addPotionEffect(new PotionEffect(ModPotions.SLOW_FALL, 200));
        SilentGems.logHelper.derp();
        return true;
      }
    } else if (this == CROP_GROWTH) {
      // Nature's Bounty
      int startX = (int) player.posX - 6;
      int startY = (int) player.posY - 1;
      int startZ = (int) player.posZ - 6;
      int endX = startX + 13;
      int endY = startY + 2;
      int endZ = startZ + 13;
      float chance = 0.15f * level;
      boolean ret = false;
      for (int y = startY; y <= endY; ++y) {
        for (int x = startX; x <= endX; ++x) {
          for (int z = startZ; z <= endZ; ++z) {
            BlockPos pos = new BlockPos(x, y, z);
            IBlockState state = player.world.getBlockState(pos);
            if (state.getBlock() instanceof BlockCrops && SilentGems.random.nextFloat() < chance) {
              IGrowable growable = (IGrowable) state.getBlock();
              if (growable.canGrow(player.world, pos, state, player.world.isRemote)) {
                growable.grow(player.world, SilentGems.random, pos, state);
                player.world.playEvent(2005, pos, 0);
                ret = true;
              }
            }
          }
        }
      }
      return ret;
    } else if (this == COFFEE_POT) {
      // Coffee Pot
      if (soul.coffeeCooldown <= 0 && player.world.getWorldTime() < 2400) {
        soul.coffeeCooldown = 12000;
        PlayerHelper.giveItem(player, ModItems.food.coffeeCup);
        ChatHelper.sendMessage(player, SilentGems.localizationHelper.getLocalizedString("skill",
            "coffee_pot.act", soul.getName(tool)));
        player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_BREWING_STAND_BREW,
            SoundCategory.PLAYERS, 0.9f, 1.5f);
        return true;
      }
    }

    return false;
  }

  public void applyToStats(ToolStats stats, int level) {

    if (this == DURABILITY_BOOST) {
      stats.durability *= 1f + (STAT_BOOST_MULTI * level);
    } else if (this == HARVEST_SPEED_BOOST) {
      stats.harvestSpeed *= 1f + (STAT_BOOST_MULTI * level);
    } else if (this == MELEE_DAMAGE_BOOST) {
      stats.meleeDamage *= 1f + (STAT_BOOST_MULTI * level);
    } else if (this == MAGIC_DAMAGE_BOOST) {
      stats.magicDamage *= 1f + (STAT_BOOST_MULTI * level);
    }
  }

  public String getLocalizedName(ItemStack tool, int level) {

    if (this == SUPER_SKILL) {
      ToolSkill superSkill = ToolHelper.getSuperSkill(tool);
      if (superSkill != null) {
        return superSkill.getTranslatedName();
      }
    }

    String strName = SilentGems.localizationHelper.getLocalizedString("skill", id + ".name");
    if (maxLevel == 1 || level == 0) {
      return strName;
    }
    String strNum = level > 10 ? Integer.toString(level)
        : SilentGems.localizationHelper.getLocalizedString("enchantment.level." + level);
    return strName + " " + strNum;
  }

  public boolean canLearn(ToolSoul soul, ItemStack tool) {

    if (this == SUPER_SKILL && soul.level != 5) {
      return false;
    }

    // Is the skill locked to favored elements?
    if (lockedToFavoredElements) {
      boolean foundMatch = false;
      for (EnumSoulElement elem : favoredElements) {
        if (elem == soul.element1 || elem == soul.element2) {
          foundMatch = true;
          break;
        }
      }

      if (!foundMatch) {
        return false;
      }
    }

    if (soul.skills.containsKey(this)) {
      int currentLevel = soul.skills.get(this);
      return currentLevel < maxLevel;
    }
    return true;
  }

  public int getActivateDelay() {

    return activateDelay;
  }

  public SoulSkill setActivateDelay(int value) {

    this.activateDelay = value;
    return this;
  }

  public SoulSkill lockToFavoredElements() {

    this.lockedToFavoredElements = true;
    return this;
  }

  public double getFavorWeightMulti() {

    return this.favorWeightMulti;
  }

  public SoulSkill setFavorWeightMulti(double value) {

    this.favorWeightMulti = value;
    return this;
  }

  public boolean sendChatMessageOnActivation() {

    return this != CROP_GROWTH && this != COFFEE_POT;
  }

  public boolean canActivateWhenUnequipped() {

    return this == COFFEE_POT;
  }

  public static SoulSkill getById(String id) {

    return SKILL_LIST.get(id);
  }

  public static SoulSkill selectSkillToLearn(ToolSoul soul, ItemStack tool) {

    if (soul.getLevel() == 5) {
      return SUPER_SKILL;
    }

    Map<SoulSkill, Double> candidates = new LinkedHashMap<>();

    // Create list of candidates
    for (SoulSkill skill : SKILL_LIST.values()) {
      if (skill.canLearn(soul, tool)) {
        boolean favorsElement = false;
        // Select a weight based on favored elements.
        double weight = skill.favoredElements.length < 1 ? 20 : 7;
        for (EnumSoulElement elem : skill.favoredElements) {
          if (elem == soul.getPrimaryElement()) {
            weight = 20;
            favorsElement = true;
            break;
          } else if (elem == soul.getSecondaryElement()) {
            weight = 15;
            favorsElement = true;
            break;
          }
        }

        // If skill has a median level, apply that to the weight.
        if (skill.medianXpLevel > 0) {
          int diff = Math.abs(soul.level - skill.medianXpLevel);
          if (diff > 6) {
            diff = 6;
          }
          weight -= 0.75 * diff;
        }

        // If a lower level of the skill is already known, reduce the weight.
        if (soul.skills.containsKey(skill)) {
          weight -= 3 * soul.skills.get(skill);
        }

        // Base weight diff, favors multiplier
        weight += skill.weightDiff;
        if (favorsElement) {
          weight *= skill.favorWeightMulti;
        }

        // Make sure weight is at least 1.
        if (weight < 1) {
          weight = 1;
        }

        candidates.put(skill, weight);
      }
    }

    // Seed based on soul elements, level, and tool UUID.
    Random rand = new Random(
        soul.getPrimaryElement().ordinal() + (soul.getSecondaryElement().ordinal() << 4)
            + (soul.getLevel() << 8) + (ToolHelper.getUUID(tool).getLeastSignificantBits() << 16));

    // Weighted random selection.
    SoulSkill selected = null;
    double bestValue = Double.MIN_VALUE;

    for (SoulSkill skill : candidates.keySet()) {
      double value = -Math.log(rand.nextFloat() / candidates.get(skill));
      SilentGems.logHelper.debug(skill.id, candidates.get(skill), value, bestValue);

      if (value > bestValue) {
        bestValue = value;
        selected = skill;
      }
    }

    return selected;
  }

  public static SoulSkill[] getSkillList() {

    List<SoulSkill> list = new ArrayList<>();
    for (SoulSkill skill : SKILL_LIST.values()) {
      list.add(skill);
    }
    return list.toArray(new SoulSkill[list.size()]);
  }
}
