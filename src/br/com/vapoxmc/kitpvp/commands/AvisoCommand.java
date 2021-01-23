package br.com.vapoxmc.kitpvp.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.Strings;

public final class AvisoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.aviso")) {
				if (args.length > 0) {
					String message = ChatColor
							.translateAlternateColorCodes('&',
									StringUtils.join(Arrays.copyOfRange(args, 0, args.length), " "))
							.replace("{discord}", Strings.getDiscord());
					Bukkit.getOnlinePlayers().forEach(players -> {
						players.sendMessage(" ");
						players.sendMessage("§c§l[AVISO] §f" + message);
						if (players.hasPermission("command.aviso"))
							players.sendMessage("§7(STAFF) O aviso foi enviado por: §f" + player.getName() + "§7.");
						players.sendMessage(" ");
					});
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Mensagem]");
			} else
				player.sendMessage("§cVocê deve ser [ADMINISTRADOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}