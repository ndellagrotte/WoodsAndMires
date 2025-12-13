package juuxel.woodsandmires.item;

import juuxel.woodsandmires.WoodsAndMires;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;

public final class WamItemTags {
    public static TagKey<Item> PINE_LOGS = tag("pine_logs");
    public static TagKey<Item> THICK_PINE_LOGS = tag("thick_pine_logs");

    private static TagKey<Item> tag(String id) {
        return TagKey.create(Registries.ITEM, WoodsAndMires.id(id));
    }
}
