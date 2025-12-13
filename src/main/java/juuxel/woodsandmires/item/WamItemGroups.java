package juuxel.woodsandmires.item;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class WamItemGroups {
    private static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(WoodsAndMires.ID, "items"));

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.WARPED_BUTTON,
                WamBlocks.PINE_LOG,
                WamBlocks.AGED_PINE_LOG,
                WamBlocks.PINE_SHRUB_LOG,
                WamBlocks.PINE_WOOD,
                WamBlocks.AGED_PINE_WOOD,
                WamBlocks.STRIPPED_PINE_LOG,
                WamBlocks.STRIPPED_PINE_WOOD,
                WamBlocks.PINE_SNAG_LOG,
                WamBlocks.PINE_SNAG_WOOD,
                WamBlocks.PINE_PLANKS,
                WamBlocks.PINE_STAIRS,
                WamBlocks.PINE_SLAB,
                WamBlocks.PINE_FENCE,
                WamBlocks.PINE_FENCE_GATE,
                WamBlocks.PINE_DOOR,
                WamBlocks.PINE_TRAPDOOR,
                WamBlocks.PINE_PRESSURE_PLATE,
                WamBlocks.PINE_BUTTON
            );
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Items.WARPED_STEM,
                WamBlocks.PINE_LOG,
                WamBlocks.AGED_PINE_LOG,
                WamBlocks.PINE_SHRUB_LOG,
                WamBlocks.PINE_SNAG_LOG);
            entries.addAfter(Items.FLOWERING_AZALEA_LEAVES,
                WamBlocks.PINE_LEAVES);
            entries.addAfter(Items.FLOWERING_AZALEA,
                WamBlocks.PINE_SAPLING);
            entries.addAfter(Items.LILY_OF_THE_VALLEY,
                WamBlocks.TANSY,
                WamBlocks.HEATHER);
            entries.addAfter(Items.PEONY,
                WamBlocks.FIREWEED);
            entries.addBefore(Items.GLOW_LICHEN,
                WamBlocks.FELL_LICHEN);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.addAfter(Items.WARPED_HANGING_SIGN,
                WamBlocks.PINE_SIGN,
                WamBlocks.PINE_HANGING_SIGN);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.addAfter(Items.BAMBOO_CHEST_RAFT,
                WamItems.PINE_BOAT,
                WamItems.PINE_CHEST_BOAT);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            addBefore(entries, stack -> stack.is(Items.ENCHANTED_BOOK),
                WamItems.PINE_CONE);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.accept(WamItems.PINE_CONE_JAM);
        });

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
            .title(Component.literal("Woods and Mires"))
            .icon(() -> WamBlocks.PINE_SAPLING.asItem().getDefaultInstance())
            .displayItems((context, entries) -> {
                BuiltInRegistries.ITEM.listElementIds().filter(itemRegistryKey -> itemRegistryKey.identifier().getNamespace().equals(WoodsAndMires.ID)).map(BuiltInRegistries.ITEM::getValue).forEach(entries::accept);
            }).build()
        );
    }

    private static void addBefore(FabricItemGroupEntries entries, Predicate<ItemStack> predicate, ItemLike... items) {
        var stacks = Arrays.stream(items).map(ItemStack::new).toList();
        entries.addBefore(predicate, stacks, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void addAfterFirstEnabled(FabricItemGroupEntries entries, List<Item> after, ItemLike... items) {
        Item start = after.stream()
            .filter(item -> item.requiredFeatures().isSubsetOf(entries.getEnabledFeatures()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Could not find any of the items " + after));
        entries.addAfter(start, items);
    }
}
