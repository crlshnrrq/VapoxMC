package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.warp.SpawnWarp;

public final class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.fly")) {
				if (args.length > 0) {
					if (player.hasPermission("command.fly.others")) {
						Player target = Bukkit.getPlayer(args[0]);
						if (target != null) {
							if (VapoxPvP.getWarp(target) instanceof SpawnWarp) {
								if (!target.getAllowFlight()) {
									target.setAllowFlight(true);
									target.sendMessage("§a§l[FLY] §fSeu modo voar foi §aativado §fpor §a"
											+ player.getName() + "§f.");
									Bukkit.getOnlinePlayers().stream()
											.filter(players -> players.hasPermission("command.fly.others"))
											.forEach(players -> players.sendMessage("§7§o(STAFF) §f" + player.getName()
													+ " §7ativou o modo §fvoar §7do jogador §f" + target.getName()));
								} else {
									target.setAllowFlight(false);
									target.sendMessage("§c§l[FLY] §fSeu modo voar foi §cdesativado §fpor §c"
											+ player.getName() + "§f.");
									Bukkit.getOnlinePlayers().stream()
											.filter(players -> players.hasPermission("command.fly.others"))
											.forEach(players -> players.sendMessage("§7§o(STAFF) §f" + player.getName()
													+ " §7desativou o modo §fvoar §7do jogador §f" + target.getName()));
								}
							} else
								player.sendMessage(
										"§c§l[FLY] §fEsse jogador está utilizando um kit, portanto o comando foi inválido.");
						} else
							player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
					} else
						player.sendMessage("§cÉ necessário ser [GERENTE] ou superior para executar este comando!");
				} else {
					if (VapoxPvP.getWarp(player) instanceof SpawnWarp) {
						if (!player.getAllowFlight()) {
							player.setAllowFlight(true);
							player.sendMessage("§a§l[FLY] §fSeu modo voar foi §aativado§f.");
						} else {
							player.setAllowFlight(false);
							player.sendMessage("§c§l[FLY] §fSeu modo voar foi §cdesativado§f.");
						}
					} else
						player.sendMessage("§c§l[FLY] §fVocê não pode usar o /" + label + " enquanto está com um kit!");
				}
			} else
				player.sendMessage("§cÉ necessário ser [VIP] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}