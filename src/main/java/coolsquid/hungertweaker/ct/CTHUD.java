package coolsquid.hungertweaker.ct;

import coolsquid.hungertweaker.ClientEventHandler;
import coolsquid.hungertweaker.util.Util;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.HUD")
public class CTHUD {

	public static Result status = Result.DEFAULT;

	@ZenMethod
	public static void setStatus(IData v) {
		Result newStatus = Util.parseResult(v);
		if (newStatus != status) {
			if (newStatus == Result.DEFAULT) {
				MinecraftForge.EVENT_BUS.unregister(ClientEventHandler.class);
			} else {
				MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
			}
			status = newStatus;
		}
	}
}