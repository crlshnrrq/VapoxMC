package br.com.vapoxmc.kitpvp.commons.medalhas.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.commons.medalhas.Medalha;
import br.com.vapoxmc.kitpvp.commons.medalhas.MedalhaAPI;

public final class MedalhaCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				Medalha medalha = Medalha.getByName(args[0]);
				if (medalha != null) {
					if (!medalha.hasPermission()
							|| (medalha.hasPermission() && player.hasPermission(medalha.getPermission()))) {
						MedalhaAPI.setMedalha(player, medalha);
						player.sendMessage("§aVocê alterou sua medalha para: " + medalha.getColoredSymbol());
					} else
						player.sendMessage("§cVocê não tem permissão para utilizar esta medalha!");
				} else
					player.sendMessage("");
			} else {
				String medalhas = "";
				for (Medalha medalha : Medalha.values()) {
					if (!medalha.hasPermission()
							|| (medalha.hasPermission() && player.hasPermission(medalha.getPermission())))
						medalhas += (!medalhas.isEmpty() ? "§f, " : "") + medalha.getColoredName()
								+ (!medalha.getSymbol().isEmpty() ? " " + medalha.getColoredSymbol() : "");
				}
				player.sendMessage("§e• §fMedalhas: " + medalhas + "§f.");
				player.sendMessage("§cComando incorreto, utilize /" + label + " [Medalha]");
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}