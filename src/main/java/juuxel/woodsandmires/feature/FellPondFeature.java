package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FellPondFeature extends Feature<FellPondFeatureConfig> {
    private static final List<Direction> BORDER_DIRECTIONS = List.of(Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    private static final int MAX_HEIGHT_TO_DIG = 2;

    public FellPondFeature(Codec<FellPondFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FellPondFeatureConfig> context) {
        FellPondFeatureConfig config = context.config();
        BlockPos.MutableBlockPos origin = context.origin().mutable().move(0, -1, 0);

        // Don't generate in water.
        if (!context.level().getFluidState(origin).isEmpty()) {
            return false;
        }

        var random = context.random();
        int depth = config.depth().sample(random);
        int semiMajor = config.radius().sample(random);
        int semiMinor = config.radius().sample(random);
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        Set<BlockPos> filledPositions = new HashSet<>();
        float theta = random.nextFloat() * Mth.TWO_PI;

        int dugHeight = 0;
        // Check for air layers
        boolean foundAir;
        do {
            foundAir = false;
            float semiMajorSq = semiMajor * semiMajor;
            float semiMinorSq = semiMinor * semiMinor;

            outer: for (int x = -semiMajor; x <= semiMajor; x++) {
                for (int z = -semiMinor; z <= semiMinor; z++) {
                    if (isInsideEllipse(x, z, semiMajorSq, semiMinorSq, theta) && context.level().isEmptyBlock(mut.set(origin).move(x, 0, z))) {
                        foundAir = true;
                        break outer;
                    }
                }
            }

            if (foundAir) {
                for (int x = -semiMajor; x <= semiMajor; x++) {
                    for (int z = -semiMinor; z <= semiMinor; z++) {
                        if (isInsideEllipse(x, z, semiMajorSq, semiMinorSq, theta)) {
                            setBlock(context.level(), mut.set(origin).move(x, 0, z), Blocks.AIR.defaultBlockState());
                        }
                    }
                }

                origin.move(0, -1, 0);
                dugHeight++;

                if (dugHeight >= MAX_HEIGHT_TO_DIG) {
                    break;
                }
            }
        } while (foundAir);

        for (int yo = 0; yo < depth; yo++) {
            filledPositions.clear();
            float semiMajorSq = semiMajor * semiMajor;
            float semiMinorSq = semiMinor * semiMinor;

            for (int x = -semiMajor; x <= semiMajor; x++) {
                for (int z = -semiMinor; z <= semiMinor; z++) {
                    if (isInsideEllipse(x, z, semiMajorSq, semiMinorSq, theta)) {
                        mut.set(origin.getX() + x, origin.getY() - yo, origin.getZ() + z);
                        setBlock(context.level(), mut, config.fillBlock().getState(random, mut));
                        filledPositions.add(new BlockPos(mut));

                        for (Direction d : BORDER_DIRECTIONS) {
                            mut.move(d);

                            if (!filledPositions.contains(mut) && shouldPlaceBorder(context.level(), mut)) {
                                setBlock(context.level(), mut, config.border().getState(random, mut));
                            }

                            mut.move(d.getOpposite());
                        }

                        if (random.nextFloat() < config.bottomReplaceChance()) {
                            mut.move(0, -1, 0);
                            setBlock(context.level(), mut, config.bottomBlock().getState(random, mut));
                        }
                    }
                }
            }

            semiMajor--;
            semiMinor--;
        }

        return true;
    }

    static boolean isInsideEllipse(int x, int y, float semiMajorSq, float semiMinorSq, float theta) {
        float sin = Mth.sin(theta);
        float cos = Mth.cos(theta);
        float sinSq = sin * sin;
        float cosSq = cos * cos;

        // https://en.wikipedia.org/wiki/Ellipse#General_ellipse
        // Ax^2 + Bxy + Cy^2 + Dx + Ey + F = 0
        // Since we're centred at the origin, D and E are 0.
        float a = semiMajorSq * sinSq + semiMinorSq * cosSq;
        float b = 2 * (semiMinorSq - semiMajorSq) * sin * cos;
        float c = semiMajorSq * cosSq + semiMinorSq * sinSq;
        float f = -semiMajorSq * semiMinorSq;

        return (a * x * x) + (b * x * y) + (c * y * y) + f <= 0;
    }

    private static boolean shouldPlaceBorder(WorldGenLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isAir() || !state.isCollisionShapeFullBlock(world, pos);
    }
}
