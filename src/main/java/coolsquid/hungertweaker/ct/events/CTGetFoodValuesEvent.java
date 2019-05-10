package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.food.FoodEvent.GetPlayerFoodValues;
import squeek.applecore.api.food.FoodValues;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.GetFoodValuesEvent")
public class CTGetFoodValuesEvent implements IPlayerEvent {

	private final GetPlayerFoodValues internal;

	public CTGetFoodValuesEvent(GetPlayerFoodValues internal) {
		this.internal = internal;
	}

	@ZenGetter("hunger")
	public int getHunger() {
		return internal.foodValues.hunger;
	}

	@ZenSetter("hunger")
	public void setHunger(int hunger) {
		this.internal.foodValues = new FoodValues(hunger, getSaturationModifier());
	}

	@ZenGetter("saturationModifier")
	public float getSaturationModifier() {
		return internal.foodValues.saturationModifier;
	}

	@ZenSetter("saturationModifier")
	public void setSaturationModifier(float saturationModifier) {
		this.internal.foodValues = new FoodValues(getHunger(), saturationModifier);
	}

	@ZenGetter("unmodifiedHunger")
	public int getUnmodifiedHunger() {
		return internal.unmodifiedFoodValues.hunger;
	}

	@ZenGetter("unmodifiedSaturationModifier")
	public float getUnmodifiedSaturationModifier() {
		return internal.unmodifiedFoodValues.saturationModifier;
	}

	@ZenGetter("food")
	public IItemStack getFood() {
		return CraftTweakerMC.getIItemStack(internal.food);
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(internal.player);
	}
}