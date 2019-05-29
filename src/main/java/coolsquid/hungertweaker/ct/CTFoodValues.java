package coolsquid.hungertweaker.ct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coolsquid.hungertweaker.util.Expression;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import squeek.applecore.api.AppleCoreAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

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

	@ZenSetter("hunger")
	@ZenMethod("setHunger")
	public void setHunger(IData v) {
		this.hunger = Expression.parse(v);
	}

	@ZenGetter("hunger")
	public int getHunger() {
		if (this.ingredient instanceof IItemStack) {
			return AppleCoreAPI.accessor.getFoodValues(CraftTweakerMC.getItemStack(this.ingredient)).hunger;
		} else {
			throw new RuntimeException("Can only retrieve the hunger value of individual item stacks");
		}
	}

	@ZenSetter("saturationModifier")
	@ZenMethod("setSaturationModifier")
	public void setSaturationModifier(IData v) {
		this.saturationModifier = Expression.parse(v);
	}

	@ZenGetter("saturationModifier")
	public float getSaturationModifier() {
		if (this.ingredient instanceof IItemStack) {
			return AppleCoreAPI.accessor.getFoodValues(CraftTweakerMC.getItemStack(this.ingredient)).saturationModifier;
		} else {
			throw new RuntimeException("Can only retrieve the saturation value of individual item stacks");
		}
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