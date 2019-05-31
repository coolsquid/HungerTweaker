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

	private final ItemFood itemFood;

	public CTFoodItemProperties(ItemFood itemFood) {
		this.itemFood = itemFood;
	}

	@ZenGetter("alwaysEdible")
	public boolean isAlwaysEdible() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFood, "field_77852_bZ");
	}

	@ZenSetter("alwaysEdible")
	public void setAlwaysEdible(boolean b) {
		ObfuscationReflectionHelper.setPrivateValue(ItemFood.class, itemFood, b, "field_77852_bZ");
	}

	@ZenGetter("wolfFood")
	public boolean isWolfFood() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFood, "field_77856_bY");
	}

	@ZenSetter("wolfFood")
	public void setWolfFood(boolean b) {
		try {
			Field field = ObfuscationReflectionHelper.findField(ItemFood.class, "field_77856_bY");
			field.setAccessible(true);
			Field modField = Field.class.getDeclaredField("modifiers");
			modField.setAccessible(true);
			modField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(itemFood, b);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@ZenGetter("effect")
	public IPotionEffect getEffect() {
		return CraftTweakerMC.getIPotionEffect(getEffectInternal());
	}

	@ZenSetter("effect")
	public void setEffect(IPotionEffect effect) {
		float probability = getEffectInternal() == null ? 1 : getEffectProbability();
		itemFood.setPotionEffect(CraftTweakerMC.getPotionEffect(effect), probability);
	}

	@ZenGetter("effectProbability")
	public float getEffectProbability() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFood, "potionEffectProbability");
	}

	@ZenSetter("effectProbability")
	public void getEffectProbability(float f) {
		itemFood.setPotionEffect(getEffectInternal(), f);
	}

	private PotionEffect getEffectInternal() {
		return ObfuscationReflectionHelper.getPrivateValue(ItemFood.class, itemFood, "potionId");
	}
}