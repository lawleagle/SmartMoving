package net.smart.moving;

import cpw.mods.fml.common.Loader;
import cuchaz.ships.EntityShip;
import cuchaz.ships.ShipLocator;
import ganymedes01.etfuturum.api.elytra.IElytraPlayer;
import jp.mc.ancientred.starminer.api.GravityDirection;
import jp.mc.ancientred.starminer.core.entity.ExtendedPropertyGravity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Compat {

	private static boolean isStarMinerPresent;
	private static boolean isShipsModPresent;
	private static boolean isEtFuturumRequiemElytraPresent;
	
	public static void init() {
		isStarMinerPresent = Loader.isModLoaded("modJ_StarMiner");
		isShipsModPresent = Loader.isModLoaded("cuchaz.ships");
		isEtFuturumRequiemElytraPresent = Loader.isModLoaded("etfuturum") && classExists("ganymedes01.etfuturum.api.elytra.IElytraPlayer");
	}
	
	public static boolean isBlockedByIncompatibility(EntityPlayer sp) {
		return isStarMinerGravitized(sp) || isOnCuchazShip(sp) || isElytraFlying(sp);
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
	
	public static boolean isElytraFlying(Entity player) {
		return isEtFuturumRequiemElytraPresent && ((IElytraPlayer)player).etfu$isElytraFlying();
	}
	
	private static boolean classExists(String className) {
		return Compat.class.getClassLoader().getResourceAsStream(className.replaceAll("\\.", "/") + ".class") != null;
	}

}
