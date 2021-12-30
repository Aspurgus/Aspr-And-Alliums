package aspr.asprandalliums;

import aspr.asprandalliums.registry.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class AsprItemGroup extends ItemGroup {

    public AsprItemGroup(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
            return  new ItemStack((ModItems.ASPR.get()));
    }

    private void registerItems(NonNullList<ItemStack> items) {
        items.add(new ItemStack(ModItems.ASPR.get()));
    }



}
