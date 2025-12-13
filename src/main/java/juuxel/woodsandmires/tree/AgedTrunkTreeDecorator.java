package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import juuxel.woodsandmires.block.AgedLogBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public final class AgedTrunkTreeDecorator extends TreeDecorator {
    public static final MapCodec<AgedTrunkTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("log").forGetter(AgedTrunkTreeDecorator::getLog),
            FloatProvider.codec(0, 1).fieldOf("aged_height_fraction")
                .forGetter(AgedTrunkTreeDecorator::getAgedHeightFraction)
        ).apply(instance, AgedTrunkTreeDecorator::new)
    );
    private final Block log;
    private final FloatProvider agedHeightFraction;

    public AgedTrunkTreeDecorator(Block log, FloatProvider agedHeightFraction) {
        this.log = log;
        this.agedHeightFraction = agedHeightFraction;
    }

    public Block getLog() {
        return log;
    }

    public FloatProvider getAgedHeightFraction() {
        return agedHeightFraction;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return WamTreeDecorators.AGED_TRUNK;
    }

    @Override
    public void place(Context generator) {
        IntSortedSet heights = new IntRBTreeSet();
        for (BlockPos pos : generator.logs()) {
            heights.add(pos.getY());
        }
        float heightPoint = agedHeightFraction.sample(generator.random());
        int midY = (int) Mth.lerpInt(heightPoint, heights.firstInt(), heights.lastInt());

        for (BlockPos pos : generator.logs()) {
            if (pos.getY() > midY) {
                break;
            } else if (generator.level().isStateAtPosition(pos, Feature::isDirt)) {
                // Don't replace the dirt underneath the trunk
                continue;
            }

            BlockState state = log.defaultBlockState().setValue(AgedLogBlock.MID, pos.getY() == midY);
            generator.setBlock(pos, state);
        }
    }
}
