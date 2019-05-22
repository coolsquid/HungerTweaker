package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.HealthRegenEvent.PeacefulRegen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.PeacefulRegenEvent")
public class CTPeacefulRegenEvent implements IPlayerEvent, IEventCancelable {

	private final PeacefulRegen internal;

	public CTPeacefulRegenEvent(PeacefulRegen internal) {
		this.internal = internal;
	}

	@ZenGetter
	public float deltaHealth() {
		return this.internal.deltaHealth;
	}

	@ZenSetter
	public void deltaHealth(float deltaHealth) {
		this.internal.deltaHealth = deltaHealth;
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