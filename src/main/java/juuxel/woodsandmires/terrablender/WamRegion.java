package juuxel.woodsandmires.terrablender;

import com.mojang.datafixers.util.Pair;
import juuxel.woodsandmires.biome.WamBiomeKeys;
import net.minecraft.resources.Identifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class WamRegion extends Region {
    public WamRegion(Identifier name, int overworldWeight) {
        super(name, RegionType.OVERWORLD, overworldWeight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.SWAMP, WamBiomeKeys.PINE_MIRE);
            builder.replaceBiome(Biomes.TAIGA, WamBiomeKeys.PINE_FOREST);
            builder.replaceBiome(Biomes.FOREST, WamBiomeKeys.LUSH_PINE_FOREST);
            builder.replaceBiome(Biomes.OLD_GROWTH_PINE_TAIGA, WamBiomeKeys.OLD_GROWTH_PINE_FOREST);
            builder.replaceBiome(Biomes.OLD_GROWTH_SPRUCE_TAIGA, WamBiomeKeys.OLD_GROWTH_PINE_FOREST);
            builder.replaceBiome(Biomes.SNOWY_TAIGA, WamBiomeKeys.SNOWY_PINE_FOREST);
            // TODO: Windswept pine forest?
            builder.replaceBiome(Biomes.WINDSWEPT_FOREST, WamBiomeKeys.SNOWY_PINE_FOREST);
            builder.replaceBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS, WamBiomeKeys.FELL);
            builder.replaceBiome(Biomes.WINDSWEPT_HILLS, WamBiomeKeys.FELL);
            builder.replaceBiome(Biomes.SNOWY_SLOPES, WamBiomeKeys.SNOWY_PINE_FOREST);
            builder.replaceBiome(Biomes.FROZEN_PEAKS, WamBiomeKeys.SNOWY_FELL);
            builder.replaceBiome(Biomes.GROVE, WamBiomeKeys.PINY_GROVE);
        });
    }
}
