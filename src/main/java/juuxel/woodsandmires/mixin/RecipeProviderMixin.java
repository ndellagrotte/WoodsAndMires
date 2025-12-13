package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.data.builtin.CommonItemTags;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RecipeProvider.class)
abstract class RecipeProviderMixin {
    @Redirect(
        method = {"fenceBuilder", "fenceGateBuilder", "signBuilder"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/ShapedRecipeBuilder;define(Ljava/lang/Character;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/data/recipes/ShapedRecipeBuilder;")
    )
    private static ShapedRecipeBuilder replaceStick(ShapedRecipeBuilder builder, Character c, ItemLike item) {
        if (item.asItem() == Items.STICK) {
            return builder.define(c, CommonItemTags.WOODEN_RODS);
        }
        return builder.define(c, item);
    }

    @Redirect(
        method = "chestBoat",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/ShapelessRecipeBuilder;requires(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/data/recipes/ShapelessRecipeBuilder;")
    )
    private static ShapelessRecipeBuilder replaceChest(ShapelessRecipeBuilder builder, ItemLike input) {
        if (input.asItem() == Items.CHEST) {
            return builder.requires(CommonItemTags.WOODEN_CHESTS);
        }
        return builder.requires(input);
    }

    @Redirect(
        method = "hangingSign",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/data/recipes/ShapedRecipeBuilder;define(Ljava/lang/Character;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/data/recipes/ShapedRecipeBuilder;")
    )
    private static ShapedRecipeBuilder replaceChain(ShapedRecipeBuilder builder, Character c, ItemLike item) {
        if (item.asItem() == Items.IRON_CHAIN) {
            return builder.define(c, CommonItemTags.CHAINS);
        }
        return builder.define(c, item);
    }
}
