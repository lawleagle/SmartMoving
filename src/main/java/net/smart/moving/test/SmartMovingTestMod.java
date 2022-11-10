package net.smart.moving.test;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "SmartMovingTestMod", version = "0.0")
public class SmartMovingTestMod {
	
    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("init");
    }
	
}
