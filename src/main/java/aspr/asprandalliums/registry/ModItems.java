package aspr.asprandalliums.registry;

import aspr.asprandalliums.AsprAndAlliums;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AsprAndAlliums.MODID);

    //Items
    public static final RegistryObject<Item> ASPR = ITEMS.register("aspr",
            () -> new Item(new Item.Properties().tab(AsprAndAlliums.ITEM_GROUP)));


}
