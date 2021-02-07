package br.com.vapoxmc.vapoxpvp.warpssystem.guis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class WarpsHabilitadasGUI implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("Warps Habilitadas")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			if (event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.LEFT) {
				event.setCancelled(true);
				return;
			}

			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§fVisualizando: §aWarps Habilitadas")) {
				if (event.isLeftClick())
					WarpsDesabilitadasGUI.openGUI(player);
				if (event.isRightClick())
					WarpsGUI.openGUI(player);
			}

			if (display.startsWith("§aWarp ")) {
				Warp warp = system.getWarpByName(display.replace("§aWarp ", ""));
				if (warp != null && !system.getWarp(player).getName().equals(warp.getName())
						&& system.setWarp(player, warp)) {
					player.sendMessage("§a• §fVocê foi §ateleportado §fpara a Warp §a§n" + warp.getName() + "§f.");
					player.closeInventory();
				}
			}
		}
	}

	public static void openGUI(Player player) {
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;

		Inventory inv = Bukkit.createInventory(null, 27, "Warps Habilitadas");
		Stack glass = new Stack(Material.THIN_GLASS).display(" ");
		for (int i = 0; i < 27; i++) {
			if (i > 9 && i < 17)
				continue;
			inv.setItem(i, glass);
		}

		inv.setItem(4,
				new Stack(Material.STAINED_GLASS_PANE, 1, 5).display("§fVisualizando: §aWarps Habilitadas").lore(" ",
						" §8» §bWarps", " §2» §aWarps Habilitadas", " §8» §cWarps Desabilitadas", " ",
						"§7Esquerdo §8» §7Avançar", "§7Direito §8» §7Retroceder"));

		List<Warp> warps = new ArrayList<>();
		system.getWarps().stream().filter(warp -> warp.isEnable()).forEach(warp -> warps.add(warp));
		Collections.sort(warps, new Comparator<Warp>() {
			@Override
			public int compare(Warp warp, Warp warp2) {
				return (warp.getPlayers().size() >= warp2.getPlayers().size()) ? 1 : 0;
			}
		});
		warps.forEach(warp -> inv.addItem(warp.getIcon().clone().hideAttributes().hidePotionEffects().hideEnchants()
				.display("§aWarp " + warp.getName()).lore("§7" + warp.getDescription(), 5, 30)
				.addLore(" ", "§7" + warp.getOnlines())));

		inv.remove(glass.toItemStack());
		player.openInventory(inv);
	}
}