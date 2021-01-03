package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.kit.Kit;

public final class KitCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!VapoxPvP.hasKit(player)) {
				if (args.length > 0) {
					Kit kit = VapoxPvP.getKitByName(args[0]);
					if (kit != null) {
						if (player.hasPermission(kit.getPermission())) {
							if (VapoxPvP.setKit(player, kit))
								player.sendMessage(
										"§e§l[KIT] §eVocê pegou o kit \"" + kit.getName().toLowerCase() + "\"");
						} else
							player.sendMessage(
									"§c§l[KIT] §fVocê não tem acesso ao kit §c" + kit.getName().toLowerCase() + "§f.");
					}
				} else
					player.sendMessage("§c§l[KITS] §fUtilize §c/" + label + " [Kit]");
			} else
				player.sendMessage(
						"§c§l[KIT] §cVocê já está usando um kit. §7(" + VapoxPvP.getKit(player).getName() + ")");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}