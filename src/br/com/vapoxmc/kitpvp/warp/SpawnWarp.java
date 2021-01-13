package br.com.vapoxmc.kitpvp.warp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.gui.SeusKitsGUI;
import br.com.vapoxmc.kitpvp.gui.ShopGUI;
import br.com.vapoxmc.kitpvp.gui.WarpsGUI;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class SpawnWarp extends Warp implements Listener {

	public SpawnWarp() {
		super("Spawn", new Stack(Material.BEACON), Bukkit.getWorlds().get(0).getSpawnLocation());
	}

	@Override
	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		super.giveItems(player);

		inv.setItem(2, WarpsGUI.ICON);
		inv.setItem(4, SeusKitsGUI.ICON);
		inv.setItem(6, ShopGUI.ICON);

		VapoxPvP.addProtection(player);
	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && VapoxPvP.getWarp((Player) event.getEntity()) instanceof SpawnWarp
				&& event.getCause() == DamageCause.VOID)
			event.getEntity().teleport(this.getLocation());
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (VapoxPvP.getWarp(player) instanceof SpawnWarp)
			event.setCancelled(true);
	}
}