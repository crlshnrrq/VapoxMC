package br.com.vapoxmc.kitpvp.warp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.gui.SeusKitsGUI;
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
		inv.setItem(6, new Stack(Material.EMERALD).display("§1» §9Loja de moedas."));
	}
}