package juuxel.woodsandmires.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import juuxel.woodsandmires.WoodsAndMires;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class WoodsAndMiresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WamBlocksClient.init();

        TerraformBoatClientHelper.registerModelLayers(Identifier.of(WoodsAndMires.ID, "pine"));
    }
}
