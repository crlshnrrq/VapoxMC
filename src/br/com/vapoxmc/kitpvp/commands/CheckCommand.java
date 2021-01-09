package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerGroup;

public final class CheckCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.check")) {
				if (args.length > 0) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						player.sendMessage("§a§l[CHECK] §fInformações administrativas de: §a" + target.getName());
						player.sendMessage("§fGrupo: §a" + PlayerGroup.getGroup(target).getColoredName());
						player.sendMessage("§fOP: §a" + (target.isOp() ? "§aSim" : "§cNão"));
						player.sendMessage("§fBuild: §a" + (VapoxPvP.hasBuild(target) ? "§aSim" : "§cNão"));
						player.sendMessage("§fAdmin: §a" + (VapoxPvP.hasAdmin(target) ? "§aSim" : "§cNão"));
						player.sendMessage("§fSpy: §a" + (VapoxPvP.isSpyingTell(target) ? "§aSim" : "§cNão"));
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador]");
			} else
				player.sendMessage("§cÉ necessário ser [ADMIN] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVoCê não pode executar este comando!");
		return true;
	}
}