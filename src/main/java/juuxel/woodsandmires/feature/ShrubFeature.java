package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import juuxel.woodsandmires.block.ShrubLogBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ShrubFeature extends Feature<ShrubFeatureConfig> {
    public ShrubFeature(Codec<ShrubFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ShrubFeatureConfig> context) {
        var world = context.level();
        var pos = context.origin();
        var config = context.config();
        var random = context.random();

        BlockPos below = pos.below();
        if (!isGrassOrDirt(world, below) || !world.getBlockState(below).isFaceSturdy(world, below, Direction.UP)) {
            return false;
        }

        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        mut.set(pos);

        BlockState log = config.log.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.Y);
        BlockState logWithLeaves = log.getBlock() instanceof ShrubLogBlock ? log.setValue(ShrubLogBlock.HAS_LEAVES, true) : log;
        BlockState leaves = config.leaves.setValue(LeavesBlock.DISTANCE, 1);
        int extraHeight = random.nextFloat() < config.extraHeightChance ? random.nextInt(config.extraHeight + 1) : 0;
        int height = config.baseHeight + extraHeight;

        for (int y = 1; y <= height; y++) {
            if (y > 1 || height == 1) {
                setBlock(world, mut, logWithLeaves);

                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    mut.move(direction);
                    setBlock(world, mut, leaves);
                    mut.move(direction.getOpposite());
                }
            } else {
                setBlock(world, mut, log);
            }

            mut.move(Direction.UP);
        }

        setBlock(world, mut, leaves);
        return false;
    }
}
