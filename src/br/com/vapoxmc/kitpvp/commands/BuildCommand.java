package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class BuildCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.build")) {
				if (args.length > 0) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (!VapoxPvP.hasBuild(target)) {
							VapoxPvP.addBuild(target);
							target.setGameMode(GameMode.CREATIVE);
							target.sendMessage("§a§l[BUILD] §fSeu modo construir foi §aativado§f.");
							player.sendMessage(
									"§a§l[BUILD] §fO modo construir de §a" + target.getName() + " §ffoi §aativado§f.");
							Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("command.build"))
									.forEach(players -> players.sendMessage("§7§o(STAFF) §f" + player.getName()
											+ " §7ativou o modo §fconstrução §7do jogador §f" + target.getName()));
						} else {
							VapoxPvP.removeBuild(target);
							((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps")).setWarp(target,
									((WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps"))
											.getDefaultWarp());
							target.sendMessage("§c§l[BUILD] §fSeu modo construir foi §cdesativado§f.");
							player.sendMessage("§c§l[BUILD] §fO modo construir de §c" + target.getName()
									+ " §ffoi §cdesativado§f.");
							Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("command.build"))
									.forEach(players -> players.sendMessage("§7§o(STAFF) §f" + player.getName()
											+ " §7desativou o modo §fconstrução §7do jogador §f" + target.getName()));
						}
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else {
					if (!VapoxPvP.hasBuild(player)) {
						VapoxPvP.addBuild(player);
						player.setGameMode(GameMode.CREATIVE);
						player.sendMessage("§a§l[BUILD] §fSeu modo construir foi §aativado§f.");
					} else {
						VapoxPvP.removeBuild(player);
						player.sendMessage("§c§l[BUILD] §fSeu modo construir foi §cdesativado§f.");
					}
				}
			} else
				player.sendMessage("§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}