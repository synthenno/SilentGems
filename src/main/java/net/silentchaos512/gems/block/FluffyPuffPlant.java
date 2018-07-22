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

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.gems.lib.CraftingItems;

public class FluffyPuffPlant extends BlockCrops {

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int age = getAge(state);
        if (age >= 7) {
            // Seeds
            for (int i = 0; i < 1 + fortune; ++i)
                if (SilentGems.random.nextInt(15) <= age)
                    drops.add(new ItemStack(getSeed()));
            // Puffs
            int puffCount = 2 + fortune + SilentGems.random.nextInt(3);
            for (int i = 0; i < puffCount; ++i)
                drops.add(new ItemStack(getCrop()));
        }
    }

    @Override
    protected Item getSeed() {
        return ModItems.fluffyPuffSeeds;
    }

    @Override
    protected Item getCrop() {

        return CraftingItems.FLUFFY_PUFF.getItem();
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }
}
