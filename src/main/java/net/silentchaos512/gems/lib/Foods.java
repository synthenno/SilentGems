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

package net.silentchaos512.gems.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.silentchaos512.lib.item.IEnumItems;
import net.silentchaos512.lib.util.PlayerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum Foods implements IEnumItems<Foods, Foods.FoodItem> {
    POTATO_STICK        (8, .8f,   false, 32, Items.STICK),
    SUGAR_COOKIE        (2, .4f,    true, 16, null),
    SECRET_DONUT        (6, .8f,   false, 32, null),
    UNCOOKED_MEATY_STEW (4, .6f,   false, 48, Items.BOWL),
    MEATY_STEW          (12, 1.6f, false, 48, Items.BOWL),
    CANDY_CANE          (2, .2f,    true, 24, null),
    COFFEE_CUP          (1, .2f,    true, 32, null);

    private final boolean alwaysEdible;
    private final int useDuration;
    @Nullable
    private final Item returnedItem;
    private final FoodItem item;

    Foods(int food, float saturation, boolean alwaysEdible, int useDuration, @Nullable Item returnedItem) {
        this.alwaysEdible = alwaysEdible;
        this.useDuration = useDuration;
        this.returnedItem = returnedItem;
        this.item = new FoodItem(this, food, saturation, false);
    }

    @Nonnull
    @Override
    public Foods getEnum() {
        return this;
    }

    @Nonnull
    @Override
    public FoodItem getItem() {
        return this.item;
    }

    public class FoodItem extends ItemFood {
        private final Foods type;

        private FoodItem(Foods type, int amount, float saturation, boolean isWolfFood) {
            super(amount, saturation, isWolfFood);
            this.type = type;
        }

        @Override
        public int getMaxItemUseDuration(ItemStack stack) {
            return type.useDuration;
        }

        @Override
        public EnumAction getItemUseAction(ItemStack stack) {
            return type == COFFEE_CUP ? EnumAction.DRINK : super.getItemUseAction(stack);
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            if (playerIn.canEat(type.alwaysEdible)) {
                playerIn.setActiveHand(handIn);
                return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
            }
            return new ActionResult<>(EnumActionResult.FAIL, heldItem);
        }

        @Override
        protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
            if (worldIn.isRemote) return;

            if (type.returnedItem != null) {
                PlayerHelper.giveItem(player, new ItemStack(type.returnedItem));
            }

            // Potion effects
            switch (type) {
                case CANDY_CANE:
                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0, true, false));
                    break;
                case COFFEE_CUP:
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 1, true, false));
                    player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, 1, true, false));
                    break;
                case POTATO_STICK:
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0, true, false));
                    break;
                case SECRET_DONUT:
                    // TODO
                    break;
                case SUGAR_COOKIE:
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 0, true, false));
                    player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, 0, true, false));
            }
        }
    }
}
