package br.com.vapoxmc.vapoxpvp.warpssystem;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public class Warp {

	private final String name, description;
	private final Stack icon;
	private final Location location;
	private final ArrayList<UUID> players;
	private boolean enable;

	public Warp(String name, String description, Stack icon, Location location, ArrayList<UUID> players,
			boolean enable) {
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.location = location;
		this.players = players;
		this.enable = enable;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Stack getIcon() {
		return this.icon;
	}

	public Stack getConstructedIcon() {
		Stack icon = this.getIcon().clone().hideAttributes().hidePotionEffects().hideEnchants()
				.display("§fWarp " + this.getName()).lore("§7" + this.getDescription(), 5, 30);
		if (!this.isEnable())
			icon.addLore(" ", "§cEsta Warp está desativada.");
		return icon;
	}

	public Location getLocation() {
		return this.location;
	}

	public String getOnlines() {
		int size = this.getPlayers().size();
		return size + " jogador" + (size != 1 ? "es" : "");
	}

	public ArrayList<UUID> getPlayers() {
		return this.players;
	}

	public boolean hasPlayer(Player player) {
		return this.getPlayers().contains(player.getUniqueId());
	}

	public void addPlayer(Player player) {
		if (!this.hasPlayer(player))
			this.getPlayers().add(player.getUniqueId());
	}

	public void removePlayer(Player player) {
		this.getPlayers().remove(player.getUniqueId());
	}

	public boolean isEnable() {
		return this.enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		inv.setArmorContents(null);
		inv.clear();

		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(false);

		player.setFireTicks(0);
		player.resetMaxHealth();
		player.setHealth(20D);
		player.setFoodLevel(20);

		player.closeInventory();
	}
}