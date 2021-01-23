package br.com.vapoxmc.kitpvp.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.VapoxUtils;

public final class ActionBarCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.actionbar")) {
				if (args.length > 1) {
					if (args[0].equalsIgnoreCase("all")) {
						String message = ChatColor.translateAlternateColorCodes('&',
								StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " "));
						Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("command.actionbar"))
								.forEach(players -> players
										.sendMessage("§7(Staff) O aviso foi enviado por §f" + player.getName()));
						Bukkit.getOnlinePlayers().forEach(players -> VapoxUtils.sendActionBar(players, message));
						player.sendMessage("§a§l[AB] §fMensagem para §a§lTODOS §fenviada com sucesso!");
					} else {
						Player target = Bukkit.getPlayer(args[0]);
						if (target != null) {
							String message = ChatColor.translateAlternateColorCodes('&',
									StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " "));
							VapoxUtils.sendActionBar(target, message);
							player.sendMessage(
									"§a§l[AB] §fMensagem para §a" + target.getName() + " §fenviada com sucesso!");
						} else
							player.sendMessage("§c§l[AB] §cO jogador §f" + args[0] + " §cnão foi encontrado.");
					}
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [all/player] [argumentos]");
			} else
				player.sendMessage("§cÉ necessário ser [ADMIN] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}