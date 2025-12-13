package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MirePondsFeature extends Feature<NoneFeatureConfiguration> {
    private static final Direction[] NON_UP_DIRECTIONS = new Direction[] {
        Direction.NORTH,
        Direction.EAST,
        Direction.SOUTH,
        Direction.WEST,
        Direction.DOWN
    };

    public MirePondsFeature(Codec<NoneFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var world = context.level();
        var pos = context.origin();
        var random = context.random();

        BlockState water = Blocks.WATER.defaultBlockState();
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        boolean generated = false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // Leave holes in the ponds
                if (random.nextBoolean()) continue;

                int xo = pos.getX() + x;
                int zo = pos.getZ() + z;
                int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, xo, zo) - 1;
                mut.set(xo, y, zo);

                if (isGrassOrDirt(world, mut) && isSolidOrWaterAround(world, mut)) {
                    setBlock(world, mut, water);
                    generated = true;

                    if (random.nextInt(3) == 0) {
                        mut.move(Direction.DOWN);

                        if (world.getBlockState(mut).isRedstoneConductor(world, mut) & isSolidOrWaterAround(world, mut)) {
                            setBlock(world, mut, water);
                        }
                    }
                }
            }
        }

        return generated;
    }

    private static boolean isSolidOrWaterAround(WorldGenLevel world, BlockPos.MutableBlockPos pos) {
        for (Direction direction : NON_UP_DIRECTIONS) {
            pos.move(direction);
            BlockState state = world.getBlockState(pos);
            pos.move(direction.getOpposite());

            if (!state.is(Blocks.WATER) && !state.isRedstoneConductor(world, pos)) {
                return false;
            }
        }

        return true;
    }
}
