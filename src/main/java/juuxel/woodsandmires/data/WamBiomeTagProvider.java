package juuxel.woodsandmires.data;

import juuxel.woodsandmires.biome.WamBiomeKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamBiomeTagProvider extends FabricTagProvider<Biome> {
    public WamBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // Vanilla tags
        generateOverworld(BiomeTags.IS_OVERWORLD);
        builder(BiomeTags.IS_FOREST)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.OLD_GROWTH_PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.LUSH_PINE_FOREST)
            .add(WamBiomeKeys.PINY_GROVE);
        builder(BiomeTags.IS_HILL)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST);
        builder(BiomeTags.IS_MOUNTAIN)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL);
        builder(BiomeTags.IS_TAIGA)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.OLD_GROWTH_PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST);
        generateOverworld(BiomeTags.STRONGHOLD_HAS_STRUCTURE);
        builder(BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.PINY_GROVE);

        // Common tags
        generateOverworld(ConventionalBiomeTags.IS_OVERWORLD);
        builder(ConventionalBiomeTags.IS_COLD_OVERWORLD)
            .add(WamBiomeKeys.PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.OLD_GROWTH_PINE_FOREST)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL)
            .add(WamBiomeKeys.PINY_GROVE);
        builder(ConventionalBiomeTags.IS_TEMPERATE_OVERWORLD)
            .add(WamBiomeKeys.LUSH_PINE_FOREST);
        forEachTag(ConventionalBiomeTags.IS_WET_OVERWORLD, ConventionalBiomeTags.IS_SWAMP)
            .add(WamBiomeKeys.PINE_MIRE);
        builder(ConventionalBiomeTags.IS_HILL)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL);
        builder(ConventionalBiomeTags.IS_MOUNTAIN_PEAK)
            .add(WamBiomeKeys.FELL)
            .add(WamBiomeKeys.SNOWY_FELL);
        builder(ConventionalBiomeTags.IS_MOUNTAIN_SLOPE)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST);
        builder(ConventionalBiomeTags.IS_SNOWY)
            .add(WamBiomeKeys.SNOWY_PINE_FOREST)
            .add(WamBiomeKeys.SNOWY_FELL)
            .add(WamBiomeKeys.PINY_GROVE);
    }

    @SafeVarargs
    private MultiBuilder<Biome> forEachTag(TagKey<Biome>... tags) {
        return new MultiBuilder<>(Stream.of(tags).map(this::builder).toList());
    }

    private void generateOverworld(TagKey<Biome> tag) {
        var tagBuilder = builder(tag);
        WamBiomeKeys.ALL.stream().forEach(tagBuilder::add);
    }

    static final class MultiBuilder<T> {
        private final List<ProvidedTagBuilder<RegistryKey<T>, T>> builders;

        private MultiBuilder(List<ProvidedTagBuilder<RegistryKey<T>, T>> builders) {
            this.builders = builders;
        }

        public MultiBuilder<T> add(RegistryKey<T> key) {
            for (var builder : builders) {
                builder.add(key);
            }
            return this;
        }
    }
}
