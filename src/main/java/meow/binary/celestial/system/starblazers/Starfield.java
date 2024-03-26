package meow.binary.celestial.system.starblazers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashSet;
import java.util.Set;

public class Starfield {
    public static Vec3[] getStarsInRadius(Level level, Vec3 pos, int radius) {
        RandomSource random = level.random.fork();
        random.setSeed(1488);
        PerlinNoise perlin = PerlinNoise.create(random, 2,4,3);
        Set<BlockPos> sphereBlocks = new HashSet<>();

        double cx = pos.x();
        double cy = pos.y();
        double cz = pos.z();

        for (double x = cx - radius; x <= cx + radius; x++) {
            for (double y = cy - radius; y <= cy + radius; y++) {
                for (double z = cz - radius; z <= cz + radius; z++) {
                    double distance = Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy) + (z - cz) * (z - cz));
                    if (distance <= radius) {
                        sphereBlocks.add(new BlockPos((int) x, (int) y, (int) z));
                    }
                }
            }
        }

        Vec3[] array = new Vec3[]{};
        for (BlockPos bp : sphereBlocks) {
            //System.out.println(perlin.getValue(bp.getX(), bp.getY(), bp.getZ()));
            if (perlin.getValue(bp.getX(), bp.getY(), bp.getZ()) > 2.5d) {
                array = ArrayUtils.add(array, new Vec3(bp.getX(), bp.getY(), bp.getZ()));
            }
        }
        return array;
    }
}
