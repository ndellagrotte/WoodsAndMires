package juuxel.woodsandmires.data;

import juuxel.woodsandmires.data.EncoderBasedDataProvider;
import juuxel.woodsandmires.feature.WamPlacedFeatureKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamPlacedFeaturesProvider extends EncoderBasedDataProvider<PlacedFeature> {
    public WamPlacedFeaturesProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, Registries.PLACED_FEATURE);
    }

    @Override
    protected Stream<ResourceKey<PlacedFeature>> getEntries() {
        return WamPlacedFeatureKeys.ALL.stream();
    }

    @Override
    public String getName() {
        return "Placed Features";
    }
}
