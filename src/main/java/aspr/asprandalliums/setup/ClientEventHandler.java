package aspr.asprandalliums.setup;

import aspr.asprandalliums.AsprAndAlliums;
import aspr.asprandalliums.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = AsprAndAlliums.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)

public class ClientEventHandler {

    public ClientEventHandler() {
    }

    public static void init(FMLClientSetupEvent event) {

        RenderTypeLookup.setRenderLayer((Block) ModBlocks.LARGE_PORK_FEAST.get(), RenderType.getCutout());

    }

}
