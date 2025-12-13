package juuxel.woodsandmires.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import juuxel.woodsandmires.block.BranchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public final class BranchTreeDecorator extends TreeDecorator {
    public static final MapCodec<BranchTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(BranchTreeDecorator::getBlock),
            Codec.FLOAT.fieldOf("chance").forGetter(BranchTreeDecorator::getChance)
        ).apply(instance, BranchTreeDecorator::new)
    );

    private final Block block;
    private final float chance;

    public BranchTreeDecorator(Block block, float chance) {
        this.block = block;
        this.chance = chance;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return WamTreeDecorators.BRANCH;
    }

    public Block getBlock() {
        return block;
    }

    public float getChance() {
        return chance;
    }

    @Override
    public void place(Context generator) {
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        RandomSource random = generator.random();

        for (BlockPos pos : generator.logs()) {
            // Don't replace the dirt underneath the trunk
            if (generator.level().isStateAtPosition(pos, Feature::isDirt)) continue;

            mut.set(pos);

            for (Direction side : Direction.Plane.HORIZONTAL) {
                if (random.nextFloat() < chance) {
                    BlockState state = block.defaultBlockState()
                        .setValue(BranchBlock.AXIS, side.getAxis())
                        .setValue(BranchBlock.STYLE, random.nextBoolean() ? BranchBlock.Style.THICK : BranchBlock.Style.THIN);

                    mut.move(side);
                    if (generator.isAir(mut)) {
                        generator.setBlock(mut, state);
                    }
                    mut.move(side.getOpposite());
                }
            }
        }
    }
}
