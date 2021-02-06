package br.com.vapoxmc.vapoxpvp.kitssystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public class Kit {

	private final String name, description, permission;
	private final Stack icon;
	private final List<Stack> items;
	private final HashMap<UUID, Long> cooldownMap;
	private boolean enable;

	public Kit(String name, String description, Stack icon, boolean enable) {
		this(name, description, icon, "kit." + name, enable, new ArrayList<>());
	}

	public Kit(String name, String description, Stack icon, String permission, boolean enable) {
		this(name, description, icon, permission, enable, new ArrayList<>());
	}

	public Kit(String name, String description, Stack icon, boolean enable, Stack... items) {
		this(name, description, icon, "kit." + name, enable, items);
	}

	public Kit(String name, String description, Stack icon, boolean enable, List<Stack> items) {
		this(name, description, icon, "kit." + name, enable, items);
	}

	public Kit(String name, String description, Stack icon, String permission, boolean enable, Stack... items) {
		this(name, description, icon, permission, enable, Arrays.asList(items));
	}

	public Kit(String name, String description, Stack icon, String permission, boolean enable, List<Stack> items) {
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.permission = permission.toLowerCase();
		this.items = items;
		this.cooldownMap = new HashMap<>();
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
		Stack icon = this.icon.clone().hideAttributes().display("§fKit " + this.getName())
				.lore("§7" + this.getDescription(), 5, 30);
		if (!this.isEnable())
			icon.addLore(" ", "§cEste Kit está desativado.");
		return icon;
	}

	public String getPermission() {
		return this.permission;
	}

	public boolean isEnable() {
		return this.enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Stack> getItems() {
		return this.items;
	}

	public boolean hasCooldown(Player player) {
		return this.cooldownMap.containsKey(player.getUniqueId());
	}

	public String getRemaingCooldown(Player player) {
		long time = this.getCooldown(player);
		if (time <= 0)
			time = 1;
		return time + " segundo" + (time > 1 ? "s" : "");
	}

	public long getCooldown(Player player) {
		long time = System.currentTimeMillis() + 1000L;
		if (this.hasCooldown(player))
			time = this.cooldownMap.get(player.getUniqueId());
		time = (time - System.currentTimeMillis()) / 1000L;
		if (time <= 0)
			this.removeCooldown(player);
		return time;
	}

	public void addCooldown(Player player, int seconds) {
		this.cooldownMap.put(player.getUniqueId(), (seconds * 1000L) + System.currentTimeMillis());
	}

	public void removeCooldown(Player player) {
		this.cooldownMap.remove(player.getUniqueId());
	}

	public void giveItems(Player player) {
	}

	public void applyKit(Player player) {
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

		inv.setItem(0, new Stack(Material.STONE_SWORD).display("§7» §fEspada §7«"));
		inv.setItem(8, new Stack(Material.COMPASS).display("§7» §fBússola §7«"));
		giveItems(player);

		inv.setItem(13, new Stack(Material.BOWL, 64).display("§7» §fRecraft §7«"));
		inv.setItem(14, new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft §7«"));
		inv.setItem(15, new Stack(Material.BROWN_MUSHROOM, 64).display("§7» §fRecraft §7«"));

		for (int i = 0; i < 36; i++)
			inv.addItem(new Stack(Material.MUSHROOM_SOUP));

		player.closeInventory();
	}
}