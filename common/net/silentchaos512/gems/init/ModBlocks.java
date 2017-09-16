package net.silentchaos512.gems.init;

import net.minecraft.block.Block;
import net.silentchaos512.gems.block.BlockChaosAltar;
import net.silentchaos512.gems.block.BlockChaosFlowerPot;
import net.silentchaos512.gems.block.BlockChaosNode;
import net.silentchaos512.gems.block.BlockChaosPylon;
import net.silentchaos512.gems.block.BlockEssenceOre;
import net.silentchaos512.gems.block.BlockFluffyBlock;
import net.silentchaos512.gems.block.BlockFluffyPuffPlant;
import net.silentchaos512.gems.block.BlockGem;
import net.silentchaos512.gems.block.BlockGemBrick;
import net.silentchaos512.gems.block.BlockGemGlass;
import net.silentchaos512.gems.block.BlockGemLamp;
import net.silentchaos512.gems.block.BlockGemOre;
import net.silentchaos512.gems.block.BlockGlowRose;
import net.silentchaos512.gems.block.BlockMisc;
import net.silentchaos512.gems.block.BlockPhantomLight;
import net.silentchaos512.gems.block.BlockSentient;
import net.silentchaos512.gems.block.BlockTeleporter;
import net.silentchaos512.gems.block.BlockTeleporterAnchor;
import net.silentchaos512.gems.block.BlockTeleporterRedstone;
import net.silentchaos512.gems.item.block.ItemBlockGemLamp;
import net.silentchaos512.gems.lib.EnumGem;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.gems.tile.TileChaosAltar;
import net.silentchaos512.gems.tile.TileChaosFlowerPot;
import net.silentchaos512.gems.tile.TileChaosNode;
import net.silentchaos512.gems.tile.TileChaosPylon;
import net.silentchaos512.gems.tile.TilePhantomLight;
import net.silentchaos512.gems.tile.TileTeleporter;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModBlocks implements IRegistrationHandler<Block> {

  public static final BlockGemOre gemOre = new BlockGemOre(EnumGem.Set.CLASSIC);
  public static final BlockGemOre gemOreDark = new BlockGemOre(EnumGem.Set.DARK);
  public static final BlockGemOre gemOreLight = new BlockGemOre(EnumGem.Set.LIGHT);
  
  public static final BlockGem gemBlock = new BlockGem(EnumGem.Set.CLASSIC, false);
  public static final BlockGem gemBlockDark = new BlockGem(EnumGem.Set.DARK, false);
  public static final BlockGem gemBlockLight = new BlockGem(EnumGem.Set.LIGHT, false);
  public static final BlockGem gemBlockSuper = new BlockGem(EnumGem.Set.CLASSIC, true);
  public static final BlockGem gemBlockSuperDark = new BlockGem(EnumGem.Set.DARK, true);
  public static final BlockGem gemBlockSuperLight = new BlockGem(EnumGem.Set.LIGHT, true);
  
  public static final BlockGemBrick gemBrickCoated = new BlockGemBrick(EnumGem.Set.CLASSIC, true);
  public static final BlockGemBrick gemBrickCoatedDark = new BlockGemBrick(EnumGem.Set.DARK, true);
  public static final BlockGemBrick gemBrickCoatedLight = new BlockGemBrick(EnumGem.Set.LIGHT, true);
  public static final BlockGemBrick gemBrickSpeckled = new BlockGemBrick(EnumGem.Set.CLASSIC, false);
  public static final BlockGemBrick gemBrickSpeckledDark = new BlockGemBrick(EnumGem.Set.DARK, false);
  public static final BlockGemBrick gemBrickSpeckledLight = new BlockGemBrick(EnumGem.Set.LIGHT, false);
  
  public static final BlockGemLamp gemLamp = new BlockGemLamp(EnumGem.Set.CLASSIC, false, false);
  public static final BlockGemLamp gemLampLit = new BlockGemLamp(EnumGem.Set.CLASSIC, true, false);
  public static final BlockGemLamp gemLampLitInverted = new BlockGemLamp(EnumGem.Set.CLASSIC, true, true);
  public static final BlockGemLamp gemLampInverted = new BlockGemLamp(EnumGem.Set.CLASSIC, false, true);
  public static final BlockGemLamp gemLampDark = new BlockGemLamp(EnumGem.Set.DARK, false, false);
  public static final BlockGemLamp gemLampLitDark = new BlockGemLamp(EnumGem.Set.DARK, true, false);
  public static final BlockGemLamp gemLampLitInvertedDark = new BlockGemLamp(EnumGem.Set.DARK, true, true);
  public static final BlockGemLamp gemLampInvertedDark = new BlockGemLamp(EnumGem.Set.DARK, false, true);
  public static final BlockGemLamp gemLampLight = new BlockGemLamp(EnumGem.Set.LIGHT, false, false);
  public static final BlockGemLamp gemLampLitLight = new BlockGemLamp(EnumGem.Set.LIGHT, true, false);
  public static final BlockGemLamp gemLampLitInvertedLight = new BlockGemLamp(EnumGem.Set.LIGHT, true, true);
  public static final BlockGemLamp gemLampInvertedLight = new BlockGemLamp(EnumGem.Set.LIGHT, false, true);
  
  public static final BlockGemGlass gemGlass = new BlockGemGlass(EnumGem.Set.CLASSIC);
  public static final BlockGemGlass gemGlassDark = new BlockGemGlass(EnumGem.Set.DARK);
  public static final BlockGemGlass gemGlassLight = new BlockGemGlass(EnumGem.Set.LIGHT);
  
  public static final BlockTeleporterAnchor teleporterAnchor = new BlockTeleporterAnchor();
  public static final BlockTeleporter teleporter = new BlockTeleporter(EnumGem.Set.CLASSIC, false);
  public static final BlockTeleporter teleporterDark = new BlockTeleporter(EnumGem.Set.DARK, false);
  public static final BlockTeleporter teleporterLight = new BlockTeleporter(EnumGem.Set.LIGHT, false);
  public static final BlockTeleporterRedstone teleporterRedstone = new BlockTeleporterRedstone(EnumGem.Set.CLASSIC);
  public static final BlockTeleporterRedstone teleporterRedstoneDark = new BlockTeleporterRedstone(EnumGem.Set.DARK);
  public static final BlockTeleporterRedstone teleporterRedstoneLight = new BlockTeleporterRedstone(EnumGem.Set.LIGHT);
  
  public static final BlockGlowRose glowRose = new BlockGlowRose();
  public static final BlockSentient sentientBlock = new BlockSentient();
  public static final BlockEssenceOre essenceOre = new BlockEssenceOre();
  public static final BlockMisc miscBlock = new BlockMisc();
  public static final BlockFluffyBlock fluffyBlock = new BlockFluffyBlock();
  public static final BlockFluffyPuffPlant fluffyPuffPlant = new BlockFluffyPuffPlant();
  public static final BlockChaosFlowerPot chaosFlowerPot = new BlockChaosFlowerPot();
  public static final BlockChaosNode chaosNode = new BlockChaosNode();
  public static final BlockChaosAltar chaosAltar = new BlockChaosAltar();
  public static final BlockChaosPylon chaosPylon = new BlockChaosPylon();
  public static final BlockPhantomLight phantomLight = new BlockPhantomLight();

  @Override
  public void registerAll(SRegistry reg) {

    reg.registerBlock(gemOre);
    reg.registerBlock(gemOreDark);
    reg.registerBlock(gemOreLight);

    reg.registerBlock(gemBlock);
    reg.registerBlock(gemBlockDark);
    reg.registerBlock(gemBlockLight);
    reg.registerBlock(gemBlockSuper);
    reg.registerBlock(gemBlockSuperDark);
    reg.registerBlock(gemBlockSuperLight);

    reg.registerBlock(gemBrickCoated);
    reg.registerBlock(gemBrickCoatedDark);
    reg.registerBlock(gemBrickCoatedLight);
    reg.registerBlock(gemBrickSpeckled);
    reg.registerBlock(gemBrickSpeckledDark);
    reg.registerBlock(gemBrickSpeckledLight);

    reg.registerBlock(gemLamp, new ItemBlockGemLamp(gemLamp));
    reg.registerBlock(gemLampLit, new ItemBlockGemLamp(gemLampLit)).setCreativeTab(null);
    reg.registerBlock(gemLampLitInverted, new ItemBlockGemLamp(gemLampLitInverted));
    reg.registerBlock(gemLampInverted, new ItemBlockGemLamp(gemLampInverted)).setCreativeTab(null);
    reg.registerBlock(gemLampDark, new ItemBlockGemLamp(gemLampDark));
    reg.registerBlock(gemLampLitDark, new ItemBlockGemLamp(gemLampLitDark)).setCreativeTab(null);
    reg.registerBlock(gemLampLitInvertedDark, new ItemBlockGemLamp(gemLampLitInvertedDark));
    reg.registerBlock(gemLampInvertedDark, new ItemBlockGemLamp(gemLampInvertedDark)).setCreativeTab(null);
    reg.registerBlock(gemLampLight, new ItemBlockGemLamp(gemLampLight));
    reg.registerBlock(gemLampLitLight, new ItemBlockGemLamp(gemLampLitLight)).setCreativeTab(null);
    reg.registerBlock(gemLampLitInvertedLight, new ItemBlockGemLamp(gemLampLitInvertedLight));
    reg.registerBlock(gemLampInvertedLight, new ItemBlockGemLamp(gemLampInvertedLight)).setCreativeTab(null);

    reg.registerBlock(gemGlass);
    reg.registerBlock(gemGlassDark);
    reg.registerBlock(gemGlassLight);

    reg.registerBlock(teleporterAnchor);
    reg.registerBlock(teleporter);
    reg.registerBlock(teleporterDark);
    reg.registerBlock(teleporterLight);
    reg.registerBlock(teleporterRedstone);
    reg.registerBlock(teleporterRedstoneDark);
    reg.registerBlock(teleporterRedstoneLight);

    reg.registerBlock(glowRose, Names.GLOW_ROSE);
    reg.registerBlock(sentientBlock);
    reg.registerBlock(essenceOre);
    reg.registerBlock(miscBlock);
    reg.registerBlock(fluffyBlock);
    reg.registerBlock(fluffyPuffPlant, Names.FLUFFY_PUFF_PLANT).setCreativeTab(null);
    reg.registerBlock(chaosFlowerPot, Names.CHAOS_FLOWER_POT);
    reg.registerBlock(chaosNode);
    reg.registerBlock(chaosAltar);
    reg.registerBlock(chaosPylon, Names.CHAOS_PYLON);
    reg.registerBlock(phantomLight);

    reg.registerTileEntity(TileTeleporter.class, Names.TELEPORTER);
    reg.registerTileEntity(TileChaosFlowerPot.class, Names.CHAOS_FLOWER_POT);
    reg.registerTileEntity(TileChaosNode.class, Names.CHAOS_NODE);
    reg.registerTileEntity(TileChaosAltar.class, Names.CHAOS_ALTAR);
    reg.registerTileEntity(TileChaosPylon.class, Names.CHAOS_PYLON);
    reg.registerTileEntity(TilePhantomLight.class, Names.PHANTOM_LIGHT);
  }
}
