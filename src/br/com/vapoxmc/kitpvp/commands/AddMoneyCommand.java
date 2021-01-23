package br.com.vapoxmc.kitpvp.commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;

public final class AddMoneyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("command.addmoney")) {
				if (args.length > 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						try {
							int amount = Integer.parseInt(args[1]);
							DecimalFormat df = new DecimalFormat("###,###.##");

							PlayerAccount.addMoedas(target, amount);
							player.sendMessage("§a§l[MOEDAS] §fVocê deu §a" + df.format(amount) + " §fmoedas para §a"
									+ target.getName());
							target.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + df.format(amount) + " §fmoedas de §a"
									+ player.getName());
							Bukkit.getOnlinePlayers().stream()
									.filter(players -> players.hasPermission("command.addmoney"))
									.forEach(players -> players
											.sendMessage("§7§o(STAFF) §f" + player.getName() + " §7deu §f"
													+ df.format(amount) + " §7moedas para §f" + target.getName()));
						} catch (NumberFormatException ex) {
							player.sendMessage("§c§l[MOEDAS] §fO valor digitado é inválido!");
						}
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador] [Quantia]");
			} else
				player.sendMessage("§cVocê deve ser [ADMINISTRADOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}