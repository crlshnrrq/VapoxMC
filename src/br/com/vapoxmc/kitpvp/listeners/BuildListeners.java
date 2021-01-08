package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class BuildListeners implements Listener {

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.hasBuild(player))
			event.setCancelled(false);
		else
			event.setCancelled(true);
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.hasBuild(player))
			event.setCancelled(false);
		else
			event.setCancelled(true);
	}
}