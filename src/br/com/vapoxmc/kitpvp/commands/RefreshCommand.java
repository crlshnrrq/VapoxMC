package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class RefreshCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.refresh")) {
				Bukkit.broadcastMessage("§a§l[RESTART] §fO servidor está reiniciando, aguarde...");
				Bukkit.getOnlinePlayers().forEach(players -> VapoxPvP.setWarp(players, VapoxPvP.getDefaultWarp()));
				Bukkit.reload();
				Bukkit.broadcastMessage("§a§l[RESTART] §fO restart foi concluído com sucesso!");
			} else
				player.sendMessage("§cÉ necessário ser [DIRETOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}