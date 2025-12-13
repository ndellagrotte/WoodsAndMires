package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public final class MeadowFeatureConfig implements FeatureConfiguration {
    public static final Codec<MeadowFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(MeadowFeatureConfig::getStateProvider),
            Codec.FLOAT.fieldOf("chance").forGetter(MeadowFeatureConfig::getChance),
            BlockPredicate.CODEC.optionalFieldOf("allowed_placement", BlockPredicate.alwaysTrue()).forGetter(MeadowFeatureConfig::getAllowedPlacement)
        ).apply(instance, MeadowFeatureConfig::new)
    );

    final BlockStateProvider stateProvider;
    final float chance;
    final BlockPredicate allowedPlacement;

    public MeadowFeatureConfig(BlockStateProvider stateProvider, float chance) {
        this(stateProvider, chance, BlockPredicate.alwaysTrue());
    }

    public MeadowFeatureConfig(BlockStateProvider stateProvider, float chance, BlockPredicate allowedPlacement) {
        this.stateProvider = stateProvider;
        this.chance = chance;
        this.allowedPlacement = allowedPlacement;
    }

    public BlockStateProvider getStateProvider() {
        return stateProvider;
    }

    public float getChance() {
        return chance;
    }

    public BlockPredicate getAllowedPlacement() {
        return allowedPlacement;
    }
}
