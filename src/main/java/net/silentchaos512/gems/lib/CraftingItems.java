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

package net.silentchaos512.gems.lib;

import net.minecraft.item.ItemStack;
import net.silentchaos512.lib.item.IEnumItems;

import javax.annotation.Nonnull;

public enum CraftingItems implements IEnumItems<CraftingItems, CraftingItems.Item> {
    CHAOS_IRON_UNFIRED,
    CHAOS_IRON,
    ORNATE_GOLD_ROD,
    ORNATE_SILVER_ROD,
    GILDED_STRING,
    SOUL_SHELL,
    FLUFFY_PUFF,
    FLUFFY_FABRIC,
    BLACK_DYE,
    BLUE_DYE,
    CHAOS_COAL(6400),
    BLAZESTONE(3200),
    PLUME,
    SHINY_PLUME,
    MYSTERY_GOO,
    IRON_POTATO;

    private final Item item;

    CraftingItems() {
        this.item = new Item(-1);
    }

    CraftingItems(int furnaceBurnTime) {
        this.item = new Item(furnaceBurnTime);
    }

    @Nonnull
    @Override
    public CraftingItems getEnum() {
        return this;
    }

    @Nonnull
    @Override
    public CraftingItems.Item getItem() {
        return item;
    }

    public class Item extends net.minecraft.item.Item {
        private final int furnaceBurnTime;

        private Item(int furnaceBurnTime) {
            this.furnaceBurnTime = furnaceBurnTime;
        }

        @Override
        public int getItemBurnTime(ItemStack itemStack) {
            return this.furnaceBurnTime;
        }
    }
}
