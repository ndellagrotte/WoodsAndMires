package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.util.RegistryCollector;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class WamConfiguredFeatureKeys {
    public static final RegistryCollector<ResourceKey<ConfiguredFeature<?, ?>>> ALL = new RegistryCollector<>();

    // General
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_PINE_SHRUB = key("short_pine_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_PINE_SHRUB = key("thin_pine_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE = key("pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_PINE = key("giant_pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_SNAG = key("pine_snag");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PLAINS_FLOWERS = key("plains_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_FROM_SAPLING = key("pine_from_sapling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_FOREST_BOULDER = key("pine_forest_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_TANSY = key("forest_tansy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEATHER_PATCH = key("heather_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LESS_PODZOL_PINE = key("less_podzol_pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NO_PODZOL_PINE = key("no_podzol_pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_PINE_FOREST_TREES = key("lush_pine_forest_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_PINE = key("fallen_pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_GROWTH_PINE_FOREST_TREES = key("old_growth_pine_forest_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWY_PINE_FOREST_TREES = key("snowy_pine_forest_trees");

    // Mire
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIRE_PONDS = key("mire_ponds");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIRE_FLOWERS = key("mire_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIRE_MEADOW = key("mire_meadow");

    // Fells
    public static final ResourceKey<ConfiguredFeature<?, ?>> FELL_VEGETATION = key("fell_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FELL_BOULDER = key("fell_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FELL_POND = key("fell_pond");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FELL_BIRCH_SHRUB = key("fell_birch_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FELL_LICHEN = key("fell_lichen");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FELL_MOSS_PATCH_VEGETATION = key("fell_moss_patch_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FELL_MOSS_PATCH = key("fell_moss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_TREASURE = key("frozen_treasure");

    // Groves
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINY_GROVE_TREES = key("piny_grove_trees");

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String id) {
        return ALL.add(ResourceKey.create(Registries.CONFIGURED_FEATURE, WoodsAndMires.id(id)));
    }
}
