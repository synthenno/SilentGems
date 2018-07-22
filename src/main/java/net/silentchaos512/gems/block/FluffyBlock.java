/*
 * Silent's Gems
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.gems.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.silentchaos512.lib.util.StackHelper;

public class FluffyBlock extends Block {
    private final EnumDyeColor color;

    public FluffyBlock(EnumDyeColor color) {
        super(Material.CLOTH);
        this.color = color;

        setHardness(0.8f);
        setResistance(3.0f);
        setSoundType(SoundType.CLOTH);
        setHarvestLevel("", 0);
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float distance) {

        if (distance < 2 || world.isRemote) {
            return;
        }

        // Count the number of fluffy blocks that are stacked up.
        int stackedBlocks = 0;
        for (; world.getBlockState(pos).getBlock() == this; pos = pos.down()) {
            ++stackedBlocks;
        }

        // Reduce fall distance by 10 blocks per stacked block
        distance -= Math.min(10 * stackedBlocks, distance);
        entity.fallDistance = 0f;
        entity.fall(distance, 1f);
    }

    public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {

        ItemStack mainHand = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if (StackHelper.isValid(mainHand) && mainHand.getItem() instanceof ItemShears) {
            int efficiency = EnchantmentHelper.getEfficiencyModifier(event.getEntityPlayer());

            float speed = event.getNewSpeed() * 4;
            if (efficiency > 0) {
                speed += (efficiency * efficiency + 1);
            }

            event.setNewSpeed(efficiency);
        }
    }

    public static String nameFor(EnumDyeColor color) {
        return color.getDyeColorName() + "_fluffy_block";
    }
}
