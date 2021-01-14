package br.com.vapoxmc.kitpvp.commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;

public final class TransferirCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (!target.getName().equals(player.getName())) {
						try {
							int amount = Integer.parseInt(args[1]);
							if (amount >= 1) {
								if (amount <= PlayerAccount.getMoedas(player)) {
									DecimalFormat df = new DecimalFormat("###,###.##");

									PlayerAccount.drawMoedas(player, amount);
									player.sendMessage("§a§l[MOEDAS] §fVocê enviou §a" + df.format(amount)
											+ " §fmoedas para §a" + target.getName());

									PlayerAccount.addMoedas(target, amount);
									player.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + df.format(amount)
											+ " §fmoedas de §a" + player.getName());
								} else
									player.sendMessage("§c§l[MOEDAS] §fVocê não possuí moedas o suficiente!");
							} else
								player.sendMessage("§c§l[MOEDAS] §fO valor digitado não pode ser menor que §c§l1§f.");
						} catch (NumberFormatException ex) {
							player.sendMessage("§c§l[MOEDAS] §fO valor digitado é inválido!");
						}
					} else
						player.sendMessage("§c§l[MOEDAS] §fVocê não pode enviar moedas para sí mesmo!");
				} else
					player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
			} else
				player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador] [Quantia]");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}