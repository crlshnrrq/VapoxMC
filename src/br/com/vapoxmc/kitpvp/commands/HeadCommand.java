package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.Stack;

public final class HeadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.head")) {
				if (args.length > 0) {
					player.getInventory().addItem(new Stack(Material.SKULL_ITEM, 1, 3).owner(args[0])
							.display("§fCabeça de: \"§b" + args[0] + "§f\""));
					player.sendMessage("§e§l[HEAD] §fVocê pegou a cabeça de §e" + args[0] + "§f.");
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador]");
			} else
				player.sendMessage("§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}