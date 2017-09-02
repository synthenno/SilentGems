package net.silentchaos512.gems.lib.soul;

import static net.silentchaos512.gems.lib.soul.EnumSoulElement.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.tool.ToolStats;
import net.silentchaos512.gems.init.ModPotions;
import net.silentchaos512.gems.potion.PotionSlowFall;
import net.silentchaos512.gems.util.ToolHelper;

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

  public static void init() {

    SUPER_SKILL = new SoulSkill("super_skill", 1, 0, 5, 0.0);
    DURABILITY_BOOST = new SoulSkill("durability_boost", 5, 0, 0, 0.0, EARTH, METAL);
    HARVEST_SPEED_BOOST = new SoulSkill("harvest_speed_boost", 5, 0, 0, 0.0, WIND, LIGHTNING);
    MELEE_DAMAGE_BOOST = new SoulSkill("melee_damage_boost", 5, 0, 0, 0.0, FIRE, VENOM);
    MAGIC_DAMAGE_BOOST = new SoulSkill("magic_damage_boost", 5, 0, 0, 0.0, WATER, ICE);
    WARM = new SoulSkill("warm", 1, 10, 3, 0.0, FIRE, METAL);
    CHILL = new SoulSkill("chill", 1, 10, 3, 0.0, WATER, ICE);
    ANTIVENOM = new SoulSkill("antivenom", 1, 5, 4, -1.5, VENOM, FLORA);
    SLOW_FALL = new SoulSkill("slow_fall", 1, 2, 10, -1.0, WIND).setActivateDelay(1);
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

  public SoulSkill(String id, int maxLevel, int apCost, int medianXpLevel, double weightDiff,
      EnumSoulElement... favoredElements) {

    this.id = id;
    this.maxLevel = maxLevel;
    this.apCost = apCost;
    this.medianXpLevel = medianXpLevel;
    this.weightDiff = weightDiff;
    this.favoredElements = favoredElements;

    SKILL_LIST.put(id, this);
  }

  public boolean activate(ToolSoul soul, ItemStack tool, EntityPlayer player) {

    if (soul.getActionPoints() < this.apCost) {
      return false;
    }

    if (this == WARM) {
      // TODO: Soul Skill "Warm" (TAN)
    } else if (this == CHILL) {
      // TODO: Soul Skill "Chill" (TAN)
    } else if (this == ANTIVENOM) {
      if (player.isPotionActive(MobEffects.POISON)) {
        player.removePotionEffect(MobEffects.POISON);
        player.addPotionEffect(new PotionEffect(ModPotions.ANTIVENOM, 300));
        player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_BREWING_STAND_BREW,
            SoundCategory.PLAYERS, 0.9f, 1.5f);
        return true;
      }
    } else if (this == SLOW_FALL) {
      if (player.motionY < PotionSlowFall.SLOW_FALL_SPEED && player.fallDistance > 2.5f) {
        player.addPotionEffect(new PotionEffect(ModPotions.SLOW_FALL, 200));
        SilentGems.logHelper.derp();
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

  public String getLocalizedName(int level) {

    String strName = SilentGems.localizationHelper.getLocalizedString("skill", id + ".name");
    if (maxLevel == 1) {
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
        // Select a weight based on favored elements.
        double weight = skill.favoredElements.length < 1 ? 10 : 5;
        for (EnumSoulElement elem : skill.favoredElements) {
          if (elem == soul.getPrimaryElement()) {
            weight = 10;
            break;
          } else if (elem == soul.getSecondaryElement()) {
            weight = 7;
            break;
          }
        }

        // If skill has a median level, apply that to the weight.
        if (skill.medianXpLevel > 0) {
          int diff = Math.abs(soul.level - skill.medianXpLevel);
          if (diff > 6) {
            diff = 6;
          }
          weight -= 0.5 * diff;
        }

        // If a lower level of the skill is already known, reduce the weight.
        if (soul.skills.containsKey(skill)) {
          weight -= soul.skills.get(skill);
        }

        weight += skill.weightDiff;

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
}
