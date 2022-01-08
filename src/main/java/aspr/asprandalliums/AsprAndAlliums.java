package aspr.asprandalliums;

import aspr.asprandalliums.registry.ModBlocks;
import aspr.asprandalliums.registry.ModItems;
import aspr.asprandalliums.setup.ClientEventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AsprAndAlliums.MODID)
public class AsprAndAlliums
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "asprandalliums";
    public static final AsprItemGroup ITEM_GROUP = new AsprItemGroup(AsprAndAlliums.MODID);

    public AsprAndAlliums() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(ClientEventHandler::init);
        ModItems.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

}
