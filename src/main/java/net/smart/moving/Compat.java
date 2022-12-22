package net.smart.moving;

import cpw.mods.fml.common.Loader;
import cuchaz.ships.EntityShip;
import cuchaz.ships.ShipLocator;
import jp.mc.ancientred.starminer.api.GravityDirection;
import jp.mc.ancientred.starminer.core.entity.ExtendedPropertyGravity;
import net.minecraft.entity.Entity;

public class Compat {

	private static boolean isStarMinerPresent;
	private static boolean isShipsModPresent;
	
	public static void init() {
		isStarMinerPresent = Loader.isModLoaded("modJ_StarMiner");
		isShipsModPresent = Loader.isModLoaded("cuchaz.ships");
	}
	
	public static boolean isStarMinerGravitized(Entity player) {
		return isStarMinerPresent && ExtendedPropertyGravity.getExtendedPropertyGravity(player).gravityDirection != GravityDirection.upTOdown_YN;
	}
	
	public static boolean isOnCuchazShip(Entity player) {
		if(isShipsModPresent) {
			for (EntityShip ship : ShipLocator.getFromEntityLocation(player)) {
				if (ship.getCollider().isEntityAboard(player)) {
					return true;
				}
			}
		}
		return false;
	}

}
