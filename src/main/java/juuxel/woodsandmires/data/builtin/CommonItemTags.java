package juuxel.woodsandmires.data.builtin;

import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.Identifier;

public final class CommonItemTags {
    public static final TagKey<Item> CHAINS = of("chains");
    public static final TagKey<Item> HONEY = of("honey");
    public static final TagKey<Item> JAMS = of("jams");
    public static final TagKey<Item> PINE_CONES = of("pine_cones");
    public static final TagKey<Item> SUGAR = of("sugar");
    public static final TagKey<Item> WOODEN_CHESTS = of("wooden_chests");
    public static final TagKey<Item> WOODEN_RODS = of("wooden_rods");

    private static TagKey<Item> of(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }
}
