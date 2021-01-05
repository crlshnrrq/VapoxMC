package br.com.vapoxmc.kitpvp.warp;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public class Warp {

	private final String name;
	private final Stack icon;
	private final Location location;

	public Warp(String name, Stack icon, Location location) {
		this.name = name;
		this.icon = icon;
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public Stack getIcon() {
		return this.icon;
	}

	public Location getLocation() {
		return this.location;
	}

	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		inv.setArmorContents(null);
		inv.clear();

		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(false);

		player.resetMaxHealth();
		player.setHealth(20D);
		player.setFoodLevel(20);

		player.closeInventory();
	}
}