package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TeleportAllCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.tpall")) {
				Bukkit.getOnlinePlayers().forEach(players -> {
					players.teleport(player.getLocation());
					players.sendMessage("§e§l[TPALL] §eTeleportado para: " + player.getName());
				});
				player.sendMessage(
						"§e§l[TPALL] §eVocê puxou " + (Bukkit.getOnlinePlayers().size() - 1) + " jogadores!");
			} else
				player.sendMessage("§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}