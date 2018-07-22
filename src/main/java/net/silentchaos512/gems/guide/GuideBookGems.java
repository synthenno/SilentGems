package net.silentchaos512.gems.guide;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.client.gui.config.GuiConfigSilentGems;
import net.silentchaos512.lib.guidebook.GuideBook;
import net.silentchaos512.lib.guidebook.entry.GuideEntry;

import javax.annotation.Nonnull;
import java.util.Random;

public class GuideBookGems extends GuideBook {

    private GuideEntry entryGettingStarted;
    private GuideEntry entryBlocks;
    private GuideEntry entryItems;
    private GuideEntry entryTools;
    private GuideEntry entrySouls;
    private GuideEntry entryEnchantments;
    private GuideEntry entryDebug;

    public GuideBookGems() {
        super(SilentGems.MOD_ID);
        this.resourceGui = new ResourceLocation(SilentGems.MOD_ID, "textures/guide/gui_guide.png");
        this.resourceGadgets = new ResourceLocation(SilentGems.MOD_ID, "textures/guide/gui_guide_gadgets.png");
        edition = SilentGems.BUILD_NUM;
    }

    @Override
    public void initEntries() {
        entryGettingStarted = new GuideEntry(this, "gettingStarted").setImportant();
        entryBlocks = new GuideEntry(this, "blocks");
        entryItems = new GuideEntry(this, "items");
        entryTools = new GuideEntry(this, "tools");
        entrySouls = new GuideEntry(this, "souls");
        entryEnchantments = new GuideEntry(this, "enchantments");
        if (edition == 0)
            entryDebug = new GuideEntry(this, "debug").setSpecial();
    }

    @Override
    public void initChapters() {
    }

    public static final String[] QUOTES = {
            "The flowers probably won't kill you.",
            "Try the donuts!",
            "May contain unintended &cR&6a&ei&an&9b&do&5w&0s!".replaceAll("&", "\u00a7"),
            "Shake well and refrigerate after opening.",
            "Download only from CurseForge!",
            "Rabbit poop coffee!",
            "It stares into your soul.",
            "Your wish has been granted!",
            "Voted most unnecessarily complicated mod in high school.",
            "Also try JEI! Seriously, learn to look up the recipes... How do you play without mods like that?",
            "Scathing comments since 2017!",
            "Muffin button not included.",
            "Now with more enchantments!",
            "Send help.@SilentChaos512",
            "Good lookin' mainframes!"
    };

    @Override
    public String[] getQuotes() {
        return QUOTES;
    }

    @Override
    public @Nonnull
    String selectQuote(Random rand) {
        if (rand.nextInt(100) == 0) {
            return "Lolis are love, lolis are life!";
        }
        return super.selectQuote(rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getConfigScreen(GuiScreen parent) {
        return new GuiConfigSilentGems(parent);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getAchievementScreen(GuiScreen parent) {
        return null;
    }
}
