package aspr.asprandalliums.data;

import aspr.asprandalliums.registry.ModItems;
import com.google.common.collect.Lists;
import com.umpaz.nethers_delight.core.registry.NDItems;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.registry.ModEffects;
import vectorwing.farmersdelight.utils.tags.ForgeTags;

import java.util.List;
import java.util.function.Consumer;

public class PotRecipes {

    public static final int FAST_COOKING = 100;
    public static final int NORMAL_COOKING = 200;
    public static final int SLOW_COOKING = 400;

    public static void register(Consumer<IFinishedRecipe> consumer) {
        originsMeatMeals(consumer);
        originsMeatFeasts(consumer);

    }

    private static void originsMeatMeals(Consumer<IFinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe((IItemProvider) ModItems.SIRLOIN_STEAK_AND_SHROOMS.get(), 1, NORMAL_COOKING, 0.4F).addIngredient(Items.WARPED_FUNGUS).addIngredient(NDItems.HOGLIN_SIRLOIN.get()).addIngredient(Items.CRIMSON_FUNGUS).build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe((IItemProvider) ModItems.CHICKEN_AND_BONE_MARROW.get(), 1, FAST_COOKING, 0.35F).addIngredient(ForgeTags.RAW_CHICKEN).addIngredient(ForgeTags.RAW_CHICKEN).addIngredient(Items.BONE).build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe((IItemProvider) ModItems.BEEF_BONE_BROTH.get(), 1, NORMAL_COOKING, 0.35F).addIngredient(Items.BEEF).addIngredient(ForgeTags.RAW_BEEF).addIngredient(Items.BONE).addIngredient(Items.BONE).build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe((IItemProvider) ModItems.LAMB_NUGGETS.get(), 2, FAST_COOKING, 0.35F).addIngredient(vectorwing.farmersdelight.registry.ModItems.MUTTON_CHOPS.get()).addIngredient(vectorwing.farmersdelight.registry.ModItems.MUTTON_CHOPS.get()).addIngredient(Items.EGG).addIngredient(vectorwing.farmersdelight.registry.ModItems.WHEAT_DOUGH.get()).build(consumer);
    }

    private static void originsMeatFeasts(Consumer<IFinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe((IItemProvider) ModItems.LARGE_PORK_FEAST.get(), 1, SLOW_COOKING, 0.6F).addIngredient(vectorwing.farmersdelight.registry.ModItems.SMOKED_HAM.get()).addIngredient(vectorwing.farmersdelight.registry.ModItems.SMOKED_HAM.get()).addIngredient(Items.COOKED_PORKCHOP).addIngredient(vectorwing.farmersdelight.registry.ModItems.BACON.get()).addIngredient(vectorwing.farmersdelight.registry.ModItems.HAM.get()).addIngredient(vectorwing.farmersdelight.registry.ModItems.COOKED_BACON.get()).build(consumer);
    }

}
