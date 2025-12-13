package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FallenLogFeatureConfig(Block mainLog, Block agedLog, FloatProvider agedHeightFraction,
                                     IntProvider length, BlockStateProvider topDecoration) implements FeatureConfiguration {
    public static final Codec<FallenLogFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("main_log").forGetter(FallenLogFeatureConfig::mainLog),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("aged_log").forGetter(FallenLogFeatureConfig::agedLog),
            FloatProvider.CODEC.fieldOf("aged_height_fraction").forGetter(FallenLogFeatureConfig::agedHeightFraction),
            IntProvider.POSITIVE_CODEC.fieldOf("length").forGetter(FallenLogFeatureConfig::length),
            BlockStateProvider.CODEC.fieldOf("top_decoration").forGetter(FallenLogFeatureConfig::topDecoration)
        ).apply(instance, FallenLogFeatureConfig::new)
    );
}
