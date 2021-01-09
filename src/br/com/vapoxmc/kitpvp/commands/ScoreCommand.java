package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class ScoreCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!VapoxPvP.hasSidebar(player)) {
				VapoxPvP.setSidebar(player, VapoxPvP.getDefaultSidebar());
				player.sendMessage("§e§l[SCORE] §eVocê ativou a scoreboard!");
			} else {
				VapoxPvP.removeSidebar(player);
				player.sendMessage("§e§l[SCORE] §eVocê desativou a scoreboard!");
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}