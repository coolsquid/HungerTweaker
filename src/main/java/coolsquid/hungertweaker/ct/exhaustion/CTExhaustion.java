package coolsquid.hungertweaker.ct.exhaustion;

import coolsquid.hungertweaker.util.Expression;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.Exhaustion")
public class CTExhaustion {

	public static Expression maxExhaustionLevel;

	public static Expression deltaExhaustion;
	public static Expression deltaHunger;
	public static Expression deltaSaturation;

	public static Result status = Result.DEFAULT;

	@ZenMethod
	public static void setMaxExhaustionLevel(IData v) {
		CTExhaustion.maxExhaustionLevel = Expression.of(v);
	}

	@ZenMethod
	public static void setDeltaExhaustion(IData v) {
		CTExhaustion.deltaExhaustion = Expression.of(v);
	}

	@ZenMethod
	public static void setDeltaHunger(IData v) {
		CTExhaustion.deltaHunger = Expression.of(v);
	}

	@ZenMethod
	public static void setDeltaSaturation(IData v) {
		CTExhaustion.deltaSaturation = Expression.of(v);
	}

	@ZenMethod
	public static void setStatus(int v) {
		status = Result.values()[v];
	}
}