package meow.binary.celestial.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import meow.binary.celestial.init.ItemRegistry;
import meow.binary.celestial.system.starblazers.Starfield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber
public class StarfieldRenderer {

    @SubscribeEvent
    public static void levelRender(RenderLevelStageEvent e) {
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        ItemStack starblazers = EntityUtils.findEquippedCurio(e.getCamera().getEntity(), ItemRegistry.STARBLAZERS.get());
        if (!(starblazers.getItem() instanceof IRelicItem relic)) return;
        if (!relic.isAbilityTicking(starblazers, "constellation_mode")) return;

        Vec3 pos = e.getCamera().getPosition();
        PoseStack poseStack = e.getPoseStack();
        //Vec3[] poses = Starfield.getStarsInRadius(e.getCamera().getEntity().level(), e.getCamera().getEntity().getPosition(e.getPartialTick()), 30);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.DST_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        //RenderSystem.disableCull();

        Vec3[] poses = {new Vec3(127, 75, 409), new Vec3(127, 75, 410), new Vec3(127, 75, 411), new Vec3(127, 75, 412), new Vec3(127, 75, 413)};

        for (int i = 0; i < 25; i++) for (Vec3 vec3 : poses) {
            poseStack.pushPose();
            poseStack.translate(vec3.x - pos.x, vec3.y - pos.y + i, vec3.z - pos.z);

            poseStack.mulPose(Axis.YP.rotationDegrees((e.getRenderTick()+e.getPartialTick())*2f));
            poseStack.mulPose(Axis.ZP.rotationDegrees((e.getRenderTick()+e.getPartialTick())*1.5f));
            poseStack.mulPose(Axis.XP.rotationDegrees((e.getRenderTick()+e.getPartialTick())*2.25f));


            BufferUploader.drawWithShader(RenderUtils3D.renderCube(poseStack, 0.2f+ Mth.sin((e.getRenderTick()+e.getPartialTick())/20f)*0.05f, 0x66FFFFFF));
            poseStack.popPose();
        }

        RenderSystem.disableBlend();
        //RenderSystem.enableCull();
    }
}
