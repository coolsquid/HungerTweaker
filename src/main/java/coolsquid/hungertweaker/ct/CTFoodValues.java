package coolsquid.hungertweaker.ct;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coolsquid.hungertweaker.util.Expression;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import squeek.applecore.api.AppleCoreAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.FoodValues")
public class CTFoodValues {

	private static final String FIELD_ALWAYS_EDIBLE = "field_77852_bZ"; // alwaysEdible
	private static final String FIELD_WOLF_FOOD = "field_77856_bY"; // isWolfsFavoriteMeat
	private static final String FIELD_EFFECT_PROBABILITY = "field_77858_cd"; // potionEffectProbability
	private static final String FIELD_EFFECT = "field_77851_ca"; // potionId

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

	@ZenGetter("unmodifiedHunger")
	public int getUnmodifiedHunger() {
		if (this.ingredient instanceof IItemStack) {
			return AppleCoreAPI.accessor.getUnmodifiedFoodValues(CraftTweakerMC.getItemStack(this.ingredient)).hunger;
		} else {
			throw new RuntimeException("Can only retrieve the hunger value of individual item stacks");
		}
	}

	@ZenGetter("unmodifiedSaturationModifier")
	public float getUnmodifiedSaturationModifier() {
		if (this.ingredient instanceof IItemStack) {
			return AppleCoreAPI.accessor
					.getUnmodifiedFoodValues(CraftTweakerMC.getItemStack(this.ingredient)).saturationModifier;
		} else {
			throw new RuntimeException("Can only retrieve the saturation value of individual item stacks");
		}
	}

	@ZenGetter("alwaysEdible")
	public boolean isAlwaysEdible() {
		List<ItemFood> itemFoods = this.getItemFoods();
		if (itemFoods.size() == 1) {
			return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFoods.get(0), FIELD_ALWAYS_EDIBLE);
		} else {
			throw new RuntimeException("Can only retrieve the properties of individual item stacks");
		}
	}

	@ZenSetter("alwaysEdible")
	public void setAlwaysEdible(boolean b) {
		for (ItemFood itemFood : this.getItemFoods()) {
			ObfuscationReflectionHelper.setPrivateValue(ItemFood.class, itemFood, b, FIELD_ALWAYS_EDIBLE);
		}
	}

	@ZenGetter("wolfFood")
	public boolean isWolfFood() {
		List<ItemFood> itemFoods = this.getItemFoods();
		if (itemFoods.size() == 1) {
			return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFoods.get(0), FIELD_WOLF_FOOD);
		} else {
			throw new RuntimeException("Can only retrieve the properties of individual item stacks");
		}
	}

	@ZenSetter("wolfFood")
	public void setWolfFood(boolean b) {
		try {
			Field field = ObfuscationReflectionHelper.findField(ItemFood.class, FIELD_WOLF_FOOD);
			field.setAccessible(true);
			Field modField = Field.class.getDeclaredField("modifiers");
			modField.setAccessible(true);
			modField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			for (ItemFood itemFood : this.getItemFoods()) {
				field.set(itemFood, b);
			}
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@ZenGetter("effect")
	public IPotionEffect getEffect() {
		List<ItemFood> itemFoods = this.getItemFoods();
		if (itemFoods.size() == 1) {
			return CraftTweakerMC.getIPotionEffect(this.getEffectInternal(itemFoods.get(0)));
		} else {
			throw new RuntimeException("Can only retrieve the properties of individual item stacks");
		}
	}

	@ZenSetter("effect")
	public void setEffect(IPotionEffect effect) {
		for (ItemFood itemFood : this.getItemFoods()) {
			float probability =
					this.getEffectInternal(itemFood) == null ? 1 : this.getEffectProbabilityInternal(itemFood);
			itemFood.setPotionEffect(CraftTweakerMC.getPotionEffect(effect), probability);
		}
	}

	@ZenGetter("effectProbability")
	public float getEffectProbability() {
		List<ItemFood> itemFoods = this.getItemFoods();
		if (itemFoods.size() == 1) {
			return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFoods.get(0),
					FIELD_EFFECT_PROBABILITY);
		} else {
			throw new RuntimeException("Can only retrieve the properties of individual item stacks");
		}
	}

	@ZenSetter("effectProbability")
	public void setEffectProbability(float f) {
		for (ItemFood itemFood : this.getItemFoods()) {
			itemFood.setPotionEffect(this.getEffectInternal(itemFood), f);
		}
	}

	private PotionEffect getEffectInternal(ItemFood itemFood) {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFood, FIELD_EFFECT);
	}

	private float getEffectProbabilityInternal(ItemFood itemFood) {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFood, FIELD_EFFECT_PROBABILITY);
	}

	private List<ItemFood> getItemFoods() {
		List<ItemFood> itemFoods = new ArrayList<>();
		List<IItemStack> iItemStacks = this.ingredient.getItems();
		if (this.ingredient instanceof IngredientAny) {
			for (Item item : Item.REGISTRY) {
				if (this.ingredient.matches(CraftTweakerMC.getIItemStack(new ItemStack(item)))) {
					if (item instanceof ItemFood) {
						itemFoods.add((ItemFood) item);
					} else {
						CraftTweakerAPI.logWarning("Item '" + item + "' in ingredient '"
								+ this.ingredient.toCommandString() + "' is not an ItemFood");
					}
				}
			}
		} else if (iItemStacks == null) {
			CraftTweakerAPI.logWarning("Ingredient '" + this.ingredient.toCommandString() + "' is unsupported");
		} else {
			for (IItemStack it : iItemStacks) {
				ItemStack in = CraftTweakerMC.getItemStack(it);
				if (in.getItem() instanceof ItemFood) {
					itemFoods.add((ItemFood) in.getItem());
				} else {
					CraftTweakerAPI.logWarning("Item '" + it.toCommandString() + "' in ingredient '"
							+ this.ingredient.toCommandString() + "' is not an ItemFood");
				}
			}
		}
		return itemFoods;
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