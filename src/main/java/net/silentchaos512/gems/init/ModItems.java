package net.silentchaos512.gems.init;

import net.minecraft.item.Item;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.item.GemItem;
import net.silentchaos512.gems.item.ItemDrawingCompass;
import net.silentchaos512.gems.item.ItemFluffyPuffSeeds;
import net.silentchaos512.gems.item.ItemGlowRoseFertilizer;
import net.silentchaos512.gems.lib.CraftingItems;
import net.silentchaos512.gems.lib.Essences;
import net.silentchaos512.gems.lib.Foods;
import net.silentchaos512.gems.lib.Gems;
import net.silentchaos512.lib.item.IEnumItems;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

import java.util.function.Function;

public class ModItems implements IRegistrationHandler<Item> {
    public static final ItemFluffyPuffSeeds fluffyPuffSeeds = new ItemFluffyPuffSeeds();
    public static final ItemGlowRoseFertilizer glowRoseFertilizier = new ItemGlowRoseFertilizer();
    public static final ItemDrawingCompass drawingCompass = new ItemDrawingCompass();

    @Override
    public void registerAll(SRegistry reg) {
        registerGemItems(reg, Gems::getItemGem, Gems::getName);
        registerGemItems(reg, Gems::getItemShard, gem -> gem.getName() + "_shard");

        IEnumItems.registerItems(Essences.values(), reg);
        IEnumItems.registerItems(CraftingItems.values(), reg);
        IEnumItems.registerItems(Foods.values(), reg);

        reg.registerItem(fluffyPuffSeeds, "fluffy_puff_seeds");
        reg.registerItem(glowRoseFertilizier, "glowrose_fertilizer");
        reg.registerItem(drawingCompass, "drawing_compass").setCreativeTab(SilentGems.tabUtility);
    }

    private void registerGemItems(SRegistry reg, Function<Gems, GemItem> item, Function<Gems, String> nameGenerator) {
        for (Gems gem : Gems.values()) {
            reg.registerItem(item.apply(gem), nameGenerator.apply(gem));
        }
    }
}
