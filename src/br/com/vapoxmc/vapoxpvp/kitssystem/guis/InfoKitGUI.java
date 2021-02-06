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

public final class InfoKitGUI implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled())
			return;
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().startsWith("Informações: Kit ")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			if (event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.LEFT) {
				event.setCancelled(true);
				return;
			}

			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§8« §7Voltar ao Menu de Kits"))
				SeusKitsGUI.openGUI(player);

			Kit kit = system.getKitByName(event.getInventory().getName().replace("Informações: Kit ", ""));
			if (kit != null) {
				if (display.equals("§aSelecionar Kit")) {
					system.setKit(player, kit);
					player.closeInventory();
				}
			}
		}
	}

	public static void openGUI(Player player, Kit kit) {
		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem) || !system.isEnable())
			return;

		Inventory inv = Bukkit.createInventory(null, 54, "Informações: Kit " + kit.getName());
		Stack glass = new Stack(Material.THIN_GLASS).display(" ");

		for (int i = 0; i < 54; i++) {
			if (i > 37 && i < 43)
				continue;
			inv.setItem(i, glass);
		}

		inv.setItem(0, new Stack(Material.ARROW).display("§8« §7Voltar ao Menu de Kits"));
		inv.setItem(13, kit.getConstructedIcon());

		if (kit.isEnable() || player.hasPermission("command.kit.manage"))
			inv.addItem(new Stack(Material.RECORD_11).display("§aSelecionar Kit"));

		for (int i = 0; i < 5; i++) {
			int slot = inv.firstEmpty();
			if (slot < 0)
				break;

			inv.setItem(inv.firstEmpty(), new Stack(Material.STAINED_GLASS_PANE, 1, 15).display(" "));
		}

		inv.remove(glass.toItemStack());
		player.openInventory(inv);
	}
}