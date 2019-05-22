package coolsquid.hungertweaker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = HungerTweaker.MODID, name = HungerTweaker.NAME, version = HungerTweaker.VERSION,
		dependencies = HungerTweaker.DEPENDENCIES, updateJSON = HungerTweaker.UPDATE_JSON)
public class HungerTweaker {

	public static final String MODID = "hungertweaker";
	public static final String NAME = "HungerTweaker";
	public static final String VERSION = "0.1.0";
	public static final String DEPENDENCIES = "required-after:crafttweaker@[4.0.0,);required-after:applecore@[3.2.0,)";
	public static final String UPDATE_JSON = "";

	public static final Logger LOGGER = LogManager.getFormatterLogger(NAME);

	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
	}
}