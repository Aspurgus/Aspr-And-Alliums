package aspr.asprandalliums.items;

import net.minecraft.item.Food.Builder;
import net.minecraft.item.Food;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import vectorwing.farmersdelight.registry.ModEffects;

public class Foods {

    //Meals
    public static final Food SIRLION_STEAK_AND_SHROOMS = (new Builder().hunger(12).saturation(0.7f).effect(() -> {
        return new EffectInstance((Effect) ModEffects.NOURISHED.get(), 3600, 0);
    }, 1.0f).build());

    public static final Food CHICKEN_AND_BONE_MARROW = (new Builder().hunger(10).saturation(0.7f).effect(() -> {
        return new EffectInstance((Effect) ModEffects.COMFORT.get(), 3600, 0);
    }, 1.0f).build());

    public static final Food BEEF_BONE_BROTH = (new Builder().hunger(10).saturation(0.7f).effect(() -> {
        return new EffectInstance((Effect) ModEffects.COMFORT.get(), 3600, 0);
    }, 1.0f).build());

    public static final Food LAMB_NUGGETS = (new Builder().hunger(6).saturation(0.4f).effect(() -> {
        return new EffectInstance((Effect) ModEffects.NOURISHED.get(), 3600, 0);
    }, 1.0f).fastToEat().build());

    public static final Food SEAFOOD_SALAD = (new Builder().hunger(12).saturation(0.7f).effect(() -> {
        return new EffectInstance((Effect) ModEffects.NOURISHED.get(), 3600, 0);
    }, 1.0f).effect(() -> {
        return new EffectInstance((Effect) Effects.CONDUIT_POWER , 1200, 0);
    }, 1.0f).build());

    //Foods
    public static final Food FISH_BALLS = (new Builder().hunger(8).saturation(0.4f).build());


}
