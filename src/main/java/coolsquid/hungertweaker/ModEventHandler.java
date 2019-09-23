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
import coolsquid.hungertweaker.ct.events.CTFoodStatsAdditionEvent;
import coolsquid.hungertweaker.ct.events.CTGetFoodValuesEvent;
import coolsquid.hungertweaker.ct.events.CTGetMaxExhaustionEvent;
import coolsquid.hungertweaker.ct.events.CTGetMaxHungerEvent;
import coolsquid.hungertweaker.ct.events.CTGetRegenTickPeriodEvent;
import coolsquid.hungertweaker.ct.events.CTGetSaturatedRegenTickPeriodEvent;
import coolsquid.hungertweaker.ct.events.CTGetStarveTickPeriodEvent;
import coolsquid.hungertweaker.ct.events.CTPeacefulHungerRegenEvent;
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
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import squeek.applecore.api.food.FoodEvent;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.hunger.ExhaustionEvent;
import squeek.applecore.api.hunger.HealthRegenEvent;
import squeek.applecore.api.hunger.HungerEvent;
import squeek.applecore.api.hunger.HungerRegenEvent;
import squeek.applecore.api.hunger.StarvationEvent;

public class ModEventHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(FoodEvent.GetFoodValues ie) {
		for (int index = CTFoodValues.LIST.size() - 1; index >= 0; index--) {
			CTFoodValues v = CTFoodValues.LIST.get(index);
			if ((v.hunger != null || v.saturationModifier != null)
					&& v.ingredient.matches(CraftTweakerMC.getIItemStack(ie.food))) {
				int hunger = (int) (v.hunger == null ? ie.foodValues.hunger : v.hunger.eval(ie.foodValues.hunger));
				float saturationModifier = (float) (v.saturationModifier == null ? ie.foodValues.saturationModifier
						: v.saturationModifier.eval(ie.foodValues.saturationModifier));
				ie.foodValues = new FoodValues(hunger, saturationModifier);
				break;
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
		if (HungerEventManager.FOOD_EATEN.hasHandlers()) {
			HungerEventManager.FOOD_EATEN.publish(new CTFoodEatenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(FoodEvent.FoodStatsAddition ie) {
		if (HungerEventManager.FOOD_STATS_ADDITION.hasHandlers()) {
			HungerEventManager.FOOD_STATS_ADDITION.publish(new CTFoodStatsAdditionEvent(ie));
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
			ie.deltaExhaustion = (float) CTExhaustion.deltaExhaustion.eval(ie.deltaExhaustion);
		}
		if (CTExhaustion.deltaHunger != null) {
			ie.deltaHunger = (int) CTExhaustion.deltaHunger.eval(ie.deltaHunger);
		}
		if (CTExhaustion.deltaSaturation != null) {
			ie.deltaSaturation = (float) CTExhaustion.deltaSaturation.eval(ie.deltaSaturation);
		}
		if (HungerEventManager.EXHAUSTED.hasHandlers()) {
			HungerEventManager.EXHAUSTED.publish(new CTExhaustedEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(ExhaustionEvent.ExhaustingAction ie) {
		CTExhaustingAction a = CTExhaustingAction.MAP.get(ie.source);
		if (a.deltaExhaustion != null) {
			ie.deltaExhaustion = (float) a.deltaExhaustion.eval(ie.deltaExhaustion);
		}
		if (HungerEventManager.EXHAUSTING_ACTION.hasHandlers()) {
			HungerEventManager.EXHAUSTING_ACTION.publish(new CTExhaustingActionEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(ExhaustionEvent.GetMaxExhaustion ie) {
		if (CTExhaustion.maxExhaustionLevel != null) {
			ie.maxExhaustionLevel = (float) CTExhaustion.maxExhaustionLevel.eval(ie.maxExhaustionLevel);
		}
		if (HungerEventManager.GET_MAX_EXHAUSTION.hasHandlers()) {
			HungerEventManager.GET_MAX_EXHAUSTION.publish(new CTGetMaxExhaustionEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HungerEvent.GetMaxHunger ie) {
		if (CTHunger.maxHunger != null) {
			ie.maxHunger = (int) CTHunger.maxHunger.eval(ie.maxHunger);
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
			ie.starveTickPeriod = (int) CTStarvation.interval.eval(ie.starveTickPeriod);
		}
		if (HungerEventManager.GET_STARVE_TICK_PERIOD.hasHandlers()) {
			HungerEventManager.GET_STARVE_TICK_PERIOD.publish(new CTGetStarveTickPeriodEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(StarvationEvent.Starve ie) {
		if (CTStarvation.starveDamage != null) {
			ie.starveDamage = (float) CTStarvation.starveDamage.eval(ie.starveDamage);
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
			ie.regenTickPeriod = (int) CTRegen.interval.eval(ie.regenTickPeriod);
		}
		if (HungerEventManager.GET_REGEN_TICK_PERIOD.hasHandlers()) {
			HungerEventManager.GET_REGEN_TICK_PERIOD.publish(new CTGetRegenTickPeriodEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.GetSaturatedRegenTickPeriod ie) {
		if (CTSaturatedRegen.interval != null) {
			ie.regenTickPeriod = (int) CTSaturatedRegen.interval.eval(ie.regenTickPeriod);
		}
		if (HungerEventManager.GET_SATURATED_REGEN_TICK_PERIOD.hasHandlers()) {
			HungerEventManager.GET_SATURATED_REGEN_TICK_PERIOD.publish(new CTGetSaturatedRegenTickPeriodEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.PeacefulRegen ie) {
		if (CTPeacefulRegen.deltaHealth != null) {
			ie.deltaHealth = (float) CTPeacefulRegen.deltaHealth.eval(ie.deltaHealth);
		}
		if (CTPeacefulRegen.healthStatus == Result.ALLOW) {
			ie.setCanceled(false);
		} else if (CTPeacefulRegen.healthStatus == Result.DENY) {
			ie.setCanceled(true);
		}
		if (HungerEventManager.PEACEFUL_REGEN.hasHandlers()) {
			HungerEventManager.PEACEFUL_REGEN.publish(new CTPeacefulRegenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.Regen ie) {
		if (CTRegen.deltaHealth != null) {
			ie.deltaHealth = (float) CTRegen.deltaHealth.eval(ie.deltaHealth);
		}
		if (CTRegen.deltaExhaustion != null) {
			ie.deltaExhaustion = (float) CTRegen.deltaExhaustion.eval(ie.deltaExhaustion);
		}
		if (HungerEventManager.REGEN.hasHandlers()) {
			HungerEventManager.REGEN.publish(new CTRegenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HealthRegenEvent.SaturatedRegen ie) {
		if (CTSaturatedRegen.deltaHealth != null) {
			ie.deltaHealth = (float) CTSaturatedRegen.deltaHealth.eval(ie.deltaHealth);
		}
		if (CTSaturatedRegen.deltaExhaustion != null) {
			ie.deltaExhaustion = (float) CTSaturatedRegen.deltaExhaustion.eval(ie.deltaExhaustion);
		}
		if (HungerEventManager.SATURATED_REGEN.hasHandlers()) {
			HungerEventManager.SATURATED_REGEN.publish(new CTSaturatedRegenEvent(ie));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void on(HungerRegenEvent.PeacefulRegen ie) {
		if (CTPeacefulRegen.deltaHunger != null) {
			ie.deltaHunger = (int) CTPeacefulRegen.deltaHunger.eval(ie.deltaHunger);
		}
		if (CTPeacefulRegen.hungerStatus == Result.ALLOW) {
			ie.setCanceled(false);
		} else if (CTPeacefulRegen.hungerStatus == Result.DENY) {
			ie.setCanceled(true);
		}
		if (HungerEventManager.PEACEFUL_HUNGER_REGEN.hasHandlers()) {
			HungerEventManager.PEACEFUL_HUNGER_REGEN.publish(new CTPeacefulHungerRegenEvent(ie));
		}
	}

	public static class TickHandler {

		public static TickHandler instance;

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void on(PlayerTickEvent event) {
			if (CTExhaustion.constantExhaustionIncrease > 0) {
				event.player.getFoodStats().addExhaustion(CTExhaustion.constantExhaustionIncrease);
			}
		}
	}
}