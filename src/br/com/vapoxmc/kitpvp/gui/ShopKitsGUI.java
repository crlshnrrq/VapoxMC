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
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class ShopKitsGUI implements Listener {

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player
				&& event.getInventory().getName().equals("§e§lMenu de Compras §7(KITs)")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
			String display = event.getCurrentItem().getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);

			if (display.equals("§aKangaroo")) {
				if (!player.hasPermission("kit.kangaroo")) {
					if (PlayerAccount.getMoedas(player) >= 3000) {
						PlayerAccount.drawMoedas(player, 3000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.kangaroo");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aKangaroo§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lKangaroo §fpor §a§l3.000 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			} else if (display.equals("§aAnchor")) {
				if (!player.hasPermission("kit.anchor")) {
					if (PlayerAccount.getMoedas(player) >= 3750) {
						PlayerAccount.drawMoedas(player, 3750);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.anchor");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aAnchor§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lAnchor §fpor §a§l3.750 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			} else if (display.equals("§aNinja")) {
				if (!player.hasPermission("kit.anchor")) {
					if (PlayerAccount.getMoedas(player) >= 3750) {
						PlayerAccount.drawMoedas(player, 3750);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.anchor");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aAnchor§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lAnchor §fpor §a§l3.750 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			} else if (display.equals("§aNinja")) {
				if (!player.hasPermission("kit.ninja")) {
					if (PlayerAccount.getMoedas(player) >= 5000) {
						PlayerAccount.drawMoedas(player, 5000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.ninja");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aNinja§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lNinja §fpor §a§l5.000 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			} else if (display.equals("§aAjnin")) {
				if (!player.hasPermission("kit.ajnin")) {
					if (PlayerAccount.getMoedas(player) >= 6000) {
						PlayerAccount.drawMoedas(player, 6000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.ajnin");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aAjnin§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lAjnin §fpor §a§l6.000 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			} else if (display.equals("§aCritical")) {
				if (!player.hasPermission("kit.critical")) {
					if (PlayerAccount.getMoedas(player) >= 8000) {
						PlayerAccount.drawMoedas(player, 8000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.critical");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aCritical§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lCritical §fpor §a§l8.000 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			} else if (display.equals("§aAntiStomper")) {
				if (!player.hasPermission("kit.antistomper")) {
					if (PlayerAccount.getMoedas(player) >= 10000) {
						PlayerAccount.drawMoedas(player, 10000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.antistomper");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aAntiStomper§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lAntiStomper §fpor §a§l10.000 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			} else if (display.equals("§aStomper")) {
				if (!player.hasPermission("kit.stomper")) {
					if (PlayerAccount.getMoedas(player) >= 13000) {
						PlayerAccount.drawMoedas(player, 13000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"pex user " + player.getName() + " add kit.stomper");
						player.sendMessage("§a§l[LOJA] §fVocê acaba de adquirir §aStomper§f.");
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§a§l[LOJA] §a§l" + player.getName()
								+ " §facaba de comprar §a§lStomper §fpor §a§l13.000 §fmoedas!");
						Bukkit.broadcastMessage(" ");
						player.closeInventory();
					} else {
						player.sendMessage("§c§l[LOJA] §fVocê não possuí moedas o suficiente!");
						player.closeInventory();
					}
				} else {
					player.sendMessage("§c§l[LOJA] §fVocê já possuí esse §ckit§f!");
					player.closeInventory();
				}
			}
		}
	}

	public static void openGUI(Player player) {
		int coins = PlayerAccount.getMoedas(player);
		String money = new DecimalFormat("###,###.##").format(coins);

		Inventory inv = Bukkit.createInventory(null, 36, "§e§lMenu de Compras §7(KITs)");

		inv.setItem(4,
				new Stack(Material.GOLD_INGOT).display("§fCarteira:").lore("§fVocê possuí §a" + money + " §fmoedas!"));

		inv.setItem(19, new Stack(Material.FIREWORK).display("§aKangaroo").lore("§fFunção: §aPule que nem um canguru!",
				"§fValor: §a3,000 §fmoedas."));
		inv.setItem(20, new Stack(Material.ANVIL).display("§aAnchor").lore("§fFunção: §aNão leve e nem dê repulsão",
				"§fValor: §a3,750 §fmoedas."));
		inv.setItem(21, new Stack(Material.ENDER_PEARL).display("§aNinja")
				.lore("§fFunção: §aTeleporte-se para seu oponente!", "§fValor: §a5,000 §fmoedas."));
		inv.setItem(22, new Stack(Material.NETHER_STAR).display("§aAjnin")
				.lore("§fFunção: §aTeleporte seu oponente até você!", "§fValor: §a6,000 §fmoedas."));
		inv.setItem(23, new Stack(Material.REDSTONE_BLOCK).display("§aCritical")
				.lore("§fFunção: §aDe danos criticos no oponente!", "§fValor: §a8,000 §fmoedas."));
		inv.setItem(24, new Stack(Material.DIAMOND_HELMET).display("§aAntiStomper")
				.lore("§fFunção: §aNão seja esmagado por stompers!", "§fValor: §a10,000 §fmoedas."));
		inv.setItem(25, new Stack(Material.IRON_BOOTS).display("§aStomper")
				.lore("§fFunção: §aEsmague os seus oponentes!", "§fValor: §a13,000 §fmoedas."));

		player.openInventory(inv);
	}
}