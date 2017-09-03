package net.silentchaos512.gems.util;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.silentchaos512.gems.lib.soul.SoulSkill;
import net.silentchaos512.gems.lib.soul.ToolSoul;
import net.silentchaos512.lib.util.StackHelper;

public class SoulHelper {

  public static ItemStack getStackThatCanUseSkill(EntityPlayer player, SoulSkill skill) {

    Iterator<ItemStack> iter = player.getEquipmentAndArmor().iterator();
    while (iter.hasNext()) {
      ItemStack stack = iter.next();
      if (canStackUseSkill(stack, skill)) {
        return stack;
      }
    }
    return StackHelper.empty();
  }

  public static boolean canStackUseSkill(ItemStack stack, SoulSkill skill) {

    ToolSoul soul = ToolHelper.getSoul(stack);
    if (soul == null) {
      return false;
    }
    return soul.hasSkill(skill);
  }
}
