package br.com.vapoxmc.vapoxpvp.warpssystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.warp.EventoWarp;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.events.PlayerSelectKitEvent;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class WarpsListeners implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerSelectKit(PlayerSelectKitEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		Player player = event.getPlayer();
		system.removeWarp(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerRespawn(PlayerRespawnEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		Warp warp = system.getWarp(player);
		if (!system.hasWarp(player) || warp instanceof EventoWarp)
			warp = system.getDefaultWarp();
		Location loc = warp.getLocation();

		((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player, warp);
		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.teleport(loc), 10L);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerQuit(PlayerQuitEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		Player player = event.getPlayer();
		system.removeWarp(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerKick(PlayerKickEvent event) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		Player player = event.getPlayer();
		system.removeWarp(player);
	}
}