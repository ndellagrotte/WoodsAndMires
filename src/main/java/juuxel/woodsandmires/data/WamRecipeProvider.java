package juuxel.woodsandmires.data;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.data.builtin.CommonItemTags;
import juuxel.woodsandmires.item.WamItemTags;
import juuxel.woodsandmires.item.WamItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Items;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class WamRecipeProvider extends FabricRecipeProvider {
    public static final BlockFamily PINE_FAMILY = BlockFamilies.familyBuilder(WamBlocks.PINE_PLANKS)
        .button(WamBlocks.PINE_BUTTON)
        .door(WamBlocks.PINE_DOOR)
        .fence(WamBlocks.PINE_FENCE)
        .fenceGate(WamBlocks.PINE_FENCE_GATE)
        .pressurePlate(WamBlocks.PINE_PRESSURE_PLATE)
        .sign(WamBlocks.PINE_SIGN, WamBlocks.PINE_WALL_SIGN)
        .slab(WamBlocks.PINE_SLAB)
        .stairs(WamBlocks.PINE_STAIRS)
        .trapdoor(WamBlocks.PINE_TRAPDOOR)
        .recipeGroupPrefix("wooden")
        .recipeUnlockedBy("has_planks")
        .getFamily();

    public WamRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    protected RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput exporter) {
        return new RecipeProvider(wrapperLookup, exporter) {
            public void offerShapelessRecipe(RecipeOutput exporter, ItemLike output, ItemLike input, @Nullable String group, int outputCount) {
                shapeless(RecipeCategory.MISC, output, outputCount)
                    .requires(input)
                    .group(group)
                    .unlockedBy(getHasName(input), has(input))
                    .save(exporter, WoodsAndMires.id(getConversionRecipeName(output, input)).toString());
            }

            @Override
            public void buildRecipes() {
                // Wooden
                generateRecipes(PINE_FAMILY, FeatureFlagSet.of(FeatureFlags.VANILLA));
                planksFromLogs(WamBlocks.PINE_PLANKS, WamItemTags.THICK_PINE_LOGS, 4);
                shapeless(RecipeCategory.BUILDING_BLOCKS, WamBlocks.PINE_PLANKS, 2)
                    .requires(WamBlocks.PINE_SHRUB_LOG)
                    .group("planks")
                    .unlockedBy("has_log", has(WamBlocks.PINE_SHRUB_LOG))
                    .save(output, WoodsAndMires.id("pine_planks_from_shrub_log").toString());
                woodFromLogs(WamBlocks.PINE_WOOD, WamBlocks.PINE_LOG);
                woodFromLogs(WamBlocks.AGED_PINE_WOOD, WamBlocks.AGED_PINE_LOG);
                woodFromLogs(WamBlocks.PINE_SNAG_WOOD, WamBlocks.PINE_SNAG_LOG);
                woodFromLogs(WamBlocks.STRIPPED_PINE_WOOD, WamBlocks.STRIPPED_PINE_LOG);
                woodenBoat(WamItems.PINE_BOAT, WamBlocks.PINE_PLANKS);
                chestBoat(WamItems.PINE_CHEST_BOAT, WamItems.PINE_BOAT);
                hangingSign(WamBlocks.PINE_HANGING_SIGN, WamBlocks.STRIPPED_PINE_LOG);

                // Dyes
                offerShapelessRecipe(output, Items.MAGENTA_DYE, WamBlocks.FIREWEED, "magenta_dye", 2);
                offerShapelessRecipe(output, Items.PINK_DYE, WamBlocks.HEATHER, "pink_dye", 1);
                offerShapelessRecipe(output, Items.YELLOW_DYE, WamBlocks.TANSY, "yellow_dye", 1);

                // Other
                shapeless(RecipeCategory.FOOD, WamItems.PINE_CONE_JAM)
                    .requires(Items.GLASS_BOTTLE)
                    .requires(tag(CommonItemTags.PINE_CONES), 2)
                    .requires(CommonItemTags.SUGAR)
                    .unlockedBy(getHasName(WamItems.PINE_CONE), has(WamItems.PINE_CONE))
                    .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "recipegen";
    }
}
