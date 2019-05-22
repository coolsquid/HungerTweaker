package coolsquid.hungertweaker.ct.exhaustion;

import com.google.common.collect.ImmutableMap;

import coolsquid.hungertweaker.util.Expression;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import squeek.applecore.api.hunger.ExhaustionEvent.ExhaustingActions;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("mods.hungertweaker.ExhaustingAction")
public class CTExhaustingAction {

	public final ExhaustingActions action;

	@ZenProperty
	public final String name;

	public Expression deltaExhaustion;

	private CTExhaustingAction(ExhaustingActions action) {
		this.action = action;
		this.name = action.name();
	}

	@ZenMethod
	public void setDeltaExhaustion(IData v) {
		this.deltaExhaustion = Expression.parse(v);
	}

	@ZenProperty
	public static final CTExhaustingAction HARVEST_BLOCK = new CTExhaustingAction(ExhaustingActions.HARVEST_BLOCK);

	@ZenProperty
	public static final CTExhaustingAction NORMAL_JUMP = new CTExhaustingAction(ExhaustingActions.NORMAL_JUMP);

	@ZenProperty
	public static final CTExhaustingAction SPRINTING_JUMP = new CTExhaustingAction(ExhaustingActions.SPRINTING_JUMP);

	@ZenProperty
	public static final CTExhaustingAction ATTACK_ENTITY = new CTExhaustingAction(ExhaustingActions.ATTACK_ENTITY);

	@ZenProperty
	public static final CTExhaustingAction DAMAGE_TAKEN = new CTExhaustingAction(ExhaustingActions.DAMAGE_TAKEN);

	@ZenProperty
	public static final CTExhaustingAction HUNGER_POTION = new CTExhaustingAction(ExhaustingActions.HUNGER_POTION);

	@ZenProperty
	public static final CTExhaustingAction MOVEMENT_DIVE = new CTExhaustingAction(ExhaustingActions.MOVEMENT_DIVE);

	@ZenProperty
	public static final CTExhaustingAction MOVEMENT_SWIM = new CTExhaustingAction(ExhaustingActions.MOVEMENT_SWIM);

	@ZenProperty
	public static final CTExhaustingAction MOVEMENT_SPRINT = new CTExhaustingAction(ExhaustingActions.MOVEMENT_SPRINT);

	@ZenProperty
	public static final CTExhaustingAction MOVEMENT_CROUCH = new CTExhaustingAction(ExhaustingActions.MOVEMENT_CROUCH);

	@ZenProperty
	public static final CTExhaustingAction MOVEMENT_WALK = new CTExhaustingAction(ExhaustingActions.MOVEMENT_WALK);

	@ZenProperty
	public static final CTExhaustingAction[] ALL =
			{ HARVEST_BLOCK, NORMAL_JUMP, SPRINTING_JUMP, ATTACK_ENTITY, DAMAGE_TAKEN, HUNGER_POTION, MOVEMENT_DIVE,
					MOVEMENT_SWIM, MOVEMENT_SPRINT, MOVEMENT_CROUCH, MOVEMENT_WALK };

	public static final ImmutableMap<ExhaustingActions, CTExhaustingAction> MAP;

	static {
		ImmutableMap.Builder<ExhaustingActions, CTExhaustingAction> b = ImmutableMap.builder();
		b.put(HARVEST_BLOCK.action, HARVEST_BLOCK);
		b.put(NORMAL_JUMP.action, NORMAL_JUMP);
		b.put(SPRINTING_JUMP.action, SPRINTING_JUMP);
		b.put(ATTACK_ENTITY.action, ATTACK_ENTITY);
		b.put(DAMAGE_TAKEN.action, DAMAGE_TAKEN);
		b.put(HUNGER_POTION.action, HUNGER_POTION);
		b.put(MOVEMENT_DIVE.action, MOVEMENT_DIVE);
		b.put(MOVEMENT_SWIM.action, MOVEMENT_SWIM);
		b.put(MOVEMENT_SPRINT.action, MOVEMENT_SPRINT);
		b.put(MOVEMENT_CROUCH.action, MOVEMENT_CROUCH);
		b.put(MOVEMENT_WALK.action, MOVEMENT_WALK);
		MAP = b.build();
	}
}