package aspr.asprandalliums.data;

import aspr.asprandalliums.AsprAndAlliums;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemGenerator extends ItemModelProvider {
    public ModItemGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AsprAndAlliums.MODID, existingFileHelper);
    }



    @Override
    protected  void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        //Items
        builder(itemGenerated, "item/aspr");

    }

    private void builder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", name);
    }
}
