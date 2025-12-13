package juuxel.woodsandmires.biome;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WamBiomeModifications {
    public static void init() {
        BiomeModifications.addFeature(
            context -> context.getBiomeKey() == Biomes.PLAINS,
            GenerationStep.Decoration.VEGETAL_DECORATION,
            WamPlacedFeatureKeys.PLAINS_FLOWERS
        );

        BiomeModifications.addFeature(
            context -> context.hasTag(ConventionalBiomeTags.FOREST) && !WoodsAndMires.ID.equals(context.getBiomeKey().identifier().getNamespace()),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            WamPlacedFeatureKeys.FOREST_TANSY
        );

        BiomeModifications.addFeature(
            context -> context.hasTag(BiomeTags.IS_TAIGA) && !WoodsAndMires.ID.equals(context.getBiomeKey().identifier().getNamespace()),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            WamPlacedFeatureKeys.TAIGA_HEATHER_PATCH
        );
    }
}
