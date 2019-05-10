package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.HealthRegenEvent.GetRegenTickPeriod;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.GetRegenTickPeriodEvent")
public class CTGetRegenTickPeriodEvent implements IPlayerEvent {

	private final GetRegenTickPeriod internal;

	public CTGetRegenTickPeriodEvent(GetRegenTickPeriod internal) {
		this.internal = internal;
	}

	@ZenGetter
	public int regenTickPeriod() {
		return internal.regenTickPeriod;
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