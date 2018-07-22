package net.silentchaos512.gems.event;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.gems.client.render.particle.ParticleRenderDispatcher;

public class GemsClientEvents {

//  public static String debugTextOverlay = "";

//  LocalizationHelper loc = SilentGems.instance.localizationHelper;
//  int fovModifier = 0;

  @SubscribeEvent
  public void onRenderGameOverlay(RenderGameOverlayEvent event) {
//    if (SilentGems.instance.isDevBuild() && GemsConfig.DEBUG_MODE
//        && event.getType() == ElementType.TEXT) {
//      GlStateManager.pushMatrix();
//      float scale = 1.0f;
//      GlStateManager.scale(scale, scale, 1.0f);
//      int y = 5;
//      for (String line : debugTextOverlay.split("\\n")) {
//        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(line, 5, y, 0xFFFFFF);
//        y += 10;
//      }
//      GlStateManager.popMatrix();
//    }
//
//    ModItems.teleporterLinker.renderGameOverlay(event);
//    if (event.getType() == ElementType.TEXT)
//      ItemChaosGem.Gui.renderGameOverlay(Minecraft.getMinecraft());
//    renderCrosshairs(event);
//    renderArmorExtra(event);
//    renderAmmoCount(event);
//    GuiToolSouls.renderAPBars(event);
//    GuiToolSouls.renderXPBar(event);
  }

  @SubscribeEvent
  public void onRenderWorldLast(RenderWorldLastEvent event) {

    ParticleRenderDispatcher.dispatch();
  }

  @SubscribeEvent
  public void stitchTexture(TextureStitchEvent.Pre pre) {
//    SilentGems.instance.log.info("Stitching misc textures into the map - M4thG33k");
//    pre.getMap().registerSprite(new ResourceLocation("silentgems", "blocks/ChaosPylonPassive"));
//    pre.getMap().registerSprite(new ResourceLocation("silentgems", "blocks/ChaosPylonBurner"));
//    pre.getMap().registerSprite(new ResourceLocation("silentgems", "blocks/ChaosAltar"));
  }
}
