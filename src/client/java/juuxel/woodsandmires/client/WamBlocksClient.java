package juuxel.woodsandmires.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.world.biome.FoliageColors;

import static juuxel.woodsandmires.block.WamBlocks.*;

public final class WamBlocksClient {
    public static void init() {
        BlockRenderLayerMap.putBlocks(
            BlockRenderLayer.CUTOUT,
            PINE_DOOR,
            PINE_TRAPDOOR,
            PINE_SAPLING,
            POTTED_PINE_SAPLING,
            FIREWEED,
            TANSY,
            POTTED_TANSY,
            FELL_LICHEN,
            POTTED_FELL_LICHEN,
            HEATHER,
            POTTED_HEATHER,
            PINE_SHRUB_LOG
        );

        ColorProviderRegistry.BLOCK.register(
            (state, world, pos, tintIndex) -> {
                if (world != null && pos != null) {
                    return BiomeColors.getFoliageColor(world, pos);
                }

                return FoliageColors.getColor(0.5, 1.0);
            },
            FIREWEED, TANSY, POTTED_TANSY, PINE_LEAVES, PINE_SHRUB_LOG
        );

        ParticleRenderEvents.ALLOW_BLOCK_DUST_TINT.register((state, world, pos) -> {
            // Prevent tinting shrub log particles.
            // See https://github.com/Juuxel/WoodsAndMires/issues/5
            return !state.isOf(PINE_SHRUB_LOG);
        });
    }
}
