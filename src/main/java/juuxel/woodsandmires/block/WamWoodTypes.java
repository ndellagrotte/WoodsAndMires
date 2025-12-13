package juuxel.woodsandmires.block;

import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class WamWoodTypes {
    public static final WoodType PINE = register("pine", WamBlockSetTypes.PINE);

    public static void init() {
    }

    private static WoodType register(String id, BlockSetType blockSetType) {
        return WoodTypeBuilder.copyOf(WoodType.SPRUCE).register(WoodsAndMires.id(id), blockSetType);
    }
}
