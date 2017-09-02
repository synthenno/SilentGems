package net.silentchaos512.gems.item;

import static net.silentchaos512.gems.lib.soul.EnumSoulElement.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.gems.lib.soul.EnumSoulElement;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.lib.util.ItemHelper;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.lib.util.StackHelper;

public class ItemSoulGem extends ItemSL {

  public static final String NBT_SOUL = "sg_soul_gem";

  List<Soul> listSorted = new ArrayList<>();
  Map<Class, Soul> classToSoul = new HashMap<>();
  Map<String, Soul> idToSoul = new HashMap<>();

  public ItemSoulGem() {

    super(1, SilentGems.MODID, Names.SOUL_GEM);

    //@formatter:off
    float blockDropRate = 0.025f;
    registerStackSoul(new Soul("Wheat", new ItemStack(Items.WHEAT),
        0xD5DA45, 0x404A10, FLORA, LIGHTNING).setDropRate(blockDropRate));
    registerStackSoul(new Soul("Potato", new ItemStack(Items.POTATO),
        0xE9BA62, 0x8F6830, FLORA, EARTH).setDropRate(blockDropRate));
    registerStackSoul(new Soul("Carrot", new ItemStack(Items.CARROT),
        0xFF911B, 0x2C8A2A, FLORA, FIRE).setDropRate(blockDropRate));
    registerStackSoul(new Soul("Beetroot", new ItemStack(Items.BEETROOT),
        0x812921, 0xA74D54, FLORA, WATER).setDropRate(blockDropRate));
    registerStackSoul(new Soul("FluffyPuff", new ItemStack(ModItems.fluffyPuff),
        0xFFFFFF, 0x999999, FLORA, WIND));

    float rateHigh = 0.075f;

    registerSoul(EntityZombie.class, new Soul("Zombie", MONSTER, VENOM));
    registerSoul(EntitySkeleton.class, new Soul("Skeleton", MONSTER, LIGHTNING));
    registerSoul(EntityCreeper.class, new Soul("Creeper", FIRE, FLORA));
    registerSoul(EntitySpider.class, new Soul("Spider", WIND, FAUNA));
    registerSoul(EntityCaveSpider.class, new Soul("CaveSpider", "cave_spider", VENOM, FAUNA));
    registerSoul(EntitySilverfish.class, new Soul("Silverfish", EARTH, FAUNA));
    registerSoul(EntitySlime.class, new Soul("Slime", WATER, EARTH));
    registerSoul(EntityWitch.class, new Soul("Witch", VENOM, ICE));
    registerSoul(EntityGuardian.class, new Soul("Guardian", WATER, LIGHTNING));
    registerSoul(EntityHusk.class, new Soul("Husk", MONSTER, EARTH));
    registerSoul(EntityStray.class, new Soul("Stray", MONSTER, ICE));

    registerSoul(EntityEvoker.class, new Soul("EvocationIllager", "evocation_illager", ICE));
    registerSoul(EntityVex.class, new Soul("Vex", LIGHTNING));
    registerSoul(EntityIllusionIllager.class,
        new Soul("IllusionIllager", "illusion_illager", WATER));

    registerSoul(EntityPigZombie.class, new Soul("PigZombie", "zombie_pigman", EARTH, FIRE));
    registerSoul(EntityGhast.class, new Soul("Ghast", LIGHTNING, FIRE));
    registerSoul(EntityBlaze.class, new Soul("Blaze", FIRE, WIND));
    registerSoul(EntityMagmaCube.class, new Soul("LavaSlime", "magma_cube", FIRE, EARTH));
    registerSoul(EntityWitherSkeleton.class,
        new Soul("WitherSkeleton", "wither_skeleton", EARTH, MONSTER));

    registerSoul(EntityEnderman.class, new Soul("Enderman", LIGHTNING, ALIEN).setDropRate(rateHigh));
    registerSoul(EntityEndermite.class, new Soul("Endermite", EARTH, ALIEN).setDropRate(rateHigh));
    registerSoul(EntityShulker.class, new Soul("Shulker", WATER, ALIEN).setDropRate(2 * rateHigh));

    registerSoul(EntityPig.class, new Soul("Pig", FAUNA, EARTH));
    registerSoul(EntitySheep.class, new Soul("Sheep", FAUNA, WIND));
    registerSoul(EntityCow.class, new Soul("Cow", FAUNA, WATER));
    registerSoul(EntityChicken.class, new Soul("Chicken", FAUNA, FIRE));
    registerSoul(EntityRabbit.class, new Soul("Rabbit", FAUNA, LIGHTNING));
    registerSoul(EntitySquid.class, new Soul("Squid", FAUNA, WATER));
    registerSoul(EntityBat.class, new Soul("Bat", FAUNA, ICE));
    registerSoul(EntityWolf.class, new Soul("Wolf", FAUNA, WIND));
    registerSoul(EntityOcelot.class, new Soul("Ozelot", "ocelot", FAUNA, FIRE)
        .setDropRate(rateHigh));
    registerSoul(EntityParrot.class, new Soul("Parrot", FAUNA, WIND)
        .setDropRate(2 * rateHigh));
    registerSoul(EntityMooshroom.class, new Soul("MushroomCow", "mooshroom", FAUNA, FLORA));
    registerSoul(EntityPolarBear.class, new Soul("PolarBear", "polar_bear", FAUNA, ICE)
        .setDropRate(rateHigh));
    registerSoul(EntitySnowman.class, new Soul("SnowMan", 0xFFFFFF, 0xFF8800, MONSTER, ICE));
    registerSoul(EntityHorse.class, new Soul("Horse", FAUNA, FIRE).setDropRate(rateHigh));
    registerSoul(EntityDonkey.class, new Soul("Donkey", FAUNA, EARTH).setDropRate(rateHigh));
    registerSoul(EntityMule.class, new Soul("Mule", FAUNA, METAL).setDropRate(rateHigh));
    registerSoul(EntityLlama.class, new Soul("Llama", FAUNA, WATER).setDropRate(rateHigh));

    registerSoul(EntityIronGolem.class,
        new Soul("VillagerGolem", 0xEEEEEE, 0xFFAAAA, METAL, MONSTER).setDropRate(0.6f));
    registerSoul(EntityVillager.class, new Soul("Villager", EARTH).setDropRate(0.1f));

    registerSoul(EntityDragon.class,new Soul("EnderDragon", 0x222222, 0xEE88EE, FIRE, ALIEN)
        .setDropRate(1.0f).setDropCount(2));
    registerSoul(EntityWither.class, new Soul("WitherBoss", 0x333333, 0xEECCEE, LIGHTNING, ALIEN)
        .setDropRate(1.0f).setDropCount(2));
    registerSoul(EntityElderGuardian.class, new Soul("ElderGuardian", "elder_guardian", WATER, ALIEN)
        .setDropRate(1.0f).setDropCount(2));
    //@formatter:on
  }

  public void registerSoul(Class clazz, Soul soul) {

    listSorted.add(soul);
    classToSoul.put(clazz, soul);
    idToSoul.put(soul.id, soul);
  }

  public void registerStackSoul(Soul soul) {

    listSorted.add(soul);
    idToSoul.put(soul.id, soul);
  }

  public ItemStack getStack(Class clazz) {

    Soul soul = classToSoul.get(clazz);
    if (soul == null) {
      return StackHelper.empty();
    }

    return getStack(soul);
  }

  public ItemStack getStack(Soul soul) {

    ItemStack stack = new ItemStack(this);
    NBTTagCompound tags = new NBTTagCompound();
    tags.setString(NBT_SOUL, soul.id);
    stack.setTagCompound(tags);
    return stack;
  }

  public Soul getSoul(ItemStack stack) {

    if (StackHelper.isEmpty(stack) || !stack.hasTagCompound()) {
      return null;
    }
    return idToSoul.get(stack.getTagCompound().getString(NBT_SOUL));
  }

  public Soul getSoul(Class<? extends Entity> clazz) {

    return classToSoul.get(clazz);
  }

  public Soul getSoul(BlockEvent.HarvestDropsEvent event) {

    for (Soul soul : listSorted) {
      if (StackHelper.isValid(soul.matchStack)) {
        for (ItemStack stack : event.getDrops()) {
          if (stack.isItemEqual(soul.matchStack)) {
            return soul;
          }
        }
      }
    }

    return null;
  }

  @Override
  protected void clGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {

    if (!ItemHelper.isInCreativeTab(item, tab))
      return;

    for (Soul soul : listSorted) {
      list.add(getStack(soul));
    }
  }

  @Override
  public void clAddInformation(ItemStack stack, World world, List<String> list, boolean advanced) {

    Soul soul = getSoul(stack);
    if (soul != null) {
      list.add("Soul ID: " + soul.id);
      list.add("Elements: " + soul.getElementsForDisplay());
    }
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {

    LocalizationHelper loc = SilentGems.localizationHelper;
    Soul soul = getSoul(stack);
    if (soul == null) {
      return getUnlocalizedName(stack);
    }

    if (StackHelper.isValid(soul.matchStack)) {
      return loc.getItemSubText(Names.SOUL_GEM, "name_proper", soul.matchStack.getDisplayName());
    } else {
      String name = "entity." + soul.id + ".name";
      name = loc.getLocalizedString(name);
      return loc.getItemSubText(Names.SOUL_GEM, "name_proper", name);
    }
  }

  public static class Soul {

    public final String id;
    public final int colorPrimary, colorSecondary;
    public final @Nonnull EnumSoulElement element1;
    public final @Nonnull EnumSoulElement element2;
    public final ItemStack matchStack;
    float dropRate = 0.05f;
    int dropCount = 1;

    public Soul(String id, int colorPrimary, int colorSecondary, EnumSoulElement... elements) {

      this.id = id;
      this.colorPrimary = colorPrimary;
      this.colorSecondary = colorSecondary;
      this.element1 = elements[0];
      this.element2 = elements.length > 1 ? elements[1] : EnumSoulElement.NONE;
      this.matchStack = StackHelper.empty();
    }

    public Soul(String entityId, EnumSoulElement... elements) {

      this(entityId, entityId, elements);
    }

    public Soul(String entityId, String eggName, EnumSoulElement... elements) {

      this.id = entityId;
      EntityEggInfo info = EntityList.ENTITY_EGGS.get(new ResourceLocation(eggName));
      if (info != null) {
        this.colorPrimary = info.primaryColor;
        this.colorSecondary = info.secondaryColor;
      } else {
        this.colorPrimary = this.colorSecondary = 0xFF00FF;
      }
      this.element1 = elements[0];
      this.element2 = elements.length > 1 ? elements[1] : EnumSoulElement.NONE;
      this.matchStack = StackHelper.empty();
    }

    /**
     * Soul for block drops.
     */
    public Soul(String id, ItemStack match, int colorPrimary, int colorSecondary,
        EnumSoulElement... elements) {

      this.id = id;
      this.colorPrimary = colorPrimary;
      this.colorSecondary = colorSecondary;
      this.element1 = elements[0];
      this.element2 = elements.length > 1 ? elements[1] : EnumSoulElement.NONE;
      this.matchStack = match;
    }

    public float getDropRate() {

      return dropRate;
    }

    public Soul setDropRate(float value) {

      dropRate = value;
      return this;
    }

    public int getDropCount() {

      return dropCount;
    }

    public Soul setDropCount(int value) {

      dropCount = value;
      return this;
    }

    public String getElementsForDisplay() {

      if (element2 == NONE) {
        return element1.getDisplayName();
      } else {
        return element1.getDisplayName() + ", " + element2.getDisplayName();
      }
    }

    public ItemStack getStack() {

      return ModItems.soulGem.getStack(this);
    }
  }
}
