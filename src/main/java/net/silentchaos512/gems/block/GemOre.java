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

package net.silentchaos512.gems.block;

import net.silentchaos512.gems.lib.Gems;
import net.silentchaos512.lib.block.BlockOreSL;

public class GemOre extends BlockOreSL {
    public GemOre(Gems gem) {
        super(gem.getItemGem(), 2, 1, 1, 1, 5);
        setHardness(4.0f);
        setResistance(15.0f);
    }

    public static String nameFor(Gems gem) {
        return gem.getName() + "_ore";
    }
}
