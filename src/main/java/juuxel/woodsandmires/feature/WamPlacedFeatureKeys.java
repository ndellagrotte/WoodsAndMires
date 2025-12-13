package juuxel.woodsandmires.feature;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.util.RegistryCollector;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WamPlacedFeatureKeys {
    public static final RegistryCollector<ResourceKey<PlacedFeature>> ALL = new RegistryCollector<>();

    // Pine forest
    public static final ResourceKey<PlacedFeature> FOREST_PINE = key("forest_pine");
    public static final ResourceKey<PlacedFeature> SNOWY_PINE_FOREST_TREES = key("snowy_pine_forest_trees");
    public static final ResourceKey<PlacedFeature> OLD_GROWTH_PINE_FOREST_TREES = key("old_growth_pine_forest_trees");
    public static final ResourceKey<PlacedFeature> PINE_FOREST_BOULDER = key("pine_forest_boulder");
    public static final ResourceKey<PlacedFeature> PINE_FOREST_HEATHER_PATCH = key("pine_forest_heather_patch");
    public static final ResourceKey<PlacedFeature> LUSH_PINE_FOREST_TREES = key("lush_pine_forest_trees");
    public static final ResourceKey<PlacedFeature> LUSH_PINE_FOREST_FLOWERS = key("lush_pine_forest_flowers");
    public static final ResourceKey<PlacedFeature> FALLEN_PINE = key("fallen_pine");

    // Mire
    public static final ResourceKey<PlacedFeature> MIRE_PONDS = key("mire_ponds");
    public static final ResourceKey<PlacedFeature> MIRE_FLOWERS = key("mire_flowers");
    public static final ResourceKey<PlacedFeature> MIRE_MEADOW = key("mire_meadow");
    public static final ResourceKey<PlacedFeature> MIRE_PINE_SNAG = key("mire_pine_snag");
    public static final ResourceKey<PlacedFeature> MIRE_PINE_SHRUB = key("mire_pine_shrub");

    // Fells
    public static final ResourceKey<PlacedFeature> FELL_VEGETATION = key("fell_vegetation");
    public static final ResourceKey<PlacedFeature> FELL_BOULDER = key("fell_boulder");
    public static final ResourceKey<PlacedFeature> FELL_POND = key("fell_pond");
    public static final ResourceKey<PlacedFeature> FELL_BIRCH_SHRUB = key("fell_birch_shrub");
    public static final ResourceKey<PlacedFeature> FELL_LICHEN = key("fell_lichen");
    public static final ResourceKey<PlacedFeature> FELL_MOSS_PATCH = key("fell_moss_patch");
    public static final ResourceKey<PlacedFeature> FROZEN_TREASURE = key("frozen_treasure");
    public static final ResourceKey<PlacedFeature> FELL_HEATHER_PATCH = key("fell_heather_patch");

    // Groves
    public static final ResourceKey<PlacedFeature> PINY_GROVE_TREES = key("piny_grove_trees");

    // Vanilla biomes
    public static final ResourceKey<PlacedFeature> PLAINS_FLOWERS = key("plains_flowers");
    public static final ResourceKey<PlacedFeature> FOREST_TANSY = key("forest_tansy");
    public static final ResourceKey<PlacedFeature> TAIGA_HEATHER_PATCH = key("taiga_heather_patch");

    private static ResourceKey<PlacedFeature> key(String id) {
        return ALL.add(ResourceKey.create(Registries.PLACED_FEATURE, WoodsAndMires.id(id)));
    }
}
