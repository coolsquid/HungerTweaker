package coolsquid.hungertweaker.util;

import crafttweaker.api.data.DataByte;
import crafttweaker.api.data.DataDouble;
import crafttweaker.api.data.DataFloat;
import crafttweaker.api.data.DataInt;
import crafttweaker.api.data.DataLong;
import crafttweaker.api.data.DataShort;
import crafttweaker.api.data.DataString;
import crafttweaker.api.data.IData;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class Util {

	public static boolean isNumber(IData data) {
		return data instanceof DataByte || data instanceof DataDouble || data instanceof DataFloat
				|| data instanceof DataInt || data instanceof DataLong || data instanceof DataShort;
	}

	public static Result parseResult(IData data) {
		if (isNumber(data)) {
			int index = data.asInt();
			switch (index) {
			case 0:
				return Result.DENY;
			case 1:
				return Result.DEFAULT;
			case 2:
				return Result.ALLOW;
			}
			throw new IllegalArgumentException("Unknown status/result: " + index);
		} else if (data instanceof DataString) {
			return Result.valueOf(data.asString().toUpperCase());
		} else {
			throw new IllegalArgumentException("The argument must be a string or a number");
		}
	}
}