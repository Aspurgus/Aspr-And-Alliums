package aspr.asprandalliums;

import aspr.asprandalliums.registry.ModItems;
import net.minecraft.data.ItemModelProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class AsprItemGroup extends ItemGroup {

    public AsprItemGroup(String label) {
        super(label);
    }

    @Nonnull
    public ItemStack createIcon() {
        return new ItemStack((IItemProvider)ModItems.ASPR.get());
    }

    public ItemStack makeIcon() {
            return  new ItemStack((ModItems.ASPR.get()));
    }

    private void registerBest(NonNullList<ItemStack> items) {
        items.add(new ItemStack((IItemProvider)ModItems.ASPR.get()));
    }

    private void registerDrops(NonNullList<ItemStack> items) {

    }

    private void registerIngredients(NonNullList<ItemStack> items) {

    }

    private void registerMeals(NonNullList<ItemStack> items) {
        items.add(new ItemStack((IItemProvider)ModItems.SIRLOIN_STEAK_AND_SHROOMS.get()));
        items.add(new ItemStack((IItemProvider)ModItems.CHICKEN_AND_BONE_MARROW.get()));
        items.add(new ItemStack((IItemProvider)ModItems.BEEF_BONE_BROTH.get()));
        items.add(new ItemStack((IItemProvider)ModItems.LAMB_NUGGETS.get()));
        items.add(new ItemStack((IItemProvider)ModItems.FISH_BALLS.get()));
        items.add(new ItemStack((IItemProvider)ModItems.SEAFOOD_SALAD.get()));
    }

    private void registerFeasts(NonNullList<ItemStack> items) {
        items.add(new ItemStack((IItemProvider)ModItems.LARGE_PORK_FEAST.get()));
    }

    private void registerFeastItems(NonNullList<ItemStack> items) {
        items.add(new ItemStack((IItemProvider)ModItems.PORK_PLATE.get()));
        items.add(new ItemStack((IItemProvider)ModItems.FRIED_PORK.get()));
        items.add(new ItemStack((IItemProvider)ModItems.RIBS.get()));
    }

    private void registerPies(NonNullList<ItemStack> items) {
        items.add(new ItemStack((IItemProvider)ModItems.RAW_MEATLOAF.get()));
        items.add(new ItemStack((IItemProvider)ModItems.MEATLOAF.get()));
    }

    private void registerPieItems(NonNullList<ItemStack> items) {
        items.add(new ItemStack((IItemProvider)ModItems.RAW_MEATLOAF_SLICE.get()));
        items.add(new ItemStack((IItemProvider)ModItems.MEATLOAF_SLICE.get()));
    }

}
