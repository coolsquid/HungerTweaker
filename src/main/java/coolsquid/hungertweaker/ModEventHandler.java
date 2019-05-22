package coolsquid.hungertweaker;

import coolsquid.hungertweaker.ct.CTFoodValues;
import coolsquid.hungertweaker.ct.CTHunger;
import coolsquid.hungertweaker.ct.CTStarvation;
import coolsquid.hungertweaker.ct.events.CTAllowExhaustionEvent;
import coolsquid.hungertweaker.ct.events.CTAllowRegenEvent;
import coolsquid.hungertweaker.ct.events.CTAllowSaturatedRegenEvent;
import coolsquid.hungertweaker.ct.events.CTAllowStarvationEvent;
import coolsquid.hungertweaker.ct.events.CTExhaustedEvent;
import coolsquid.hungertweaker.ct.events.CTExhaustingActionEvent;
import coolsquid.hungertweaker.ct.events.CTFoodEatenEvent;
import coolsquid.hungertweaker.ct.events.CTGetFoodValuesEvent;
import coolsquid.hungertweaker.ct.events.CTGetMaxExhaustionEvent;
import coolsquid.hungertweaker.ct.events.CTGetMaxHungerEvent;
import coolsquid.hungertweaker.ct.events.CTGetRegenTickPeriodEvent;
import coolsquid.hungertweaker.ct.events.CTGetSaturatedRegenTickPeriodEvent;
import coolsquid.hungertweaker.ct.events.CTGetStarveTickPeriodEvent;
import coolsquid.hungertweaker.ct.events.CTPeacefulRegenEvent;
import coolsquid.hungertweaker.ct.events.CTRegenEvent;
import coolsquid.hungertweaker.ct.events.CTSaturatedRegenEvent;
import coolsquid.hungertweaker.ct.events.CTStarveEvent;
import coolsquid.hungertweaker.ct.events.HungerEventManager;
import coolsquid.hungertweaker.ct.exhaustion.CTExhaustingAction;
import coolsquid.hungertweaker.ct.exhaustion.CTExhaustion;
import coolsquid.hungertweaker.ct.regen.CTPeacefulRegen;
import coolsquid.hungertweaker.ct.regen.CTRegen;
import coolsquid.hungertweaker.ct.regen.CTSaturatedRegen;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import squeek.applecore.api.hunger.HungerEvent;
import squeek.applecore.api.hunger.StarvationEvent;

public class ModEventHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(FoodEvent.GetFoodValues ie) {
		for (CTFoodValues v : CTFoodValues.LIST) {
			if (v.ingredient.matches(CraftTweakerMC.getIItemStack(ie.food))) {
				int hunger = (int) (v.hunger == null ? ie.foodValues.hunger : v.hunger.execute(ie.foodValues.hunger));
				float saturationModifier = (float) (v.saturationModifier == null ? ie.foodValues.saturationModifier : v.saturationModifier.execute(ie.foodValues.saturationModifier));
				ie.foodValues = new FoodValues(hunger, saturationModifier);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(FoodEvent.GetPlayerFoodValues ie) {
		if (HungerEventManager.GET_FOOD_VALUES.hasHandlers()) {
			HungerEventManager.GET_FOOD_VALUES.publish(new CTGetFoodValuesEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(FoodEvent.FoodEaten ie) {
		if (HungerEventManager.GET_FOOD_VALUES.hasHandlers()) {
			HungerEventManager.FOOD_EATEN.publish(new CTFoodEatenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(ExhaustionEvent.AllowExhaustion ie) {
		if (CTExhaustion.status != Result.DEFAULT) {
			ie.setResult(CTExhaustion.status);
		}
		if (HungerEventManager.ALLOW_EXHAUSTION.hasHandlers()) {
			HungerEventManager.ALLOW_EXHAUSTION.publish(new CTAllowExhaustionEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(ExhaustionEvent.Exhausted ie) {
		if (CTExhaustion.deltaExhaustion != null) {
			ie.deltaExhaustion = (float) CTExhaustion.deltaExhaustion.execute(ie.deltaExhaustion);
		}
		if (CTExhaustion.deltaHunger != null) {
			ie.deltaHunger = (int) CTExhaustion.deltaHunger.execute(ie.deltaHunger);
		}
		if (CTExhaustion.deltaSaturation != null) {
			ie.deltaSaturation = (float) CTExhaustion.deltaSaturation.execute(ie.deltaSaturation);
		}
		if (HungerEventManager.EXHAUSTED.hasHandlers()) {
			HungerEventManager.EXHAUSTED.publish(new CTExhaustedEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(ExhaustionEvent.ExhaustingAction ie) {
		CTExhaustingAction a = CTExhaustingAction.MAP.get(ie.source);
		if (a.deltaExhaustion != null) {
			ie.deltaExhaustion = (float) a.deltaExhaustion.execute(ie.deltaExhaustion);
		}
		if (HungerEventManager.EXHAUSTING_ACTION.hasHandlers()) {
			HungerEventManager.EXHAUSTING_ACTION.publish(new CTExhaustingActionEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(ExhaustionEvent.GetMaxExhaustion ie) {
		if (CTExhaustion.maxExhaustionLevel != null) {
			ie.maxExhaustionLevel = (float) CTExhaustion.maxExhaustionLevel.execute(ie.maxExhaustionLevel);
		}
		if (HungerEventManager.GET_MAX_EXHAUSTION.hasHandlers()) {
			HungerEventManager.GET_MAX_EXHAUSTION.publish(new CTGetMaxExhaustionEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HungerEvent.GetMaxHunger ie) {
		if (CTHunger.maxHunger != null) {
			ie.maxHunger = (int) CTHunger.maxHunger.execute(ie.maxHunger);
		}
		if (HungerEventManager.GET_MAX_HUNGER.hasHandlers()) {
			HungerEventManager.GET_MAX_HUNGER.publish(new CTGetMaxHungerEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(StarvationEvent.AllowStarvation ie) {
		if (CTStarvation.status != Result.DEFAULT) {
			ie.setResult(CTStarvation.status);
		}
		if (HungerEventManager.ALLOW_STARVATION.hasHandlers()) {
			HungerEventManager.ALLOW_STARVATION.publish(new CTAllowStarvationEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(StarvationEvent.GetStarveTickPeriod ie) {
		if (CTStarvation.interval != null) {
			ie.starveTickPeriod = (int) CTStarvation.interval.execute(ie.starveTickPeriod);
		}
		if (HungerEventManager.GET_STARVE_TICK_PERIOD.hasHandlers()) {
			HungerEventManager.GET_STARVE_TICK_PERIOD.publish(new CTGetStarveTickPeriodEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(StarvationEvent.Starve ie) {
		if (CTStarvation.starveDamage != null) {
			ie.starveDamage = (float) CTStarvation.starveDamage.execute(ie.starveDamage);
		}
		if (HungerEventManager.STARVE.hasHandlers()) {
			HungerEventManager.STARVE.publish(new CTStarveEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.AllowRegen ie) {
		if (CTRegen.status != Result.DEFAULT) {
			ie.setResult(CTRegen.status);
		}
		if (HungerEventManager.ALLOW_REGEN.hasHandlers()) {
			HungerEventManager.ALLOW_REGEN.publish(new CTAllowRegenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.AllowSaturatedRegen ie) {
		if (CTSaturatedRegen.status != Result.DEFAULT) {
			ie.setResult(CTSaturatedRegen.status);
		}
		if (HungerEventManager.ALLOW_SATURATED_REGEN.hasHandlers()) {
			HungerEventManager.ALLOW_SATURATED_REGEN.publish(new CTAllowSaturatedRegenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.GetRegenTickPeriod ie) {
		if (CTRegen.interval != null) {
			ie.regenTickPeriod = (int) CTRegen.interval.execute(ie.regenTickPeriod);
		}
		if (HungerEventManager.GET_REGEN_TICK_PERIOD.hasHandlers()) {
			HungerEventManager.GET_REGEN_TICK_PERIOD.publish(new CTGetRegenTickPeriodEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.GetSaturatedRegenTickPeriod ie) {
		if (CTSaturatedRegen.interval != null) {
			ie.regenTickPeriod = (int) CTSaturatedRegen.interval.execute(ie.regenTickPeriod);
		}
		if (HungerEventManager.GET_SATURATED_REGEN_TICK_PERIOD.hasHandlers()) {
			HungerEventManager.GET_SATURATED_REGEN_TICK_PERIOD.publish(new CTGetSaturatedRegenTickPeriodEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.PeacefulRegen ie) {
		if (CTPeacefulRegen.deltaHealth != null) {
			ie.deltaHealth = (float) CTPeacefulRegen.deltaHealth.execute(ie.deltaHealth);
		}
		if (CTPeacefulRegen.status != Result.DEFAULT) {
			ie.setResult(CTPeacefulRegen.status);
		}
		if (HungerEventManager.PEACEFUL_REGEN.hasHandlers()) {
			HungerEventManager.PEACEFUL_REGEN.publish(new CTPeacefulRegenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.Regen ie) {
		if (CTRegen.deltaHealth != null) {
			ie.deltaHealth = (float) CTRegen.deltaHealth.execute(ie.deltaHealth);
		}
		if (CTRegen.deltaExhaustion != null) {
			ie.deltaExhaustion = (float) CTRegen.deltaExhaustion.execute(ie.deltaExhaustion);
		}
		if (HungerEventManager.REGEN.hasHandlers()) {
			HungerEventManager.REGEN.publish(new CTRegenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.SaturatedRegen ie) {
		if (CTSaturatedRegen.deltaHealth != null) {
			ie.deltaHealth = (float) CTSaturatedRegen.deltaHealth.execute(ie.deltaHealth);
		}
		if (CTSaturatedRegen.deltaExhaustion != null) {
			ie.deltaExhaustion = (float) CTSaturatedRegen.deltaExhaustion.execute(ie.deltaExhaustion);
		}
		if (HungerEventManager.SATURATED_REGEN.hasHandlers()) {
			HungerEventManager.SATURATED_REGEN.publish(new CTSaturatedRegenEvent(ie));
		}
	}
}