package aspr.asprandalliums.data;

import aspr.asprandalliums.registry.ModItems;
import com.umpaz.nethers_delight.core.registry.NDItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.recipesOriginsMeatMeals(consumer);
        PotRecipes.register(consumer);
    }

    private void recipesOriginsMeatMeals(Consumer<IFinishedRecipe> consumer) {


    }




}
