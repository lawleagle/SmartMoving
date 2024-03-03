package net.smart.moving;

import cpw.mods.fml.common.Loader;
import cuchaz.ships.EntityShip;
import cuchaz.ships.ShipLocator;
import ganymedes01.etfuturum.api.elytra.IElytraPlayer;
import jp.mc.ancientred.starminer.api.Gravity;
import jp.mc.ancientred.starminer.api.GravityDirection;
import jp.mc.ancientred.starminer.core.entity.ExtendedPropertyGravity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Compat {

	private static boolean isStarMinerPresent;
	private static boolean isShipsModPresent;
	private static boolean isEtFuturumRequiemPresent;
	private static boolean isEtFuturumRequiemElytraPresent;
	
	public static void init()
	{
		isStarMinerPresent = Loader.isModLoaded("modJ_StarMiner");
		isShipsModPresent = Loader.isModLoaded("cuchaz.ships");
		isEtFuturumRequiemPresent = Loader.isModLoaded("etfuturum");
		isEtFuturumRequiemElytraPresent = isEtFuturumRequiemPresent && classExists("ganymedes01.etfuturum.api.elytra.IElytraPlayer");
	}
	
	private static boolean classExists(String className)
	{
		return Compat.class.getClassLoader().getResourceAsStream(className.replaceAll("\\.", "/") + ".class") != null;
	}
	
	public static boolean isBlockedByIncompatibility(EntityPlayer sp)
	{
		return isStarMinerGravitized(sp) || isOnCuchazShip(sp) || isElytraFlying(sp) || isSpectator(sp);
	}
	
	public static boolean isStarMinerGravitized(Entity player)
	{
		return isStarMinerPresent && StarMinerCompat.isGravitized(player);
	}
	
	public static boolean isOnCuchazShip(Entity player)
	{
		return isShipsModPresent && ShipsCompat.isOnShip(player);
	}
	
	public static boolean isElytraFlying(Entity player)
	{
		return isEtFuturumRequiemElytraPresent && EtFuturumRequiemElytraCompat.isElytraFlying(player);
	}
	
	public static boolean isSpectator(Entity player)
	{
		return isEtFuturumRequiemPresent && Minecraft.getMinecraft().playerController.currentGameType.getID() == 3;
	}
	
	private static class StarMinerCompat
	{
		public static boolean isGravitized(Entity player)
		{
			Gravity gravity = ExtendedPropertyGravity.getExtendedPropertyGravity(player);
			return gravity.gravityDirection != GravityDirection.upTOdown_YN || gravity.isZeroGravity();
		}
	}
	
	private static class ShipsCompat
	{
		public static boolean isOnShip(Entity player)
		{
			for (EntityShip ship : ShipLocator.getFromEntityLocation(player))
			{
				if (ship.getCollider().isEntityAboard(player))
				{
					return true;
				}
			}
			return false;
		}	
	}
	
	private static class EtFuturumRequiemElytraCompat
	{
		public static boolean isElytraFlying(Entity player)
		{
			return player instanceof IElytraPlayer && ((IElytraPlayer)player).etfu$isElytraFlying();
		}
	}

}
