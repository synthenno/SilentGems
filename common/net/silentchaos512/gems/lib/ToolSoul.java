package net.silentchaos512.gems.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.silentchaos512.gems.item.ItemSoulGem;

public class ToolSoul {

  static final int BASE_XP = 10;
  static final float XP_CURVE_FACTOR = 2.5f;

  int xp = 0;
  int level = 1;
  EnumSoulElement element1, element2 = EnumSoulElement.NONE;

  public void addXp(int amount) {

    xp += amount;
    if (xp >= getXpToNextLevel()) {
      levelUp();
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

  protected void levelUp() {

    ++level;
  }

  public EnumSoulElement getPrimaryElement() {

    return element1;
  }

  public EnumSoulElement getSecondaryElement() {

    return element2;
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

  public static ToolSoul readFromNBT(NBTTagCompound tags) {

    ToolSoul toolSoul = new ToolSoul();

    String e1 = tags.getString("element1");
    String e2 = tags.getString("element2");
    for (EnumSoulElement element : EnumSoulElement.values()) {
      if (element.name().equalsIgnoreCase(e1)) {
        toolSoul.element1 = element;
      } else if (element.name().equalsIgnoreCase(e2)) {
        toolSoul.element2 = element;
      }
    }

    toolSoul.xp = tags.getInteger("xp");
    toolSoul.level = tags.getInteger("level");

    return toolSoul;
  }

  public void writeToNBT(NBTTagCompound tags) {

    tags.setString("element1", this.element1.name());
    tags.setString("element2", this.element2.name());
    tags.setInteger("xp", xp);
    tags.setInteger("level", level);
  }

  @Override
  public String toString() {

    return "ToolSoul{" + "Elements: {" + element1.name() + ", " + element2.name() + "}" + "}";
  }
}
