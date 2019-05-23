package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.food.FoodEvent.FoodStatsAddition;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.FoodStatsAdditionEvent")
public class CTFoodStatsAdditionEvent implements IPlayerEvent, IEventCancelable {

	private final FoodStatsAddition internal;

	public CTFoodStatsAdditionEvent(FoodStatsAddition internal) {
		this.internal = internal;
	}

	@ZenGetter
	public int hunger() {
		return internal.foodValuesToBeAdded.hunger;
	}

	@ZenGetter
	public float saturationModifier() {
		return internal.foodValuesToBeAdded.saturationModifier;
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(internal.player);
	}

	@Override
	public boolean isCanceled() {
		return internal.isCanceled();
	}

	@Override
	public void setCanceled(boolean canceled) {
		internal.setCanceled(canceled);
	}
}