package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class InfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.info")) {
				if (args.length > 0) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						player.sendMessage("§fInformações sobre §a" + target.getName() + "§f.");
						player.sendMessage("§aNick: §f" + target.getName());
						if (VapoxPvP.hasKit(target))
							player.sendMessage("§aKit: §f" + VapoxPvP.getKit(target).getName());
						else
							player.sendMessage("§aWarp: §f" + VapoxPvP.getWarp(target).getName());
						player.sendMessage("§aPing: §f" + ((CraftPlayer) target).getHandle().ping);
						player.sendMessage("§aGameMode: §f" + target.getGameMode().name());
						player.sendMessage("§aFly: §f" + (target.getAllowFlight() ? "§aSim" : "§cNão"));
						TextComponent history = new TextComponent("[Histórico de punições]");
						history.setColor(ChatColor.GRAY);
						history.setItalic(true);
						history.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder("§aClique para ver as punições!").create()));
						history.setClickEvent(
								new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/history " + target.getName()));
						player.spigot().sendMessage(history);
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador]");
			} else
				player.sendMessage("§cÉ necessário ser [TRIAL] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}