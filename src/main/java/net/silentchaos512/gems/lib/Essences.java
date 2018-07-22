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

import lombok.Getter;
import net.minecraft.item.Item;
import net.silentchaos512.lib.item.IEnumItems;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum Essences implements IEnumItems<Essences, Item> {
    CHAOS(3),
    ENRICHED_CHAOS(-1),
    CRYSTALLIZED_CHAOS(-1),
    PURE(4),
    ENDER(5);

    @Getter
    private final int oreHarvestLevel;
    private final Item item;

    Essences(int oreHarvestLevel) {
        this.oreHarvestLevel = oreHarvestLevel;
        this.item = new Item();
    }

    @Nonnull
    @Override
    public Essences getEnum() {
        return this;
    }

    @Nonnull
    @Override
    public Item getItem() {
        return item;
    }

    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT) + "_essence";
    }
}
