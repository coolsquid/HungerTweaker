package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.HealthRegenEvent.SaturatedRegen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.SaturatedRegenEvent")
public class CTSaturatedRegenEvent implements IPlayerEvent, IEventCancelable {

	private final SaturatedRegen internal;

	public CTSaturatedRegenEvent(SaturatedRegen internal) {
		this.internal = internal;
	}

	@ZenGetter
	public float deltaHealth() {
		return internal.deltaHealth;
	}

	@ZenSetter
	public void deltaHealth(float deltaHealth) {
		this.internal.deltaHealth = deltaHealth;
	}

	@ZenGetter
	public float deltaExhaustion() {
		return internal.deltaExhaustion;
	}

	@ZenSetter
	public void deltaExhaustion(float deltaExhaustion) {
		this.internal.deltaExhaustion = deltaExhaustion;
	}

	@Override
	public boolean isCanceled() {
		return internal.isCanceled();
	}

	@Override
	public void setCanceled(boolean canceled) {
		internal.setCanceled(canceled);
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(internal.player);
	}
}