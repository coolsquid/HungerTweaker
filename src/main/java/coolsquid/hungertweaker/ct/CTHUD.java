package coolsquid.hungertweaker.ct;

import coolsquid.hungertweaker.ClientEventHandler;
import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.common.MinecraftForge;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.HUD")
public class CTHUD {

	@ZenMethod
	public static void enable() {
		MinecraftForge.EVENT_BUS.unregister(ClientEventHandler.class);
	}

	@ZenMethod
	public static void disable() {
		MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
	}
}