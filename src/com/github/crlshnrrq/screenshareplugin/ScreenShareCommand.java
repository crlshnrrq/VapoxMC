package com.github.crlshnrrq.screenshareplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.crlshnrrq.screenshareplugin.guis.ScreenShareHistoryGUI;
import com.github.crlshnrrq.screenshareplugin.guis.ScreenSharePlayerGUI;
import com.github.crlshnrrq.screenshareplugin.guis.ScreenShareSessionsGUI;

public final class ScreenShareCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.screenshare")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
						if (player.hasPermission("command.screenshare.teleport")) {
							Location loc = ScreenSharePlugin.getDefaultWorldLocation();
							if (!player.getWorld().getName().equals(loc.getWorld().getName())) {
								player.teleport(loc);
								player.sendMessage("§e§l[SS] §fVocê se teleportou para a §eScreenShare§f.");
							} else {
								player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
								player.sendMessage("§e§l[SS] §fVocê se teleportou para o §aMundo Padrão§f.");
							}
						} else
							player.sendMessage("§c§l[SS] §fVocê não possui §cPermissão §fpara usar este §cComando§f.");
					} else if (args[0].equalsIgnoreCase("-sessions")) {
						if (player.hasPermission("screenshare.gui.viewsessions")) {
							ScreenShareSessionsGUI.openGUI(player);
							player.sendMessage("§a§l[SS] §fVocê abriu as §aSessões em Andamento§f.");
						} else
							player.sendMessage("§c§l[SS] §fVocê não possui §cPermissão §fpara usar este §cComando§f.");
					} else if (args[0].equalsIgnoreCase("-history")) {
						if (player.hasPermission("screenshare.gui.viewhistory")) {
							ScreenShareHistoryGUI.openGUI(player);
							player.sendMessage("§a§l[SS] §fVocê abriu o §aHistórico de Sessões§f.");
						} else
							player.sendMessage("§c§l[SS] §fVocê não possui §cPermissão §fpara usar este §cComando§f.");
					} else {
						ScreenSharePlayerGUI.openGUI(player, args[0]);
						player.sendMessage("§a§l[SS] §fVocê abriu as §aInformações do Jogador " + args[0] + "§f.");
					}
				} else
					player.sendMessage("§c§l[SS] §fSintaxe incorreta, utilize: §c/" + label + " (Jogador)");
			} else
				player.sendMessage("§c§l[SS] §fVocê não possui §cPermissão §fpara usar este §cComando§f.");
		} else
			sender.sendMessage("§c§l[SS] §fApenas §cJogadores §fpodem usar este §cComando§f.");
		return true;
	}
}