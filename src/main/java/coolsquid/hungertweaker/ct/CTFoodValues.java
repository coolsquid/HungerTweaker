package coolsquid.hungertweaker.ct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coolsquid.hungertweaker.util.Expression;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.FoodValues")
public class CTFoodValues {

	public static final List<CTFoodValues> LIST = new ArrayList<>();
	private static final Map<IIngredient, CTFoodValues> MAP = new HashMap<>();

	public final IIngredient ingredient;
	public Expression hunger;
	public Expression saturationModifier;

	private CTFoodValues(IIngredient ingredient) {
		this.ingredient = ingredient;
	}

	@ZenMethod
	public void setHunger(IData v) {
		this.hunger = Expression.parse(v);
	}

	@ZenMethod
	public void setSaturationModifier(IData v) {
		this.saturationModifier = Expression.parse(v);
	}

	static CTFoodValues get(IIngredient i) {
		CTFoodValues in = MAP.get(i);
		if (in == null) {
			in = new CTFoodValues(i);
			MAP.put(i, in);
			LIST.add(in);
		}
		return in;
	}
}