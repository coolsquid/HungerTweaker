package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.HungerRegenEvent.PeacefulRegen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.PeacefulHungerRegenEvent")
public class CTPeacefulHungerRegenEvent implements IPlayerEvent, IEventCancelable {

	private final PeacefulRegen internal;

	public CTPeacefulHungerRegenEvent(PeacefulRegen internal) {
		this.internal = internal;
	}

	@ZenGetter
	public int deltaHunger() {
		return this.internal.deltaHunger;
	}

	@ZenSetter
	public void deltaHunger(int deltaHunger) {
		this.internal.deltaHunger = deltaHunger;
	}

	@Override
	public boolean isCanceled() {
		return this.internal.isCanceled();
	}

	@Override
	public void setCanceled(boolean canceled) {
		this.internal.setCanceled(canceled);
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(this.internal.player);
	}
}