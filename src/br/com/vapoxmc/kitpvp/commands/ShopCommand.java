package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.gui.ShopGUI;
import br.com.vapoxmc.kitpvp.gui.ShopKDRGUI;
import br.com.vapoxmc.kitpvp.gui.ShopKitsGUI;

public final class ShopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("VIP"))
					player.sendMessage("§c§l[LOJA] §fA loja de vip está §c§ltemporariamente §fdesativada.");
				else if (args[0].equalsIgnoreCase("ResetKDR"))
					ShopKDRGUI.openGUI(player);
				else if (args[0].equalsIgnoreCase("Kits"))
					ShopKitsGUI.openGUI(player);
			} else
				ShopGUI.openGUI(player);
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}