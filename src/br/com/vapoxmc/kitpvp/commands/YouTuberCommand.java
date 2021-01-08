package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.gui.YouTuberGUI;

public final class YouTuberCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			YouTuberGUI.openGUI(player);
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}