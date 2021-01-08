package br.com.vapoxmc.kitpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.Strings;

public final class YouTuberGUI implements Listener {

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("§e§lRequisitos YouTuber!")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta())
			event.setCancelled(true);
	}

	public static void openGUI(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, "§eRequisitos YouTuber!");

		inv.setItem(12,
				new Stack(Material.SKULL_ITEM, 1, 3).owner("Plotinum").display("§b§lYOUTUBER").lore(
						"§a1 vídeo §fno servidor.", "§a+5 minutos§f de vídeos no servidor.",
						"§a+60 visualizações nos vídeos no servidor."));
		inv.setItem(14,
				new Stack(Material.SKULL_ITEM, 1, 3).display("DiamondDude5_").display("§3§lYOUTUBER+").lore(
						"§a3 vídeos §fno servidor.", "§a+15 minutos §fde vídeos no servidor.",
						"§a+300 visualizações §fnos vídeos no servidor.", "§a+14 anos §fde idade."));
		inv.setItem(26, new Stack(Material.SKULL_ITEM, 1, 3).owner("MHF_Question").display("§e§lComo solicitar?").lore(
				"§fPara solicitar a sua tag, acesse nosso discord",
				"§fe abra um chamado em \"§a#ticket§f\", ou contate", "§fo §c§lADMIN §fresponsável pelos YouTuber's",
				"§fAdmin atual: §a§lExTziin§f.", "§fdiscord: §a" + Strings.getDiscord() + " §7(/discord)"));

		player.openInventory(inv);
	}
}