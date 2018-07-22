package net.silentchaos512.gems.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.event.GemsCommonEvents;
import net.silentchaos512.gems.init.ModMaterials;
import net.silentchaos512.gems.lib.EnumModParticles;
import net.silentchaos512.gems.lib.module.ModuleHolidayCheer;
import net.silentchaos512.lib.proxy.IProxy;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.lib.util.Color;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(SRegistry registry, FMLPreInitializationEvent event) {
        registry.addPhasedInitializer(ModMaterials.INSTANCE);


//    ModItems.guideBook.book.preInit();

//        NetworkHandler.init();

//        NetworkRegistry.INSTANCE.registerGuiHandler(SilentGems.instance, new GuiHandlerSilentGems());

//    MinecraftForge.EVENT_BUS.register(new PlayerDataHandler.EventHandler());
        MinecraftForge.EVENT_BUS.register(new GemsCommonEvents());
//        MinecraftForge.EVENT_BUS.register(new SoulManager());
        MinecraftForge.EVENT_BUS.register(ModuleHolidayCheer.instance);

        LootTableList.register(new ResourceLocation(SilentGems.MOD_ID, "ender_slime"));

        registry.preInit(event);
    }

    @Override
    public void init(SRegistry registry, FMLInitializationEvent event) {
        registry.init(event);
    }

    @Override
    public void postInit(SRegistry registry, FMLPostInitializationEvent event) {
//        ModItems.guideBook.book.postInit();
        registry.postInit(event);
    }

    public void spawnParticles(EnumModParticles type, Color color, World world, double x, double y,
                               double z, double motionX, double motionY, double motionZ) {
    }

    public int getParticleSettings() {
        return 0;
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }
}
