package net.silentchaos512.gems.block;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.lib.block.BlockSL;

public class BlockSentient extends BlockSL {

  public static enum Type implements IStringSerializable {

    STONE, DIRT, GRAVEL, SAND;

    @Override
    public String getName() {

      return name().toLowerCase();
    }

    public String getTool() {

      if (this == STONE) {
        return "pickaxe";
      } else {
        return "shovel";
      }
    }
  }

  public static final PropertyEnum<Type> PROPERTY = PropertyEnum.create("type", Type.class);

  public BlockSentient() {

    super(Type.values().length, SilentGems.MODID, Names.SENTIENT_BLOCK, Material.ROCK);
    setDefaultState(blockState.getBaseState().withProperty(PROPERTY, Type.STONE));
    setTickRandomly(true);
    setHardness(3.0f);

    for (Type type : Type.values()) {
      setHarvestLevel(type.getTool(), 0, getDefaultState().withProperty(PROPERTY, type));
    }
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

    int moveChance = 100; // 20
    if (world.isRemote || rand.nextInt(100) > moveChance) {
      return;
    }

    world.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);

    // Try to move
    boolean moved = false;
    for (int tries = 0; tries < 4; ++tries) {
      EnumFacing direction = EnumFacing.HORIZONTALS[rand.nextInt(4)];
      BlockPos target = pos.offset(direction);

      if (canMoveTo(world, target)) {
        moveTo(world, pos, target, state);
        moved = true;
      } else {
        // Can we go up or down?
        for (int y = 2; y > -3; y = y == 1 ? -1 : y - 1) {
          BlockPos newTarget = target.up(y);
          if (canMoveTo(world, newTarget)) {
            moveTo(world, pos, newTarget, state);
            moved = true;
            break;
          }
        }
      }

      if (moved) {
        break;
      }
    }
  }

  protected boolean canMoveTo(World world, BlockPos pos) {

    return world.isAirBlock(pos) && world.isBlockFullCube(pos.down());
  }

  protected void moveTo(World world, BlockPos from, BlockPos to, IBlockState state) {

    world.setBlockState(to, state);
    world.setBlockToAir(from);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    return ModItems.craftingMaterial;
    // return super.getItemDropped(state, rand, fortune);
  }

  @Override
  public int damageDropped(IBlockState state) {

    return ModItems.craftingMaterial.sentientStoneShard.getItemDamage()
        + state.getBlock().getMetaFromState(state);
    // return ((Type) state.getValue(PROPERTY)).ordinal();
  }

  @Override
  public void clGetSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {

    for (Type type : Type.values()) {
      list.add(new ItemStack(item, 1, type.ordinal()));
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public IBlockState getStateFromMeta(int meta) {

    meta = MathHelper.clamp(meta, 0, Type.values().length - 1);
    return this.getDefaultState().withProperty(PROPERTY, Type.values()[meta]);
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return ((Type) state.getValue(PROPERTY)).ordinal();
  }

  @Override
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, new IProperty[] { PROPERTY });
  }

  @Override
  public void getModels(Map<Integer, ModelResourceLocation> models) {

    for (Type type : Type.values()) {
      models.put(type.ordinal(),
          new ModelResourceLocation(getFullName().toLowerCase(), "type=" + type.getName()));
    }
  }
}
