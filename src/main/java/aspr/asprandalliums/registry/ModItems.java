package aspr.asprandalliums.registry;

import aspr.asprandalliums.AsprAndAlliums;
import aspr.asprandalliums.AsprItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Properties;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AsprAndAlliums.MODID);

    //Items
    public static final RegistryObject<Item> ASPR = ITEMS.register("aspr",
            () -> new Item(new Item.Properties().containerItem(Items.CARROT).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> MEAT_FEAST = ITEMS.register("meat_feast",
            () -> new BlockItem((Block)ModBlocks.MEAT.get(), new Item.Properties().maxStackSize(1).group(AsprAndAlliums.ITEM_GROUP)));


}
