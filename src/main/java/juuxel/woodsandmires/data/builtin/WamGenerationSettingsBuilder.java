package juuxel.woodsandmires.data.builtin;

import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.util.*;

public class WamGenerationSettingsBuilder extends BiomeGenerationSettings.Builder {
    // step -> before -> [after]
    private final Map<GenerationStep.Decoration, Map<Holder<PlacedFeature>, Set<Holder<PlacedFeature>>>> orderingsByStep =
        new EnumMap<>(GenerationStep.Decoration.class);
    private final HolderGetter<PlacedFeature> placedFeatureLookup;

    public WamGenerationSettingsBuilder(HolderGetter<PlacedFeature> placedFeatureLookup, HolderGetter<ConfiguredWorldCarver<?>> configuredCarverLookup) {
        super(placedFeatureLookup, configuredCarverLookup);
        this.placedFeatureLookup = placedFeatureLookup;
    }

    public WamGenerationSettingsBuilder addOrdering(GenerationStep.Decoration step, ResourceKey<PlacedFeature> before, ResourceKey<PlacedFeature> after) {
        orderingsByStep.computeIfAbsent(step, s -> new HashMap<>())
            .computeIfAbsent(placedFeatureLookup.getOrThrow(before), entry -> new HashSet<>())
            .add(placedFeatureLookup.getOrThrow(after));
        return this;
    }

    @Deprecated
    @Override
    public BiomeGenerationSettings build() {
        throw new UnsupportedOperationException("Use build(DirectedAcyclicGraph) instead");
    }

    public BiomeGenerationSettings build(DirectedAcyclicGraph<Holder<PlacedFeature>, DefaultEdge> globalGraph) {
        order(globalGraph);
        return super.build();
    }

    private void order(DirectedAcyclicGraph<Holder<PlacedFeature>, DefaultEdge> globalGraph) {
        var steps = GenerationStep.Decoration.values();
        for (int i = 0; i < this.features.size(); i++) {
            var features = this.features.get(i);
            var localGraph = new DirectedAcyclicGraph<Holder<PlacedFeature>, DefaultEdge>(DefaultEdge.class);
            Graphs.addGraph(localGraph, globalGraph);

            for (var feature : features) {
                localGraph.addVertex(feature);
            }

            var step = steps[i];
            orderingsByStep.getOrDefault(step, Map.of()).forEach((before, allAfter) -> {
                for (var after : allAfter) {
                    localGraph.addEdge(before, after);
                }
            });

            var orderProvider = new ArrayList<Holder<PlacedFeature>>(localGraph.vertexSet().size());
            for (var feature : localGraph) {
                orderProvider.add(feature);
            }

            features.sort(Comparator.comparingInt(orderProvider::indexOf));
        }
    }
}
