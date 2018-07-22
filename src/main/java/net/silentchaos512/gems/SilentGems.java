package net.silentchaos512.gems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.gems.config.GemsConfig;
import net.silentchaos512.gems.entity.ModEntities;
import net.silentchaos512.gems.init.*;
import net.silentchaos512.gems.lib.Gems;
import net.silentchaos512.gems.proxy.CommonProxy;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.lib.util.I18nHelper;
import net.silentchaos512.lib.util.LogHelper;

import java.util.Random;

//@formatter:off
@Mod(modid = SilentGems.MOD_ID,
        name = SilentGems.MOD_NAME,
        version = SilentGems.VERSION,
        dependencies = SilentGems.DEPENDENCIES,
        acceptedMinecraftVersions = SilentGems.ACCEPTED_MC_VERSIONS,
        guiFactory = "net.silentchaos512.gems.client.gui.config.GuiFactorySilentGems")
//@formatter:on
public class SilentGems {

    public static final String MOD_ID = "silentgems";
    public static final String MOD_NAME = "Silent's Gems";
    public static final String VERSION = "3.0.0";
    public static final String VERSION_SILENTLIB = "2.3.11";
    public static final int BUILD_NUM = 0;
    public static final String DEPENDENCIES = "required-after:silentlib@[" + VERSION_SILENTLIB + ",)";
    public static final String ACCEPTED_MC_VERSIONS = "[1.12,1.12.2]";
    public static final String RESOURCE_PREFIX = MOD_ID + ":";

    public static Random random = new Random();
    public static LogHelper log = new LogHelper(MOD_NAME, BUILD_NUM);
    public static I18nHelper i18n = new I18nHelper(MOD_ID, log, true);

    public static SRegistry registry = new SRegistry(MOD_ID, log);

    public static CreativeTabs tabMaterials = registry.makeCreativeTab(MOD_ID + ".materials", () -> Gems.getRandom().getItemGem().getStack());
    public static CreativeTabs tabUtility = registry.makeCreativeTab(MOD_ID + ".utility", () -> new ItemStack(ModItems.drawingCompass));

    @Instance(MOD_ID)
    public static SilentGems instance;

    @SidedProxy(clientSide = "net.silentchaos512.gems.proxy.ClientProxy", serverSide = "net.silentchaos512.gems.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GemsConfig.INSTANCE.init(event.getSuggestedConfigurationFile());

        registry.addRegistrationHandler(new ModEnchantments(), Enchantment.class);
        registry.addRegistrationHandler(new ModBlocks(), Block.class);
        registry.addRegistrationHandler(new ModItems(), Item.class);
        registry.addRegistrationHandler(new ModPotions(), Potion.class);
        registry.addRegistrationHandler(new ModRecipes(), IRecipe.class);
//        SoulSkill.init();

//        GemsConfig.INSTANCE.loadModuleConfigs();

        // World generation
//        GameRegistry.registerWorldGenerator(new GemsWorldGenerator(), 0);
//        GameRegistry.registerWorldGenerator(new GemsGeodeWorldGenerator(), -10);

        proxy.preInit(registry, event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModEntities.init(registry);

        GemsConfig.INSTANCE.save();

        proxy.init(registry, event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(registry, event);
    }

    public int getBuildNum() {
        return BUILD_NUM;
    }
}
