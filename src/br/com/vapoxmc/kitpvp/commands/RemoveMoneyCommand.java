package br.com.vapoxmc.kitpvp.commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;

public final class RemoveMoneyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.remmoney")) {
				if (args.length > 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						try {
							int amount = Integer.parseInt(args[1]);
							DecimalFormat df = new DecimalFormat("###,###.##");

							if (PlayerAccount.getGeral().getMoedas(target) >= amount) {
								PlayerAccount.getGeral().drawMoedas(target, amount);
								player.sendMessage("§a§l[MOEDAS] §fVocê removeu §a" + df.format(amount)
										+ " §fmoedas de §a" + target.getName());
								target.sendMessage("§a§l[MOEDAS] §f" + player.getName() + " removeu §a"
										+ df.format(amount) + " §fmoedas suas.");
								Bukkit.getOnlinePlayers().stream()
										.filter(players -> players.hasPermission("ciphen.comandos.addmoney"))
										.forEach(players -> players
												.sendMessage("§7§o(STAFF) §f" + player.getName() + " §7removeu §f"
														+ df.format(amount) + " §7moedas de §f" + target.getName()));
							} else
								player.sendMessage("§c§l[MOEDAS] §fO jogador não possui essa quantia de moedas!");
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