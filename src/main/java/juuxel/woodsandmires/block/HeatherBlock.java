package juuxel.woodsandmires.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

public class HeatherBlock extends FlowerBlock {
    private static final VoxelShape SHAPE = box(1, 0, 1, 15, 11, 15);

    public HeatherBlock(Holder<MobEffect> suspiciousStewEffect, int effectDuration, Properties settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 modelOffset = state.getOffset(pos);
        return SHAPE.move(modelOffset.x, modelOffset.y, modelOffset.z);
    }
}
