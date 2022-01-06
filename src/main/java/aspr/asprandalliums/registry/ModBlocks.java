package aspr.asprandalliums.registry;

import aspr.asprandalliums.AsprAndAlliums;
import aspr.asprandalliums.blocks.LargePorkFeast;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AsprAndAlliums.MODID);

    //Feasts
//    public static final RegistryObject<Block> MEAT = BLOCKS.register("meat_feast",
//    () -> new LargePorkFeast(AbstractBlock.Properties.create(Material.BAMBOO, MaterialColor.WHITE_TERRACOTTA).
//            hardnessAndResistance(1.0f, 1.0f).sound(SoundType.SLIME), ModItems.ASPR, true));


}
