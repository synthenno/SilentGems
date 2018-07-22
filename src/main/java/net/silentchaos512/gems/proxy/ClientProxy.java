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

package net.silentchaos512.gems.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.client.render.ModBlockRenderers;
import net.silentchaos512.gems.client.render.entity.RenderEnderSlime;
import net.silentchaos512.gems.client.render.particle.ParticleChaos;
import net.silentchaos512.gems.client.render.particle.ParticleCompass;
import net.silentchaos512.gems.client.render.particle.ParticleFreezing;
import net.silentchaos512.gems.client.render.particle.ParticleShocking;
import net.silentchaos512.gems.entity.EntityEnderSlime;
import net.silentchaos512.gems.event.GemsClientEvents;
import net.silentchaos512.gems.lib.EnumModParticles;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.lib.util.Color;
import org.apache.commons.lang3.NotImplementedException;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(SRegistry registry, FMLPreInitializationEvent event) {
        super.preInit(registry, event);
        OBJLoader.INSTANCE.addDomain(SilentGems.MOD_ID);
        MinecraftForge.EVENT_BUS.register(new GemsClientEvents());
        registry.clientPreInit(event);
        registerRenderers();
        ModBlockRenderers.init(SilentGems.registry);
    }

    @Override
    public void init(SRegistry registry, FMLInitializationEvent event) {
        super.init(registry, event);
        registry.clientInit(event);
    }

    @Override
    public void postInit(SRegistry registry, FMLPostInitializationEvent event) {
        super.postInit(registry, event);
        registry.clientPostInit(event);
    }

    private void registerRenderers() {
        SRegistry reg = SilentGems.registry;

//    reg.registerEntityRenderer(EntityChaosProjectile.class, new RenderChaosProjectile.Factory());
//    reg.registerEntityRenderer(EntityThrownTomahawk.class, new RenderThrownTomahawk.Factory());
        reg.registerEntityRenderer(EntityEnderSlime.class, new RenderEnderSlime.Factory());
//    reg.registerEntityRenderer(EntityGemArrow.class, new RenderGemArrow.Factory());
    }

    // Particles

    @Override
    public void spawnParticles(EnumModParticles type, Color color, World world, double x, double y,
                               double z, double motionX, double motionY, double motionZ) {
        Particle fx = null;

        float r = color.getRed();
        float g = color.getGreen();
        float b = color.getBlue();

        switch (type) {
            case CHAOS:
                fx = new ParticleChaos(world, x, y, z, .2f, r, g, b, 25).setSpeed(motionX, motionY, motionZ);
                break;
            case CHAOS_PROJECTILE_BODY:
                fx = new ParticleChaos(world, x, y, z, .3f, r, g, b, 2);
                break;
            case CHAOS_PACKET_HEAD:
                fx = new ParticleChaos(world, x, y, z, .2f, r, g, b, 1).setSpeed(motionX, motionY, motionZ);
                break;
            case CHAOS_PACKET_TAIL:
                fx = new ParticleChaos(world, x, y, z, .1f, r, g, b, 15).setSpeed(motionX, motionY, motionZ);
                break;
            case CHAOS_NODE:
                fx = new ParticleChaos(world, x, y, z, .3f, r, g, b, 20).setSpeed(motionX, motionY, motionZ);
                break;
            case PHANTOM_LIGHT:
                fx = new ParticleChaos(world, x, y, z, .1f, r, g, b, 15).setSpeed(motionX, motionY, motionZ);
                break;
            case DRAWING_COMPASS:
                fx = new ParticleCompass(world, x, y, z, motionX, motionY, motionZ, 1.0f, 10, r, g, b);
                break;
            case FREEZING:
                fx = new ParticleFreezing(world, x, y, z, motionX, motionY, motionZ);
                break;
            case SHOCKING:
                fx = new ParticleShocking(world, x, y, z, motionX, motionY, motionZ);
                break;
            default:
                throw new NotImplementedException("Unknown particle type: " + type);
        }

        if (fx != null) {
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    @Override
    public int getParticleSettings() {
        return Minecraft.getMinecraft().gameSettings.particleSetting;
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }
}
