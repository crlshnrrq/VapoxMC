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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class WarpsGUI implements Listener {

	public static final Stack ICON = new Stack(Material.COMPASS).display("§3» §bWarps §3«");

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasItem() && event.getItem().isSimilar(ICON.toItemStack()))
			openGUI(player);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled())
			return;
		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem) || !system.isEnable())
			return;
		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("Warps")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			if (event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.LEFT) {
				event.setCancelled(true);
				return;
			}

			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§fVisualizando: §bWarps")) {
				if (event.isLeftClick())
					WarpsHabilitadasGUI.openGUI(player);
				if (event.isRightClick())
					WarpsDesabilitadasGUI.openGUI(player);
			}

			if (display.startsWith("§bWarp ")) {
				Warp warp = system.getWarpByName(display.replace("§bWarp ", ""));
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

		Inventory inv = Bukkit.createInventory(null, 27, "Warps");
		Stack glass = new Stack(Material.THIN_GLASS).display(" ");
		for (int i = 0; i < 27; i++) {
			if (i > 9 && i < 17)
				continue;
			inv.setItem(i, glass);
		}

		inv.setItem(4,
				new Stack(Material.STAINED_GLASS_PANE, 1, 3).display("§fVisualizando: §bWarps").lore(" ",
						" §2» §bWarps", " §8» §aWarps Habilitadas", " §8» §cWarps Desabilitadas", " ",
						"§7Esquerdo §8» §7Avançar", "§7Direito §8» §7Retroceder"));

		List<Warp> warps = new ArrayList<>(system.getWarps());
		Collections.sort(warps, new Comparator<Warp>() {
			@Override
			public int compare(Warp warp, Warp warp2) {
				return (warp.getPlayers().size() >= warp2.getPlayers().size()) ? 1 : 0;
			}
		});
		warps.forEach(warp -> {
			Stack icon = warp.getIcon().clone().hideAttributes().hidePotionEffects().hideEnchants()
					.display("§bWarp " + warp.getName()).lore("§7" + warp.getDescription(), 5, 30);
			if (warp.isEnable())
				icon.addLore(" ", "§7" + warp.getOnlines());
			else
				icon.addLore(" ", "§cEsta Warp está desativada.");
			inv.addItem(icon);
		});

		inv.remove(glass.toItemStack());
		player.openInventory(inv);
	}
}