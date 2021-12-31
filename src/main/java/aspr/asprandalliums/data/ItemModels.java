package aspr.asprandalliums.data;

import aspr.asprandalliums.AsprAndAlliums;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Collectors;

public class ItemModels extends ItemModelProvider {

    public static final String GENERATED = "item/generated";

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, AsprAndAlliums.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        builder(itemGenerated);
    }

    private void builder(ModelFile itemGenerated, String name) {
        return getBuilder("aspr").parent(itemGenerated).texture("layer0", name);
    }
}
