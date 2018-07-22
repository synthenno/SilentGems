package net.silentchaos512.gems.event;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.gems.init.ModEnchantments;
import net.silentchaos512.gems.lib.module.ModuleCoffee;
import net.silentchaos512.lib.util.StackHelper;

public class GemsCommonEvents {

//    @SubscribeEvent
//    public void onLootLoad(LootTableLoadEvent event) {
//        LootHandler.init(event);
//    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {

        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack mainHand = player.getHeldItemMainhand();
            ItemStack offHand = player.getHeldItemOffhand();

            int lifeStealLevel = 0;
            int iceAspectLevel = 0;
            int lightningAspectLevel = 0;

            // Get levels of relevant enchantments.
            if (StackHelper.isValid(mainHand)) {
                lifeStealLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.lifeSteal, mainHand);
                iceAspectLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.iceAspect, mainHand);
                lightningAspectLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.lightningAspect, mainHand);
            }
            // If not, is it on off hand?
            if (lifeStealLevel < 1 && StackHelper.isValid(offHand)) {
                lifeStealLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.lifeSteal, offHand);
            }

            // Do life steal?
            if (lifeStealLevel > 0) {
                float amount = Math.min(event.getAmount(), event.getEntityLiving().getHealth());
                float healAmount = ModEnchantments.lifeSteal.getAmountHealed(lifeStealLevel, amount);
                player.heal(healAmount);
            }

            // Ice Aspect
            if (iceAspectLevel > 0) {
                ModEnchantments.iceAspect.applyTo(event.getEntityLiving(), iceAspectLevel);
            }

            // Lightning Aspect
            if (lightningAspectLevel > 0) {
                ModEnchantments.lightningAspect.applyTo(event.getEntityLiving(), lightningAspectLevel);
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {

        EntityLivingBase entity = event.getEntityLiving();
        if (!entity.world.isRemote) {
            // Rabbit coffee
            if (entity instanceof EntityRabbit) {
                EntityRabbit rabbit = (EntityRabbit) event.getEntityLiving();
                ModuleCoffee.tickRabbit(rabbit);
            }
        }
    }
}
