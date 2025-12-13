package juuxel.woodsandmires.data.builtin;

import com.mojang.serialization.Lifecycle;
import juuxel.woodsandmires.biome.WamBiomeKeys;
import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.core.Holder;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.GraphCycleProhibitedException;

import java.util.function.Consumer;

public final class WamBiomes {
    // See Biome.doesNotSnow (1.19.4)
    private static final float WARM_BIOME_MINIMUM_TEMPERATURE = 0.15f;

    private final BootstrapContext<Biome> registerable;
    private final DirectedAcyclicGraph<Holder<PlacedFeature>, DefaultEdge> featureGraph =
        new DirectedAcyclicGraph<>(DefaultEdge.class);

    private WamBiomes(BootstrapContext<Biome> registerable) {
        this.registerable = registerable;
        initGraph();
    }

    public static void register(BootstrapContext<Biome> registerable) {
        new WamBiomes(registerable).registerAll();
    }

    private void registerAll() {
        register(WamBiomeKeys.PINE_FOREST, pineForest());
        register(WamBiomeKeys.SNOWY_PINE_FOREST, snowyPineForest());
        register(WamBiomeKeys.OLD_GROWTH_PINE_FOREST, oldGrowthPineForest());
        register(WamBiomeKeys.LUSH_PINE_FOREST, lushPineForest());
        register(WamBiomeKeys.PINE_MIRE, pineMire());
        register(WamBiomeKeys.FELL, fell());
        register(WamBiomeKeys.SNOWY_FELL, snowyFell());
        register(WamBiomeKeys.PINY_GROVE, pinyGrove());
    }

    private void initGraph() {
        record CapturingRegisterable<T>(BootstrapContext<?> parent, Consumer<T> sink) implements BootstrapContext<T> {
            @Override
            public Holder.Reference<T> register(ResourceKey<T> key, T value, Lifecycle lifecycle) {
                sink.accept(value);
                return null;
            }

            @Override
            public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> registryRef) {
                return parent.lookup(registryRef);
            }
        }

        BiomeData.bootstrap(new CapturingRegisterable<>(registerable, this::addBiomeToGraph));
    }

    private void addBiomeToGraph(Biome biome) {
        for (var features : biome.getGenerationSettings().features()) {
            if (features.size() == 0) continue;

            var before = features.get(0);
            featureGraph.addVertex(before);

            for (int i = 1; i < features.size(); i++) {
                var after = features.get(i);
                featureGraph.addVertex(after);

                try {
                    featureGraph.addEdge(before, after);
                } catch (GraphCycleProhibitedException e) {
                    throw new IllegalStateException("Feature order cycle found between " + before.unwrapKey() + " and " + after.unwrapKey(), e);
                }

                before = after;
            }
        }
    }

    private void register(ResourceKey<Biome> key, Biome biome) {
        registerable.register(key, biome);

        try {
            addBiomeToGraph(biome);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage() + " [biome=" + key.identifier() + "]", e);
        }
    }

    private static int getSkyColor(float temperature) {
        return OverworldBiomes.calculateSkyColor(temperature);
    }

    private Biome pineForest(float temperature, Consumer<WamGenerationSettingsBuilder> generationSettingsConfigurator) {
        BiomeGenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomes.globalOverworldGeneration(builder);
            BiomeDefaultFeatures.addForestFlowers(builder);
            BiomeDefaultFeatures.addFerns(builder);
            BiomeDefaultFeatures.addDefaultOres(builder);
            BiomeDefaultFeatures.addDefaultSoftDisks(builder);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.FALLEN_PINE);

            // Stone boulders
            builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WamPlacedFeatureKeys.PINE_FOREST_BOULDER);

            if (temperature >= WARM_BIOME_MINIMUM_TEMPERATURE) {
                BiomeDefaultFeatures.addDefaultFlowers(builder);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.PINE_FOREST_HEATHER_PATCH);
            }

            generationSettingsConfigurator.accept(builder);

            BiomeDefaultFeatures.addForestGrass(builder);
            BiomeDefaultFeatures.addDefaultMushrooms(builder);
            BiomeDefaultFeatures.addDefaultExtraVegetation(builder, true);
            BiomeDefaultFeatures.addCommonBerryBushes(builder);
        });

        MobSpawnSettings spawnSettings = spawnSettings(builder -> {
            BiomeDefaultFeatures.farmAnimals(builder);
            BiomeDefaultFeatures.commonSpawns(builder);

            builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4));
            builder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 4));
        });

        return new Biome.BiomeBuilder()
            .specialEffects(
                new BiomeSpecialEffects.Builder()
                    .waterColor(OverworldBiomes.NORMAL_WATER_COLOR)
                    .foliageColorOverride(0x43C44F)
                    .build()
            )
            .setAttribute(EnvironmentAttributes.SKY_COLOR, getSkyColor(temperature))
            .downfall(0.6f)
            .temperature(temperature)
            .generationSettings(generationSettings)
            .mobSpawnSettings(spawnSettings)
            .build();
    }

    private Biome pineForest() {
        // noinspection CodeBlock2Expr
        return pineForest(0.6f, builder -> {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.FOREST_PINE);
        });
    }

    private Biome snowyPineForest() {
        // noinspection CodeBlock2Expr
        return pineForest(0f, builder -> {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.SNOWY_PINE_FOREST_TREES);
        });
    }

    private Biome oldGrowthPineForest() {
        return pineForest(0.4f, builder -> {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.OLD_GROWTH_PINE_FOREST_TREES);
        });
    }

    private Biome lushPineForest() {
        return pineForest(0.6f, builder -> {
            BiomeDefaultFeatures.addSavannaGrass(builder);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.LUSH_PINE_FOREST_TREES);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.LUSH_PINE_FOREST_FLOWERS);
            BiomeDefaultFeatures.addWarmFlowers(builder);

            // https://github.com/Juuxel/WoodsAndMires/issues/14
            builder.addOrdering(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                VegetationPlacements.PATCH_GRASS_FOREST,
                VegetationPlacements.FLOWER_WARM
            );

            // https://github.com/Juuxel/WoodsAndMires/issues/22
            builder.addOrdering(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                VegetationPlacements.PATCH_TALL_GRASS,
                VegetationPlacements.FLOWER_DEFAULT
            );
            builder.addOrdering(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                VegetationPlacements.PATCH_TALL_GRASS,
                VegetationPlacements.PATCH_LARGE_FERN
            );
        });
    }

    private Biome pineMire() {
        BiomeGenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomes.globalOverworldGeneration(builder);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.MIRE_PINE_SHRUB);
            builder.addFeature(GenerationStep.Decoration.LAKES, WamPlacedFeatureKeys.MIRE_PONDS);
            builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WamPlacedFeatureKeys.MIRE_MEADOW);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.MIRE_FLOWERS);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.MIRE_PINE_SNAG);
        });

        MobSpawnSettings spawnSettings = spawnSettings(builder -> {
            BiomeDefaultFeatures.farmAnimals(builder);
            BiomeDefaultFeatures.commonSpawns(builder);
        });

        return new Biome.BiomeBuilder()
            .specialEffects(
                new BiomeSpecialEffects.Builder()
                    .waterColor(0x7B6D1B)
                    .foliageColorOverride(0xBFA243)
                    .grassColorOverride(0xADA24C)
                    .build()
            )
            .setAttribute(EnvironmentAttributes.SKY_COLOR, getSkyColor(0.6f))
            .downfall(0.9f)
            .temperature(0.6f)
            .generationSettings(generationSettings)
            .mobSpawnSettings(spawnSettings)
            .build();
    }

    private Biome fell(float temperature, Consumer<BiomeGenerationSettings.Builder> generationSettingsConfigurator) {
        MobSpawnSettings spawnSettings = spawnSettings(builder -> {
            BiomeDefaultFeatures.commonSpawns(builder);

            builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4));
            builder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 4));
        });
        BiomeGenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomes.globalOverworldGeneration(builder);
            BiomeDefaultFeatures.addDefaultOres(builder);
            BiomeDefaultFeatures.addDefaultSoftDisks(builder);
            generationSettingsConfigurator.accept(builder);
        });

        return new Biome.BiomeBuilder()
            .specialEffects(
                new BiomeSpecialEffects.Builder()
                    .waterColor(OverworldBiomes.NORMAL_WATER_COLOR)
                    .build()
            )
            .setAttribute(EnvironmentAttributes.SKY_COLOR, getSkyColor(temperature))
            .downfall(0.7f)
            .temperature(temperature)
            .generationSettings(generationSettings)
            .mobSpawnSettings(spawnSettings)
            .build();
    }

    private Biome fell() {
        return fell(0.25f, builder -> {
            BiomeDefaultFeatures.addDefaultFlowers(builder);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_HEATHER_PATCH);
            BiomeDefaultFeatures.addForestGrass(builder);
            BiomeDefaultFeatures.addDefaultMushrooms(builder);
            BiomeDefaultFeatures.addDefaultExtraVegetation(builder, true);

            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_LICHEN);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_MOSS_PATCH);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_VEGETATION);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.FELL_BIRCH_SHRUB);
            builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WamPlacedFeatureKeys.FELL_BOULDER);
            builder.addFeature(GenerationStep.Decoration.LAKES, WamPlacedFeatureKeys.FELL_POND);
        });
    }

    private Biome snowyFell() {
        return fell(0f, builder -> {
            builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WamPlacedFeatureKeys.FELL_BOULDER);
            builder.addFeature(GenerationStep.Decoration.LAKES, WamPlacedFeatureKeys.FELL_POND);
            builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, WamPlacedFeatureKeys.FROZEN_TREASURE);
        });
    }

    private Biome pinyGrove() {
        BiomeGenerationSettings generationSettings = generationSettings(builder -> {
            OverworldBiomes.globalOverworldGeneration(builder);
            BiomeDefaultFeatures.addFrozenSprings(builder);
            BiomeDefaultFeatures.addDefaultOres(builder);
            BiomeDefaultFeatures.addDefaultSoftDisks(builder);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WamPlacedFeatureKeys.PINY_GROVE_TREES);
            BiomeDefaultFeatures.addDefaultExtraVegetation(builder, true);
            BiomeDefaultFeatures.addExtraEmeralds(builder);
            BiomeDefaultFeatures.addInfestedStone(builder);
        });
        MobSpawnSettings spawnSettings = spawnSettings(builder -> {
            BiomeDefaultFeatures.farmAnimals(builder);
            builder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4))
                .addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3))
                .addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 4));
            BiomeDefaultFeatures.commonSpawns(builder);
        });
        return new Biome.BiomeBuilder()
            .specialEffects(
                new BiomeSpecialEffects.Builder()
                    .waterColor(OverworldBiomes.NORMAL_WATER_COLOR)
                    .build()
            )
            .setAttribute(EnvironmentAttributes.SKY_COLOR, getSkyColor(-0.2f))
            .downfall(0.8f)
            .temperature(-0.2f)
            .generationSettings(generationSettings)
            .mobSpawnSettings(spawnSettings)
            .build();
    }

    private BiomeGenerationSettings generationSettings(Consumer<WamGenerationSettingsBuilder> configurator) {
        HolderGetter<PlacedFeature> placedFeatures = registerable.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = registerable.lookup(Registries.CONFIGURED_CARVER);
        WamGenerationSettingsBuilder builder = new WamGenerationSettingsBuilder(placedFeatures, configuredCarvers);
        configurator.accept(builder);
        return builder.build(featureGraph);
    }

    private static MobSpawnSettings spawnSettings(Consumer<MobSpawnSettings.Builder> configurator) {
        MobSpawnSettings.Builder builder = new MobSpawnSettings.Builder();
        configurator.accept(builder);
        return builder.build();
    }
}
