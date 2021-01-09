package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;

public final class ClickTestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.clicktest")) {
				if (args.length > 0) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (!VapoxPvP.hasClickTest(target)) {
							VapoxPvP.setClickTest(target, 0);
							player.sendMessage("§a§l[ClickTest] §fClickTest iniciado no jogador §a§l" + target.getName()
									+ "§f, aguarde 5 segundos.");
							Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
								int clicks = VapoxPvP.removeClickTest(target);
								player.sendMessage("§e§l[ClickTest] §fResultados de: §e" + target.getName() + "§f.");
								player.sendMessage("§e§l[ClickTest] §fClicks em §e§l5 §fsegundos: §e§l" + clicks);
								player.sendMessage("§e§l[ClickTest] §fClicks por segundo: §e§l" + (clicks / 5)
										+ " §7(clicks / tempo)");
							}, 100L);
						} else
							player.sendMessage("§c§l[ClickTest] §fEsse jogador já está sendo testado!");
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