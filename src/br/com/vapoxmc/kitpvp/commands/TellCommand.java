package br.com.vapoxmc.kitpvp.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class TellCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("ON")) {
					if (VapoxPvP.isTellDisabled(player)) {
						VapoxPvP.removeTellDisabled(player);
						player.sendMessage("§a§l[TELL] §fVocê §aativou §fo seu modo tell.");
					} else
						player.sendMessage("§a§l[TELL] §fO seu modo tell já está §aativado§f.");
				} else if (args[0].equalsIgnoreCase("OFF")) {
					if (!VapoxPvP.isTellDisabled(player)) {
						VapoxPvP.addTellDisabled(player);
						player.sendMessage("§c§l[TELL] §fVocê §cdesativou §fo seu modo tell.");
					} else
						player.sendMessage("§c§l[TELL] §fO seu modo tell já está §cdesativado§f.");
				} else if (args[0].equalsIgnoreCase("Spy")) {
					if (player.hasPermission("ciphen.comandos.tellspy")) {
						if (!VapoxPvP.isSpyingTell(player)) {
							VapoxPvP.addSpyingTell(player);
							player.sendMessage("§e§l[TELL] §fVocê §aativou §fo modo spy.");
						} else {
							VapoxPvP.removeSpyingTell(player);
							player.sendMessage("§e§l[TELL] §fVocê §cdesativou §fo modo spy.");
						}
					} else
						player.sendMessage("§cComando incorreto, utilize /" + label + " [ON/OFF] ou /" + label
								+ " [Jogador] [Mensagem]");
				} else if (args.length > 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (!VapoxPvP.isTellDisabled(target)) {
							String message = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
							if (!message.contains("mc-hype.com.br") && !message.contains("helixnetwork.com.br")) {
								player.sendMessage("§e§l[TELL] §7Para: §f" + target.getName() + " §7» §f" + message);
								target.sendMessage("§e§l[TELL] §7De: §f" + player.getName() + " §7» §f" + message);
								Bukkit.getOnlinePlayers().stream().filter(players -> VapoxPvP.isSpyingTell(players))
										.forEach(players -> players.sendMessage("§7§l[TELL-SPY] §f" + player.getName()
												+ " §7» §f" + target.getName() + " §7» " + message));
							} else
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
										"tempban " + player.getName() + " 14 D Divulgação de IP");
						} else
							player.sendMessage("§c§l[TELL] §fEsse jogador está com tell §cdesativado§f.");
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [ON/OFF] ou /" + label
							+ " [Jogador] [Mensagem]");
			} else
				player.sendMessage(
						"§cComando incorreto, utilize /" + label + " [ON/OFF] ou /" + label + " [Jogador] [Mensagem]");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}