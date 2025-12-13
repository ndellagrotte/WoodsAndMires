package juuxel.woodsandmires.block;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.feature.WamConfiguredFeatureKeys;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class WamBlocks {
    public static final Block PINE_LOG = register("pine_log", copyWoodSettings(Blocks.OAK_LOG), RotatedPillarBlock::new);
    public static final Block AGED_PINE_LOG = register("aged_pine_log", BlockBehaviour.Properties.ofFullCopy(PINE_LOG), s -> new AgedLogBlock(PINE_LOG, s));
    public static final Block PINE_PLANKS = register("pine_planks", copyWoodSettings(Blocks.OAK_PLANKS), Block::new);
    public static final Block PINE_SLAB = register("pine_slab", copyWoodSettings(Blocks.OAK_SLAB), SlabBlock::new);
    public static final Block PINE_STAIRS = register("pine_stairs", copyWoodSettings(Blocks.OAK_STAIRS), settings -> new StairBlock(PINE_PLANKS.defaultBlockState(), settings));
    public static final Block PINE_FENCE = register("pine_fence", copyWoodSettings(Blocks.OAK_FENCE), FenceBlock::new);
    public static final Block PINE_FENCE_GATE = register("pine_fence_gate", copyWoodSettings(Blocks.OAK_FENCE_GATE), settings -> new FenceGateBlock(WamWoodTypes.PINE, settings));
    public static final Block PINE_DOOR = register("pine_door", copyWoodSettings(Blocks.OAK_DOOR), settings -> new DoorBlock(WamBlockSetTypes.PINE, settings));
    public static final Block PINE_TRAPDOOR = register("pine_trapdoor", copyWoodSettings(Blocks.OAK_TRAPDOOR), settings -> new TrapDoorBlock(WamBlockSetTypes.PINE, settings));
    public static final Block PINE_BUTTON = register("pine_button", copyWoodSettings(Blocks.OAK_BUTTON), settings -> new ButtonBlock(WamBlockSetTypes.PINE, 30, settings));
    public static final Block PINE_PRESSURE_PLATE = register("pine_pressure_plate", copyWoodSettings(Blocks.OAK_PRESSURE_PLATE), settings -> new PressurePlateBlock(WamBlockSetTypes.PINE, settings));
    public static final Block PINE_SIGN = register("pine_sign", copyWoodSettings(Blocks.OAK_SIGN), settings -> new StandingSignBlock(WamWoodTypes.PINE, settings), null);
    public static final Block PINE_WALL_SIGN = register("pine_wall_sign", copyWoodSettings(Blocks.OAK_WALL_SIGN), settings -> new WallSignBlock(WamWoodTypes.PINE, settings), null);
    public static final Block PINE_HANGING_SIGN = register("pine_hanging_sign", copyWoodSettings(Blocks.OAK_HANGING_SIGN), settings -> new CeilingHangingSignBlock(WamWoodTypes.PINE, settings), null);
    public static final Block PINE_WALL_HANGING_SIGN = register("pine_wall_hanging_sign", copyWoodSettings(Blocks.OAK_WALL_HANGING_SIGN), settings -> new WallHangingSignBlock(WamWoodTypes.PINE, settings), null);
    // Supplier for same reason as above
    public static final Block PINE_LEAVES = register("pine_leaves", Blocks.leavesProperties(SoundType.GRASS), s -> new TintedParticleLeavesBlock(0.01f, s));
    public static final Block PINE_SAPLING = register("pine_sapling", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING), settings -> new SaplingBlock(new TreeGrower("pine", Optional.empty(), Optional.of(WamConfiguredFeatureKeys.PINE_FROM_SAPLING), Optional.empty()), settings));
    public static final Block POTTED_PINE_SAPLING = register("potted_pine_sapling", Blocks.flowerPotProperties(), settings -> new FlowerPotBlock(PINE_SAPLING, settings), null);
    public static final Block PINE_WOOD = register("pine_wood", copyWoodSettings(Blocks.OAK_WOOD), RotatedPillarBlock::new);
    public static final Block AGED_PINE_WOOD = register("aged_pine_wood", BlockBehaviour.Properties.ofFullCopy(PINE_WOOD)
        .overrideDescription(PINE_WOOD.getDescriptionId()), RotatedPillarBlock::new);
    public static final Block STRIPPED_PINE_LOG = register("stripped_pine_log", copyWoodSettings(Blocks.STRIPPED_OAK_LOG), RotatedPillarBlock::new);
    public static final Block STRIPPED_PINE_WOOD = register("stripped_pine_wood", copyWoodSettings(Blocks.STRIPPED_OAK_WOOD), RotatedPillarBlock::new);
    public static final Block PINE_SNAG_LOG = register("pine_snag_log", copyWoodSettings(Blocks.STRIPPED_OAK_LOG), RotatedPillarBlock::new);
    public static final Block PINE_SNAG_WOOD = register("pine_snag_wood", copyWoodSettings(Blocks.STRIPPED_OAK_WOOD), RotatedPillarBlock::new);;
    public static final Block PINE_SNAG_BRANCH = register("pine_snag_branch", copyWoodSettings(PINE_SNAG_WOOD), BranchBlock::new, null);
    public static final Block PINE_SHRUB_LOG = register("pine_shrub_log", copyWoodSettings(PINE_SNAG_WOOD).noOcclusion(), settings -> new ShrubLogBlock(settings, PINE_LEAVES));
    public static final Block FIREWEED = register("fireweed", createFlowerSettings(), TallFlowerBlock::new, DoubleHighBlockItem::new);
    public static final Block TANSY = register("tansy", createFlowerSettings(), settings -> new BigFlowerBlock(MobEffects.SLOW_FALLING, 10, settings));
    public static final Block POTTED_TANSY = register("potted_tansy", Blocks.flowerPotProperties(), settings -> new FlowerPotBlock(TANSY, settings), null);
    public static final Block FELL_LICHEN = register("fell_lichen", createFlowerSettings().mapColor(MapColor.QUARTZ).offsetType(BlockBehaviour.OffsetType.XZ), LichenBlock::new);
    public static final Block POTTED_FELL_LICHEN = register("potted_fell_lichen", Blocks.flowerPotProperties(), settings -> new FlowerPotBlock(FELL_LICHEN, settings), null);
    public static final Block HEATHER = register("heather", createFlowerSettings(), settings -> new HeatherBlock(MobEffects.REGENERATION, 8, settings));
    public static final Block POTTED_HEATHER = register("potted_heather", Blocks.flowerPotProperties(), settings -> new FlowerPotBlock(HEATHER, settings), null);

    private WamBlocks() {
    }

    public static void init() {
        FlammableBlockRegistry fbr = FlammableBlockRegistry.getDefaultInstance();
        fbr.add(PINE_LOG, 5, 5);
        fbr.add(AGED_PINE_LOG, 5, 5);
        fbr.add(PINE_WOOD, 5, 5);
        fbr.add(AGED_PINE_WOOD, 5, 5);
        fbr.add(STRIPPED_PINE_LOG, 5, 5);
        fbr.add(STRIPPED_PINE_WOOD, 5, 5);
        fbr.add(PINE_SNAG_LOG, 5, 5);
        fbr.add(PINE_SNAG_WOOD, 5, 5);
        fbr.add(PINE_LEAVES, 5, 20);

        StrippableBlockRegistry.register(PINE_LOG, STRIPPED_PINE_LOG);
        StrippableBlockRegistry.register(AGED_PINE_LOG, STRIPPED_PINE_LOG);
        StrippableBlockRegistry.register(PINE_WOOD, STRIPPED_PINE_WOOD);
        StrippableBlockRegistry.register(AGED_PINE_WOOD, STRIPPED_PINE_WOOD);
        BlockEntityType.SIGN.addSupportedBlock(PINE_SIGN);
        BlockEntityType.SIGN.addSupportedBlock(PINE_WALL_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(PINE_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(PINE_WALL_HANGING_SIGN);
    }

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> block) {
        return register(id, block, BlockItem::new);
    }

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> block, @Nullable BiFunction<Block, Item.Properties, Item> item) {
        return register(id, BlockBehaviour.Properties.of(), block, item);
    }

    private static Block register(String id, BlockBehaviour.Properties settings, Function<BlockBehaviour.Properties, Block> block) {
        return register(id, settings, block, BlockItem::new);
    }

    private static Block register(String id, BlockBehaviour.Properties settings, Function<BlockBehaviour.Properties, Block> block, @Nullable BiFunction<Block, Item.Properties, Item> item) {
        var val = block.apply(settings.setId(ResourceKey.create(Registries.BLOCK, WoodsAndMires.id(id))));
        Registry.register(BuiltInRegistries.BLOCK, WoodsAndMires.id(id), val);

        if (item != null) {
            Registry.register(BuiltInRegistries.ITEM, WoodsAndMires.id(id), item.apply(val, new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, WoodsAndMires.id(id)))
                .overrideDescription(val.getDescriptionId())
            ));
        }

        return val;
    }

    private static BlockBehaviour.Properties copyWoodSettings(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }

    private static BlockBehaviour.Properties createFlowerSettings() {
        return BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.GRASS);
    }
}
