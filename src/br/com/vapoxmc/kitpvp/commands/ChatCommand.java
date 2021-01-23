package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class ChatCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.chat")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("Clear")) {
						for (int i = 0; i < 255; i++)
							Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§e§l[CHAT] §eO chat do servidor foi limpo por: " + player.getName());
					} else if (args[0].equalsIgnoreCase("Switch")) {
						if (!VapoxPvP.getChat()) {
							VapoxPvP.setChat(true);
							Bukkit.broadcastMessage(" ");
							Bukkit.broadcastMessage(
									"§a§l[CHAT] §fO chat foi §a§lativado §fpor §a" + player.getName() + "§f!");
							Bukkit.broadcastMessage(" ");
							Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("command.chat"))
									.forEach(players -> players.sendMessage(
											"§7(STAFF) o §fchat §7foi alterado por: §f" + player.getName() + "§7."));
						} else {
							VapoxPvP.setChat(false);
							Bukkit.broadcastMessage(" ");
							Bukkit.broadcastMessage(
									"§c§l[CHAT] §fO chat foi §c§ldesativado §fpor §c" + player.getName() + "§f!");
							Bukkit.broadcastMessage(" ");
							Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("command.chat"))
									.forEach(players -> players.sendMessage(
											"§7(STAFF) o §fchat §7foi alterado por: §f" + player.getName() + "§7."));
						}
					}
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Clear/Switch]");
			} else
				player.sendMessage("§cÉ necessário ser [TRIAL] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}