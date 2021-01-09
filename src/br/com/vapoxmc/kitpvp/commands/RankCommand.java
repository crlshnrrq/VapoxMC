package br.com.vapoxmc.kitpvp.commands;

import java.text.DecimalFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.player.PlayerRank;

public final class RankCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage("§a§lVapoxMC - Sistema de Pontos");
			for (PlayerRank rank : PlayerRank.values()) {
				player.sendMessage("§7(" + rank.getColoredSymbol() + "§7 §f| " + rank.getName() + " §a"
						+ new DecimalFormat().format(rank.getPontos()) + " pontos§f.");
			}
			player.sendMessage("§eSeu rank atual é: §l" + PlayerRank.getRank(player).getName().toUpperCase() + "§e.");
			player.sendMessage("§fVocê possui: §a" + PlayerAccount.getGeral().getPontos(player) + " §fpontos!");
			player.sendMessage("§fConsiga mais §apontos §fmatando oponentes nas §aarenas§f!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}