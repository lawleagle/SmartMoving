package net.smart.moving.test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(modid = "SmartMovingTestMod", version = "0.0")
public class SmartMovingTestMod {
	
	public static final Logger LOGGER = LogManager.getLogger("smartmovingtest");
	
	private double lastX;
	private double lastY;
	private double lastZ;
	
	private boolean running;
	private int time;
	
	public static SmartMovingTestMod instance;
	
	private static final List<TestEvent> testEvents = Arrays.asList(
		new TestEvent("WAIT_BEFORE_WALK", 3),
		new TestEvent("WALK_PRE", 4, new SetKeyState(gs -> gs.keyBindForward, true)),
		new TestEvent("WALK_JUMP", 8, new SetKeyState(gs -> gs.keyBindJump, true)),
		new TestEvent("WALK_END", 4, new SetKeyState(gs -> gs.keyBindJump, false)),
		new TestEvent("SPRINT_PRE", 4, new SetKeyState(gs -> gs.keyBindSprint, true)),
		new TestEvent("SPRINT_JUMP", 8, new SetKeyState(gs -> gs.keyBindJump, true)),
		new TestEvent("SPRINT_END", 4, new SetKeyState(gs -> gs.keyBindJump, false)),
		new TestEvent("WAIT_BEFORE_FINISH", 3, new SetKeyState(gs -> gs.keyBindForward, false))
	);
	
	private static class TestEvent {
		
		String name;
		int duration;
		Runnable[] actions;
		
		private TestEvent(String name, int duration, Runnable...actions) {
			this.name = name;
			this.duration = duration;
			this.actions = actions;
		}
	}
	
	private static class SetKeyState implements Runnable {
		
		private KeyBinding keyBind;
		private boolean state;
		
		public SetKeyState(Function<GameSettings, KeyBinding> keyFunction, boolean state) {
			keyBind = keyFunction.apply(Minecraft.getMinecraft().gameSettings);
			this.state = state;
		}

		@Override
		public void run() {
			// Not using KeyBinding.setKeyBindState because it doesn't work with conflicting keybinds
			ReflectionHelper.setPrivateValue(KeyBinding.class, keyBind, state, "pressed", "field_74513_e");
		}
		
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		instance = this;
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new SmartMovingTestCommand());
	}
	
	@SubscribeEvent
	public void onTick(PlayerTickEvent event) {
		if(event.side == Side.SERVER && event.phase == Phase.START && running) {
			if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
				System.out.println("P key pressed, aborting test!");
				running = false;
			}
			
			int timeCounter = 0;
			for(TestEvent e : testEvents) {
				if(time == timeCounter) {
					System.out.println("Running event " + e.name + " for " + e.duration + " seconds.");
					for(Runnable a : e.actions) {
						a.run();
					}
					break;
				}
				timeCounter += e.duration * 20;
			}
			if(time > timeCounter) {
				System.out.println("All test events executed!");
				running = false;
			}
			time++;
			/*PlayerTickEvent pte = (PlayerTickEvent)event;
			double x = pte.player.posX;
			double y = pte.player.posY;
			double z = pte.player.posZ;
			LOGGER.info(String.format(Locale.ROOT, "delta XYZ:%12.4f%12.4f%12.4f", x-lastX, y-lastY, z-lastZ));
			lastX = x;
			lastY = y;
			lastZ = z;*/
		}
	}

	public void startTest() {
		System.out.println("Tests will take " + testEvents.stream().mapToInt(e -> e.duration).sum() + " seconds in total.");
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		player.rotationPitch = player.rotationYaw = 0;
		time = 0;
		running = true;
	}
	
}
