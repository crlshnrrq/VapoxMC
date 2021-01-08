package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class GameModeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.gamemode")) {
				if (args.length > 0) {
					if (args[0].equals("0")) {
						player.setGameMode(GameMode.SURVIVAL);
						player.sendMessage("§aGamemode alterado para sobrevivência.");
					}
					if (args[0].equals("1")) {
						player.setGameMode(GameMode.CREATIVE);
						player.sendMessage("§aGamemode alterado para criativo.");
					}
					if (args[0].equals("2")) {
						player.setGameMode(GameMode.ADVENTURE);
						player.sendMessage("§aGamemode alterado para aventura.");
					}
					if (args[0].equals("3")) {
						player.setGameMode(GameMode.SPECTATOR);
						player.sendMessage("§aGamemode alterado para espectador.");
					}
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [0/1/2/3]");
			} else
				player.sendMessage("§cÉ necessário ser [TRIAL] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}