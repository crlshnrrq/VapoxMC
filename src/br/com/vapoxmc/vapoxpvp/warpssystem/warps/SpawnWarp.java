package br.com.vapoxmc.vapoxpvp.warpssystem.warps;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.gui.ShopGUI;
import br.com.vapoxmc.kitpvp.gui.StatusGUI;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.guis.SeusKitsGUI;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;
import br.com.vapoxmc.vapoxpvp.warpssystem.guis.WarpsGUI;

public final class SpawnWarp extends Warp implements Listener {

	public SpawnWarp() {
		super("Spawn", "Local de nascimento padrão do Servidor.", new Stack(Material.BEACON),
				Bukkit.getWorlds().get(0).getSpawnLocation(), new ArrayList<>(), true);
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setItem(1, WarpsGUI.ICON);
		inv.setItem(3, SeusKitsGUI.ICON);
		inv.setItem(5, ShopGUI.ICON);
		inv.setItem(7, StatusGUI.ICON.clone().owner(player.getName()));
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerTeleportWarp(PlayerTeleportWarpEvent event) {
		if (event.isCancelled())
			return;
		if (event.getWarp() instanceof SpawnWarp) {
			Player player = event.getPlayer();
			VapoxPvP.addProtection(player);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.VOID) {
			Player player = (Player) event.getEntity();
			if (system.getWarp(player) instanceof SpawnWarp)
				player.teleport(this.getLocation());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (system.getWarp(player) instanceof SpawnWarp)
			event.setCancelled(true);
	}
}