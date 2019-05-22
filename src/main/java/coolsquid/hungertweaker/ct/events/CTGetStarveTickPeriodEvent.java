package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.StarvationEvent.GetStarveTickPeriod;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.GetStarveTickPeriodEvent")
public class CTGetStarveTickPeriodEvent implements IPlayerEvent {

	private final GetStarveTickPeriod internal;

	public CTGetStarveTickPeriodEvent(GetStarveTickPeriod internal) {
		this.internal = internal;
	}

	@ZenGetter
	public int starveTickPeriod() {
		return this.internal.starveTickPeriod;
	}

	@ZenSetter
	public void starveTickPeriod(int starveTickPeriod) {
		this.internal.starveTickPeriod = starveTickPeriod;
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(this.internal.player);
	}
}