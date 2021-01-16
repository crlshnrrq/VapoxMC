package com.github.crlshnrrq.screenshareplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.crlshnrrq.screenshareplugin.configuration.gui.ScreenShareConfigGUI;
import com.github.crlshnrrq.screenshareplugin.guis.ScreenShareHistoryGUI;
import com.github.crlshnrrq.screenshareplugin.guis.ScreenSharePlayerGUI;
import com.github.crlshnrrq.screenshareplugin.guis.ScreenShareSessionsGUI;

public final class ScreenShareCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission(ScreenSharePermissions.USE_COMMAND.toPermission())) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
						if (player.hasPermission(ScreenSharePermissions.SCREENSHARE_TELEPORT.toPermission())) {
							Location loc = ScreenSharePlugin.getDefaultWorldLocation();
							if (!player.getWorld().getName().equals(loc.getWorld().getName())) {
								player.teleport(loc);
								ScreenShareMessages.COMMAND_TELEPORT_TO_SCREENSHARE.sendMessage(player);
							} else {
								player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
								ScreenShareMessages.COMMAND_TELEPORT_TO_DEFAULT_WORLD.sendMessage(player);
							}
						} else
							ScreenShareMessages.COMMAND_INSUFICIENT_PERMISSIONS.sendMessage(player);
					} else if (args[0].equalsIgnoreCase("-sessions")) {
						if (player.hasPermission(ScreenSharePermissions.VIEW_SESSIONS.toPermission())) {
							ScreenShareSessionsGUI.openGUI(player);
							ScreenShareMessages.COMMAND_OPEN_SESSIONS.sendMessage(player);
						} else
							ScreenShareMessages.COMMAND_INSUFICIENT_PERMISSIONS.sendMessage(player);
					} else if (args[0].equalsIgnoreCase("-history")) {
						if (player.hasPermission(ScreenSharePermissions.VIEW_HISTORY.toPermission())) {
							ScreenShareHistoryGUI.openGUI(player);
							ScreenShareMessages.COMMAND_OPEN_HISTORY_SESSIONS.sendMessage(player);
						} else
							ScreenShareMessages.COMMAND_INSUFICIENT_PERMISSIONS.sendMessage(player);
					} else if (args[0].equalsIgnoreCase("-config")) {
						if (player.hasPermission(ScreenSharePermissions.OPEN_CONFIG.toPermission())) {
							ScreenShareConfigGUI.openGUI(player);
							ScreenShareMessages.COMMAND_OPEN_CONFIG.sendMessage(player);
						} else
							ScreenShareMessages.COMMAND_INSUFICIENT_PERMISSIONS.sendMessage(player);
					} else {
						ScreenSharePlayerGUI.openGUI(player, args[0]);
						ScreenShareMessages.COMMAND_OPEN_PLAYER_INFO.replace("<nickname>", args[0]).sendMessage(player);
					}
				} else
					ScreenShareMessages.COMMAND_USAGE.replace("<command>", label).sendMessage(player);
			} else
				ScreenShareMessages.COMMAND_INSUFICIENT_PERMISSIONS.sendMessage(player);
		} else
			ScreenShareMessages.COMMAND_ONLY_PLAYERS_USE.sendMessage(sender);
		return true;
	}
}