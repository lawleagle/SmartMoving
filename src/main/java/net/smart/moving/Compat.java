package net.smart.moving;

import cpw.mods.fml.common.Loader;
import jp.mc.ancientred.starminer.api.GravityDirection;
import jp.mc.ancientred.starminer.core.entity.ExtendedPropertyGravity;
import net.minecraft.entity.Entity;

public class Compat {

	private static boolean isStarMinerPresent;
	
	public static void init() {
		isStarMinerPresent = Loader.isModLoaded("modJ_StarMiner");
	}
	
	public static boolean isStarMinerGravitized(Entity player) {
		return isStarMinerPresent && ExtendedPropertyGravity.getExtendedPropertyGravity(player).gravityDirection != GravityDirection.upTOdown_YN;
	}

}
