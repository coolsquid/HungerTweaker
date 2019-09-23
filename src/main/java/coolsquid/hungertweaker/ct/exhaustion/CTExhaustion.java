package coolsquid.hungertweaker.ct.exhaustion;

import coolsquid.hungertweaker.ModEventHandler.TickHandler;
import coolsquid.hungertweaker.util.Expression;
import coolsquid.hungertweaker.util.Util;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.Exhaustion")
public class CTExhaustion {

	public static Expression maxExhaustionLevel;

	public static float constantExhaustionIncrease;

	public static Expression deltaExhaustion;
	public static Expression deltaHunger;
	public static Expression deltaSaturation;

	public static Result status = Result.DEFAULT;

	@ZenMethod
	public static void setMaxExhaustionLevel(IData v) {
		maxExhaustionLevel = Expression.parse(v);
	}

	@ZenMethod
	public static void setConstantExhaustionIncrease(IData v) {
		constantExhaustionIncrease = v.asFloat();
		if (constantExhaustionIncrease > 0) {
			if (TickHandler.instance == null) {
				TickHandler.instance = new TickHandler();
				MinecraftForge.EVENT_BUS.register(TickHandler.instance);
			}
		} else {
			if (TickHandler.instance != null) {
				MinecraftForge.EVENT_BUS.unregister(TickHandler.instance);
				TickHandler.instance = null;
			}
		}
	}

	@ZenMethod
	public static void setDeltaExhaustion(IData v) {
		deltaExhaustion = Expression.parse(v);
	}

	@ZenMethod
	public static void setDeltaHunger(IData v) {
		deltaHunger = Expression.parse(v);
	}

	@ZenMethod
	public static void setDeltaSaturation(IData v) {
		deltaSaturation = Expression.parse(v);
	}

	@ZenMethod
	public static void setStatus(IData v) {
		status = Util.parseResult(v);
	}
}