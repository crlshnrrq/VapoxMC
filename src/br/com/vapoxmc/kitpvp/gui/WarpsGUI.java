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

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.warp.UMvUMWarp;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class WarpsGUI implements Listener {

	public static final Stack ICON = new Stack(Material.COMPASS).display("§3» §bSeleciona uma warp.");

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasItem() && event.getItem().isSimilar(ICON.toItemStack()))
			openGUI(player);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("§e§lMenu de Warps")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.startsWith("§a")) {
				Warp warp = ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
						.getWarpByName(ChatColor.stripColor(display));
				if (warp != null
						&& ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(player, warp)) {
					player.sendMessage(
							"§a§l[WARP] §fVocê foi teleportado para a warp §a" + warp.getName().toLowerCase() + "§f!");
					if (warp instanceof UMvUMWarp)
						player.sendMessage("§a§l[WARP] §fÉ necessário clicar §a§lduas §fvezes para confirmar o duelo.");
					player.closeInventory();
				}
			}
		}
	}

	public static void openGUI(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, "§e§lMenu de Warps");

		Warp knockback = ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getWarpByName("Knockback");
		inv.setItem(9,
				knockback.getConstructedIcon().clone().display("§aKnockback").addLore(" ",
						"§fAcesso a warp: §a§l" + knockback.getName().toUpperCase() + "§7.",
						"§fJogadores: §a" + ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
								.getPlayersInWarp(knockback)));

		Warp fps = ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getWarpByName("FPS");
		inv.setItem(10, fps.getConstructedIcon().clone().display("§a" + fps.getName()).addLore(" ",
				"§fAcesso a warp: §a§l" + fps.getName().toUpperCase() + "§7.", "§fJogadores: §a"
						+ ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getPlayersInWarp(fps)));

		Warp fisherman = ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getWarpByName("Fisherman");
		inv.setItem(12,
				fisherman.getConstructedIcon().clone().display("§a" + fisherman.getName()).addLore(" ",
						"§fAcesso a warp: §a§l" + fisherman.getName().toUpperCase() + "§7.",
						"§fJogadores: §a" + ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
								.getPlayersInWarp(fisherman)));

		Warp potpvp = ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getWarpByName("PotPvP");
		inv.setItem(14, potpvp.getConstructedIcon().clone().display("§a" + potpvp.getName()).addLore(" ",
				"§fAcesso a warp: §a§l" + potpvp.getName() + "§7.", "§fJogadores: §a"
						+ ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getPlayersInWarp(potpvp)));

		Warp lavaChallenge = ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
				.getWarpByName("Lava Challenge");
		inv.setItem(16,
				lavaChallenge.getConstructedIcon().clone().display("§a" + lavaChallenge.getName()).addLore(" ",
						"§fAcesso a warp: §a§l" + lavaChallenge.getName().toUpperCase(),
						"§fJogadores: §a" + ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
								.getPlayersInWarp(lavaChallenge)));

		Warp umvum = ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getWarpByName("1v1");
		inv.setItem(17, umvum.getConstructedIcon().clone().display("§a" + umvum.getName()).addLore(" ",
				"§fAcesso a warp: §a§l" + umvum.getName(), "§fJogadores: §a"
						+ ((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).getPlayersInWarp(umvum)));

		player.openInventory(inv);
	}
}