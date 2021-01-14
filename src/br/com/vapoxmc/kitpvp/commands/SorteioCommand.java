package br.com.vapoxmc.kitpvp.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;

public final class SorteioCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.sorteio")) {
				if (args.length > 0) {
					try {
						int amount = Integer.parseInt(args[0]);
						DecimalFormat df = new DecimalFormat("###,###.##");
						ArrayList<Player> jogadores = new ArrayList<>();
						Bukkit.getOnlinePlayers().forEach(players -> {
							if (!players.hasPermission("ciphen.comandos.staffchat"))
								jogadores.add(players);
							players.sendMessage("§a§l[SORTEIO] §fUm sorteio acabou de começar!");
							players.sendMessage("§a§l[SORTEIO] §fVocê já está participando!");
							players.sendMessage(
									"§a§l[SORTEIO] §fJogadores participando: §a" + Bukkit.getOnlinePlayers().size());
							players.sendMessage("§a§l[SORTEIO] §fPrêmio: §a" + df.format(amount) + " §fmoedas!");
							if (players.hasPermission("ciphen.comandos.sorteio"))
								players.sendMessage("§7§o(STAFF) Sorteio feito por: §f" + player.getName());
						});
						if (jogadores.size() < 1)
							player.sendMessage(
									"§c§l[SORTEIO] §c§lSORTEIO CANCELADO§f! Jogadores insuficientes para fazer o sorteio!");

						Player vencedor = jogadores.stream().findAny().orElse(null);
						int chance = jogadores.size() / 100;

						Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
							Bukkit.broadcastMessage("§a§l[SORTEIO] §fO vencedor foi: §a" + vencedor.getName() + " §7("
									+ chance + "% de chance)");
							if (vencedor != null && vencedor.isOnline()) {
								vencedor.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
								PlayerAccount.addMoedas(vencedor, amount);
								vencedor.sendMessage("§a§l[MOEDAS] §fVocê recebeu §a" + df.format(amount)
										+ " §fmoedas de §aSorteio§f!");
							} else
								Bukkit.broadcastMessage(
										"§c§l[SORTEIO] §fO vencedor está §coffline§f, o sorteio foi §ccancelado§f!");
						}, 200L);
					} catch (NumberFormatException ex) {
						player.sendMessage("§c§l[SORTEIO] §fO valor digitado é inválido!");
					}
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Quantia]");
			} else
				player.sendMessage("§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}