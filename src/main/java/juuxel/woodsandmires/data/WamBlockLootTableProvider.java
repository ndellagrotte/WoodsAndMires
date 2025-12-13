package juuxel.woodsandmires.data;

import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.item.WamItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class WamBlockLootTableProvider extends FabricBlockLootTableProvider {
    WamBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(WamBlocks.PINE_LOG);
        dropSelf(WamBlocks.AGED_PINE_LOG);
        dropSelf(WamBlocks.PINE_PLANKS);
        add(WamBlocks.PINE_SLAB, this::createSlabItemTable);
        dropSelf(WamBlocks.PINE_STAIRS);
        dropSelf(WamBlocks.PINE_FENCE);
        dropSelf(WamBlocks.PINE_FENCE_GATE);
        dropSelf(WamBlocks.PINE_BUTTON);
        dropSelf(WamBlocks.PINE_PRESSURE_PLATE);
        dropSelf(WamBlocks.PINE_SIGN);
        add(WamBlocks.PINE_DOOR, this::createDoorTable);
        dropSelf(WamBlocks.PINE_TRAPDOOR);
        add(WamBlocks.PINE_LEAVES,
            block -> createLeavesDrops(block, WamBlocks.PINE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(applyExplosionCondition(WamItems.PINE_CONE, LootPool.lootPool()
                    .when(doesNotHaveShearsOrSilkTouch())
                    .add(LootItem.lootTableItem(WamItems.PINE_CONE)
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.04f)))
                    )
                ))
        );
        dropSelf(WamBlocks.PINE_SAPLING);
        dropPottedContents(WamBlocks.POTTED_PINE_SAPLING);
        dropSelf(WamBlocks.PINE_WOOD);
        dropSelf(WamBlocks.AGED_PINE_WOOD);
        dropSelf(WamBlocks.STRIPPED_PINE_LOG);
        dropSelf(WamBlocks.STRIPPED_PINE_WOOD);
        dropSelf(WamBlocks.PINE_SNAG_LOG);
        dropSelf(WamBlocks.PINE_SNAG_WOOD);
        add(WamBlocks.PINE_SNAG_BRANCH,
            LootTable.lootTable()
                .withPool(applyExplosionCondition(Items.STICK, LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.STICK)
                        .apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(4, 0.8f)))
                    )
                ))
        );
        dropSelf(WamBlocks.PINE_SHRUB_LOG);
        add(WamBlocks.FIREWEED, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        dropSelf(WamBlocks.TANSY);
        dropPottedContents(WamBlocks.POTTED_TANSY);
        dropSelf(WamBlocks.FELL_LICHEN);
        dropPottedContents(WamBlocks.POTTED_FELL_LICHEN);
        dropSelf(WamBlocks.HEATHER);
        dropPottedContents(WamBlocks.POTTED_HEATHER);
    }
}
