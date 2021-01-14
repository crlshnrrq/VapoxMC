package br.com.vapoxmc.kitpvp.gui;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class ShopGUI implements Listener {

	public static final Stack ICON = new Stack(Material.EMERALD).display("§1» §9Loja de moedas.");

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.hasItem() && event.getItem().isSimilar(ICON.toItemStack()) && !VapoxPvP.hasKit(player))
			openGUI(player);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player && event.getInventory().getName().equals("§e§lMenu de Compras")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§f» §bKits"))
				ShopKitsGUI.openGUI(player);
			else if (display.equals("§f» §bReset KDR"))
				ShopKDRGUI.openGUI(player);
			else if (display.equals("§f» §bVIP"))
				ShopVIPGUI.openGUI(player);
		}
	}

	public static void openGUI(Player player) {
		int coins = PlayerAccount.getMoedas(player);
		String money = new DecimalFormat("###,###.##").format(coins);

		Inventory inv = Bukkit.createInventory(null, 36, "§e§lMenu de Compras");

		inv.setItem(4,
				new Stack(Material.GOLD_INGOT).display("§fCarteira:").lore("§fVocê possuí §a" + money + " §fmoedas!"));
		inv.setItem(20, new Stack(Material.STONE_SWORD).display("§f» §bKits").lore("§fCompre §lKITs §fcom moedas!"));
		inv.setItem(22, new Stack(Material.REDSTONE).display("§f» §bReset KDR")
				.lore("§fApague §ltodas §fas suas estatísticas no servidor."));
		inv.setItem(24, new Stack(Material.DIAMOND).display("§f» §bVIP").lore("§fCompre §lVIPs §fcom moedas!"));

		player.openInventory(inv);
	}
}