package meow.binary.celestial.client.render;

import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StarfieldRenderer {

    @SubscribeEvent
    public static void levelRender(RenderLevelStageEvent e) {
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) return;

        Vec3 pos = e.getCamera().getPosition();
        PoseStack poseStack = e.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(-pos.x, -pos.y, -pos.z);
        poseStack.translate(127, 73, 413);

        BlockRenderDispatcher block = Minecraft.getInstance().getBlockRenderer();
        block.renderSingleBlock(
                Blocks.DIAMOND_BLOCK.defaultBlockState(),
                poseStack,
                Minecraft.getInstance().renderBuffers().bufferSource(),
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                ModelData.builder().build(),
                RenderType.solid());
        poseStack.popPose();
        RenderSystem.setShader();
    }
}
