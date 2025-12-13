package juuxel.woodsandmires.data;

import juuxel.woodsandmires.data.builtin.WamConfiguredFeatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public final class WamChestLootTableProvider extends SimpleFabricLootTableProvider {
    public WamChestLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.CHEST);
    }


    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> sink) {
        sink.accept(WamConfiguredFeatures.FROZEN_TREASURE_LOOT_TABLE,
            new LootTable.Builder()
                .withPool(
                    LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2, 4))
                        .add(TagEntry.expandTag(ConventionalItemTags.IRON_INGOTS))
                        .add(TagEntry.expandTag(ConventionalItemTags.GOLD_INGOTS))
                        .add(TagEntry.expandTag(ConventionalItemTags.COPPER_INGOTS))
                )
                .withPool(
                    LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 2))
                        .add(TagEntry.expandTag(ConventionalItemTags.EMERALDS))
                        .add(LootItem.lootTableItem(Items.NAME_TAG))
                )
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(4))
                        .add(LootItem.lootTableItem(Items.STRING))
                        .add(LootItem.lootTableItem(Items.SPIDER_EYE))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH))
                        .add(LootItem.lootTableItem(Items.BONE))
                )
        );
    }
}
