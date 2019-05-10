package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import squeek.applecore.api.hunger.HealthRegenEvent.AllowSaturatedRegen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.events.AllowSaturatedRegenEvent")
public class CTAllowSaturatedRegenEvent implements IPlayerEvent {

	private final AllowSaturatedRegen internal;

	public CTAllowSaturatedRegenEvent(AllowSaturatedRegen ie) {
		this.internal = ie;
	}

	@ZenMethod
	public void allow() {
		this.internal.setResult(Result.ALLOW);
	}

	@ZenMethod
	public void deny() {
		this.internal.setResult(Result.DENY);
	}

	@ZenMethod
	public void pass() {
		this.internal.setResult(Result.DEFAULT);
	}

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(internal.player);
	}
}