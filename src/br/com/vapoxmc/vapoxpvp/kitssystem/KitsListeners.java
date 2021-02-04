package br.com.vapoxmc.vapoxpvp.kitssystem;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.events.PlayerRemoveKitEvent;
import br.com.vapoxmc.vapoxpvp.kitssystem.events.PlayerSelectKitEvent;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;

public final class KitsListeners implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerSelectKit(PlayerSelectKitEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;
		Player player = event.getPlayer();

		ArrayList<Location> locations = new ArrayList<>();
		locations.add(new Location(Bukkit.getWorlds().get(0), -44, 67, 0));
		locations.add(new Location(Bukkit.getWorlds().get(0), -63, 64, 34));
		locations.add(new Location(Bukkit.getWorlds().get(0), 11, 67, -33));
		locations.add(new Location(Bukkit.getWorlds().get(0), 51, 67, -12));
		player.teleport(locations.get(new Random().nextInt(locations.size())));

		VapoxPvP.removeProtection(player);
		VapoxPvP.removeCombat(player);

		player.sendMessage("§a• §fVocê selecionou o Kit §a§n" + event.getKit().getName() + "§f.");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerRemoveKit(PlayerRemoveKitEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		event.getKit().removeCooldown(player);
		VapoxPvP.removeCombat(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;
		system.removeKit(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerQuit(PlayerQuitEvent event) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;
		system.removeKit(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerKick(PlayerKickEvent event) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;
		system.removeKit(event.getPlayer());
	}
}