package coolsquid.hungertweaker.ct;

import com.google.common.base.Preconditions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemDefinition;
import net.minecraft.item.ItemFood;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenExpansion("crafttweaker.item.IItemDefinition")
public class CTIItemDefinitionExt {

	@ZenGetter("foodItemProperties")
	public static CTFoodItemProperties getFoodItemProperties(IItemDefinition def) {
		Object item = def.getInternal();
		Preconditions.checkArgument(item instanceof ItemFood,
				"Can only retrieve the 'foodItemProperties' of ItemFood instances, but %s is not an ItemFood",
				def.getId());
		return new CTFoodItemProperties((ItemFood) def.getInternal());
	}
}