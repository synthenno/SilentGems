package net.silentchaos512.gems.loot;

public class LootHandler {
//
//    public static void init(LootTableLoadEvent event) {
//        if (event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)) {
//            LootPool main = event.getTable().getPool("main");
//            if (main != null) {
//                main.addEntry(new LootEntryItem(Items.FLINT, 30, 0, count(6, 12), new LootCondition[0],
//                        SilentGems.MOD_ID + ":flint"));
//            }
//        } else if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)
//                || event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)
//                || event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)) {
//            LootPool main = event.getTable().getPool("main");
//            if (main != null) {
//                main.addEntry(
//                        new LootEntryItem(ModItems.gem, 16, 1, countAndMeta(3, 8, 0, EnumGem.values().length - 1),
//                                new LootCondition[0], SilentGems.MOD_ID + "Gems1"));
//                main.addEntry(
//                        new LootEntryItem(ModItems.gem, 16, 1, countAndMeta(3, 8, 0, EnumGem.values().length - 1),
//                                new LootCondition[0], SilentGems.MOD_ID + "Gems2"));
//            }
//        }
//    }
//
//    private static LootFunction[] count(float min, float max) {
//        return new LootFunction[]{
//                new SetCount(new LootCondition[0], new RandomValueRange(min, max))};
//    }
//
//    private static LootFunction[] countAndMeta(float minCount, float maxCount, int minMeta, int maxMeta) {
//        return new LootFunction[]{
//                new SetCount(new LootCondition[0], new RandomValueRange(minCount, maxCount)),
//                new SetMetadata(new LootCondition[0], new RandomValueRange(minMeta, maxMeta))};
//    }
}
