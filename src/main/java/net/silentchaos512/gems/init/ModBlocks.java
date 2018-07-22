package net.silentchaos512.gems.init;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.silentchaos512.gems.block.*;
import net.silentchaos512.gems.lib.Essences;
import net.silentchaos512.gems.lib.Gems;
import net.silentchaos512.lib.item.IEnumItems;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModBlocks implements IRegistrationHandler<Block> {
    public static final MultiGemOre multiGemOreClassic = new MultiGemOre(Gems.Set.CLASSIC);
    public static final MultiGemOre multiGemOreDark = new MultiGemOre(Gems.Set.DARK);
    public static final MultiGemOre multiGemOreLight = new MultiGemOre(Gems.Set.LIGHT);
    public static final EssenceOre chaosEssenceOre = new EssenceOre(Essences.CHAOS);
    public static final EssenceOre pureEssenceOre = new EssenceOre(Essences.PURE);
    public static final EssenceOre enderEssenceOre = new EssenceOre(Essences.ENDER);

    public static final HardenedRock hardenedStone = new HardenedRock();
    public static final HardenedRock hardenedNetherrack = new HardenedRock();
    public static final HardenedRock hardenedEndStone = new HardenedRock();

    public static final FluffyPuffPlant fluffyPuffPlant = new FluffyPuffPlant();
    public static final ChaosFlowerPot chaosFlowerPot = new ChaosFlowerPot();
    public static final BlockPhantomLight phantomLight = new BlockPhantomLight();

    @Override
    public void registerAll(SRegistry reg) {
        IEnumItems.RegistrationHelper enumBlocks = new IEnumItems.RegistrationHelper(reg);

        enumBlocks.registerBlocksGenericEnum(Gems::getBlockGem, GemBlock::nameFor, Gems.class);
        enumBlocks.registerBlocksGenericEnum(Gems::getBlockBricks, GemBricks::nameFor, Gems.class);
        enumBlocks.registerBlocksGenericEnum(Gems::getBlockGlass, GemGlass::nameFor, Gems.class);
        enumBlocks.registerBlocksGenericEnum(Gems::getGlowrose, Glowrose::nameFor, Gems.class);

        enumBlocks.registerBlocksGenericEnum(Gems::getBlockOre, GemOre::nameFor, Gems.class);
        reg.registerBlock(multiGemOreClassic, "multi_gem_ore_classic");
        reg.registerBlock(multiGemOreDark, "multi_gem_ore_dark");
        reg.registerBlock(multiGemOreLight, "multi_gem_ore_light");
        reg.registerBlock(chaosEssenceOre, "chaos_essence_ore");
        reg.registerBlock(pureEssenceOre, "pure_essence_ore");
        reg.registerBlock(enderEssenceOre, "ender_essence_ore");

        reg.registerBlock(hardenedStone, "hardened_stone");
        reg.registerBlock(hardenedNetherrack, "hardened_netherrack");
        reg.registerBlock(hardenedEndStone, "hardened_end_stone");

        enumBlocks.registerBlocksGenericEnum(FluffyBlock::new, FluffyBlock::nameFor, EnumDyeColor.class);

        reg.registerBlock(fluffyPuffPlant, "fluffy_puff_plant").setCreativeTab(null);
//        reg.registerBlock(chaosFlowerPot, "chaos_flower_pot");
//        reg.registerBlock(phantomLight);
    }
}
