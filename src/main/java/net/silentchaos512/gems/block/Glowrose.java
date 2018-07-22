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

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.silentchaos512.gems.config.GemsConfig;
import net.silentchaos512.gems.lib.Gems;

public class Glowrose extends BlockBush {
    private final Gems gem;

    public Glowrose(Gems gem) {
        this.gem = gem;
        setSoundType(SoundType.PLANT);
        lightValue = GemsConfig.GLOW_ROSE_LIGHT_LEVEL;
    }

    public static String nameFor(Gems gem) {
        return gem.getName() + "_glowrose";
    }
}
