package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.food.FoodEvent.FoodEaten;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.FoodEatenEvent")
public class CTFoodEatenEvent implements IPlayerEvent {

	private final FoodEaten internal;

	public CTFoodEatenEvent(FoodEaten internal) {
		this.internal = internal;
	}

	@ZenGetter
	public int hunger() {
		return this.internal.foodValues.hunger;
	}

	@ZenGetter
	public float saturationModifier() {
		return this.internal.foodValues.saturationModifier;
	}

	@ZenGetter
	public int hungerAdded() {
		return this.internal.hungerAdded;
	}

	@ZenGetter
	public float saturationAdded() {
		return this.internal.saturationAdded;
	}

	@ZenGetter
	public IItemStack food() {
		return CraftTweakerMC.getIItemStack(this.internal.food);
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(this.internal.player);
	}
}