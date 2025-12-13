package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FrozenTreasureFeatureConfig(
    BlockStateProvider ice,
    IntProvider height,
    IntProvider radius,
    ResourceKey<LootTable> lootTable
) implements FeatureConfiguration {
    public static final Codec<FrozenTreasureFeatureConfig> CODEC = RecordCodecBuilder.create(builder ->
        builder.group(
            BlockStateProvider.CODEC.fieldOf("ice").forGetter(FrozenTreasureFeatureConfig::ice),
            IntProvider.POSITIVE_CODEC.fieldOf("height").forGetter(FrozenTreasureFeatureConfig::height),
            IntProvider.POSITIVE_CODEC.fieldOf("radius").forGetter(FrozenTreasureFeatureConfig::radius),
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(FrozenTreasureFeatureConfig::lootTable)
        ).apply(builder, FrozenTreasureFeatureConfig::new)
    );
}
