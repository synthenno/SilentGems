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
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.silentchaos512.gems.lib.Gems;

import java.util.Random;

public class GemLamp extends Block {
    private static final PropertyBool LIT = PropertyBool.create("lit");
    private static final PropertyBool INVERTED = PropertyBool.create("inverted");

    public GemLamp(Gems gem) {
        super(Material.REDSTONE_LIGHT);
        setDefaultState(this.blockState.getBaseState().withProperty(LIT, false).withProperty(INVERTED, false));
        setHardness(0.3f);
        setResistance(10.0f);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT, INVERTED);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LIT, (meta & 1) == 1).withProperty(INVERTED, (meta & 2) == 2);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(LIT) ? 1 : 0;
        meta |= state.getValue(INVERTED) ? 2 : 0;
        return meta;
    }

    private IBlockState getState(boolean lit, boolean inverted) {
        return getDefaultState().withProperty(LIT, lit).withProperty(INVERTED, inverted);
    }

    private void setState(World world, BlockPos pos, boolean lit, boolean inverted) {
        world.setBlockState(pos, getState(lit, inverted), 2);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            boolean powered = world.isBlockPowered(pos);
            boolean inverted = state.getValue(LIT);
            setState(world, pos, powered != inverted, inverted);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (world.isRemote) return;

        boolean powered = world.isBlockPowered(pos);
        boolean lit = state.getValue(LIT);
        boolean inverted = state.getValue(INVERTED);

        if (inverted) {
            if (!lit && !powered)
                world.scheduleUpdate(pos, this, 4);
            else if (lit && powered)
                setState(world, pos, false, inverted);
        } else {
            if (lit && !powered)
                world.scheduleUpdate(pos, this, 4);
            else if (!lit && powered)
                setState(world, pos, true, inverted);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (world.isRemote) return;

        boolean powered = world.isBlockPowered(pos);
        if (!powered) {
            boolean inverted = state.getValue(INVERTED);
            setState(world, pos, inverted, inverted);
        }
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }
}
