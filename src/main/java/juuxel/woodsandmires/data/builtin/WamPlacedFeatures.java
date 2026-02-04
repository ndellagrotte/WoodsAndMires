package juuxel.woodsandmires.data.builtin;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;

import java.util.List;

public final class WamPlacedFeatures {
    private static List<PlacementModifier> cons(PlacementModifier head, List<PlacementModifier> tail) {
        return ImmutableList.<PlacementModifier>builder().add(head).addAll(tail).build();
    }

    private static List<PlacementModifier> treeModifiers(PlacementModifier countModifier) {
        return VegetationPlacements.treePlacement(countModifier);
    }

    private static List<PlacementModifier> countExtraTreeModifiers(int count, float extraChance, int extraCount) {
        return treeModifiers(PlacementUtils.countExtra(count, extraChance, extraCount));
    }

    private static List<PlacementModifier> treeModifiersWithWouldSurvive(PlacementModifier countModifier, Block block) {
        return VegetationPlacements.treePlacement(countModifier, block);
    }

    private static List<PlacementModifier> countModifiers(int count) {
        return VegetationPlacements.worldSurfaceSquaredWithCount(count);
    }

    private static List<PlacementModifier> chanceModifiers(int chance) {
        return List.of(RarityFilter.onAverageOnceEvery(chance), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    private static void registerPineForests(BootstrapContext<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.FOREST_PINE, WamConfiguredFeatureKeys.PINE, treeModifiersWithWouldSurvive(CountPlacement.of(10), WamBlocks.PINE_SAPLING));
        register(registerable, WamPlacedFeatureKeys.SNOWY_PINE_FOREST_TREES, WamConfiguredFeatureKeys.SNOWY_PINE_FOREST_TREES, countExtraTreeModifiers(8, 0.1f, 1));
        register(registerable, WamPlacedFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES, WamConfiguredFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES, countExtraTreeModifiers(10, 0.1f, 1));
        register(registerable, WamPlacedFeatureKeys.PINE_FOREST_BOULDER, WamConfiguredFeatureKeys.PINE_FOREST_BOULDER, chanceModifiers(16));
        register(registerable, WamPlacedFeatureKeys.PINE_FOREST_HEATHER_PATCH, WamConfiguredFeatureKeys.HEATHER_PATCH, chanceModifiers(12));
        register(registerable, WamPlacedFeatureKeys.LUSH_PINE_FOREST_TREES, WamConfiguredFeatureKeys.LUSH_PINE_FOREST_TREES, countExtraTreeModifiers(8, 0.1f, 1));
        register(registerable, WamPlacedFeatureKeys.LUSH_PINE_FOREST_FLOWERS, WamConfiguredFeatureKeys.PLAINS_FLOWERS, chanceModifiers(2));
        register(registerable, WamPlacedFeatureKeys.FALLEN_PINE, WamConfiguredFeatureKeys.FALLEN_PINE, chanceModifiers(7));
    }

    private static void registerMires(BootstrapContext<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.MIRE_PONDS, WamConfiguredFeatureKeys.MIRE_PONDS, List.of(BiomeFilter.biome()));
        register(registerable, WamPlacedFeatureKeys.MIRE_FLOWERS, WamConfiguredFeatureKeys.MIRE_FLOWERS, chanceModifiers(2));
        register(registerable, WamPlacedFeatureKeys.MIRE_MEADOW, WamConfiguredFeatureKeys.MIRE_MEADOW, List.of(BiomeFilter.biome()));
        register(registerable, WamPlacedFeatureKeys.MIRE_PINE_SNAG, WamConfiguredFeatureKeys.PINE_SNAG, treeModifiersWithWouldSurvive(RarityFilter.onAverageOnceEvery(6), WamBlocks.PINE_SAPLING));
        register(registerable, WamPlacedFeatureKeys.MIRE_PINE_SHRUB, WamConfiguredFeatureKeys.SHORT_PINE_SHRUB,
            treeModifiersWithWouldSurvive(
                PlacementUtils.countExtra(3, 1 / 3f, 3),
                WamBlocks.PINE_SAPLING
            )
        );
    }

    private static void registerFells(BootstrapContext<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.FELL_VEGETATION, WamConfiguredFeatureKeys.FELL_VEGETATION, List.of(BiomeFilter.biome()));
        register(registerable, WamPlacedFeatureKeys.FELL_BOULDER, WamConfiguredFeatureKeys.FELL_BOULDER, chanceModifiers(16));
        register(registerable, WamPlacedFeatureKeys.FELL_POND, WamConfiguredFeatureKeys.FELL_POND, chanceModifiers(7));
        register(registerable, WamPlacedFeatureKeys.FELL_BIRCH_SHRUB, WamConfiguredFeatureKeys.FELL_BIRCH_SHRUB,
            cons(
                RarityFilter.onAverageOnceEvery(3),
                treeModifiersWithWouldSurvive(
                    PlacementUtils.countExtra(1, 1/3f, 2),
                    Blocks.BIRCH_SAPLING
                )
            )
        );
        register(registerable, WamPlacedFeatureKeys.FELL_LICHEN, WamConfiguredFeatureKeys.FELL_LICHEN, chanceModifiers(2));
        register(registerable, WamPlacedFeatureKeys.FELL_MOSS_PATCH, WamConfiguredFeatureKeys.FELL_MOSS_PATCH, chanceModifiers(5));
        register(registerable, WamPlacedFeatureKeys.FROZEN_TREASURE, WamConfiguredFeatureKeys.FROZEN_TREASURE, countModifiers(2));
        register(registerable, WamPlacedFeatureKeys.FELL_HEATHER_PATCH, WamConfiguredFeatureKeys.HEATHER_PATCH, chanceModifiers(5));
    }

    private static void registerGroves(BootstrapContext<PlacedFeature> registerable) {
        register(registerable, WamPlacedFeatureKeys.PINY_GROVE_TREES, WamConfiguredFeatureKeys.PINY_GROVE_TREES, countExtraTreeModifiers(10, 0.1f, 1));
    }

    private WamPlacedFeatures() {
    }

    public static void register(BootstrapContext<PlacedFeature> registerable) {
        registerPineForests(registerable);
        registerMires(registerable);
        registerFells(registerable);
        registerGroves(registerable);
    }

    private static void register(BootstrapContext<PlacedFeature> registerable, ResourceKey<PlacedFeature> key,
                                 ResourceKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = registerable.lookup(Registries.CONFIGURED_FEATURE);
        PlacedFeature placed = new PlacedFeature(lookup.getOrThrow(feature), modifiers);
        registerable.register(key, placed);
    }
}
