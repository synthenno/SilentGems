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
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.block.*;
import net.silentchaos512.gems.item.GemItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Getter
public enum Gems implements IStringSerializable {
    // Classic Set
    RUBY            (0xE61D1D, Set.CLASSIC),
    GARNET          (0xE64F1D, Set.CLASSIC),
    TOPAZ           (0xE6711D, Set.CLASSIC),
    AMBER           (0xE6A31D, Set.CLASSIC),
    HELIODOR        (0xE6C51D, Set.CLASSIC),
    PERIDOT         (0xA3E61D, Set.CLASSIC),
    GREEN_SAPPHIRE  (0x1DE61D, Set.CLASSIC),
    INDICOLITE      (0x1DE682, Set.CLASSIC),
    AQUAMARINE      (0x1DE6E6, Set.CLASSIC),
    SAPPHIRE        (0x1D1DE6, Set.CLASSIC),
    IOLITE          (0x601DE6, Set.CLASSIC),
    AMETHYST        (0xA31DE6, Set.CLASSIC),
    AGATE           (0xE61DE6, Set.CLASSIC),
    MORGANITE       (0xFF88FE, Set.CLASSIC),
    ONYX            (0x2F2F2F, Set.CLASSIC),
    OPAL            (0xE4E4E4, Set.CLASSIC),
    // Dark Set
    CARNELIAN       (0xA30E00, Set.DARK),
    SPINEL          (0xA34400, Set.DARK),
    CITRINE         (0xA35F00, Set.DARK),
    JASPER          (0xA38800, Set.DARK),
    GOLDEN_BERYL    (0xA3A300, Set.DARK),
    MOLDAVITE       (0x88A300, Set.DARK),
    MALACHITE       (0x00A336, Set.DARK),
    TURQUOISE       (0x00A388, Set.DARK),
    MOONSTONE       (0x006DA3, Set.DARK),
    BLUE_TOPAZ      (0x001BA3, Set.DARK),
    TANZANITE       (0x5F00A3, Set.DARK),
    VIOLET_SAPPHIRE (0x9500A3, Set.DARK),
    LEPIDOLITE      (0xA3007A, Set.DARK),
    AMETRINE        (0xA30052, Set.DARK),
    BLACK_DIAMOND   (0x1E1E1E, Set.DARK),
    ALEXANDRITE     (0x898989, Set.DARK),
    // Light Set
    PYROPE          (0xFF4574, Set.LIGHT),
    CORAL           (0xFF5545, Set.LIGHT),
    SUNSTONE        (0xFF7445, Set.LIGHT),
    CATS_EYE        (0xFFC145, Set.LIGHT),
    ZIRCON          (0xFFFF45, Set.LIGHT),
    JADE            (0xA2FF45, Set.LIGHT),
    CHRYSOPRASE     (0x64FF45, Set.LIGHT),
    APATITE         (0x45FFD1, Set.LIGHT),
    FLUORITE        (0x45D1FF, Set.LIGHT),
    KYANITE         (0x4583FF, Set.LIGHT),
    SODALITE        (0x5445FF, Set.LIGHT),
    AMMOLITE        (0xE045FF, Set.LIGHT),
    KUNZITE         (0xFF45E0, Set.LIGHT),
    ROSE_QUARTZ     (0xFF78B6, Set.LIGHT),
    TEKTITE         (0x8F7C6B, Set.LIGHT),
    PEARL           (0xE2E8F1, Set.LIGHT);

    public static final PropertyEnum<Gems> VARIANT = PropertyEnum.create("gem", Gems.class);

    private final int color;
    private final Set set;

    private final GemOre blockOre;
    private final GemBlock blockGem;
    private final GemBricks blockBricks;
    private final GemGlass blockGlass;
    private final Glowrose glowrose;

    private final GemItem itemGem;
    private final GemItem itemShard;

    Gems(int color, Set set) {
        this.color = color;
        this.set = set;
        set.gems.add(this);

        this.blockOre = new GemOre(this);
        this.blockGem = new GemBlock(this);
        this.blockBricks = new GemBricks(this);
        this.blockGlass = new GemGlass(this);
        this.glowrose = new Glowrose(this);

        this.itemGem = new GemItem(this);
        this.itemShard = new GemItem(this);
    }

    public static Gems getRandom() {
        return values()[SilentGems.random.nextInt(values().length)];
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public enum Set {
        CLASSIC, DARK, LIGHT;

        private List<Gems> gems = new ArrayList<>();

        public Gems selectRandom(Random random) {
            return this.gems.get(random.nextInt(gems.size()));
        }
    }
}
