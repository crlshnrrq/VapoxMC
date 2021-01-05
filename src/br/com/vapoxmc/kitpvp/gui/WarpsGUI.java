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
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.warp.UMvUMWarp;
import br.com.vapoxmc.kitpvp.warp.Warp;

public final class WarpsGUI implements Listener {

	public static final Stack ICON = new Stack(Material.COMPASS).display("§3» §bSeleciona uma warp.");

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasItem() && event.getItem().isSimilar(ICON.toItemStack()) && !VapoxPvP.hasKit(player))
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
				Warp warp = VapoxPvP.getWarpByName(ChatColor.stripColor(display));
				if (warp != null && VapoxPvP.setWarp(player, warp)) {
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

		Warp knockback = VapoxPvP.getWarpByName("Knockback");
		inv.setItem(9,
				knockback.getIcon().clone().display("§aKnockback").lore(
						"§fAcesso a warp: §a§l" + knockback.getName().toUpperCase() + "§7.",
						"§fJogadores: §a" + VapoxPvP.getPlayersInWarp(knockback)));

		Warp fps = VapoxPvP.getWarpByName("FPS");
		inv.setItem(10,
				fps.getIcon().clone().display("§a" + fps.getName()).lore(
						"§fAcesso a warp: §a§l" + fps.getName().toUpperCase() + "§7.",
						"§fJogadores: §a" + VapoxPvP.getPlayersInWarp(fps)));

		Warp fisherman = VapoxPvP.getWarpByName("Fisherman");
		inv.setItem(12,
				fisherman.getIcon().clone().display("§a" + fisherman.getName()).lore(
						"§fAcesso a warp: §a§l" + fisherman.getName().toUpperCase() + "§7.",
						"§fJogadores: §a" + VapoxPvP.getPlayersInWarp(fisherman)));

		Warp potpvp = VapoxPvP.getWarpByName("PotPvP");
		inv.setItem(14,
				potpvp.getIcon().clone().display("§a" + potpvp.getName()).lore(
						"§fAcesso a warp: §a§l" + potpvp.getName() + "§7.",
						"§fJogadores: §a" + VapoxPvP.getPlayersInWarp(potpvp)));

		Warp lavaChallenge = VapoxPvP.getWarpByName("Lava Challenge");
		inv.setItem(16,
				lavaChallenge.getIcon().clone().display("§a" + lavaChallenge.getName()).lore(
						"§fAcesso a warp: §a§l" + lavaChallenge.getName().toUpperCase(),
						"§fJogadores: §a" + VapoxPvP.getPlayersInWarp(lavaChallenge)));

		Warp umvum = VapoxPvP.getWarpByName("1v1");
		inv.setItem(17, umvum.getIcon().clone().display("§a" + umvum.getName())
				.lore("§fAcesso a warp: §a§l" + umvum.getName(), "§fJogadores: §a" + VapoxPvP.getPlayersInWarp(umvum)));

		player.openInventory(inv);
	}
}