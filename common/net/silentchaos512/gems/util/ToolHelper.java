package net.silentchaos512.gems.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.api.IBlockPlacer;
import net.silentchaos512.gems.api.ITool;
import net.silentchaos512.gems.api.tool.ToolStats;
import net.silentchaos512.gems.api.tool.part.ToolPart;
import net.silentchaos512.gems.api.tool.part.ToolPartMain;
import net.silentchaos512.gems.api.tool.part.ToolPartRegistry;
import net.silentchaos512.gems.config.ConfigOptionToolClass;
import net.silentchaos512.gems.config.GemsConfig;
import net.silentchaos512.gems.guide.GuideBookGems;
import net.silentchaos512.gems.init.ModItems;
import net.silentchaos512.gems.item.ToolRenderHelper;
import net.silentchaos512.gems.item.ToolRenderHelperBase;
import net.silentchaos512.gems.item.tool.ItemGemAxe;
import net.silentchaos512.gems.item.tool.ItemGemBow;
import net.silentchaos512.gems.item.tool.ItemGemHoe;
import net.silentchaos512.gems.item.tool.ItemGemPickaxe;
import net.silentchaos512.gems.item.tool.ItemGemShield;
import net.silentchaos512.gems.item.tool.ItemGemShovel;
import net.silentchaos512.gems.item.tool.ItemGemSickle;
import net.silentchaos512.gems.item.tool.ItemGemSword;
import net.silentchaos512.gems.lib.Greetings;
import net.silentchaos512.gems.lib.Names;
import net.silentchaos512.gems.skills.SkillAreaMiner;
import net.silentchaos512.gems.skills.SkillLumberjack;
import net.silentchaos512.gems.skills.ToolSkill;
import net.silentchaos512.lib.recipe.IngredientSL;
import net.silentchaos512.lib.util.ItemHelper;
import net.silentchaos512.lib.util.StackHelper;
import net.silentchaos512.lib.util.WorldHelper;

public class ToolHelper {

  /**
   * A fake material for tools. Tools need a tool material, even if it's not used. Unfortunately, some mods still
   * reference the tool material (such as Progressive Automation, which is why I chose harvest level 1).
   */
  public static final ToolMaterial FAKE_MATERIAL = EnumHelper
      .addToolMaterial("silentgems:fake_material", 1, 512, 5.12f, 5.12f, 32);

  /*
   * NBT keys
   */

  // Root keys
  public static final String NBT_ROOT_CONSTRUCTION = "SGConstruction";
  public static final String NBT_ROOT_PROPERTIES = "SGProperties";
  public static final String NBT_ROOT_STATISTICS = "SGStatistics";

  // Settings
  public static final String NBT_SETTINGS_SPECIAL = "SpecialEnabled";

  // Construction
  public static final String NBT_PART_ROOT = "Part";

  public static final String NBT_PART_HEAD = "Head";
  public static final String NBT_PART_ROD = "Rod";
  public static final String NBT_PART_ROD_DECO = "RodDeco";
  public static final String NBT_PART_HEAD_TIP = "HeadTip";

  // Stats
  public static final String NBT_PROP_DURABILITY = "Durability";
  public static final String NBT_PROP_HARVEST_LEVEL = "HarvestLevel";
  public static final String NBT_PROP_HARVEST_SPEED = "HarvestSpeed";
  public static final String NBT_PROP_MELEE_DAMAGE = "MeleeDamage";
  public static final String NBT_PROP_MAGIC_DAMAGE = "MagicDamage";
  public static final String NBT_PROP_ENCHANTABILITY = "Enchantability";
  public static final String NBT_PROP_MELEE_SPEED = "MeleeSpeed";
  public static final String NBT_PROP_BLOCKING_POWER = "BlockingPower";

  // NBT for statistics
  public static final String NBT_STATS_ORIGINAL_OWNER = "OriginalOwner";
  public static final String NBT_STATS_REDECORATED = "Redecorated";
  public static final String NBT_STATS_BLOCKS_MINED = "BlocksMined";
  public static final String NBT_STATS_BLOCKS_PLACED = "BlocksPlaced";
  public static final String NBT_STATS_BLOCKS_TILLED = "BlocksTilled";
  public static final String NBT_STATS_HITS = "HitsLanded";
  public static final String NBT_STATS_KILL_COUNT = "KillCount";
  public static final String NBT_STATS_PATHS_MADE = "PathsMade";
  public static final String NBT_STATS_SHOTS_FIRED = "ShotsFired";
  public static final String NBT_STATS_SHOTS_LANDED = "ShotsLanded";
  public static final String NBT_STATS_THROWN = "ThrownCount";

  // NBT example key
  public static final String NBT_EXAMPLE_TOOL_TIER = "ExampleToolTier";

  public static void init() {

  }

  /**
   * Recalculate all stats and properties, including the rendering cache for the given tool. In general, this should be
   * called any time changes are made to a tool (aside from incrementing statistics, or something like that). For
   * example, this is called during construction, decoration, and for all tools in the players inventory during login.
   * 
   * @param tool
   */
  public static void recalculateStats(ItemStack tool) {

    ToolPart partHead = getPartHead(tool);
    ToolPart partRod = getPartRod(tool);
    ToolPart partTip = getPartTip(tool);
    ToolPart partDeco = getPartDeco(tool);
    ToolPart[] parts = { partHead, partRod, partTip };

    ToolStats stats = new ToolStats(tool, parts).calculate();

    // Reset render cache
    for (int i = 0; i < ToolRenderHelperBase.RENDER_PASS_COUNT; ++i) {
      NBTTagCompound compound = tool.getTagCompound()
          .getCompoundTag(ToolRenderHelper.NBT_MODEL_INDEX);
      String str = "Layer" + i;
      for (int frame = 0; frame < (tool.getItem() instanceof ItemGemBow ? 4 : 1); ++frame)
        compound.removeTag(str + (frame > 0 ? "_" + frame : ""));
      compound.removeTag(str + "Color");
    }

    // Set color for parts
    colorCache(tool, ToolRenderHelper.LAYER_HEAD, partHead);
    colorCache(tool, ToolRenderHelper.LAYER_HEAD_HIGHLIGHT, partHead);
    colorCache(tool, ToolRenderHelper.LAYER_ROD, partRod);
    colorCache(tool, ToolRenderHelper.LAYER_TIP, partTip);
    colorCache(tool, ToolRenderHelper.LAYER_ROD_DECO, partDeco);

    setTagInt(tool, NBT_ROOT_PROPERTIES, NBT_PROP_DURABILITY, (int) stats.durability);
    setTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_HARVEST_SPEED, stats.harvestSpeed);
    setTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_MELEE_DAMAGE, stats.meleeDamage);
    setTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_MAGIC_DAMAGE, stats.magicDamage);
    setTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_MELEE_SPEED, stats.meleeSpeed);
    setTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_BLOCKING_POWER, stats.blockingPower);
    setTagInt(tool, NBT_ROOT_PROPERTIES, NBT_PROP_ENCHANTABILITY, (int) stats.enchantability);
    setTagInt(tool, NBT_ROOT_PROPERTIES, NBT_PROP_HARVEST_LEVEL, stats.harvestLevel);
  }

  private static void colorCache(ItemStack tool, int layer, ToolPart part) {

    if (tool == null)
      return;

    String key = "Layer" + layer + "_Color";
    int color = 0xFFFFFF;
    if (part != null) {
      color = part.getColor(tool);
    }
    setTagInt(tool, ToolRenderHelper.NBT_MODEL_INDEX, key, color);
  }

  // ==========================================================================
  // Mining, using, repairing, etc
  // ==========================================================================

  public static boolean getIsRepairable(ItemStack tool, ItemStack material) {

    // TODO
    return false;
  }

  public static void attemptDamageTool(ItemStack tool, int amount, EntityLivingBase entityLiving) {

    tool.damageItem(amount, entityLiving);
  }

  public static float getDigSpeed(ItemStack tool, IBlockState state, Material[] extraMaterials) {

    float speed = getDigSpeedOnProperMaterial(tool);

    // Tool effective on block?
    if (tool.getItem().canHarvestBlock(state, tool)) {
      return speed;
    }

    for (String type : tool.getItem().getToolClasses(tool)) {
      try {
        if (state.getBlock().isToolEffective(type, state)) {
          return speed;
        }
      } catch (IllegalArgumentException ex) {
        return 1f;
      }
    }

    if (extraMaterials != null) {
      for (Material material : extraMaterials) {
        if (state.getMaterial() == material) {
          return speed;
        }
      }
    }

    // Tool ineffective.
    return 1f;
  }

  public static float getDigSpeedOnProperMaterial(ItemStack tool) {

    return getTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_HARVEST_SPEED);
  }

  public static float getMeleeSpeed(ItemStack tool) {

    return getTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_MELEE_SPEED);
  }

  public static float getMeleeDamage(ItemStack tool) {

    return getTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_MELEE_DAMAGE);
  }

  public static float getMagicDamage(ItemStack tool) {

    return getTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_MAGIC_DAMAGE);
  }

  public static int getHarvestLevel(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_PROPERTIES, NBT_PROP_HARVEST_LEVEL);
  }

  public static int getMaxDamage(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_PROPERTIES, NBT_PROP_DURABILITY);
  }

  public static float getBlockingPower(ItemStack tool) {

    return getTagFloat(tool, NBT_ROOT_PROPERTIES, NBT_PROP_BLOCKING_POWER);
  }

  /**
   * Check if the tool has no construction parts. Only checks the head, but no valid tools will have no head parts.
   * 
   * @return true if the has no construction parts.
   */
  public static boolean hasNoConstruction(ItemStack tool) {

    String key = getPartId(tool, NBT_PART_HEAD);
    return key == null || key.isEmpty();
  }

  public static int getItemEnchantability(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_PROPERTIES, NBT_PROP_ENCHANTABILITY);
  }

  public static Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot,
      ItemStack tool) {

    String name;
    if (tool.getItem() instanceof ItemTool)
      name = "Tool modifier";
    else
      name = "Weapon modifier";

    @SuppressWarnings("deprecation")
    Multimap<String, AttributeModifier> map = tool.getItem().getItemAttributeModifiers(slot);

    if (slot == EntityEquipmentSlot.MAINHAND) {

      // Melee Damage
      String key = SharedMonsterAttributes.ATTACK_DAMAGE.getName();
      float value = getMeleeDamageModifier(tool);
      replaceAttributeModifierInMap(map, key, value);

      // Melee Speed
      key = SharedMonsterAttributes.ATTACK_SPEED.getName();
      value = getMeleeSpeedModifier(tool);
      replaceAttributeModifierInMap(map, key, value);
    }

    return map;
  }

  private static void replaceAttributeModifierInMap(Multimap<String, AttributeModifier> map,
      String key, float value) {

    if (map.containsKey(key)) {
      Iterator<AttributeModifier> iter = map.get(key).iterator();
      if (iter.hasNext()) {
        AttributeModifier mod = iter.next();
        map.removeAll(key);
        map.put(key, new AttributeModifier(mod.getID(), mod.getName(), value, mod.getOperation()));
      }
    }
  }

  public static float getMeleeDamageModifier(ItemStack tool) {

    float val;
    if (tool.getItem() instanceof ITool)
      val = ((ITool) tool.getItem()).getMeleeDamage(tool);
    else
      val = getMeleeDamage(tool);

    return val < 0 ? 0 : val;
  }

  public static float getMagicDamageModifier(ItemStack tool) {

    float val;
    if (tool.getItem() instanceof ITool)
      val = ((ITool) tool.getItem()).getMagicDamage(tool);
    else
      val = getMagicDamage(tool);

    return val < 0 ? 0 : val;
  }

  public static float getMeleeSpeedModifier(ItemStack tool) {

    if (!(tool.getItem() instanceof ITool))
      return 0.0f;

    float base = ((ITool) tool.getItem()).getMeleeSpeedModifier();
    float speed = getMeleeSpeed(tool);
    return ((base + 4) * speed - 4);
  }

  /**
   * This controls the block placing ability of mining tools.
   */
  public static EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
      EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

    ItemStack stack = player.getHeldItem(hand);

    // If block is in offhand, allow that to place instead.
    ItemStack stackOffHand = player.getHeldItemOffhand();
    if (StackHelper.isValid(stackOffHand) && stackOffHand.getItem() instanceof ItemBlock) {
      ItemBlock itemBlock = (ItemBlock) stackOffHand.getItem();
      BlockPos target = pos;

      if (!itemBlock.getBlock().isReplaceable(world, pos))
        target = pos.offset(side);

      if (player.canPlayerEdit(target, side, stackOffHand) && WorldHelper.mayPlace(world,
          itemBlock.getBlock(), target, false, side, player, stackOffHand))
        return EnumActionResult.PASS;
    }

    // Behavior configs.
    if (!GemsConfig.RIGHT_CLICK_TO_PLACE_ENABLED)
      return EnumActionResult.PASS;
    if (GemsConfig.RIGHT_CLICK_TO_PLACE_ON_SNEAK_ONLY && !player.isSneaking())
      return EnumActionResult.PASS;

    EnumActionResult result = EnumActionResult.PASS;
    int toolSlot = player.inventory.currentItem;
    int itemSlot = toolSlot + 1;
    ItemStack nextStack = StackHelper.empty();
    ItemStack lastStack = player.inventory.getStackInSlot(8); // Slot 9 in hotbar

    if (toolSlot < 8) {
      // Get stack in slot after tool.
      nextStack = player.inventory.getStackInSlot(itemSlot);
      boolean emptyOrNoPlacingTag = StackHelper.isEmpty(nextStack)
          || (nextStack.hasTagCompound() && nextStack.getTagCompound().hasKey("NoPlacing"));

      // If there's nothing there we can use, try slot 9 instead.
      if (emptyOrNoPlacingTag || (!(nextStack.getItem() instanceof ItemBlock)
          && !(nextStack.getItem() instanceof IBlockPlacer))) {
        nextStack = lastStack;
        itemSlot = 8;
      }

      emptyOrNoPlacingTag = StackHelper.isEmpty(nextStack)
          || (nextStack.hasTagCompound() && nextStack.getTagCompound().hasKey("NoPlacing"));

      if (!emptyOrNoPlacingTag) {
        Item item = nextStack.getItem();
        if (item instanceof ItemBlock || item instanceof IBlockPlacer) {
          BlockPos targetPos = pos.offset(side);
          int playerX = (int) Math.floor(player.posX);
          int playerY = (int) Math.floor(player.posY);
          int playerZ = (int) Math.floor(player.posZ);

          // Check for block overlap with player, if necessary.
          if (item instanceof ItemBlock) {
            int px = targetPos.getX();
            int py = targetPos.getY();
            int pz = targetPos.getZ();
            AxisAlignedBB blockBounds = new AxisAlignedBB(px, py, pz, px + 1, py + 1, pz + 1);
            AxisAlignedBB playerBounds = player.getEntityBoundingBox();
            ItemBlock itemBlock = (ItemBlock) item;
            Block block = itemBlock.getBlock();
            IBlockState state = block.getStateFromMeta(itemBlock.getMetadata(nextStack));
            if (state.getMaterial().blocksMovement() && playerBounds.intersects(blockBounds)) {
              return EnumActionResult.FAIL;
            }
          }

          int prevSize = StackHelper.getCount(nextStack);
          result = ItemHelper.useItemAsPlayer(nextStack, player, world, pos, side, hitX, hitY,
              hitZ);

          // Don't consume in creative mode?
          if (player.capabilities.isCreativeMode) {
            StackHelper.setCount(nextStack, prevSize);
          }

          // Remove empty stacks.
          if (StackHelper.isEmpty(nextStack)) {
            nextStack = StackHelper.empty();
            player.inventory.setInventorySlotContents(itemSlot, StackHelper.empty());
          }
        }
      }
    }

    if (result == EnumActionResult.SUCCESS)
      incrementStatBlocksPlaced(stack, 1);

    return result;
  }

  public static @Nullable ToolSkill getSuperSkill(ItemStack tool) {

    // TODO: What conditions do we allow super skills in?

    Item item = tool.getItem();
    if (item instanceof ItemGemPickaxe || item instanceof ItemGemShovel) {
      return SkillAreaMiner.INSTANCE;
    } else if (item instanceof ItemGemAxe) {
      return GemsConfig.SWITCH_AXE_SUPER ? SkillAreaMiner.INSTANCE : SkillLumberjack.INSTANCE;
    }

    return null;
  }

  /**
   * Called by mining tools if block breaking isn't canceled.
   * 
   * @return False in all cases, because this method is only called when Item.onBlockStartBreak returns false.
   */
  public static boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {

    boolean abilityActivated = false;

    ToolSkill skill = getSuperSkill(stack);
    if (skill != null)
      skill.activate(stack, player, pos);
    // TODO: Add some sort of delay, so tool name doesn't pop up every time?
    incrementStatBlocksMined(stack, 1);

    // Mining achievements TODO: Uncomment
    // amount = getStatBlocksMined(stack);
    // if (amount >= 1000) {
    // player.addStat(GemsAchievement.mined1K, 1);
    // }
    // if (amount >= 10000) {
    // player.addStat(GemsAchievement.mined10K, 1);
    // }
    // if (amount >= 100000) {
    // player.addStat(GemsAchievement.mined100K, 1);
    // }

    return false;
  }

  public static boolean onBlockDestroyed(ItemStack tool, World world, IBlockState state,
      BlockPos pos, EntityLivingBase entityLiving) {

    boolean isSickle = tool.getItem() == ModItems.sickle;
    if ((isSickle || state.getBlockHardness(world, pos) != 0)) {
      if (state.getMaterial() != Material.LEAVES) {
        attemptDamageTool(tool, isSickle ? ItemGemSickle.DURABILITY_USAGE : 1, entityLiving);

        // if (isBroken(tool))
        // entityLiving.renderBrokenItemStack(tool);
      }
    }
    return true;
  }

  public static boolean hitEntity(ItemStack tool, EntityLivingBase target,
      EntityLivingBase attacker) {

    ToolHelper.incrementStatHitsLanded(tool, 1);

    boolean isSword = tool.getItem() instanceof ItemGemSword;
    boolean isShield = tool.getItem() instanceof ItemGemShield;
    boolean isTool = tool.getItem() instanceof ItemTool || tool.getItem() instanceof ItemGemHoe;
    // boolean isBroken = isBroken(tool);

    // if (!isBroken) {
    int currentDmg = tool.getItemDamage();
    int maxDmg = tool.getMaxDamage();
    attemptDamageTool(tool, isTool ? 2 : (isSword || isShield ? 1 : 0), attacker);

    // if (isBroken(tool)) {
    // attacker.renderBrokenItemStack(tool);
    // }
    // }

    return /* !isBroken && */ isTool;
  }

  public static void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot,
      boolean isSelected) {

    if (!world.isRemote) {
      if (hasNoConstruction(tool)) {
        ItemStack newTool = ToolRandomizer.INSTANCE.randomize(tool);
      }
      return;
    }
  }

  public static boolean isSpecialAbilityEnabled(ItemStack tool) {

    return getTagBoolean(tool, NBT_ROOT_PROPERTIES, NBT_SETTINGS_SPECIAL);
  }

  public static void toggleSpecialAbility(ItemStack tool) {

    setTagBoolean(tool, NBT_ROOT_PROPERTIES, NBT_SETTINGS_SPECIAL,
        !getTagBoolean(tool, NBT_ROOT_PROPERTIES, NBT_SETTINGS_SPECIAL));
  }

  // ==========================================================================
  // Tool construction and decoration
  // ==========================================================================

  public static ItemStack constructTool(Item item, ItemStack rod, ItemStack head) {

    ItemStack result = new ItemStack(item);
    result.setTagCompound(new NBTTagCompound());

    // Set construction materials
    ToolPart part;
    // Head
    if (StackHelper.isEmpty(head)) {
      String str = "ToolHelper.constructTool: empty head! " + head;
      throw new IllegalArgumentException(str);
    }
    part = ToolPartRegistry.fromStack(head);
    setTagPart(result, NBT_PART_HEAD, part);

    // Rod
    part = ToolPartRegistry.fromStack(rod);
    setTagPart(result, NBT_PART_ROD, part);

    recalculateStats(result);

    return result;
  }

  static boolean foundEmptyPart = false;
  static Set<ToolPartMain> emptyPartSet = Sets.newHashSet();

  public static List<ItemStack> getSubItems(Item item, int materialLength) {

    List<ItemStack> list = Lists.newArrayList();
    // final boolean isSuperTool = item instanceof ITool && ((ITool) item).isSuperTool();
    final ItemStack rodWood = new ItemStack(Items.STICK);
    final ItemStack rodIron = ModItems.craftingMaterial.toolRodIron;
    final ItemStack rodGold = ModItems.craftingMaterial.toolRodGold;

    for (ToolPartMain part : ToolPartRegistry.getMains()) {
      // Check for parts with empty crafting stacks and scream at the user if any are found.
      if (StackHelper.isEmpty(part.getCraftingStack())) {
        if (!emptyPartSet.contains(part)) {
          emptyPartSet.add(part);
          SilentGems.logHelper.severe("Part with empty crafting stack: " + part);
          if (!foundEmptyPart) {
            Greetings.addExtraMessage(TextFormatting.RED
                + "Errored tool part found! Please report this issue on the GitHub issue tracker.");
            foundEmptyPart = true;
          }
          Greetings.addExtraMessage(TextFormatting.ITALIC + part.toString());
        }
      } else {
        if (!part.isBlacklisted(part.getCraftingStack())) {
          list.add(constructTool(item, part.getPreferredRod(), part.getCraftingStack()));
        }
      }
    }

    // Set maker name.
    String makerName = SilentGems.localizationHelper.getMiscText("Tooltip.OriginalOwner.Creative");
    for (ItemStack stack : list)
      ToolHelper.setOriginalOwner(stack, makerName);

    return list;
  }

  public static boolean areToolsEqual(ItemStack a, ItemStack b) {

    return a.getItem() == b.getItem()
        && getRootTag(a, NBT_ROOT_CONSTRUCTION).equals(getRootTag(b, NBT_ROOT_CONSTRUCTION));
  }

  public static IRecipe addExampleRecipe(Item item, String line1, String line2, String line3) {

    // New ingredient-based recipes

    ConfigOptionToolClass config = ((ITool) item).getConfig();

    // Head parts for tier
    List<ItemStack> heads = new ArrayList<>();
    for (ToolPart part : ToolPartRegistry.getMains())
      if (StackHelper.isValid(part.getCraftingStack()))
        heads.add(part.getCraftingStack());
    IngredientSL headIngredient = IngredientSL.from(heads.toArray(new ItemStack[heads.size()]));
    // Rods for tier
    List<ItemStack> rods = new ArrayList<>();
    for (ToolPart part : ToolPartRegistry.getRods())
      rods.add(part.getCraftingStack());
    IngredientSL rodIngredient = IngredientSL.from(rods.toArray(new ItemStack[rods.size()]));

    ResourceLocation recipeName = new ResourceLocation(
        item.getRegistryName().toString() + "_example");
    ItemStack result = new ItemStack(item);
    NBTTagCompound tags = new NBTTagCompound();
    result.setTagCompound(tags);

    if (line3 == null) {
      GameRegistry.addShapedRecipe(recipeName, new ResourceLocation(SilentGems.MODID), result,
          line1, line2, 'g', headIngredient, 's', rodIngredient);
      return SilentGems.registry.recipes.makeShaped(result, line1, line2, 'g', headIngredient, 's',
          rodIngredient);
    } else {
      GameRegistry.addShapedRecipe(recipeName, new ResourceLocation(SilentGems.MODID), result,
          line1, line2, line3, 'g', headIngredient, 's', rodIngredient);
      return SilentGems.registry.recipes.makeShaped(result, line1, line2, line3, 'g',
          headIngredient, 's', rodIngredient);
    }
  }

  public static String getDisplayName(ItemStack tool, String toolClass) {

    ToolPart head = getPartHead(tool);
    if (head == null) {
      return SilentGems.localizationHelper.getLocalizedString("item", toolClass + ".name");
    }

    String material = head.getDisplayName(head.getCraftingStack());
    return SilentGems.localizationHelper.getLocalizedString("tool", toolClass, material);
  }

  // ==========================================================================
  // NBT helper methods
  // ==========================================================================

  private static NBTTagCompound getRootTag(ItemStack tool, String key) {

    if (key != null && !key.isEmpty()) {
      if (!tool.getTagCompound().hasKey(key)) {
        tool.getTagCompound().setTag(key, new NBTTagCompound());
      }
      return tool.getTagCompound().getCompoundTag(key);
    }
    return tool.getTagCompound();
  }

  private static void initRootTag(ItemStack tool) {

    if (!tool.hasTagCompound()) {
      tool.setTagCompound(new NBTTagCompound());
    }
  }

  private static int getTagInt(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound()) {
      return 0;
    }
    return getRootTag(tool, root).getInteger(name);
  }

  private static int getTagInt(ItemStack tool, String root, String name, int defaultValue) {

    if (!tool.hasTagCompound() || !getRootTag(tool, root).hasKey(name)) {
      return defaultValue;
    }
    return getRootTag(tool, root).getInteger(name);
  }

  private static void setTagInt(ItemStack tool, String root, String name, int value) {

    initRootTag(tool);
    getRootTag(tool, root).setInteger(name, value);
  }

  private static float getTagFloat(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound()) {
      return 0.0f;
    }
    return getRootTag(tool, root).getFloat(name);
  }

  private static void setTagFloat(ItemStack tool, String root, String name, float value) {

    initRootTag(tool);
    getRootTag(tool, root).setFloat(name, value);
  }

  private static boolean getTagBoolean(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound()) {
      return false;
    }
    return getRootTag(tool, root).getBoolean(name);
  }

  private static void setTagBoolean(ItemStack tool, String root, String name, boolean value) {

    initRootTag(tool);
    getRootTag(tool, root).setBoolean(name, value);
  }

  private static String getTagString(ItemStack tool, String root, String name) {

    if (!tool.hasTagCompound()) {
      return "";
    }
    return getRootTag(tool, root).getString(name);
  }

  private static void setTagString(ItemStack tool, String root, String name, String value) {

    initRootTag(tool);
    getRootTag(tool, root).setString(name, value);
  }

  private static void setTagPart(ItemStack tool, String name, ToolPart part) {

    initRootTag(tool);
    getRootTag(tool, NBT_ROOT_CONSTRUCTION).setString(name, part.getKey());
  }

  // ---------------------
  // Tool construction NBT
  // ---------------------

  public static String getPartId(ItemStack tool, String key) {

    if (!tool.hasTagCompound()) {
      return null;
    }

    return getRootTag(tool, NBT_ROOT_CONSTRUCTION).getString(key).split("#")[0];
  }

  public static ToolPart getPartHead(ItemStack tool) {

    return ToolPartRegistry.getPart(getPartId(tool, NBT_PART_HEAD));
  }

  public static ToolPart getPartRod(ItemStack tool) {

    return ToolPartRegistry.getPart(getPartId(tool, NBT_PART_ROD));
  }

  public static ToolPart getPartTip(ItemStack tool) {

    return ToolPartRegistry.getPart(getPartId(tool, NBT_PART_HEAD_TIP));
  }

  public static ToolPart getPartDeco(ItemStack tool) {

    return ToolPartRegistry.getPart(getPartId(tool, NBT_PART_ROD_DECO));
  }

  public static void setPartTip(ItemStack tool, ToolPart part) {

    setTagPart(tool, NBT_PART_HEAD_TIP, part);
  }

  // --------------
  // Statistics NBT
  // --------------

  public static String getOriginalOwner(ItemStack tool) {

    return getTagString(tool, NBT_ROOT_STATISTICS, NBT_STATS_ORIGINAL_OWNER);
  }

  public static void setOriginalOwner(ItemStack tool, EntityPlayer player) {

    setOriginalOwner(tool, player.getName());
  }

  public static void setOriginalOwner(ItemStack tool, String name) {

    if (getOriginalOwner(tool).isEmpty()) {
      if (name.equals(Names.SILENT_CHAOS_512))
        name = TextFormatting.RED + name;
      else if (name.equals(Names.M4THG33K))
        name = TextFormatting.GREEN + name;
      else if (name.equals(Names.CHAOTIC_PLAYZ))
        name = TextFormatting.BLUE + name;
      else if (name.equals(GuideBookGems.TOOL_OWNER_NAME))
        name = TextFormatting.AQUA + name;

      setTagString(tool, NBT_ROOT_STATISTICS, NBT_STATS_ORIGINAL_OWNER, name);
    }
  }

  public static int getStatBlocksMined(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_BLOCKS_MINED);
  }

  public static void incrementStatBlocksMined(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_BLOCKS_MINED, getStatBlocksMined(tool) + amount);
  }

  public static int getStatBlocksPlaced(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_BLOCKS_PLACED);
  }

  public static void incrementStatBlocksPlaced(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_BLOCKS_PLACED,
        getStatBlocksPlaced(tool) + amount);
  }

  public static int getStatBlocksTilled(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_BLOCKS_TILLED);
  }

  public static void incrementStatBlocksTilled(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_BLOCKS_TILLED,
        getStatBlocksTilled(tool) + amount);
  }

  public static int getStatPathsMade(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_PATHS_MADE);
  }

  public static void incrementStatPathsMade(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_PATHS_MADE, getStatPathsMade(tool) + amount);
  }

  public static int getStatHitsLanded(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_HITS);
  }

  public static void incrementStatHitsLanded(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_HITS, getStatHitsLanded(tool) + amount);
  }

  public static int getStatKillCount(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_KILL_COUNT);
  }

  public static void incrementStatKillCount(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_KILL_COUNT, getStatKillCount(tool) + amount);
  }

  public static int getStatRedecorated(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_REDECORATED);
  }

  public static void incrementStatRedecorated(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_REDECORATED, getStatRedecorated(tool) + amount);
  }

  public static int getStatShotsFired(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_SHOTS_FIRED);
  }

  public static void incrementStatShotsFired(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_SHOTS_FIRED, getStatShotsFired(tool) + amount);
  }

  public static int getStatShotsLanded(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_SHOTS_LANDED);
  }

  public static void incrementStatShotsLanded(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_SHOTS_FIRED, getStatShotsLanded(tool) + amount);
  }

  public static int getStatThrownCount(ItemStack tool) {

    return getTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_THROWN);
  }

  public static void incrementStatThrownCount(ItemStack tool, int amount) {

    setTagInt(tool, NBT_ROOT_STATISTICS, NBT_STATS_THROWN, getStatThrownCount(tool) + amount);
  }

  // ---------------------
  // Rendering NBT methods
  // ---------------------

  public static int getColorForPass(ItemStack tool, int pass) {

    return getTagInt(tool, ToolRenderHelper.NBT_MODEL_INDEX, "Layer" + pass + "_Color", 0xFFFFFF);
  }

  // public static int getAnimationFrame(ItemStack tool) {
  //
  // return getTagInt(tool, NBT_ANIMATION_FRAME);
  // }
  //
  // public static void setAnimationFrame(ItemStack tool, int value) {
  //
  // setTagInt(tool, NBT_ANIMATION_FRAME, value);
  // }
}
