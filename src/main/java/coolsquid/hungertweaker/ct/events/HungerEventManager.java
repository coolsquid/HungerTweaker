package coolsquid.hungertweaker.ct.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.hungertweaker.events.HungerEvents")
public class HungerEventManager {

	public static final EventList<CTGetFoodValuesEvent> GET_FOOD_VALUES = new EventList<>();
	public static final EventList<CTFoodEatenEvent> FOOD_EATEN = new EventList<>();
	public static final EventList<CTAllowExhaustionEvent> ALLOW_EXHAUSTION = new EventList<>();
	public static final EventList<CTExhaustedEvent> EXHAUSTED = new EventList<>();
	public static final EventList<CTExhaustingActionEvent> EXHAUSTING_ACTION = new EventList<>();
	public static final EventList<CTGetMaxExhaustionEvent> GET_MAX_EXHAUSTION = new EventList<>();
	public static final EventList<CTGetMaxHungerEvent> GET_MAX_HUNGER = new EventList<>();
	public static final EventList<CTAllowStarvationEvent> ALLOW_STARVATION = new EventList<>();
	public static final EventList<CTGetStarveTickPeriodEvent> GET_STARVE_TICK_PERIOD = new EventList<>();
	public static final EventList<CTStarveEvent> STARVE = new EventList<>();
	public static final EventList<CTAllowRegenEvent> ALLOW_REGEN = new EventList<>();
	public static final EventList<CTAllowSaturatedRegenEvent> ALLOW_SATURATED_REGEN = new EventList<>();
	public static final EventList<CTGetRegenTickPeriodEvent> GET_REGEN_TICK_PERIOD = new EventList<>();
	public static final EventList<CTGetSaturatedRegenTickPeriodEvent> GET_SATURATED_REGEN_TICK_PERIOD =
			new EventList<>();
	public static final EventList<CTPeacefulRegenEvent> PEACEFUL_REGEN = new EventList<>();
	public static final EventList<CTRegenEvent> REGEN = new EventList<>();
	public static final EventList<CTSaturatedRegenEvent> SATURATED_REGEN = new EventList<>();

	@ZenMethod
	public static IEventHandle onGetFoodValues(IEventHandler<CTGetFoodValuesEvent> handler) {
		return GET_FOOD_VALUES.add(handler);
	}

	@ZenMethod
	public static IEventHandle onFoodEaten(IEventHandler<CTFoodEatenEvent> handler) {
		return FOOD_EATEN.add(handler);
	}

	@ZenMethod
	public static IEventHandle onAllowExhaustion(IEventHandler<CTAllowExhaustionEvent> handler) {
		return ALLOW_EXHAUSTION.add(handler);
	}

	@ZenMethod
	public static IEventHandle onExhausted(IEventHandler<CTExhaustedEvent> handler) {
		return EXHAUSTED.add(handler);
	}

	@ZenMethod
	public static IEventHandle onExhaustingAction(IEventHandler<CTExhaustingActionEvent> handler) {
		return EXHAUSTING_ACTION.add(handler);
	}

	@ZenMethod
	public static IEventHandle onGetMaxExhaustion(IEventHandler<CTGetMaxExhaustionEvent> handler) {
		return GET_MAX_EXHAUSTION.add(handler);
	}

	@ZenMethod
	public static IEventHandle onGetMaxHunger(IEventHandler<CTGetMaxHungerEvent> handler) {
		return GET_MAX_HUNGER.add(handler);
	}

	@ZenMethod
	public static IEventHandle onAllowStarvation(IEventHandler<CTAllowStarvationEvent> handler) {
		return ALLOW_STARVATION.add(handler);
	}

	@ZenMethod
	public static IEventHandle onGetStarveTickPeriod(IEventHandler<CTGetStarveTickPeriodEvent> handler) {
		return GET_STARVE_TICK_PERIOD.add(handler);
	}

	@ZenMethod
	public static IEventHandle onStarve(IEventHandler<CTStarveEvent> handler) {
		return STARVE.add(handler);
	}

	@ZenMethod
	public static IEventHandle onAllowRegen(IEventHandler<CTAllowRegenEvent> handler) {
		return ALLOW_REGEN.add(handler);
	}

	@ZenMethod
	public static IEventHandle onAllowSaturatedRegen(IEventHandler<CTAllowSaturatedRegenEvent> handler) {
		return ALLOW_SATURATED_REGEN.add(handler);
	}

	@ZenMethod
	public static IEventHandle onGetRegenTickPeriod(IEventHandler<CTGetRegenTickPeriodEvent> handler) {
		return GET_REGEN_TICK_PERIOD.add(handler);
	}

	@ZenMethod
	public static IEventHandle onGetSaturatedRegenTickPeriod(
			IEventHandler<CTGetSaturatedRegenTickPeriodEvent> handler) {
		return GET_SATURATED_REGEN_TICK_PERIOD.add(handler);
	}

	@ZenMethod
	public static IEventHandle onPeacefulRegen(IEventHandler<CTPeacefulRegenEvent> handler) {
		return PEACEFUL_REGEN.add(handler);
	}

	@ZenMethod
	public static IEventHandle onRegen(IEventHandler<CTRegenEvent> handler) {
		return REGEN.add(handler);
	}

	@ZenMethod
	public static IEventHandle onSaturatedRegen(IEventHandler<CTSaturatedRegenEvent> handler) {
		return SATURATED_REGEN.add(handler);
	}
}