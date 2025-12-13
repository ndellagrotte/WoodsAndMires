package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public final class ChanceTreeDecorator extends TreeDecorator {
    public static final MapCodec<ChanceTreeDecorator> CODEC =
        RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                TreeDecorator.CODEC.fieldOf("parent").forGetter(ChanceTreeDecorator::getParent),
                Codec.doubleRange(0, 1).fieldOf("chance").forGetter(ChanceTreeDecorator::getChance)
            ).apply(builder, ChanceTreeDecorator::new)
        );

    private final TreeDecorator parent;
    private final double chance;

    public ChanceTreeDecorator(TreeDecorator parent, double chance) {
        this.parent = parent;
        this.chance = chance;
    }

    public TreeDecorator getParent() {
        return parent;
    }

    public double getChance() {
        return chance;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return WamTreeDecorators.CHANCE;
    }

    @Override
    public void place(Context generator) {
        if (generator.random().nextDouble() <= chance) {
            parent.place(generator);
        }
    }
}
