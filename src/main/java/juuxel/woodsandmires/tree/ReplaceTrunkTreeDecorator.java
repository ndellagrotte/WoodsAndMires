package juuxel.woodsandmires.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public final class ReplaceTrunkTreeDecorator extends TreeDecorator {
    public static final MapCodec<ReplaceTrunkTreeDecorator> CODEC =
        RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                BlockStateProvider.CODEC.fieldOf("trunk").forGetter(ReplaceTrunkTreeDecorator::getTrunk)
            ).apply(builder, ReplaceTrunkTreeDecorator::new)
        );
    private final BlockStateProvider trunk;

    public ReplaceTrunkTreeDecorator(BlockStateProvider trunk) {
        this.trunk = trunk;
    }

    public BlockStateProvider getTrunk() {
        return trunk;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return WamTreeDecorators.REPLACE_TRUNK;
    }

    @Override
    public void place(Context generator) {
        for (BlockPos pos : generator.logs()) {
            // Don't replace the dirt underneath the trunk
            if (generator.level().isStateAtPosition(pos, Feature::isDirt)) continue;

            generator.setBlock(pos, trunk.getState(generator.random(), pos));
        }
    }
}
