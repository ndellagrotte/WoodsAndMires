package juuxel.woodsandmires.terrablender;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.biome.WamBiomeKeys;
import juuxel.woodsandmires.config.WamConfig;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.data.worldgen.SurfaceRuleData;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public final class WoodsAndMiresTb implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        WamConfig.load();
        Regions.register(new WamRegion(WoodsAndMires.id("biomes"), WamConfig.biomeRegionWeight));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, WoodsAndMires.ID, createSurfaceRule());
    }

    private static SurfaceRules.RuleSource createSurfaceRule() {
        SurfaceRules.ConditionSource hasWater = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.RuleSource stone = SurfaceRuleData.makeStateRule(Blocks.STONE);
        SurfaceRules.RuleSource snowBlock = SurfaceRuleData.makeStateRule(Blocks.SNOW_BLOCK);
        SurfaceRules.RuleSource powderSnow = SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35, 0.6),
            SurfaceRules.ifTrue(hasWater, SurfaceRuleData.makeStateRule(Blocks.POWDER_SNOW))
        );

        return SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(WamBiomeKeys.FELL),
                SurfaceRules.ifTrue(SurfaceRuleData.surfaceNoiseAbove(1.75), stone)
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(WamBiomeKeys.OLD_GROWTH_PINE_FOREST),
                SurfaceRules.ifTrue(
                    SurfaceRuleData.surfaceNoiseAbove(1.75),
                    SurfaceRuleData.makeStateRule(Blocks.COARSE_DIRT)
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(WamBiomeKeys.PINY_GROVE),
                SurfaceRules.sequence(
                    powderSnow,
                    SurfaceRules.ifTrue(hasWater, snowBlock),
                    SurfaceRuleData.makeStateRule(Blocks.DIRT)
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(WamBiomeKeys.SNOWY_PINE_FOREST),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.steep(), stone),
                    powderSnow,
                    SurfaceRules.ifTrue(hasWater, snowBlock)
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(WamBiomeKeys.SNOWY_FELL),
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRuleData.surfaceNoiseAbove(1.75), stone),
                    SurfaceRules.ifTrue(SurfaceRules.steep(), stone),
                    SurfaceRules.ifTrue(hasWater, snowBlock)
                )
            )
        ));
    }
}
