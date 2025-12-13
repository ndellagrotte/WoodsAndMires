package juuxel.woodsandmires.biome;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.util.RegistryCollector;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

public final class WamBiomeKeys {
    public static final RegistryCollector<ResourceKey<Biome>> ALL = new RegistryCollector<>();
    public static final ResourceKey<Biome> PINE_FOREST = key("pine_forest");
    public static final ResourceKey<Biome> SNOWY_PINE_FOREST = key("snowy_pine_forest");
    public static final ResourceKey<Biome> OLD_GROWTH_PINE_FOREST = key("old_growth_pine_forest");
    public static final ResourceKey<Biome> LUSH_PINE_FOREST = key("lush_pine_forest");
    public static final ResourceKey<Biome> PINE_MIRE = key("pine_mire");
    public static final ResourceKey<Biome> FELL = key("fell");
    public static final ResourceKey<Biome> SNOWY_FELL = key("snowy_fell");
    public static final ResourceKey<Biome> PINY_GROVE = key("piny_grove");

    private WamBiomeKeys() {
    }

    private static ResourceKey<Biome> key(String id) {
        return ALL.add(ResourceKey.create(Registries.BIOME, WoodsAndMires.id(id)));
    }
}
