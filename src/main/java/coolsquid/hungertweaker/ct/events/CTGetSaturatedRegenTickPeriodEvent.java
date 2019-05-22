package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.HealthRegenEvent.GetSaturatedRegenTickPeriod;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.GetSaturatedRegenTickPeriodEvent")
public class CTGetSaturatedRegenTickPeriodEvent implements IPlayerEvent {

	private final GetSaturatedRegenTickPeriod internal;

	public CTGetSaturatedRegenTickPeriodEvent(GetSaturatedRegenTickPeriod internal) {
		this.internal = internal;
	}

	@ZenGetter
	public int regenTickPeriod() {
		return this.internal.regenTickPeriod;
	}

	@ZenSetter
	public void regenTickPeriod(int regenTickPeriod) {
		this.internal.regenTickPeriod = regenTickPeriod;
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(this.internal.player);
	}
}