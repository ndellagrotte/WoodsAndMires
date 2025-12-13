package juuxel.woodsandmires.data;

import juuxel.woodsandmires.biome.WamBiomeKeys;
import juuxel.woodsandmires.data.EncoderBasedDataProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class WamBiomeProvider extends EncoderBasedDataProvider<Biome> {
    public WamBiomeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, Registries.BIOME);
    }

    @Override
    protected Stream<ResourceKey<Biome>> getEntries() {
        return WamBiomeKeys.ALL.stream();
    }

    @Override
    public String getName() {
        return "Biomes";
    }
}
