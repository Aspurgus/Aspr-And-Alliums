package aspr.asprandalliums.data;

import aspr.asprandalliums.AsprAndAlliums;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import vectorwing.farmersdelight.data.BlockStates;

@Mod.EventBusSubscriber(modid = AsprAndAlliums.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();



        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModItemModels(gen, existingFileHelper));
        gen.addProvider(new ModBlockStates(gen, existingFileHelper));
        gen.addProvider(new Recipes(gen));
    }

}
