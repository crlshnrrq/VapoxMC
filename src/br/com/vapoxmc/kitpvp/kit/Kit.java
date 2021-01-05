package br.com.vapoxmc.kitpvp.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.vapoxmc.kitpvp.utils.Stack;

public class Kit {

	private final String name, description, permission;
	private final Stack icon;
	private final List<Stack> items;

	public Kit(String name, String description, Stack icon) {
		this(name, description, icon, "kit." + name, new ArrayList<>());
	}

	public Kit(String name, String description, Stack icon, String permission) {
		this(name, description, icon, permission, new ArrayList<>());
	}

	public Kit(String name, String description, Stack icon, Stack... items) {
		this(name, description, icon, "kit." + name, items);
	}

	public Kit(String name, String description, Stack icon, List<Stack> items) {
		this(name, description, icon, "kit." + name, items);
	}

	public Kit(String name, String description, Stack icon, String permission, Stack... items) {
		this(name, description, icon, permission, Arrays.asList(items));
	}

	public Kit(String name, String description, Stack icon, String permission, List<Stack> items) {
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.permission = permission;
		this.items = items;
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

	public String getPermission() {
		return this.permission;
	}

	public List<Stack> getItems() {
		return this.items;
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

		player.resetMaxHealth();
		player.setHealth(20D);
		player.setFoodLevel(20);

		inv.setItem(0, new Stack(Material.STONE_SWORD).display("§aEspada §7(§f§l" + this.getName() + "§7)")
				.lore("§7Clique com o §6esquerdo §7para atacar um oponente."));
		inv.setItem(8, new Stack(Material.COMPASS).display("§7» §fBússola"));
		giveItems(player);

		inv.setItem(13, new Stack(Material.RED_MUSHROOM, 64).display("§7» §fRecraft"));
		inv.setItem(14, new Stack(Material.BROWN_MUSHROOM, 64).display("§7» §fRecraft"));
		inv.setItem(15, new Stack(Material.BOWL, 64).display("§7» §fRecraft"));

		for (int i = 0; i < 36; i++)
			inv.addItem(new Stack(Material.MUSHROOM_SOUP));

		player.closeInventory();
	}
}