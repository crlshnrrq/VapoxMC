package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				if (player.hasPermission("ciphen.comandos.spawn")) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null && VapoxPvP.setWarp(target, VapoxPvP.getDefaultWarp())) {
						target.sendMessage("§aTeleportado para o spawn por: " + player.getName());

						player.sendMessage("§eVocê teleportou " + target.getName() + " para o spawn!");
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else
					player.sendMessage("§cÉ necessário ser [MODERADOR] ou superior para executar este comando!");
			} else {
				if (VapoxPvP.setWarp(player, VapoxPvP.getDefaultWarp()))
					player.sendMessage("§aTeleportado para o spawn!");
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}