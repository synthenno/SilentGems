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

package net.silentchaos512.gems.init;

import net.minecraft.potion.Potion;
import net.silentchaos512.gems.potion.PotionFreezing;
import net.silentchaos512.gems.potion.PotionShocking;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModPotions implements IRegistrationHandler<Potion> {
    public static final PotionFreezing freezing = new PotionFreezing();
    public static final PotionShocking shocking = new PotionShocking();

    @Override
    public void registerAll(SRegistry reg) {
        reg.registerPotion(freezing);
        reg.registerPotion(shocking);
    }
}
