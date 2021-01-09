package br.com.vapoxmc.kitpvp.gui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.player.PlayerGroup;
import br.com.vapoxmc.kitpvp.player.PlayerRank;
import br.com.vapoxmc.kitpvp.utils.Stack;

public final class StatusGUI implements Listener {

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player
				&& event.getInventory().getName().equals("§eSeu perfil dentro do servidor.")
				&& event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta())
			event.setCancelled(true);
	}

	public static void openGUI(Player player, Player target) {
		Inventory inv = Bukkit.createInventory(null, 54, "§eSeu perfil dentro do servidor.");

		inv.setItem(4, new Stack(Material.SKULL_ITEM, 1, 3).owner(target.getName()).display("§aInformações:").lore(
				"§fNick: §a" + target.getName(), "§fUUID: §a" + target.getUniqueId(),
				"§fMoedas: §a" + new DecimalFormat("###,###.##").format(PlayerAccount.getGeral().getMoedas(target)),
				"§fPrimeiro acesso: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(target.getFirstPlayed())));

		inv.setItem(10, new Stack(Material.DIAMOND_SWORD).display("§aEstatísticas:").lore(
				"§fAbates: §a" + PlayerAccount.getGeral().getAbates(target),
				"§fMortes: §a" + PlayerAccount.getGeral().getMortes(target), "§fKit favorito: §cDesenvolvimento"));

		inv.setItem(12,
				new Stack(Material.EYE_OF_ENDER).display("§aCargo:").lore(
						"§fVIP: §a" + (target.hasPermission("ciphen.doublexp") ? "Sim" : "Não"),
						"§fCargo: §a" + PlayerGroup.getGroup(target).getColoredName()));

		inv.setItem(14,
				new Stack(Material.COMPASS).display("§aEstatísticas: §7(warps)").lore("§e§l1v1",
						"§fKills: §a" + PlayerAccount.get1v1().getVitorias(target),
						"§fDeaths: §a" + PlayerAccount.get1v1().getDerrotas(target), " ", "§e§lSumô",
						"§fKills: §a" + PlayerAccount.getSumo().getVitorias(target),
						"§fDeaths: §a" + PlayerAccount.getSumo().getDerrotas(target), " ", "§e§lPotPvP",
						"§fKills: §cOFF", "§fDeaths: §cOFF", " "));

		inv.setItem(16,
				new Stack(Material.EXP_BOTTLE).display("§aExperiência:").lore(
						"§fPontos: §a" + PlayerAccount.getGeral().getPontos(target),
						"§fRank: §a" + PlayerRank.getRank(target).getColoredSymbolName(),
						"§fBooster: §a" + (target.hasPermission("ciphen.doublexp") ? "x2.0" : "x1.0")));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.UNRANKED.getPontos())
			inv.setItem(19,
					new Stack(Material.STAINED_GLASS_PANE, 1, 5)
							.display("§7(" + PlayerRank.UNRANKED.getColoredSymbol() + "§7) |"
									+ PlayerRank.UNRANKED.getColoredName())
							.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(19,
					new Stack(Material.STAINED_GLASS_PANE, 1, 14)
							.display("§7(" + PlayerRank.UNRANKED.getColoredSymbol() + "§7) |"
									+ PlayerRank.UNRANKED.getColoredName())
							.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.PRIMARY.getPontos())
			inv.setItem(20,
					new Stack(Material.STAINED_GLASS_PANE, 1, 5)
							.display("§7(" + PlayerRank.PRIMARY.getColoredSymbol() + "§7) |"
									+ PlayerRank.PRIMARY.getColoredName())
							.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(20,
					new Stack(Material.STAINED_GLASS_PANE, 1, 14)
							.display("§7(" + PlayerRank.PRIMARY.getColoredSymbol() + "§7) |"
									+ PlayerRank.PRIMARY.getColoredName())
							.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.ADVANCED.getPontos())
			inv.setItem(21,
					new Stack(Material.STAINED_GLASS_PANE, 1, 5)
							.display("§7(" + PlayerRank.ADVANCED.getColoredSymbol() + "§7) |"
									+ PlayerRank.ADVANCED.getColoredName())
							.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(21,
					new Stack(Material.STAINED_GLASS_PANE, 1, 14)
							.display("§7(" + PlayerRank.ADVANCED.getColoredSymbol() + "§7) |"
									+ PlayerRank.ADVANCED.getColoredName())
							.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.PLATINA.getPontos())
			inv.setItem(22,
					new Stack(Material.STAINED_GLASS_PANE, 1, 5)
							.display("§7(" + PlayerRank.PLATINA.getColoredSymbol() + "§7) |"
									+ PlayerRank.PLATINA.getColoredName())
							.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(22,
					new Stack(Material.STAINED_GLASS_PANE, 1, 14)
							.display("§7(" + PlayerRank.PLATINA.getColoredSymbol() + "§7) |"
									+ PlayerRank.PLATINA.getColoredName())
							.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.GOLD.getPontos())
			inv.setItem(32, new Stack(Material.STAINED_GLASS_PANE, 1, 5)
					.display("§7(" + PlayerRank.GOLD.getColoredSymbol() + "§7) |" + PlayerRank.GOLD.getColoredName())
					.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(32, new Stack(Material.STAINED_GLASS_PANE, 1, 14)
					.display("§7(" + PlayerRank.GOLD.getColoredSymbol() + "§7) |" + PlayerRank.GOLD.getColoredName())
					.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.DIAMANTE.getPontos())
			inv.setItem(33,
					new Stack(Material.STAINED_GLASS_PANE, 1, 5)
							.display("§7(" + PlayerRank.DIAMANTE.getColoredSymbol() + "§7) |"
									+ PlayerRank.DIAMANTE.getColoredName())
							.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(33,
					new Stack(Material.STAINED_GLASS_PANE, 1, 14)
							.display("§7(" + PlayerRank.DIAMANTE.getColoredSymbol() + "§7) |"
									+ PlayerRank.DIAMANTE.getColoredName())
							.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.ESMERALDA.getPontos())
			inv.setItem(34,
					new Stack(Material.STAINED_GLASS_PANE, 1, 5)
							.display("§7(" + PlayerRank.ESMERALDA.getColoredSymbol() + "§7) |"
									+ PlayerRank.ESMERALDA.getColoredName())
							.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(34,
					new Stack(Material.STAINED_GLASS_PANE, 1, 14)
							.display("§7(" + PlayerRank.ESMERALDA.getColoredSymbol() + "§7) |"
									+ PlayerRank.ESMERALDA.getColoredName())
							.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.RUBY.getPontos())
			inv.setItem(37, new Stack(Material.STAINED_GLASS_PANE, 1, 5)
					.display("§7(" + PlayerRank.RUBY.getColoredSymbol() + "§7) |" + PlayerRank.RUBY.getColoredName())
					.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(37, new Stack(Material.STAINED_GLASS_PANE, 1, 14)
					.display("§7(" + PlayerRank.RUBY.getColoredSymbol() + "§7) |" + PlayerRank.RUBY.getColoredName())
					.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.ELITE.getPontos())
			inv.setItem(38, new Stack(Material.STAINED_GLASS_PANE, 1, 5)
					.display("§7(" + PlayerRank.ELITE.getColoredSymbol() + "§7) |" + PlayerRank.ELITE.getColoredName())
					.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(38, new Stack(Material.STAINED_GLASS_PANE, 1, 14)
					.display("§7(" + PlayerRank.ELITE.getColoredSymbol() + "§7) |" + PlayerRank.ELITE.getColoredName())
					.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.SAFIRA.getPontos())
			inv.setItem(39, new Stack(Material.STAINED_GLASS_PANE, 1, 5)
					.display(
							"§7(" + PlayerRank.SAFIRA.getColoredSymbol() + "§7) |" + PlayerRank.SAFIRA.getColoredName())
					.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(39, new Stack(Material.STAINED_GLASS_PANE, 1, 14)
					.display(
							"§7(" + PlayerRank.SAFIRA.getColoredSymbol() + "§7) |" + PlayerRank.SAFIRA.getColoredName())
					.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		if (PlayerAccount.getGeral().getPontos(target) >= PlayerRank.VAPOX.getPontos())
			inv.setItem(40, new Stack(Material.STAINED_GLASS_PANE, 1, 5)
					.display("§7(" + PlayerRank.VAPOX.getColoredSymbol() + "§7) |" + PlayerRank.VAPOX.getColoredName())
					.lore("§a" + target.getName() + " já atingiu esse rank!."));
		else
			inv.setItem(40, new Stack(Material.STAINED_GLASS_PANE, 1, 14)
					.display("§7(" + PlayerRank.VAPOX.getColoredSymbol() + "§7) |" + PlayerRank.VAPOX.getColoredName())
					.lore("§c" + target.getName() + " ainda não atingiu esse rank."));

		player.openInventory(inv);
	}
}