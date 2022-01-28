package aspr.asprandalliums.registry;

import aspr.asprandalliums.AsprAndAlliums;
import aspr.asprandalliums.AsprItemGroup;
import aspr.asprandalliums.items.Foods;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.items.ConsumableItem;

import java.util.Properties;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AsprAndAlliums.MODID);

    //Icon Item
    public static final RegistryObject<Item> ASPR = ITEMS.register("aspr",
            () -> new Item(new Item.Properties()));

    //Meal Items
    public static final RegistryObject<Item> SIRLOIN_STEAK_AND_SHROOMS = ITEMS.register("sirloin_steak_and_shrooms",
            () -> new ConsumableItem(new Item.Properties().food(Foods.SIRLION_STEAK_AND_SHROOMS).containerItem(Items.BOWL).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> FISH_BALLS = ITEMS.register("fish_balls",
            () -> new Item(new Item.Properties().food(Foods.FISH_BALLS).containerItem(Items.STICK).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> CHICKEN_AND_BONE_MARROW = ITEMS.register("chicken_and_bone_marrow",
            () -> new ConsumableItem(new Item.Properties().food(Foods.CHICKEN_AND_BONE_MARROW).containerItem(Items.BONE_MEAL).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> BEEF_BONE_BROTH = ITEMS.register("beef_bone_broth",
            () -> new ConsumableItem(new Item.Properties().food(Foods.BEEF_BONE_BROTH).containerItem(Items.BOWL).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> LAMB_NUGGETS = ITEMS.register("lamb_nuggets",
            () -> new ConsumableItem(new Item.Properties().food(Foods.LAMB_NUGGETS).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> SEAFOOD_SALAD = ITEMS.register("seafood_salad",
            () -> new ConsumableItem(new Item.Properties().food(Foods.SEAFOOD_SALAD).containerItem(Items.BOWL).group(AsprAndAlliums.ITEM_GROUP)));

    //Feasts and Items

    public static final RegistryObject<Item> LARGE_PORK_FEAST = ITEMS.register("large_pork_feast",
            () -> new BlockItem((Block)ModBlocks.LARGE_PORK_FEAST.get(), (new Item.Properties()).maxStackSize(1).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> PORK_PLATE = ITEMS.register("pork_plate",
            () -> new ConsumableItem(new Item.Properties().food(Foods.PORK_PLATE).containerItem(Items.BOWL).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> FRIED_PORK = ITEMS.register("fried_pork",
            () -> new Item(new Item.Properties().food(Foods.FRIED_PORK).group(AsprAndAlliums.ITEM_GROUP)));

    public static final RegistryObject<Item> RIBS = ITEMS.register("ribs",
            () -> new Item(new Item.Properties().food(Foods.RIBS).group(AsprAndAlliums.ITEM_GROUP)));

    //Pies and Items
    public static final RegistryObject<Item> RAW_MEATLOAF = ITEMS.register("raw_meatloaf",
            () -> new BlockItem((Block)ModBlocks.RAW_MEATLOAF.get(), (new Item.Properties()).maxStackSize(1).group(AsprAndAlliums.ITEM_GROUP)));
    public static final RegistryObject<Item> RAW_MEATLOAF_SLICE = ITEMS.register("raw_meatloaf_slice",
            () -> new Item(new Item.Properties().food(Foods.RAW_MEATLOAF_SLICE).group(AsprAndAlliums.ITEM_GROUP)));
    public static final RegistryObject<Item> MEATLOAF = ITEMS.register("meatloaf",
            () -> new BlockItem((Block)ModBlocks.MEATLOAF.get(), (new Item.Properties()).maxStackSize(1).group(AsprAndAlliums.ITEM_GROUP)));
    public static final RegistryObject<Item> MEATLOAF_SLICE = ITEMS.register("meatloaf_slice",
            () -> new Item(new Item.Properties().food(Foods.MEATLOAF_SLICE).group(AsprAndAlliums.ITEM_GROUP)));
}
