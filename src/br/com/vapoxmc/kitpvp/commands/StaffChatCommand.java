package br.com.vapoxmc.kitpvp.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerGroup;

public final class StaffChatCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.staffchat")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("off")) {
						if (VapoxPvP.isIgnoreStaffChat(player)) {
							VapoxPvP.removeIgnoreStaffChat(player);
							player.sendMessage("§a§l[SC] §fVocê §aativou §fo seu staffchat.");
						} else {
							VapoxPvP.addIgnoreStaffChat(player);
							player.sendMessage(
									"§c§l[SC] §fVocê §cdesativou §fo seu staffchat, preste atenção no chat.");
							player.sendMessage(
									"§c§l[SC] §fNão recomendamos deixar o staffchat desativado por muito tempo.");
							player.sendMessage(
									"§c§l[SC] §fUm membro da equipe pode precisar de você e você pode não ver.");
						}
					}
					if (!VapoxPvP.isIgnoreStaffChat(player)) {
						PlayerGroup group = PlayerGroup.getGroup(player);
						String message = ChatColor.translateAlternateColorCodes('&',
								StringUtils.join(Arrays.copyOfRange(args, 0, args.length), " "));
						Bukkit.getOnlinePlayers().stream()
								.filter(players -> players.hasPermission("ciphen.comandos.staffchat")
										&& !VapoxPvP.isIgnoreStaffChat(players))
								.forEach(players -> players
										.sendMessage("§c§l[SC] §7[KitPvP] " + group.getBoldColoredName() + " "
												+ group.getColor() + player.getName() + " §7» §f" + message));
					} else
						player.sendMessage("§c§l[SC] §fSeu staffchat está §cdesativado§f, ative usando §c/sc off.");
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [OFF/Mensagem]");
			} else
				player.sendMessage("§cVocê deve ser [AJUDANTE] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}