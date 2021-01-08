package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class PingCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					int ping = ((CraftPlayer) player).getHandle().ping;
					String quality = "§cOcorreu um erro ao pegar a qualidade do ping.";
					if (ping <= 20)
						quality = "§aExcelente!";
					if (ping > 20 && ping <= 40)
						quality = "§aBoa";
					if (ping > 40 && ping <= 60)
						quality = "§eMédia";
					if (ping > 60 && ping <= 80)
						quality = "§cRuim";
					if (ping > 80)
						quality = "§4Horrível";
					player.sendMessage("§aO ping atual de " + target.getName() + " é de: " + ping + "ms!");
					player.sendMessage("§aQualidade: " + quality);
				} else
					player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
			} else
				player.sendMessage("§aSeu ping atual é: " + ((CraftPlayer) player).getHandle().ping + "ms!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}