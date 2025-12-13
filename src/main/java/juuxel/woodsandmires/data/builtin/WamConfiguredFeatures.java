package juuxel.woodsandmires.data.builtin;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.feature.*;
import juuxel.woodsandmires.tree.*;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.ArrayList;
import java.util.List;

public final class WamConfiguredFeatures {
    private final BootstrapContext<ConfiguredFeature<?, ?>> registerable;
    private final HolderGetter<PlacedFeature> placedFeatures;

    // Individual features
    private Holder<ConfiguredFeature<?, ?>> noPodzolPine;

    private WamConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> registerable) {
        this.registerable = registerable;
        this.placedFeatures = registerable.lookup(Registries.PLACED_FEATURE);
    }

    private void registerGeneral() {
        register(WamConfiguredFeatureKeys.SHORT_PINE_SHRUB, WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_LOG.defaultBlockState(),
                WamBlocks.PINE_LEAVES.defaultBlockState(),
                1, 2, 0.6f
            )
        );
        var thinPineShrub = register(WamConfiguredFeatureKeys.THIN_PINE_SHRUB, WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                WamBlocks.PINE_SHRUB_LOG.defaultBlockState(),
                WamBlocks.PINE_LEAVES.defaultBlockState(),
                1, 2, 0.8f
            )
        );
        var pine = register(WamConfiguredFeatureKeys.PINE, Feature.TREE, pineTree(1, 1));
        var giantPine = register(WamConfiguredFeatureKeys.GIANT_PINE, Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(WamBlocks.PINE_LOG.defaultBlockState()),
                new GiantTrunkPlacer(10, 4, 2),
                BlockStateProvider.simple(WamBlocks.PINE_LEAVES.defaultBlockState()),
                new PineFoliagePlacer(
                    ConstantInt.of(1),
                    ConstantInt.of(1),
                    UniformInt.of(3, 5)
                ),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(
                    List.of(
                        new AgedTrunkTreeDecorator(WamBlocks.AGED_PINE_LOG, UniformFloat.of(0.5f, 0.85f)),
                        new AlterGroundDecorator(
                            new WeightedStateProvider(
                                WeightedList.<BlockState>builder()
                                    .add(Blocks.GRASS_BLOCK.defaultBlockState(), 1)
                                    .add(Blocks.PODZOL.defaultBlockState(), 1)
                            )
                        )
                    )
                )
                .build()
        );
        var pineSnag = register(WamConfiguredFeatureKeys.PINE_SNAG, Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(WamBlocks.PINE_SNAG_LOG.defaultBlockState()),
                new ForkingTrunkPlacer(4, 4, 0),
                BlockStateProvider.simple(Blocks.AIR.defaultBlockState()),
                new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0),
                new TwoLayersFeatureSize(2, 0, 2)
            )
                .ignoreVines()
                .decorators(List.of(new BranchTreeDecorator(WamBlocks.PINE_SNAG_BRANCH, 0.2f)))
                .build()
        );
        register(WamConfiguredFeatureKeys.PLAINS_FLOWERS, Feature.SIMPLE_RANDOM_SELECTOR,
            new SimpleRandomFeatureConfiguration(
                HolderSet.direct(
                    createFlowerPatchFeature(WamBlocks.FIREWEED),
                    createFlowerPatchFeature(WamBlocks.TANSY)
                )
            )
        );
        register(WamConfiguredFeatureKeys.PINE_FROM_SAPLING, Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(WamBlocks.PINE_LOG.defaultBlockState()),
                new StraightTrunkPlacer(6, 4, 0),
                BlockStateProvider.simple(WamBlocks.PINE_LEAVES.defaultBlockState()),
                new PineFoliagePlacer(
                    ConstantInt.of(1),
                    ConstantInt.of(1),
                    UniformInt.of(3, 5)
                ),
                new TwoLayersFeatureSize(2, 0, 2)
            ).ignoreVines().decorators(List.of(createPineTrunkDecorator())).build()
        );
        register(WamConfiguredFeatureKeys.PINE_FOREST_BOULDER, Feature.FOREST_ROCK,
            new BlockStateConfiguration(Blocks.STONE.defaultBlockState())
        );
        register(WamConfiguredFeatureKeys.FOREST_TANSY, Feature.RANDOM_PATCH,
            FeatureUtils.simplePatchConfiguration(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(WamBlocks.TANSY))
            )
        );
        register(WamConfiguredFeatureKeys.HEATHER_PATCH, Feature.RANDOM_PATCH,
            FeatureUtils.simplePatchConfiguration(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(WamBlocks.HEATHER))
            )
        );
        var lessPodzolPine = register(WamConfiguredFeatureKeys.LESS_PODZOL_PINE, Feature.TREE, pineTree(3, 1));
        noPodzolPine = register(WamConfiguredFeatureKeys.NO_PODZOL_PINE, Feature.TREE, pineTree(1, 0));
        register(WamConfiguredFeatureKeys.LUSH_PINE_FOREST_TREES, Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(
                List.of(
                    new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(
                            thinPineShrub,
                            PlacementUtils.filteredByBlockSurvival(WamBlocks.PINE_SAPLING)
                        ),
                        0.12f
                    ),
                    new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.1f),
                    new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.OAK_BEES_0002_LEAF_LITTER), 0.1f),
                    new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_0002_LEAF_LITTER), 0.1f)
                ),
                PlacementUtils.inlinePlaced(lessPodzolPine, PlacementUtils.filteredByBlockSurvival(WamBlocks.PINE_SAPLING))
            )
        );
        register(WamConfiguredFeatureKeys.FALLEN_PINE, WamFeatures.FALLEN_LOG,
            new FallenLogFeatureConfig(
                WamBlocks.PINE_LOG,
                WamBlocks.AGED_PINE_LOG,
                UniformFloat.of(-0.2f, 1.2f),
                UniformInt.of(2, 6),
                new WeightedStateProvider(
                    WeightedList.<BlockState>builder()
                        .add(Blocks.AIR.defaultBlockState(), 3)
                        .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 1)
                        .add(Blocks.RED_MUSHROOM.defaultBlockState(), 1)
                        .build()
                )
            )
        );
        register(WamConfiguredFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES, Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(
                List.of(
                    new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(
                            pine,
                            PlacementUtils.filteredByBlockSurvival(WamBlocks.PINE_SAPLING)
                        ),
                        0.15f
                    ),
                    new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(
                            pineSnag,
                            PlacementUtils.filteredByBlockSurvival(WamBlocks.PINE_SAPLING)
                        ),
                        0.1f
                    )
                ),
                PlacementUtils.inlinePlaced(giantPine, PlacementUtils.filteredByBlockSurvival(WamBlocks.PINE_SAPLING))
            )
        );
        register(WamConfiguredFeatureKeys.SNOWY_PINE_FOREST_TREES, Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(
                List.of(
                    new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.PINE_ON_SNOW), 0.1f)
                ),
                PlacementUtils.inlinePlaced(pine, getTreeOnSnowPlacementModifiers())
            )
        );
    }

    private static TreeDecorator createPineTrunkDecorator() {
        WeightedList.Builder<TreeDecorator> trunkDecorators = WeightedList.builder();
        trunkDecorators.add(
            new ChanceTreeDecorator(
                new AgedTrunkTreeDecorator(WamBlocks.AGED_PINE_LOG, UniformFloat.of(0.3f, 0.65f)),
                0.95
            ),
            14
        );
        trunkDecorators.add(new ReplaceTrunkTreeDecorator(BlockStateProvider.simple(WamBlocks.AGED_PINE_LOG)), 1);
        return new PoolTreeDecorator(trunkDecorators.build());
    }

    private static TreeConfiguration pineTree(int grassWeight, int podzolWeight) {
        List<TreeDecorator> decorators = new ArrayList<>();
        decorators.add(createPineTrunkDecorator());

        if (podzolWeight > 0) {
            decorators.add(
                new AlterGroundDecorator(
                    new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                            .add(Blocks.GRASS_BLOCK.defaultBlockState(), grassWeight)
                            .add(Blocks.PODZOL.defaultBlockState(), podzolWeight)
                    )
                )
            );
        }

        return new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(WamBlocks.PINE_LOG.defaultBlockState()),
            new StraightTrunkPlacer(6, 4, 0),
            BlockStateProvider.simple(WamBlocks.PINE_LEAVES.defaultBlockState()),
            new PineFoliagePlacer(
                ConstantInt.of(1),
                ConstantInt.of(1),
                UniformInt.of(3, 5)
            ),
            new TwoLayersFeatureSize(2, 0, 2)
        )
            .ignoreVines()
            .decorators(List.copyOf(decorators))
            .build();
    }

    private static PlacementModifier[] getTreeOnSnowPlacementModifiers() {
        return new PlacementModifier[] {
            EnvironmentScanPlacement.scanningFor(
                Direction.UP,
                BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)),
                8
            ),
            BlockPredicateFilter.forPredicate(
                BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(),
                    Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW)
            )
        };
    }

    private void registerMires() {
        register(WamConfiguredFeatureKeys.MIRE_PONDS, WamFeatures.MIRE_PONDS);
        register(WamConfiguredFeatureKeys.MIRE_FLOWERS, Feature.FLOWER,
            VegetationFeatures.grassPatch(
                new WeightedStateProvider(
                    WeightedList.<BlockState>builder()
                        .add(Blocks.BLUE_ORCHID.defaultBlockState(), 1)
                        .add(WamBlocks.TANSY.defaultBlockState(), 1)
                ),
                64
            )
        );
        register(WamConfiguredFeatureKeys.MIRE_MEADOW, WamFeatures.MEADOW,
            new MeadowFeatureConfig(
                new WeightedStateProvider(
                    WeightedList.<BlockState>builder()
                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 5)
                        .add(Blocks.FERN.defaultBlockState(), 1)
                ),
                0.5f
            )
        );
    }

    // Fells
    public static final ResourceKey<LootTable> FROZEN_TREASURE_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, WoodsAndMires.id("chests/frozen_treasure"));

    private void registerFells() {
        register(WamConfiguredFeatureKeys.FELL_VEGETATION, WamFeatures.MEADOW,
            new MeadowFeatureConfig(
                BlockStateProvider.simple(Blocks.SHORT_GRASS),
                0.3f
            )
        );
        register(WamConfiguredFeatureKeys.FELL_BOULDER, Feature.FOREST_ROCK,
            new BlockStateConfiguration(Blocks.COBBLESTONE.defaultBlockState())
        );
        register(WamConfiguredFeatureKeys.FELL_POND, WamFeatures.FELL_POND,
            new FellPondFeatureConfig.Builder()
                .radius(UniformInt.of(2, 5))
                .depth(UniformInt.of(1, 4))
                .fillWith(BlockStateProvider.simple(Blocks.WATER))
                .border(BlockStateProvider.simple(Blocks.STONE))
                .bottomBlock(
                    new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                            .add(Blocks.EMERALD_ORE.defaultBlockState(), 1)
                            .add(Blocks.GOLD_ORE.defaultBlockState(), 1)
                    ),
                    0.08f
                )
                .build()
        );
        register(WamConfiguredFeatureKeys.FELL_BIRCH_SHRUB, WamFeatures.SHRUB,
            new ShrubFeatureConfig(
                Blocks.BIRCH_LOG.defaultBlockState(),
                Blocks.BIRCH_LEAVES.defaultBlockState(),
                1, 1, 0.7f
            )
        );
        register(WamConfiguredFeatureKeys.FELL_LICHEN, Feature.RANDOM_PATCH,
            FeatureUtils.simplePatchConfiguration(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(WamBlocks.FELL_LICHEN)),
                List.of(Blocks.STONE)
            )
        );
        var fellMossPatchVegetation = register(WamConfiguredFeatureKeys.FELL_MOSS_PATCH_VEGETATION, Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(
                new WeightedStateProvider(
                    new WeightedList.Builder<BlockState>()
                        .add(Blocks.MOSS_CARPET.defaultBlockState(), 5)
                        .add(WamBlocks.FELL_LICHEN.defaultBlockState(), 2)
                        .add(WamBlocks.HEATHER.defaultBlockState(), 1)
                )
            )
        );
        register(WamConfiguredFeatureKeys.FELL_MOSS_PATCH, Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                BlockTags.MOSS_REPLACEABLE,
                BlockStateProvider.simple(Blocks.MOSS_BLOCK),
                PlacementUtils.inlinePlaced(fellMossPatchVegetation),
                CaveSurface.FLOOR,
                ConstantInt.of(1),
                0f,
                5,
                0.75f,
                UniformInt.of(2, 4),
                0.3f
            )
        );
        register(WamConfiguredFeatureKeys.FROZEN_TREASURE, WamFeatures.FROZEN_TREASURE,
            new FrozenTreasureFeatureConfig(
                new WeightedStateProvider(
                    WeightedList.<BlockState>builder()
                        .add(Blocks.PACKED_ICE.defaultBlockState(), 3)
                        .add(Blocks.BLUE_ICE.defaultBlockState(), 1)
                ),
                UniformInt.of(5, 7),
                ConstantInt.of(2),
                FROZEN_TREASURE_LOOT_TABLE
            )
        );
    }

    private void registerGroves() {
        register(WamConfiguredFeatureKeys.PINY_GROVE_TREES, Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(
                List.of(
                    new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.PINE_ON_SNOW), 0.1f),
                    new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.SPRUCE_ON_SNOW), 0.1f)
                ),
                PlacementUtils.inlinePlaced(noPodzolPine, getTreeOnSnowPlacementModifiers())
            )
        );
    }

    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> registerable) {
        WamConfiguredFeatures configuredFeatures = new WamConfiguredFeatures(registerable);
        configuredFeatures.registerGeneral();
        configuredFeatures.registerMires();
        configuredFeatures.registerFells();
        configuredFeatures.registerGroves();
    }

    private <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        return registerable.register(key, new ConfiguredFeature<>(feature, config));
    }

    private Holder<ConfiguredFeature<?, ?>> register(ResourceKey<ConfiguredFeature<?, ?>> key, Feature<NoneFeatureConfiguration> feature) {
        return register(key, feature, FeatureConfiguration.NONE);
    }

    private static Holder<PlacedFeature> createFlowerPatchFeature(Block block) {
        return PlacementUtils.onlyWhenEmpty(Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(BlockStateProvider.simple(block), 64));
    }
}
