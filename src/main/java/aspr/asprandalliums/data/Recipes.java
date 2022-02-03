package aspr.asprandalliums.data;

import aspr.asprandalliums.registry.ModItems;
import com.umpaz.nethers_delight.core.registry.NDItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.recipesOriginsMeatCooking(consumer);
        PotRecipes.register(consumer);
    }

    private void recipesOriginsMeatCooking(Consumer<IFinishedRecipe> consumer) {
        foodSmeltingRecipes("meatloaf", ModItems.RAW_MEATLOAF.get(), ModItems.MEATLOAF.get(), 0.6F, consumer);
    }

    private static void foodSmeltingRecipes(String name, IItemProvider ingredient, IItemProvider result, float experience, Consumer<IFinishedRecipe> consumer) {
        String namePrefix = (new ResourceLocation("asprandalliums", name)).toString();
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(new IItemProvider[]{ingredient}), result, experience, 200).addCriterion(name, InventoryChangeTrigger.Instance.forItems(new IItemProvider[]{ingredient})).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(new IItemProvider[]{ingredient}), result, experience, 600, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion(name, InventoryChangeTrigger.Instance.forItems(new IItemProvider[]{ingredient})).build(consumer, namePrefix + "_from_campfire_cooking");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(new IItemProvider[]{ingredient}), result, experience, 100, IRecipeSerializer.SMOKING).addCriterion(name, InventoryChangeTrigger.Instance.forItems(new IItemProvider[]{ingredient})).build(consumer, namePrefix + "_from_smoking");
    }

}
