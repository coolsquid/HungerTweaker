package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.StarvationEvent.Starve;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.StarveEvent")
public class CTStarveEvent implements IPlayerEvent, IEventCancelable {

	private final Starve internal;

	public CTStarveEvent(Starve internal) {
		this.internal = internal;
	}

	@ZenGetter
	public float starveDamage() {
		return internal.starveDamage;
	}

	@ZenSetter
	public void starveDamage(float starveDamage) {
		this.internal.starveDamage = starveDamage;
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