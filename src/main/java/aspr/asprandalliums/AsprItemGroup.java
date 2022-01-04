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


    public ItemStack createIcon() {
        return new ItemStack((IItemProvider)ModItems.ASPR.get());
    }

    @Nonnull

    public ItemStack makeIcon() {
            return  new ItemStack((ModItems.ASPR.get()));
    }

    private void registerItems(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.ASPR.get()));
    }



}
