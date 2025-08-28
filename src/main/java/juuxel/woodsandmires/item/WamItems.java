package juuxel.woodsandmires.item;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class WamItems {
    public static final Item PINE_BOAT = TerraformBoatItemHelper.registerBoatItem(Identifier.of(WoodsAndMires.ID, "pine"), false);
    public static final Item PINE_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(Identifier.of(WoodsAndMires.ID, "pine"), true);
    public static final Item PINE_CONE = register("pine_cone", new Item.Settings());
    public static final Item PINE_CONE_JAM = register("pine_cone_jam", new Item.Settings()
        .recipeRemainder(Items.GLASS_BOTTLE)
        .useRemainder(Items.GLASS_BOTTLE)
        .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).build())
    );
    public static final Item SIGN = register("pine_sign", settings -> new SignItem(WamBlocks.PINE_SIGN, WamBlocks.PINE_WALL_SIGN, settings), new Item.Settings().maxCount(16).useBlockPrefixedTranslationKey());
    public static final Item HANGING_SIGN = register("pine_hanging_sign", settings -> new HangingSignItem(WamBlocks.PINE_HANGING_SIGN, WamBlocks.PINE_WALL_HANGING_SIGN, settings), new Item.Settings().maxCount(16).useBlockPrefixedTranslationKey());

    public static void init() {
    }

    private static Item register(String id, Function<Item.Settings, Item> function, Item.Settings settings) {
        return Registry.register(Registries.ITEM, WoodsAndMires.id(id), function.apply(settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, WoodsAndMires.id(id)))));
    }

    private static Item register(String id, Item.Settings settings) {
        return register(id, Item::new, settings);
    }
}
