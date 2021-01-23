package br.com.vapoxmc.kitpvp.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public final class LastLoginCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.lastlogin")) {
				if (args.length > 0) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					if (target.hasPlayedBefore()) {
						Date date = new Date(target.getLastPlayed());
						player.sendMessage("§c§l[LastLogin] §fO último login de §c" + target.getName() + " §ffoi em: "
								+ new SimpleDateFormat("dd/MM/yyyy").format(date) + " §làs §f"
								+ new SimpleDateFormat("HH:mm:ss").format(date));
					} else {
						player.sendMessage(
								"§c§l[LastLogin] §fNão foi possível encontrar esse jogador no banco de dados. §7(hpb_01)");
					}
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador]");
			} else
				player.sendMessage("§cÉ necessário ser [ADMIN] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}