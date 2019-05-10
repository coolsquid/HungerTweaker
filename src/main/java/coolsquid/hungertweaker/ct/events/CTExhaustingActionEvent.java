package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import squeek.applecore.api.hunger.ExhaustionEvent.ExhaustingAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.hungertweaker.events.ExhaustingActionEvent")
public class CTExhaustingActionEvent implements IPlayerEvent {

	private final ExhaustingAction event;

	public CTExhaustingActionEvent(ExhaustingAction event) {
		this.event = event;
	}

	@ZenGetter
	public String action() {
		return event.source.name();
	}

	@ZenGetter
	public float deltaExhaustion() {
		return event.deltaExhaustion;
	}

	@ZenSetter
	public void deltaExhaustion(float deltaExhaustion) {
		event.deltaExhaustion = deltaExhaustion;
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(event.player);
	}
}