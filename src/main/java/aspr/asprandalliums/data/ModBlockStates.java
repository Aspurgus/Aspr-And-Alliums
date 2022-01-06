package aspr.asprandalliums.data;

import aspr.asprandalliums.AsprAndAlliums;
import aspr.asprandalliums.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.blocks.FeastBlock;

public class ModBlockStates extends BlockStateProvider {

    public ModBlockStates(DataGenerator gen,  ExistingFileHelper exFileHelper) {
        super(gen, AsprAndAlliums.MODID, exFileHelper);
    }

    private String blockName(Block block) {
        return block.getRegistryName().getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(AsprAndAlliums.MODID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(this.resourceBlock(this.blockName(block)), this.models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(this.resourceBlock(path), this.models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        this.feastBlock((FeastBlock)ModBlocks.MEAT.get());
    }

    public void feastBlock(FeastBlock block) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            int servings = (Integer)state.get(FeastBlock.SERVINGS);
            String suffix = "_stage" + (block.getMaxServings() - servings);
            if (servings == 0) {
                suffix = block.hasLeftovers ? "_leftover" : "_stage3";
            }

            return ConfiguredModel.builder().modelFile(this.existingModel(this.blockName(block) + suffix)).rotationY(((int)((Direction)state.get(FeastBlock.FACING)).getHorizontalAngle() + 180) % 360).build();
        });
    }
}
