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

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.gems.tile.TileChaosFlowerPot;
import net.silentchaos512.lib.block.ITileEntityBlock;
import net.silentchaos512.lib.util.StackHelper;

public class ChaosFlowerPot extends Block implements ITileEntityProvider, ITileEntityBlock {
    private static final AxisAlignedBB FLOWER_POT_AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 0.375, 0.6875);

    public ChaosFlowerPot() {
        super(Material.CIRCUITS);
        setHardness(1.0f);
        setResistance(30.0f);
        lightValue = 2;
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileChaosFlowerPot.class;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);

        if (StackHelper.isValid(heldItem) && heldItem.getItem() instanceof ItemBlock) {
            TileChaosFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);

            if (tileentityflowerpot == null) {
                return false;
            } else if (StackHelper.isValid(tileentityflowerpot.getFlowerItemStack())) {
                return false;
            } else {
                Block block = Block.getBlockFromItem(heldItem.getItem());

                if (!(block instanceof Glowrose)) {
                    return false;
                } else {
                    // FIXME
                    ItemStack flower = new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage());
                    tileentityflowerpot.setFlowerItemStack(flower);
                    tileentityflowerpot.markDirty();
                    worldIn.notifyBlockUpdate(pos, state, state, 3);
                    worldIn.checkLight(pos);
                    playerIn.addStat(StatList.FLOWER_POTTED);

                    if (playerIn instanceof EntityPlayerMP)
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) playerIn, pos, heldItem);

                    if (!playerIn.capabilities.isCreativeMode) {
                        StackHelper.shrink(heldItem, 1);
                    }

                    return true;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileChaosFlowerPot tile = getTileEntity(world, pos);
        return tile != null && StackHelper.isValid(tile.getFlowerItemStack()) ? 15 : lightValue;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 0;
    }

    public TileChaosFlowerPot getTileEntity(IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileChaosFlowerPot)) {
            return null;
        }
        return (TileChaosFlowerPot) tile;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FLOWER_POT_AABB;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos)
                && worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!world.getBlockState(pos.down()).isSideSolid(world, pos, EnumFacing.UP)) {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        TileChaosFlowerPot te = getTileEntity(world, pos);
        if (te != null && StackHelper.isValid(te.getFlowerItemStack())) {
            drops.add(te.getFlowerItemStack());
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack tool) {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileChaosFlowerPot();
    }
}
