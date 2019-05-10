package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.ExhaustionEvent.GetMaxExhaustion;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.GetMaxExhaustionEvent")
public class CTGetMaxExhaustionEvent implements IPlayerEvent {

	private final GetMaxExhaustion internal;

	public CTGetMaxExhaustionEvent(GetMaxExhaustion internal) {
		this.internal = internal;
	}

	@ZenGetter
	public float maxExhaustionLevel() {
		return internal.maxExhaustionLevel;
	}

	@ZenSetter
	public void maxExhaustionLevel(float maxExhaustionLevel) {
		internal.maxExhaustionLevel = maxExhaustionLevel;
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(internal.player);
	}
}