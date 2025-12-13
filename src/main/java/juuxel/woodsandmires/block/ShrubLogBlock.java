package juuxel.woodsandmires.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;

public class ShrubLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty HAS_LEAVES = BooleanProperty.create("has_leaves");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape X_SHAPE = box(0, 2, 2, 16, 14, 14);
    private static final VoxelShape Y_SHAPE = box(2, 0, 2, 14, 16, 14);
    private static final VoxelShape Z_SHAPE = box(2, 2, 0, 14, 14, 16);
    private final Block leaves;

    public ShrubLogBlock(Properties settings, Block leaves) {
        super(settings);
        this.leaves = leaves;
        registerDefaultState(defaultBlockState().setValue(HAS_LEAVES, false).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        // noinspection ConstantConditions
        return super.getStateForPlacement(ctx)
            .setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }

        return super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HAS_LEAVES)) {
            return Shapes.block();
        }

        switch (state.getValue(AXIS)) {
            case X:
                return X_SHAPE;
            case Y:
                return Y_SHAPE;
            case Z:
            default:
                return Z_SHAPE;
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        var stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!state.getValue(HAS_LEAVES) && stack.is(leaves.asItem())) {
            if (!world.isClientSide()) {
                world.setBlockAndUpdate(pos, state.setValue(HAS_LEAVES, true));
                var soundGroup = leaves.defaultBlockState().getSoundType();

                world.playSound(null, pos, soundGroup.getPlaceSound(), SoundSource.BLOCKS, (soundGroup.getVolume() + 1f) / 2f, soundGroup.getPitch() * 0.8f);
                return InteractionResult.CONSUME;
            }

            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HAS_LEAVES, WATERLOGGED);
    }
}
