package coolsquid.hungertweaker.ct;

import coolsquid.hungertweaker.util.Expression;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.Hunger")
public class CTHunger {

	public static Expression maxHunger;

	@ZenMethod
	public static void setMaxHunger(IData v) {
		maxHunger = Expression.of(v);
	}
}