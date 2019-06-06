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
			throw new IllegalArgumentException(
					"Unknown status/result: " + index + ". Possible values are: 0, 1 and 2.");
		} else if (data instanceof DataString) {
			try {
				return Result.valueOf(data.asString().toUpperCase());
			} catch (IllegalArgumentException e) {
				StringBuilder values = new StringBuilder();
				if (Result.values().length > 0) {
					values.append('\'').append(Result.values()[0].name()).append('\'');
					if (Result.values().length > 1) {
						for (int i = 1; i < Result.values().length - 1; i++) {
							values.append(", '").append(Result.values()[i].name()).append('\'');
						}
						values.append(" and '").append(Result.values()[Result.values().length - 1].name()).append('\'');
					}
				}
				throw new IllegalArgumentException("Unknown status/result: '" + data.asString()
						+ "'. Possible values are: " + values.toString() + ".", e);
			}
		} else {
			throw new IllegalArgumentException("The argument must be a string or a number.");
		}
	}
}