package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import juuxel.woodsandmires.block.AgedLogBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.List;

public class FallenLogFeature extends Feature<FallenLogFeatureConfig> {
    public FallenLogFeature(Codec<FallenLogFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenLogFeatureConfig> context) {
        BlockPos.MutableBlockPos mut = context.origin().mutable();
        if (!canPlace(context.level(), mut)) {
            return false;
        }

        var config = context.config();
        var random = context.random();
        Direction.Axis axis = Direction.Plane.HORIZONTAL.getRandomAxis(random);
        // We need to correct it so that the "mid" texture will align correctly.
        Direction direction = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        int length = config.length().sample(random);
        int mid = (int) (config.agedHeightFraction().sample(random) * length);
        List<BlockPos.MutableBlockPos> trunkPositions = new ArrayList<>();

        if (random.nextInt(5) == 0) {
            for (int i = 0; i < length; i++) {
                if (!canPlace(context.level(), mut)) {
                    break;
                }

                Block block = i < mid ? config.mainLog() : config.agedLog();
                BlockState state = block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis);

                if (i == mid) {
                    state = state.setValue(AgedLogBlock.MID, true);
                }

                setBlock(context.level(), mut, state);
                trunkPositions.add(mut.mutable());
                mut.move(direction);
            }
        } else {
            Block block = random.nextBoolean() ? config.mainLog() : config.agedLog();
            BlockState state = block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis);

            for (int i = 0; i < length; i++) {
                if (!canPlace(context.level(), mut)) {
                    break;
                }

                setBlock(context.level(), mut, state);
                trunkPositions.add(mut.mutable());
                mut.move(direction);
            }
        }

        for (BlockPos.MutableBlockPos pos : trunkPositions) {
            pos.move(Direction.UP);
            BlockState state = config.topDecoration().getState(random, pos);
            if (!state.isAir() && state.canSurvive(context.level(), pos)) {
                setBlock(context.level(), pos, state);
            }
        }

        return true;
    }

    private static boolean canPlace(LevelSimulatedReader world, BlockPos.MutableBlockPos mut) {
        if (!world.isStateAtPosition(mut, BlockState::isAir)) return false;
        mut.move(0, -1, 0);
        boolean result = isGrassOrDirt(world, mut);
        mut.move(0, 1, 0);
        return result;
    }
}
