package aspr.asprandalliums.data;

import aspr.asprandalliums.AsprAndAlliums;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AsprAndAlliums.MODID, existingFileHelper);
    }



    @Override
    protected  void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        //Items
        builder(itemGenerated, "item/aspr");
        builder(itemGenerated, "item/sirloin_steak_and_shrooms");
        builder(itemGenerated, "item/fish_balls");
        builder(itemGenerated, "item/chicken_and_bone_marrow");
        builder(itemGenerated, "item/beef_bone_broth");
        builder(itemGenerated, "item/lamb_nuggets");
        builder(itemGenerated, "item/seafood_salad");

        //Feast Items
        builder(itemGenerated, "item/large_pork_feast");
        builder(itemGenerated, "item/pork_plate");
        builder(itemGenerated, "item/fried_pork");
        builder(itemGenerated, "item/ribs");

        //Pie Items
        builder(itemGenerated, "item/raw_meatloaf");
        builder(itemGenerated, "item/raw_meatloaf_slice");
        builder(itemGenerated, "item/meatloaf");
        builder(itemGenerated, "item/meatloaf_slice");




    }

    private void builder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", name);
    }
}
