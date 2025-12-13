package juuxel.woodsandmires.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public final class PoolTreeDecorator extends TreeDecorator {
    public static final MapCodec<PoolTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(builder ->
        builder.group(
            WeightedList.codec(TreeDecorator.CODEC).fieldOf("decorators")
                .forGetter(PoolTreeDecorator::getDecorators)
        ).apply(builder, PoolTreeDecorator::new)
    );
    private final WeightedList<TreeDecorator> decorators;

    public PoolTreeDecorator(WeightedList<TreeDecorator> decorators) {
        if (decorators.isEmpty()) {
            throw new IllegalArgumentException("Cannot create PoolTreeDecorator with an empty pool!");
        }

        this.decorators = decorators;
    }

    public WeightedList<TreeDecorator> getDecorators() {
        return decorators;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return WamTreeDecorators.POOL;
    }

    @Override
    public void place(Context generator) {
        decorators.getRandom(generator.random())
            .orElseThrow(IllegalStateException::new)
            .place(generator);
    }
}
