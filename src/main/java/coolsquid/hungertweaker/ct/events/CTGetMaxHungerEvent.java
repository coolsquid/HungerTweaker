package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.HungerEvent.GetMaxHunger;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.GetMaxHungerEvent")
public class CTGetMaxHungerEvent implements IPlayerEvent {

	private final GetMaxHunger internal;

	public CTGetMaxHungerEvent(GetMaxHunger internal) {
		this.internal = internal;
	}

	@ZenGetter
	public int maxHunger() {
		return this.internal.maxHunger;
	}

	@ZenSetter
	public void maxHunger(int maxHunger) {
		this.internal.maxHunger = maxHunger;
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(this.internal.player);
	}
}