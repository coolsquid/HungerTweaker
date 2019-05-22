package coolsquid.hungertweaker.ct.regen;

import coolsquid.hungertweaker.util.Expression;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.PeacefulRegen")
public class CTPeacefulRegen {

	public static Expression deltaHealth;

	public static Result status = Result.DEFAULT;

	@ZenMethod
	public static void setDeltaHealth(IData v) {
		deltaHealth = Expression.parse(v);
	}

	@ZenMethod
	public static void setStatus(int v) {
		status = Result.values()[v];
	}
}