package coolsquid.hungertweaker.ct;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.FoodItemProperties")
public class CTFoodItemProperties {

	private static final String FIELD_ALWAYS_EDIBLE = "field_77852_bZ"; // alwaysEdible
	private static final String FIELD_WOLF_FOOD = "field_77856_bY"; // isWolfsFavoriteMeat
	private static final String FIELD_EFFECT_PROBABILITY = "field_77858_cd"; // potionEffectProbability
	private static final String FIELD_EFFECT = "field_77851_ca"; // potionId

	private final ItemFood itemFood;

	public CTFoodItemProperties(ItemFood itemFood) {
		this.itemFood = itemFood;
	}

	@ZenGetter("alwaysEdible")
	public boolean isAlwaysEdible() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, this.itemFood, FIELD_ALWAYS_EDIBLE);
	}

	@ZenSetter("alwaysEdible")
	public void setAlwaysEdible(boolean b) {
		ObfuscationReflectionHelper.setPrivateValue(ItemFood.class, this.itemFood, b, FIELD_ALWAYS_EDIBLE);
	}

	@ZenGetter("wolfFood")
	public boolean isWolfFood() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, this.itemFood, FIELD_WOLF_FOOD);
	}

	@ZenSetter("wolfFood")
	public void setWolfFood(boolean b) {
		try {
			Field field = ObfuscationReflectionHelper.findField(ItemFood.class, FIELD_WOLF_FOOD);
			field.setAccessible(true);
			Field modField = Field.class.getDeclaredField("modifiers");
			modField.setAccessible(true);
			modField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(this.itemFood, b);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@ZenGetter("effect")
	public IPotionEffect getEffect() {
		return CraftTweakerMC.getIPotionEffect(this.getEffectInternal());
	}

	@ZenSetter("effect")
	public void setEffect(IPotionEffect effect) {
		float probability = this.getEffectInternal() == null ? 1 : this.getEffectProbability();
		this.itemFood.setPotionEffect(CraftTweakerMC.getPotionEffect(effect), probability);
	}

	@ZenGetter("effectProbability")
	public float getEffectProbability() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, this.itemFood, FIELD_EFFECT_PROBABILITY);
	}

	@ZenSetter("effectProbability")
	public void getEffectProbability(float f) {
		this.itemFood.setPotionEffect(this.getEffectInternal(), f);
	}

	private PotionEffect getEffectInternal() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, this.itemFood, FIELD_EFFECT);
	}
}