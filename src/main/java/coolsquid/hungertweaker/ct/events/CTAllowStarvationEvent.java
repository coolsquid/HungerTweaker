package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IPlayerEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import squeek.applecore.api.hunger.StarvationEvent.AllowStarvation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.events.AllowStarvationEvent")
public class CTAllowStarvationEvent implements IPlayerEvent {

	private final AllowStarvation internal;

	public CTAllowStarvationEvent(AllowStarvation internal) {
		this.internal = internal;
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
		return CraftTweakerMC.getIPlayer(this.internal.player);
	}
}