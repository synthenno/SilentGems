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

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.gear.api.parts.PartMain;
import net.silentchaos512.gear.api.parts.PartRegistry;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.lib.Gems;
import net.silentchaos512.lib.registry.IPhasedInitializer;
import net.silentchaos512.lib.registry.SRegistry;

public class ModMaterials implements IPhasedInitializer {
    public static final ModMaterials INSTANCE = new ModMaterials();

    private ModMaterials() {
    }

    @Override
    public void preInit(SRegistry registry, FMLPreInitializationEvent event) {
        for (Gems gem : Gems.values()) {
            ResourceLocation name = new ResourceLocation(SilentGems.MOD_ID, "main_" + gem.getName());
            PartRegistry.putPart(new PartMain(name));
        }
    }
}
