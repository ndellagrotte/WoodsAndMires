package juuxel.woodsandmires.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public abstract class EncoderBasedDataProvider<T> extends FabricDynamicRegistryProvider {
    private final ResourceKey<? extends Registry<T>> registryKey;

    protected EncoderBasedDataProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, ResourceKey<? extends Registry<T>> registryKey) {
        super(output, registriesFuture);
        this.registryKey = registryKey;
    }

    protected abstract Stream<ResourceKey<T>> getEntries();

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        var wrapper = registries.lookupOrThrow(registryKey);
        getEntries().forEach(key -> entries.add(wrapper, key));
    }
}
