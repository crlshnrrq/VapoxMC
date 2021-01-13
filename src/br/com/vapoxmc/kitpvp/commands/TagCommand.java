package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerTag;

public final class TagCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				PlayerTag tag = PlayerTag.getByName(args[0]);
				if (tag != null) {
					if (player.hasPermission("ciphen.tag." + tag.name().toLowerCase())) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"nte player " + player.getName() + " prefix " + tag.getPrefix().replace("§", "&"));
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"nte player " + player.getName() + " priority " + tag.getPriority());
						player.setDisplayName(tag.getPrefix() + player.getName());
						player.sendMessage("§aSua tag foi alterada para: " + args[0]);
					} else {
						player.sendMessage("§cVocê não tem permissão para utilizar esta tag!");
						if (tag == PlayerTag.TORNEIO)
							player.sendMessage(
									"§cPara liberar a permissão, utilize /discord e acesse o canal #torneio!");
					}
				}
			} else {
				player.sendMessage("§cComando incorreto, utilize /" + label + " [Tag]");
				String tags = "";
				for (PlayerTag tag : PlayerTag.values()) {
					if (player.hasPermission("ciphen.tag." + tag.name().toLowerCase()))
						tags += (!tags.isEmpty() ? "§f, " : "") + tag.getColor() + "§l" + tag.name();
				}
				player.sendMessage("§e§l[TAGS] " + tags);
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}