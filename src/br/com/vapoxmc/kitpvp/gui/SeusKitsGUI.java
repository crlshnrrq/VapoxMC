package br.com.vapoxmc.kitpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.kit.Kit;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class SeusKitsGUI implements Listener {

	public static final Stack ICON = new Stack(Material.STORAGE_MINECART).display("§2» §aSelecione um kit.");

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasItem() && event.getItem().isSimilar(ICON.toItemStack()) && !VapoxPvP.hasKit(player))
			openGUI(player);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("§e§lMenu de Kits")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§a§l[STATUS]")) {
			}

			if (!VapoxPvP.hasKit(player) && display.startsWith("§a")) {
				Kit kit = VapoxPvP.getKitByName(ChatColor.stripColor(display));
				if (kit != null && VapoxPvP.setKit(player, kit)) {
					player.sendMessage("§e§l[KIT] §eVocê pegou o kit \"" + kit.getName().toLowerCase() + "\"");
					player.closeInventory();
				}
			}
		}
	}

	public static void openGUI(Player player) {
		Inventory inv = Bukkit.createInventory(null, 45, "§e§lMenu de Kits");

		inv.setItem(4, new Stack(Material.SKULL_ITEM, 1, 3).owner(player.getName()).display("§a§l[STATUS]")
				.lore("§fKills: §a0", "§fDeaths: §a0", "§f(Clique para mais informações)"));

		Kit pvp = VapoxPvP.getKitByName("PvP");
		if (player.hasPermission(pvp.getPermission()))
			inv.setItem(10, pvp.getIcon().clone().display("§a" + pvp.getName()).lore("§7" + pvp.getDescription()));
		else
			inv.setItem(10, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit kangaroo = VapoxPvP.getKitByName("Kangaroo");
		if (player.hasPermission(kangaroo.getPermission()))
			inv.setItem(11, kangaroo.getIcon().clone().display("§a" + kangaroo.getName())
					.lore("§7" + kangaroo.getDescription()));
		else
			inv.setItem(11, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit ninja = VapoxPvP.getKitByName("Ninja");
		if (player.hasPermission(ninja.getPermission()))
			inv.setItem(12,
					ninja.getIcon().clone().display("§a" + ninja.getName()).lore("§7" + ninja.getDescription()));
		else
			inv.setItem(12, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit ajnin = VapoxPvP.getKitByName("Ajnin");
		if (player.hasPermission(ajnin.getPermission()))
			inv.setItem(13,
					ajnin.getIcon().clone().display("§a" + ajnin.getName()).lore("§7" + ajnin.getDescription()));
		else
			inv.setItem(13, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit stomper = VapoxPvP.getKitByName("Stomper");
		if (player.hasPermission(stomper.getPermission()))
			inv.setItem(14,
					stomper.getIcon().clone().display("§a" + stomper.getName()).lore("§7" + stomper.getDescription()));
		else
			inv.setItem(14, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit anchor = VapoxPvP.getKitByName("Anchor");
		if (player.hasPermission(anchor.getPermission()))
			inv.setItem(15,
					anchor.getIcon().clone().display("§a" + anchor.getName()).lore("§7" + anchor.getDescription()));
		else
			inv.setItem(15, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit viking = VapoxPvP.getKitByName("Viking");
		if (player.hasPermission(viking.getPermission()))
			inv.setItem(16,
					viking.getIcon().clone().display("§a" + viking.getName()).lore("§7" + viking.getDescription()));
		else
			inv.setItem(16, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit archer = VapoxPvP.getKitByName("Archer");
		if (player.hasPermission(archer.getPermission()))
			inv.setItem(19,
					archer.getIcon().clone().display("§a" + archer.getName()).lore("§7" + archer.getDescription()));
		else
			inv.setItem(19, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit magma = VapoxPvP.getKitByName("Magma");
		if (player.hasPermission(magma.getPermission()))
			inv.setItem(20,
					magma.getIcon().clone().display("§a" + magma.getName()).lore("§7" + magma.getDescription()));
		else
			inv.setItem(20, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit snail = VapoxPvP.getKitByName("Snail");
		if (player.hasPermission(snail.getPermission()))
			inv.setItem(21,
					snail.getIcon().clone().display("§a" + snail.getName()).lore("§7" + snail.getDescription()));
		else
			inv.setItem(21, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit viper = VapoxPvP.getKitByName("Viper");
		if (player.hasPermission(viper.getPermission()))
			inv.setItem(22,
					viper.getIcon().clone().display("§a" + viper.getName()).lore("§7" + viper.getDescription()));
		else
			inv.setItem(22, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit thor = VapoxPvP.getKitByName("Thor");
		if (player.hasPermission(thor.getPermission()))
			inv.setItem(23, thor.getIcon().clone().display("§a" + thor.getName()).lore("§7" + thor.getDescription()));
		else
			inv.setItem(23, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit urgal = VapoxPvP.getKitByName("Urgal");
		if (player.hasPermission(urgal.getPermission()))
			inv.setItem(24,
					urgal.getIcon().clone().display("§a" + urgal.getName()).lore("§7" + urgal.getDescription()));
		else
			inv.setItem(24, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit monk = VapoxPvP.getKitByName("Monk");
		if (player.hasPermission(monk.getPermission()))
			inv.setItem(25, monk.getIcon().clone().display("§a" + monk.getName()).lore("§7" + monk.getDescription()));
		else
			inv.setItem(25, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit antiStomper = VapoxPvP.getKitByName("AntiStomper");
		if (player.hasPermission(antiStomper.getPermission()))
			inv.setItem(28, antiStomper.getIcon().clone().display("§a" + antiStomper.getName())
					.lore("§7" + antiStomper.getDescription()));
		else
			inv.setItem(28, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit scout = VapoxPvP.getKitByName("Scout");
		if (player.hasPermission(scout.getPermission()))
			inv.setItem(29,
					scout.getIcon().clone().display("§a" + scout.getName()).lore("§7" + scout.getDescription()));
		else
			inv.setItem(29, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit critical = VapoxPvP.getKitByName("Critical");
		if (player.hasPermission(critical.getPermission()))
			inv.setItem(30, critical.getIcon().clone().display("§a" + critical.getName())
					.lore("§7" + critical.getDescription()));
		else
			inv.setItem(30, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		Kit fisherman = VapoxPvP.getKitByName("Fisherman");
		if (player.hasPermission(fisherman.getPermission()))
			inv.setItem(31, fisherman.getIcon().clone().display("§a" + fisherman.getName())
					.lore("§7" + fisherman.getDescription()));
		else
			inv.setItem(31, new Stack(Material.STAINED_GLASS_PANE, 1, 14).display("§cVocê não possui esse kit."));

		player.openInventory(inv);
	}
}