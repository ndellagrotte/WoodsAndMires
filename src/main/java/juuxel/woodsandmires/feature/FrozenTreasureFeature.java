package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public final class FrozenTreasureFeature extends Feature<FrozenTreasureFeatureConfig> {
    public FrozenTreasureFeature(Codec<FrozenTreasureFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FrozenTreasureFeatureConfig> context) {
        FrozenTreasureFeatureConfig config = context.config();
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        var random = context.random();
        int height = config.height().sample(random);
        int semiMajor = config.radius().sample(random);
        int semiMinor = config.radius().sample(random);
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        int chestY = height / 3;

        for (int x = -semiMajor; x <= semiMajor; x++) {
            for (int z = -semiMinor; z <= semiMinor; z++) {
                mut.set(origin.getX() + x, origin.getY() - 1, origin.getZ() + z);
                if (!hasSolidTop(world, mut) || !world.isFluidAtPosition(mut, FluidState::isEmpty)) {
                    return false;
                }
            }
        }

        for (int yo = -1; yo < height; yo++) {
            float width = getWidthAdjustmentFromProgress(Mth.inverseLerp(yo, -1f, height - 1f));
            int sma = Math.round(semiMajor * width);
            int smi = Math.round(semiMinor * width);
            float semiMajorSq = sma * sma;
            float semiMinorSq = smi * smi;

            for (int x = -sma; x <= sma; x++) {
                for (int z = -smi; z <= smi; z++) {
                    mut.set(origin.getX() + x, origin.getY() + yo, origin.getZ() + z);
                    if (x == 0 && z == 0 && yo == chestY) {
                        BlockState chest = Blocks.CHEST.defaultBlockState()
                            .setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        setBlock(world, mut, chest);
                        RandomizableContainer.setBlockEntityLootTable(world, random, mut, config.lootTable());
                    } else if (FellPondFeature.isInsideEllipse(x, z, semiMajorSq, semiMinorSq, 0)) {
                        setBlock(world, mut, config.ice().getState(random, mut));
                    }
                }
            }
        }

        return true;
    }

    private static float getWidthAdjustmentFromProgress(float progress) {
        float coefficient = progress < 0.6f ? 0.6666f : -2.5f;
        return coefficient * (progress - 0.6f) + 1f;
    }

    private static boolean hasSolidTop(WorldGenLevel world, BlockPos pos) {
        return world.isStateAtPosition(pos, state -> {
            VoxelShape shape = state.getCollisionShape(world, pos);
            return Block.isFaceFull(shape, Direction.UP);
        });
    }
}
