package juuxel.woodsandmires.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public final class PoolTreeDecorator extends TreeDecorator {
    public static final MapCodec<PoolTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(builder ->
        builder.group(
            Pool.createCodec(TreeDecorator.TYPE_CODEC).fieldOf("decorators")
                .forGetter(PoolTreeDecorator::getDecorators)
        ).apply(builder, PoolTreeDecorator::new)
    );
    private final Pool<TreeDecorator> decorators;

    public PoolTreeDecorator(Pool<TreeDecorator> decorators) {
        if (decorators.isEmpty()) {
            throw new IllegalArgumentException("Cannot create PoolTreeDecorator with an empty pool!");
        }

        this.decorators = decorators;
    }

    public Pool<TreeDecorator> getDecorators() {
        return decorators;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WamTreeDecorators.POOL;
    }

    @Override
    public void generate(Generator generator) {
        decorators.getOrEmpty(generator.getRandom())
            .orElseThrow(IllegalStateException::new)
            .generate(generator);
    }
}
