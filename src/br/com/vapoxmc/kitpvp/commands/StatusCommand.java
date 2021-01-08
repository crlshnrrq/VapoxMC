package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.gui.StatusGUI;

public final class StatusCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null)
					StatusGUI.openGUI(player, target);
				else
					player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
			} else
				StatusGUI.openGUI(player, player);
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}