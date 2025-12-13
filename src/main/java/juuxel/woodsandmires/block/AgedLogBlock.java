package juuxel.woodsandmires.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.core.Direction;

public class AgedLogBlock extends RotatedPillarBlock {
    public static final BooleanProperty MID = BooleanProperty.create("mid");
    public AgedLogBlock(Block main, Properties settings) {
        super(settings.overrideDescription(main.getDescriptionId()));
        registerDefaultState(defaultBlockState().setValue(MID, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MID);
    }
}
