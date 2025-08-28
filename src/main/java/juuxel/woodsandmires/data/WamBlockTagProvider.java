package juuxel.woodsandmires.data;

import juuxel.woodsandmires.block.WamBlockTags;
import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public final class WamBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public WamBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // Minecraft tags
        valueLookupBuilder(BlockTags.AXE_MINEABLE)
            .add(WamBlocks.PINE_SNAG_BRANCH);
        valueLookupBuilder(BlockTags.CEILING_HANGING_SIGNS)
            .add(WamBlocks.PINE_HANGING_SIGN);
        valueLookupBuilder(BlockTags.FENCE_GATES)
            .add(WamBlocks.PINE_FENCE_GATE);
        valueLookupBuilder(BlockTags.FLOWER_POTS)
            .add(WamBlocks.POTTED_PINE_SAPLING)
            .add(WamBlocks.POTTED_TANSY)
            .add(WamBlocks.POTTED_FELL_LICHEN)
            .add(WamBlocks.POTTED_HEATHER);
        valueLookupBuilder(BlockTags.LEAVES)
            .add(WamBlocks.PINE_LEAVES);
        valueLookupBuilder(BlockTags.LOGS_THAT_BURN)
            .addTag(WamBlockTags.PINE_LOGS);
        valueLookupBuilder(BlockTags.MUSHROOM_GROW_BLOCK)
            .addTag(WamBlockTags.THICK_PINE_LOGS);
        valueLookupBuilder(BlockTags.PLANKS)
            .add(WamBlocks.PINE_PLANKS);
        valueLookupBuilder(BlockTags.SAPLINGS)
            .add(WamBlocks.PINE_SAPLING);
        valueLookupBuilder(BlockTags.SMALL_FLOWERS)
            .add(WamBlocks.HEATHER, WamBlocks.TANSY);
        valueLookupBuilder(BlockTags.STANDING_SIGNS)
            .add(WamBlocks.PINE_SIGN);
        valueLookupBuilder(BlockTags.BEE_ATTRACTIVE)
            .add(WamBlocks.FIREWEED);
        valueLookupBuilder(BlockTags.WALL_HANGING_SIGNS)
            .add(WamBlocks.PINE_WALL_HANGING_SIGN);
        valueLookupBuilder(BlockTags.WALL_SIGNS)
            .add(WamBlocks.PINE_WALL_SIGN);
        valueLookupBuilder(BlockTags.WOODEN_BUTTONS)
            .add(WamBlocks.PINE_BUTTON);
        valueLookupBuilder(BlockTags.WOODEN_DOORS)
            .add(WamBlocks.PINE_DOOR);
        valueLookupBuilder(BlockTags.WOODEN_FENCES)
            .add(WamBlocks.PINE_FENCE);
        valueLookupBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
            .add(WamBlocks.PINE_PRESSURE_PLATE);
        valueLookupBuilder(BlockTags.WOODEN_SLABS)
            .add(WamBlocks.PINE_SLAB);
        valueLookupBuilder(BlockTags.WOODEN_STAIRS)
            .add(WamBlocks.PINE_STAIRS);
        valueLookupBuilder(BlockTags.WOODEN_TRAPDOORS)
            .add(WamBlocks.PINE_TRAPDOOR);

        // WaM tags
        valueLookupBuilder(WamBlockTags.LICHEN_PLANTABLE_ON)
            .forceAddTag(BlockTags.DIRT)
            .add(Blocks.FARMLAND)
            .forceAddTag(BlockTags.BASE_STONE_OVERWORLD);
        valueLookupBuilder(WamBlockTags.PINE_LOGS)
            .addTag(WamBlockTags.THICK_PINE_LOGS)
            .add(WamBlocks.PINE_SHRUB_LOG);
        valueLookupBuilder(WamBlockTags.THICK_PINE_LOGS)
            .add(WamBlocks.PINE_LOG, WamBlocks.AGED_PINE_LOG)
            .add(WamBlocks.PINE_WOOD, WamBlocks.AGED_PINE_WOOD)
            .add(WamBlocks.STRIPPED_PINE_LOG, WamBlocks.STRIPPED_PINE_WOOD)
            .add(WamBlocks.PINE_SNAG_LOG, WamBlocks.PINE_SNAG_WOOD);
    }
}
