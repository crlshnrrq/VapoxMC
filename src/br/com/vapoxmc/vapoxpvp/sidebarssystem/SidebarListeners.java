package br.com.vapoxmc.vapoxpvp.sidebarssystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.crlshnrrq.screenshareplugin.events.TimeSecondEvent;

import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class SidebarListeners implements Listener {

	@EventHandler
	private void onTimeSecond(TimeSecondEvent event) {
		SidebarsSystem system = (SidebarsSystem) KitPvP.getGeneralSystem().getSystemByName("Sidebars");
		if (system == null || !(system instanceof SidebarsSystem) || !system.isEnable())
			return;

		Bukkit.getOnlinePlayers().forEach(system::updateSidebar);
	}

	@EventHandler
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		SidebarsSystem system = (SidebarsSystem) KitPvP.getGeneralSystem().getSystemByName("Sidebars");
		if (system == null || !(system instanceof SidebarsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		Warp warp = event.getWarp();
		Sidebar sidebar = system.getSidebarByName(warp.getName());

		if (sidebar == null)
			sidebar = system.getDefaultSidebar();
		system.setSidebar(player, sidebar);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerQuit(PlayerQuitEvent event) {
		SidebarsSystem system = (SidebarsSystem) KitPvP.getGeneralSystem().getSystemByName("Sidebars");
		if (system == null || !(system instanceof SidebarsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		system.removeSidebar(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerKick(PlayerKickEvent event) {
		SidebarsSystem system = (SidebarsSystem) KitPvP.getGeneralSystem().getSystemByName("Sidebars");
		if (system == null || !(system instanceof SidebarsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		system.removeSidebar(player);
	}
}