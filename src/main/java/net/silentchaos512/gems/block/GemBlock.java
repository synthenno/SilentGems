package net.silentchaos512.gems.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.silentchaos512.gems.lib.Gems;

public class GemBlock extends Block {

    public GemBlock(Gems gem) {
        super(Material.IRON);
        setHardness(4.0f);
        setResistance(30.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
        return true;
    }

    public static String nameFor(Gems gem) {
        return gem.getName() + "_block";
    }
}
