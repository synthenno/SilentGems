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

package net.silentchaos512.gems.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGlowRoseFertilizer extends Item {

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        Random rand = SilentGems.random;
//        BlockPos center = pos.offset(EnumFacing.UP);
//
//        if (worldIn.isAirBlock(center)) {
//            // Always one at position used.
//            Gems gem = Gems.Set.CLASSIC.selectRandom(rand);
//            IBlockState glowRose = ModBlocks.glowRose.getDefaultState().withProperty(Gems.VARIANT, gem);
//
//            // Fail to use if glow rose can't be placed at center.
//            if (!ModBlocks.glowRose.canBlockStay(worldIn, center, glowRose) || !worldIn.isAirBlock(center) || !worldIn.isAirBlock(center.up()))
//                return EnumActionResult.FAIL;
//                // Only make changes on the server side!
//            else if (worldIn.isRemote)
//                return EnumActionResult.SUCCESS;
//
//            worldIn.setBlockState(center, glowRose, 2);
//
//            // Random extras
//            int extraCount = rand.nextInt(3);
//            BlockPos target;
//            int x, y, z;
//            for (int i = 0; i < extraCount; ++i) {
//                x = center.getX() + rand.nextInt(7) - 3;
//                z = center.getZ() + rand.nextInt(7) - 3;
//                // Try place one block higher, move down 1-2 if needed.
//                for (y = center.getY() + 1; y > center.getY() - 2; --y) {
//                    target = new BlockPos(x, y, z);
//                    gem = Gems.Set.CLASSIC.selectRandom(rand);
//                    glowRose = ModBlocks.glowRose.getDefaultState().withProperty(Gems.VARIANT, gem);
//                    if (ModBlocks.glowRose.canBlockStay(worldIn, target, glowRose) && worldIn.isAirBlock(target) && worldIn.isAirBlock(target.up())) {
//                        worldIn.setBlockState(target, glowRose, 2);
//                        break;
//                    }
//                }
//            }
//
//            if (!playerIn.capabilities.isCreativeMode)
//                playerIn.getHeldItem(hand).shrink(1);
//
//            return EnumActionResult.SUCCESS;
//        }
//
//        return EnumActionResult.FAIL;

        return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
