package coolsquid.hungertweaker.ct;

import coolsquid.hungertweaker.ClientEventHandler;
import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.HUD")
public class CTHUD {

	public static Result status = Result.DEFAULT;

	@ZenMethod
	public static void setStatus(int v) {
		Result newStatus = Result.values()[v];
		if (newStatus != status) {
			if (status == Result.DEFAULT) {
				MinecraftForge.EVENT_BUS.unregister(ClientEventHandler.class);
			} else {
				MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
			}
		}
		status = newStatus;
	}
}