package coolsquid.hungertweaker.ct;

import coolsquid.hungertweaker.util.Expression;
import coolsquid.hungertweaker.util.Util;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.Starvation")
public class CTStarvation {

	public static Expression interval;
	public static Expression starveDamage;
	public static Result status = Result.DEFAULT;

	@ZenMethod
	public static void setInterval(IData v) {
		interval = Expression.parse(v);
	}

	@ZenMethod
	public static void setDamage(IData v) {
		starveDamage = Expression.parse(v);
	}

	@ZenMethod
	public static void setStatus(IData v) {
		status = Util.parseResult(v);
	}
}