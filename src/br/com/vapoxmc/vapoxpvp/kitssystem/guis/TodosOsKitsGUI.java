package br.com.vapoxmc.vapoxpvp.kitssystem.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class TodosOsKitsGUI implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("Todos os Kits")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			if (event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.LEFT) {
				event.setCancelled(true);
				return;
			}

			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§fVisualizando: §6Todos os Kits")) {
				if (event.isLeftClick())
					SeusKitsGUI.openGUI(player);
				if (event.isRightClick())
					KitsDesabilitadosGUI.openGUI(player);
			}

			if (display.startsWith("§6Kit ")) {
				Kit kit = system.getKitByName(display.replace("§6Kit ", ""));
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

		Inventory inv = Bukkit.createInventory(null, 54, "Todos os Kits");
		Stack glass = new Stack(Material.THIN_GLASS).display(" ");

		for (int i = 0; i < 9; i++)
			inv.setItem(i, glass);

		inv.setItem(4,
				new Stack(Material.STAINED_GLASS_PANE, 1, 1).display("§fVisualizando: §6Todos os Kits").lore(" ",
						" §2» §6Todos os Kits", " §8» §eSeus Kits", " §8» §aKits Habilitados",
						" §8» §cKits Desabilitados", " ", "§7Esquerdo §8» §7Avançar", "§7Direito §8» §7Retroceder"));

		system.getKits().forEach(kit -> {
			Stack icon = kit.getIcon().clone().hideAttributes().display("§6Kit " + kit.getName())
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