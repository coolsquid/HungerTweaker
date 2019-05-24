package coolsquid.hungertweaker;

import coolsquid.hungertweaker.ct.CTHUD;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public static void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == ElementType.FOOD) {
			event.setCanceled(CTHUD.status == Result.DENY);
		}
	}
}