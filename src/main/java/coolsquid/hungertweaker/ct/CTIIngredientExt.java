package coolsquid.hungertweaker.ct;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenExpansion("crafttweaker.item.IIngredient")
public class CTIIngredientExt {

	@ZenGetter("foodValues")
	public static CTFoodValues getFoodValues(IIngredient ingredient) {
		return CTFoodValues.get(ingredient);
	}
}