package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MeadowFeature extends Feature<MeadowFeatureConfig> {
    public MeadowFeature(Codec<MeadowFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MeadowFeatureConfig> context) {
        var world = context.level();
        var pos = context.origin();
        var config = context.config();
        var random = context.random();

        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        boolean generated = false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (random.nextFloat() > config.chance) continue;

                int xo = pos.getX() + x;
                int zo = pos.getZ() + z;
                int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, xo, zo);
                mut.set(xo, y, zo);

                if (!config.allowedPlacement.test(world, mut)) continue;

                BlockState vegetation = config.stateProvider.getState(random, mut);
                if (world.isEmptyBlock(mut) && vegetation.canSurvive(world, mut)) {
                    setBlock(world, mut, vegetation);
                    generated = true;
                }
            }
        }

        return generated;
    }
}
