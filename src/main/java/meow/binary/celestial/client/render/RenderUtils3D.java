package meow.binary.celestial.client.render;

import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix4f;

public class RenderUtils3D {
    public static final int[][] cube_vertices = {
            {-1, -1, -1}, {-1, -1,  1}, {-1,  1,  1},
            { 1,  1, -1}, {-1, -1, -1}, {-1,  1, -1},
            { 1, -1,  1}, {-1, -1, -1}, { 1, -1, -1},
            { 1,  1, -1}, { 1, -1, -1}, {-1, -1, -1},
            {-1, -1, -1}, {-1,  1,  1}, {-1,  1, -1},
            { 1, -1,  1}, {-1, -1,  1}, {-1, -1, -1},
            {-1,  1,  1}, {-1, -1,  1}, { 1, -1,  1},
            { 1,  1,  1}, { 1, -1, -1}, { 1,  1, -1},
            { 1, -1, -1}, { 1,  1,  1}, { 1, -1,  1},
            { 1,  1,  1}, { 1,  1, -1}, {-1,  1, -1},
            { 1,  1,  1}, {-1,  1, -1}, {-1,  1,  1},
            { 1,  1,  1}, {-1,  1,  1}, { 1, -1,  1}
    };

    public static BufferBuilder.RenderedBuffer renderCube(PoseStack poseStack, float size, int color) {
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        Matrix4f matrix = poseStack.last().pose();
        builder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        for (int[] pos : cube_vertices) builder.vertex(matrix, pos[0]*size/2f, pos[1]*size/2f, pos[2]*size/2f).color(color).endVertex();

        return builder.end();
    }
}
