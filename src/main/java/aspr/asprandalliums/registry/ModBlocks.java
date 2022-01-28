package aspr.asprandalliums.registry;

import aspr.asprandalliums.AsprAndAlliums;
import aspr.asprandalliums.blocks.LargePorkFeast;
import net.minecraft.block.AbstractBlock.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.blocks.PieBlock;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AsprAndAlliums.MODID);

    //Feasts
    public static final RegistryObject<Block> LARGE_PORK_FEAST = BLOCKS.register("large_pork_feast",
    () -> new LargePorkFeast(Properties.from(Blocks.CAKE).setLightLevel((state) -> {
        return 5;
    }), ModItems.RIBS, ModItems.FRIED_PORK, ModItems.PORK_PLATE));

    //Pies
    public static final RegistryObject<Block> RAW_MEATLOAF = BLOCKS.register("raw_meatloaf",
            () -> new PieBlock(Properties.from(Blocks.CAKE), ModItems.RAW_MEATLOAF_SLICE));
    public static final RegistryObject<Block> MEATLOAF = BLOCKS.register("meatloaf",
            () -> new PieBlock(Properties.from(Blocks.CAKE), ModItems.MEATLOAF_SLICE));

}
