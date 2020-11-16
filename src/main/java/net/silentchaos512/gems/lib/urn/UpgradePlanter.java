/*
 * Silent's Gems -- UrnPlanter
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

package net.silentchaos512.gems.lib.urn;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.block.urn.SoulUrnTileEntity;
import net.silentchaos512.lib.util.InventoryUtils;
import net.silentchaos512.lib.util.TimeUtils;
import net.silentchaos512.utils.MathUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class UpgradePlanter extends UrnUpgrade {
    private static final int TICK_FREQUENCY = TimeUtils.ticksFromSeconds(30);
    private static final Map<IItemProvider, Plant> PLANTS = new HashMap<>();

    private int ticks = 0;

    @Override
    public void tickTile(SoulUrnTileEntity.SoulUrnState state, World world, BlockPos pos) {
        if (!state.getLidState().isOpen()) return;

        ++ticks;
        if (ticks >= TICK_FREQUENCY) {
            ticks = 0;
            ItemStack seed = getSeed(state);
            if (!seed.isEmpty()) {
                Plant plant = PLANTS.get(seed.getItem());
                if (plant != null) {
                    InventoryUtils.mergeItems(state.getTileEntity(), 0, state.getTileEntity().getSizeInventory(), plant.drops.apply(SilentGems.random));
                }
            }
        }
    }

    @Override
    public void tickItem(ItemStack urn, World world, PlayerEntity player, int itemSlot, boolean isSelected) {
//        if (UrnHelper.hasLid(urn)) return;
//
//        ++ticks;
//        if (ticks >= TICK_FREQUENCY) {
//            ticks = 0;
//            ItemStack seed = UrnHelper.getContainedItems(urn).firstMatch(UpgradePlanter::isGrowablePlant);
//            if (!seed.isEmpty()) {
//                Plant plant = PLANTS.get(seed.getItem());
//                if (plant != null) {
//                    ;
//                }
//            }
//        }
    }

    private static ItemStack getSeed(SoulUrnTileEntity.SoulUrnState state) {
        IItemHandler itemHandler = state.getTileEntity().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalStateException::new);
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (isGrowablePlant(stack)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        // TODO: Need a better way to handle this! Maybe loot tables? Would be nice if mods could
        //  add plants as well.

        PLANTS.put(Items.BEETROOT_SEEDS, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.BEETROOT), 1.0,
                new ItemStack(Items.BEETROOT_SEEDS), 0.5)));
        PLANTS.put(Items.CARROT, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.CARROT), 1.0)));
        PLANTS.put(Blocks.CHORUS_FLOWER, new Plant(2, random -> dropsWithChance(random,
                new ItemStack(Items.CHORUS_FRUIT), 1.0,
                new ItemStack(Blocks.CHORUS_FLOWER), 0.05)));
        PLANTS.put(Items.MELON_SEEDS, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.MELON_SLICE, random.nextInt(4) + 1), 0.85,
                new ItemStack(Blocks.MELON), 0.2)));
        PLANTS.put(Items.NETHER_WART, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.NETHER_WART, random.nextInt(3) + 1), 0.95)));
        PLANTS.put(Items.POTATO, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.POTATO), 1.0,
                new ItemStack(Items.POISONOUS_POTATO), 0.05)));
        PLANTS.put(Items.PUMPKIN_SEEDS, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Blocks.PUMPKIN), 0.85,
                new ItemStack(Blocks.JACK_O_LANTERN), 0.01)));
        PLANTS.put(Blocks.SUGAR_CANE, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Blocks.SUGAR_CANE, random.nextInt(2) + 1), 0.85)));
        PLANTS.put(Items.WHEAT_SEEDS, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.WHEAT), 1.0,
                new ItemStack(Items.WHEAT_SEEDS), 0.5)));
        PLANTS.put(Items.COAL_SEEDS, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.COAL_ESSENCE, random.nextInt(2)), 1.0,
                new ItemStack(Items.COAL_SEEDS), 1.0)));
        PLANTS.put(Items.NETHERITE_SEEDS, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.NETHERITE_ESSENCE), 1.0,
                new ItemStack(Items.NETHERITE_SEEDS), 1.0)));
        PLANTS.put(Items.EMERALD_SEEDS, new Plant(1, random -> dropsWithChance(random,
                new ItemStack(Items.EMERALD_ESSENCE), 1.0,
                new ItemStack(Items.EMERALD_SEEDS), 1.0)));
    }

    private static boolean isGrowablePlant(ItemStack stack) {
        return !stack.isEmpty() && PLANTS.containsKey(stack.getItem());
    }

    private static Collection<ItemStack> dropsWithChance(Random random, ItemStack stack1, double rate1) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        if (!stack1.isEmpty() && MathUtils.tryPercentage(random, rate1)) builder.add(stack1);
        return builder.build();
    }

    private static Collection<ItemStack> dropsWithChance(Random random, ItemStack stack1, double rate1, ItemStack stack2, double rate2) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        if (!stack1.isEmpty() && MathUtils.tryPercentage(random, rate1)) builder.add(stack1);
        if (!stack2.isEmpty() && MathUtils.tryPercentage(random, rate2)) builder.add(stack2);
        return builder.build();
    }

    public static class Plant {
        private final int growthTicks;
        private final Function<Random, Collection<ItemStack>> drops;

        public Plant(int growthRate, Function<Random, Collection<ItemStack>> drops) {
            this.growthTicks = growthRate;
            this.drops = drops;
        }
    }
}
