package juuxel.woodsandmires.data;

import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamConfiguredFeaturesProvider extends EncoderBasedDataProvider<ConfiguredFeature<?, ?>> {
    public WamConfiguredFeaturesProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, Registries.CONFIGURED_FEATURE);
    }

    @Override
    protected Stream<ResourceKey<ConfiguredFeature<?, ?>>> getEntries() {
        return WamConfiguredFeatureKeys.ALL.stream();
    }

    @Override
    public String getName() {
        return "Configured Features";
    }
}
