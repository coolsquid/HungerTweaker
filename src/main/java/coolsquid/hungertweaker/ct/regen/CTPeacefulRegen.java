package coolsquid.hungertweaker.ct.regen;

import coolsquid.hungertweaker.util.Expression;
import coolsquid.hungertweaker.util.Util;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.PeacefulRegen")
public class CTPeacefulRegen {

	public static Expression deltaHealth;
	public static Expression deltaHunger;

	public static Result healthStatus = Result.DEFAULT;
	public static Result hungerStatus = Result.DEFAULT;

	@ZenMethod
	public static void setDeltaHealth(IData v) {
		deltaHealth = Expression.parse(v);
	}

	@ZenMethod
	public static void setDeltaHunger(IData v) {
		deltaHunger = Expression.parse(v);
	}

	/**
	 * Use {@link #setHealthStatus(IData)} instead!
	 */
	@Deprecated
	@ZenMethod
	public static void setStatus(IData v) {
		healthStatus = Util.parseResult(v);
	}

	@ZenMethod
	public static void setHealthStatus(IData v) {
		healthStatus = Util.parseResult(v);
	}

	@ZenMethod
	public static void setHungerStatus(IData v) {
		hungerStatus = Util.parseResult(v);
	}
}