package juuxel.woodsandmires.data;

import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.data.builtin.CommonItemTags;
import juuxel.woodsandmires.item.WamItemTags;
import juuxel.woodsandmires.item.WamItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public final class WamItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public WamItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, null);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // Minecraft tags
        valueLookupBuilder(ItemTags.BOATS)
            .add(WamItems.PINE_BOAT);
        valueLookupBuilder(ItemTags.CHEST_BOATS)
            .add(WamItems.PINE_CHEST_BOAT);
        valueLookupBuilder(ItemTags.HANGING_SIGNS)
            .add(WamBlocks.PINE_HANGING_SIGN.asItem());
        valueLookupBuilder(ItemTags.LEAVES)
            .add(WamBlocks.PINE_LEAVES.asItem());
        valueLookupBuilder(ItemTags.LOGS_THAT_BURN)
            .addTag(WamItemTags.PINE_LOGS);
        valueLookupBuilder(ItemTags.PLANKS)
            .add(WamBlocks.PINE_PLANKS.asItem());
        valueLookupBuilder(ItemTags.SAPLINGS)
            .add(WamBlocks.PINE_SAPLING.asItem());
        valueLookupBuilder(ItemTags.SIGNS)
            .add(WamBlocks.PINE_SIGN.asItem());
        valueLookupBuilder(ItemTags.SMALL_FLOWERS)
            .add(WamBlocks.HEATHER.asItem(), WamBlocks.TANSY.asItem());
        valueLookupBuilder(ItemTags.BEE_FOOD)
            .add(WamBlocks.FIREWEED.asItem());
        valueLookupBuilder(ItemTags.WOODEN_BUTTONS)
            .add(WamBlocks.PINE_BUTTON.asItem());
        valueLookupBuilder(ItemTags.WOODEN_DOORS)
            .add(WamBlocks.PINE_DOOR.asItem());
        valueLookupBuilder(ItemTags.WOODEN_FENCES)
            .add(WamBlocks.PINE_FENCE.asItem());
        valueLookupBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
            .add(WamBlocks.PINE_PRESSURE_PLATE.asItem());
        valueLookupBuilder(ItemTags.WOODEN_SLABS)
            .add(WamBlocks.PINE_SLAB.asItem());
        valueLookupBuilder(ItemTags.WOODEN_STAIRS)
            .add(WamBlocks.PINE_STAIRS.asItem());
        valueLookupBuilder(ItemTags.WOODEN_TRAPDOORS)
            .add(WamBlocks.PINE_TRAPDOOR.asItem());

        // WaM tags
        valueLookupBuilder(WamItemTags.PINE_LOGS)
            .addTag(WamItemTags.THICK_PINE_LOGS)
            .add(WamBlocks.PINE_SHRUB_LOG.asItem());
        valueLookupBuilder(WamItemTags.THICK_PINE_LOGS)
            .add(WamBlocks.PINE_LOG.asItem(), WamBlocks.AGED_PINE_LOG.asItem())
            .add(WamBlocks.PINE_WOOD.asItem(), WamBlocks.AGED_PINE_WOOD.asItem())
            .add(WamBlocks.STRIPPED_PINE_LOG.asItem(), WamBlocks.STRIPPED_PINE_WOOD.asItem())
            .add(WamBlocks.PINE_SNAG_LOG.asItem(), WamBlocks.PINE_SNAG_WOOD.asItem());

        // Common tags

        ProvidedTagBuilder<Item, Item> chains = valueLookupBuilder(CommonItemTags.CHAINS);
        chains.add(Items.IRON_CHAIN);
        Items.COPPER_CHAINS.forEach(chains::add);
        valueLookupBuilder(CommonItemTags.HONEY)
            .add(WamItems.PINE_CONE_JAM);
        valueLookupBuilder(CommonItemTags.JAMS)
            .add(WamItems.PINE_CONE_JAM);
        valueLookupBuilder(CommonItemTags.PINE_CONES)
            .add(WamItems.PINE_CONE);
        valueLookupBuilder(CommonItemTags.SUGAR)
            .add(Items.SUGAR);
        valueLookupBuilder(CommonItemTags.WOODEN_CHESTS)
            .add(Items.CHEST, Items.TRAPPED_CHEST);
        valueLookupBuilder(CommonItemTags.WOODEN_RODS)
            .add(Items.STICK);
    }
}
