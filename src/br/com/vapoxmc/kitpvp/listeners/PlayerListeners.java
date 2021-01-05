package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.warp.Warp;

public final class PlayerListeners implements Listener {

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);

		Warp warp = VapoxPvP.getDefaultWarp();
		VapoxPvP.setWarp(player, warp);

		try {
			player.teleport(new Location(Bukkit.getWorlds().get(0), 1000, 102, 1000));
		} catch (Exception ex) {
		}

		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.teleport(warp.getLocation()), 10L);
		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> warp.giveItems(player), 20L);
	}

	@EventHandler
	private void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Warp warp = VapoxPvP.getDefaultWarp();

		VapoxPvP.setWarp(player, warp);
		Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> player.teleport(warp.getLocation()), 10L);
	}
}