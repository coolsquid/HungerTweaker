package coolsquid.hungertweaker.util;

import crafttweaker.api.data.DataByte;
import crafttweaker.api.data.DataDouble;
import crafttweaker.api.data.DataFloat;
import crafttweaker.api.data.DataInt;
import crafttweaker.api.data.DataLong;
import crafttweaker.api.data.DataShort;
import crafttweaker.api.data.IData;

public class Util {

	public static boolean isNumber(IData data) {
		return data instanceof DataByte || data instanceof DataDouble || data instanceof DataFloat
				|| data instanceof DataInt || data instanceof DataLong || data instanceof DataShort;
	}
}