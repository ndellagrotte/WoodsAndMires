package juuxel.woodsandmires.data;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.data.builtin.WamBiomes;
import juuxel.woodsandmires.data.builtin.WamConfiguredFeatures;
import juuxel.woodsandmires.data.builtin.WamPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public final class WamDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(WamConfiguredFeaturesProvider::new);
        pack.addProvider(WamPlacedFeaturesProvider::new);
        pack.addProvider(WamBiomeProvider::new);
        pack.addProvider(WamBlockLootTableProvider::new);
        pack.addProvider(WamChestLootTableProvider::new);
        pack.addProvider(WamBiomeTagProvider::new);
        pack.addProvider(WamBlockTagProvider::new);
        pack.addProvider(WamItemTagProvider::new);
        pack.addProvider(WamRecipeProvider::new);
        pack.addProvider(WamItemModelProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, WamConfiguredFeatures::register);
        registryBuilder.add(Registries.PLACED_FEATURE, WamPlacedFeatures::register);
        registryBuilder.add(Registries.BIOME, WamBiomes::register);
    }

    @Override
    public String getEffectiveModId() {
        return WoodsAndMires.ID;
    }
}
