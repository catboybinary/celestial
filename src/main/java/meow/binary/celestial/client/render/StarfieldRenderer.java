package meow.binary.celestial.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import meow.binary.celestial.init.ItemRegistry;
import meow.binary.celestial.system.starblazers.Starfield;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber
public class StarfieldRenderer {

    @SubscribeEvent
    public static void levelRender(RenderLevelStageEvent e) {
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) return;
        ItemStack starblazers = EntityUtils.findEquippedCurio(e.getCamera().getEntity(), ItemRegistry.STARBLAZERS.get());
        if (!(starblazers.getItem() instanceof IRelicItem relic)) return;
        if (!relic.isAbilityTicking(starblazers, "constellation_mode")) return;

        Vec3 pos = e.getCamera().getPosition();
        PoseStack poseStack = e.getPoseStack();
        Vec3[] poses = Starfield.getStarsInRadius(e.getCamera().getEntity().level(), e.getCamera().getEntity().getPosition(e.getPartialTick()), 30);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableCull();

        for (Vec3 vec3 : poses) {
            poseStack.pushPose();
            poseStack.translate(vec3.x - pos.x, vec3.y - pos.y, vec3.z - pos.z);
            Matrix4f matrix = poseStack.last().pose();


            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();

            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            bufferBuilder.vertex(matrix, 0, 0, 0).color(255, 255, 255, 255).endVertex();
            bufferBuilder.vertex(matrix, 0, 1, 0).color(0, 255, 255, 255).endVertex();
            bufferBuilder.vertex(matrix, 1, 1, 0).color(255, 0, 255, 255).endVertex();
            bufferBuilder.vertex(matrix, 1, 0, 0).color(255, 255, 0, 255).endVertex();
            tesselator.end();
            poseStack.popPose();
        }

        RenderSystem.enableCull();
    }
}
