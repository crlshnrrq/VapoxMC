package br.com.vapoxmc.kitpvp.gui;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.player.PlayerAccount.Geral;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class ShopKDRGUI implements Listener {

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player
				&& event.getInventory().getName().equals("§e§lMenu de Compras §7(KDR)")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			Geral geral = PlayerAccount.getGeral();
			event.setCancelled(true);

			if (display.equals("§c§lReset KDR")) {
				if (geral.getMoedas(player) >= 5000) {
					geral.drawMoedas(player, 5000).setAbates(player, 0).setMortes(player, 0).setKillStreak(player, 0)
							.setPontos(player, 0);

					PlayerAccount.get1v1().setVitorias(player, 0).setDerrotas(player, 0).setWinStreak(player, 0);
					PlayerAccount.getSumo().setVitorias(player, 0).setDerrotas(player, 0).setWinStreak(player, 0);

					player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aresetkdr§f.");
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
							+ " §facaba de comprar §c§lReset KDR §fpor §a§l5.000 §fmoedas!");
					Bukkit.broadcastMessage(" ");
					player.kickPlayer("§e§lVAPOXMC\n" + "§fSeus dados foram alterados no banco de dados.");
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
					player.closeInventory();
				}
			}
		}
	}

	public static void openGUI(Player player) {
		int coins = PlayerAccount.getGeral().getMoedas(player);
		String money = new DecimalFormat("###,###.##").format(coins);

		Inventory inv = Bukkit.createInventory(null, 36, "§e§lMenu de Compras §7(KDR)");

		inv.setItem(4,
				new Stack(Material.GOLD_INGOT).display("§fCarteira:").lore("§fVocê possuí §a" + money + " §fmoedas!"));

		inv.setItem(22,
				new Stack(Material.REDSTONE).display("§c§lReset KDR").lore("§fO que acontecerá ao §acomprar§f:",
						"§fAbates definidos para: §a§l0", "§fMortes definidos para: §a§l0",
						"§fPontos definidos para: §a§l0", "§fEstatísticas de warp definidas para: §a§l0",
						"§e§lOBS: §fas moedas §cnão §fsão resetadas!", "§fValor: §a5.000 §fmoedas."));

		player.openInventory(inv);
	}
}