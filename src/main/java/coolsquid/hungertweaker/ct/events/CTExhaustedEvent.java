package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.ExhaustionEvent.Exhausted;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.ExhaustedEvent")
public class CTExhaustedEvent implements IPlayerEvent, IEventCancelable {

	private final Exhausted internal;

	public CTExhaustedEvent(Exhausted internal) {
		this.internal = internal;
	}

	@ZenGetter
	public float deltaExhaustion() {
		return this.internal.deltaExhaustion;
	}

	@ZenSetter
	public void deltaExhaustion(float deltaExhaustion) {
		this.internal.deltaExhaustion = deltaExhaustion;
	}

	@ZenGetter
	public int deltaHunger() {
		return this.internal.deltaHunger;
	}

	@ZenSetter
	public void deltaHunger(int deltaHunger) {
		this.internal.deltaHunger = deltaHunger;
	}

	@ZenGetter
	public float deltaSaturation() {
		return this.internal.deltaSaturation;
	}

	@ZenSetter
	public void deltaSaturation(float deltaSaturation) {
		this.internal.deltaSaturation = deltaSaturation;
	}

	@ZenGetter
	public float currentExhaustionLevel() {
		return this.internal.currentExhaustionLevel;
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(this.internal.player);
	}

	@Override
	public boolean isCanceled() {
		return internal.isCanceled();
	}

	@Override
	public void setCanceled(boolean canceled) {
		this.internal.setCanceled(canceled);
	}
}