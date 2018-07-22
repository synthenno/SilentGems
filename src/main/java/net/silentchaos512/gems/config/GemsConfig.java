package net.silentchaos512.gems.config;

import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.lib.config.ConfigBase;

public class GemsConfig extends ConfigBase {

    /*
     * Blocks
     */

    public static int GLOW_ROSE_LIGHT_LEVEL = 10;

//    public static int TELEPORTER_FREE_RANGE = 64;
//    public static int TELEPORTER_COST_PER_BLOCK = 100;
//    public static int TELEPORTER_COST_CROSS_DIMENSION = 100000;
//    public static int TELEPORTER_MAX_CHARGE = 1000000;
//    public static float TELEPORTER_REDSTONE_SEARCH_RADIUS = 2.0f;
//    public static boolean TELEPORTER_ALLOW_DUMB = true;

    /*
     * Items
     */

//    public static int BURN_TIME_CHAOS_COAL = 6400;
//    public static boolean SPAWN_PLAYER_WITH_GUIDE_BOOK = true;

//    public static int FOOD_SUPPORT_DURATION = 600;
//    public static float FOOD_SECRET_DONUT_CHANCE = 0.33f;
//    public static float FOOD_SECRET_DONUT_TEXT_CHANCE = 0.6f;

//    public static int RETURN_HOME_USE_TIME = 16;
//    public static int RETURN_HOME_USE_COST = 10000;
//    public static int RETURN_HOME_MAX_CHARGE = 100000;

    /*
     * Tool Souls
     */

//    public static boolean SOULS_GAIN_XP_FROM_FAKE_PLAYERS;

    /*
     * Nodes
     */

//    public static boolean CHAOS_NODE_SALT_DELAY = true;
//    public static int CHAOS_NODE_PARTICLE_OVERRIDE = -1;
//    public static Set<Item> NODE_REPAIR_WHITELIST = new HashSet<>();
//    public static Set<Item> NODE_REPAIR_BLACKLIST = new HashSet<>();

    /*
     * Entities
     */

    public static int ENDER_SLIME_SPAWN_WEIGHT = 3;
    public static int ENDER_SLIME_SPAWN_MIN = 1;
    public static int ENDER_SLIME_SPAWN_MAX = 2;

    /*
     * GUI
     */

//    public static int CHAOS_GEM_BAR_POS_X = 5;
//    public static int CHAOS_GEM_BAR_POS_Y = 5;

    /*
     * Misc
     */

//    public static boolean HIDE_FLAVOR_TEXT_ALWAYS = false;
//    public static boolean HIDE_FLAVOR_TEXT_UNTIL_SHIFT = true;

    /*
     * Compat
     */

    // nada

    /*
     * World generation
     */

//    public static ConfigOptionOreGen WORLD_GEN_GEMS;
//    public static ConfigOptionOreGen WORLD_GEN_GEMS_DARK;
//    public static ConfigOptionOreGen WORLD_GEN_GEMS_LIGHT;
//    public static boolean GEM_CLASSIC_USE_MULTI_ORE;
//    public static boolean GEM_DARK_USE_MULTI_ORE;
//    public static boolean GEM_LIGHT_USE_MULTI_ORE;
//    public static ConfigOptionOreGen WORLD_GEN_CHAOS;
//    public static ConfigOptionOreGen WORLD_GEN_ENDER;
//    public static List<WeightedRandomItemSG> GEM_WEIGHTS = new ArrayList<>();
//    public static List<WeightedRandomItemSG> GEM_WEIGHTS_DARK = new ArrayList<>();
//    public static List<WeightedRandomItemSG> GEM_WEIGHTS_LIGHT = new ArrayList<>();

    // Regions
//    public static boolean GEM_REGIONS_ENABLED;
//    public static int GEM_REGIONS_SIZE;
//    public static float GEM_REGIONS_SECOND_GEM_CHANCE;

    // Geodes
//    public static float GEODE_DARK_FREQUENCY;
//    public static float GEODE_LIGHT_FREQUENCY;
//    public static int GEODE_MIN_Y;
//    public static int GEODE_MAX_Y;
//    public static float GEODE_FILL_RATIO;
//    public static float GEODE_GEM_DENSITY;
//    public static boolean GEODE_SEAL_BREAKS;

//    public static int GLOW_ROSE_PER_CHUNK = 2;
//    public static final Set<Integer> GLOW_ROSE_DIMENSION_BLACKLIST = new HashSet<>();

    /*
     * Categories
     */

    // TODO

    public static final GemsConfig INSTANCE = new GemsConfig();

    private GemsConfig() {
        super(SilentGems.MOD_ID);
    }

    @Override
    public void load() {
        try {
            // TODO
        } catch (Exception e) {
            System.out.println("Oh noes!!! Couldn't load configuration file properly!");
        }
    }
}
