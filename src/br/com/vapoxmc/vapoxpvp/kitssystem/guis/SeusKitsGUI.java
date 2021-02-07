package br.com.vapoxmc.vapoxpvp.kitssystem.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class SeusKitsGUI implements Listener {

	public static final Stack ICON = new Stack(Material.STORAGE_MINECART).display("§6» §eSeus Kits §6«");

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Player player = event.getPlayer();
		if (event.hasItem() && event.getItem().isSimilar(ICON.toItemStack())) {
			event.setCancelled(true);
			if (event.getAction().name().contains("RIGHT"))
				openGUI(player);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("Seus Kits")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			if (event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.LEFT) {
				event.setCancelled(true);
				return;
			}

			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§fVisualizando: §eSeus Kits")) {
				if (event.isLeftClick())
					KitsHabilitadosGUI.openGUI(player);
				if (event.isRightClick())
					TodosOsKitsGUI.openGUI(player);
			}

			if (display.startsWith("§eKit ")) {
				Kit kit = system.getKitByName(display.replace("§eKit ", ""));
				if (kit != null) {
					if (event.isLeftClick()) {
						if (kit.isEnable() || player.hasPermission("command.kit.manage"))
							system.setKit(player, kit);
						else
							player.sendMessage("§c• §fO Kit §c§n" + kit.getName() + " §festá §cdesabilitado§f.");
						player.closeInventory();
					}
					if (event.isRightClick())
						InfoKitGUI.openGUI(player, kit);
				}
			}
		}
	}

	public static void openGUI(Player player) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Inventory inv = Bukkit.createInventory(null, 54, "Seus Kits");
		Stack glass = new Stack(Material.THIN_GLASS).display(" ");

		for (int i = 0; i < 54; i++) {
			if ((i > 9 && i < 17) || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44))
				continue;
			inv.setItem(i, glass);
		}

		inv.setItem(4,
				new Stack(Material.STAINED_GLASS_PANE, 1, 4).display("§fVisualizando: §eSeus Kits").lore(" ",
						" §8» §6Todos os Kits", " §2» §eSeus Kits", " §8» §aKits Habilitados",
						" §8» §cKits Desabilitados", " ", "§7Esquerdo §8» §7Avançar", "§7Direito §8» §7Retroceder"));

		system.getKits().stream().filter(kit -> player.hasPermission(kit.getPermission())).forEach(kit -> {
			Stack icon = kit.getIcon().clone().hideAttributes().display("§eKit " + kit.getName())
					.lore("§7" + kit.getDescription(), 5, 30);
			if (!kit.isEnable())
				icon.addLore(" ", "§cEste Kit está desativado.");
			icon.addLore(" ", "§7Esquerdo §8» §7Selecionar", "§7Direito §8» §7Informações");
			inv.addItem(icon);
		});

		inv.remove(glass.toItemStack());
		player.openInventory(inv);
	}
}