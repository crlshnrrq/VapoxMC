package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerGroup;

public final class GroupCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.group")) {
				if (args.length > 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						PlayerGroup group = PlayerGroup.getByName(args[1]);
						if (group != null) {
							String permission = "ciphen.tag.gerente";
							if (group == PlayerGroup.DONO || group == PlayerGroup.DIRETOR || group == PlayerGroup.COORD)
								permission = "ciphen.tag.dono";
							if (group == PlayerGroup.ADMIN)
								permission = "ciphen.tag.diretor";
							if (group == PlayerGroup.ADMIN || group == PlayerGroup.MODGC || group == PlayerGroup.MOD
									|| group == PlayerGroup.YOUTUBERPLUS || group == PlayerGroup.HYPER
									|| group == PlayerGroup.PRO || group == PlayerGroup.MVP || group == PlayerGroup.VIP)
								permission = "ciphen.tag.admin";

							if (player.hasPermission(permission)) {
								Bukkit.getOnlinePlayers().stream()
										.filter(players -> players.hasPermission("ciphen.comandos.group"))
										.forEach(players -> {
											players.sendMessage(
													"§7§o(STAFF) §f" + player.getName() + " §7alterou o grupo de §f"
															+ target.getName() + " §7para: §f" + args[1]);
										});
								player.sendMessage("§b§l[GRUPO] §fVocê alterou o grupo de §b" + target.getName()
										+ " §fpara " + group.getColoredName() + " §fpermanentemente.");

								Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
										"pex user " + target.getName() + " group set " + args[1]);
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tag " + args[1]);
								target.sendMessage("§a§l[GRUPO] §fSeu grupo foi alterado para " + group.getColoredName()
										+ " §fpor §b" + player.getName() + " §fpermanentemente!");
							} else
								player.sendMessage(
										"§c§l[GRUPO] §fVocê não tem §cpermissão §fpara utilizar esse §cgrupo§f!");
						} else
							player.sendMessage("§c§l[GRUPO] §fGrupo digitado não encontrado.");
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else {
					player.sendMessage("§cComando incorreto, utilize /group [Jogador] [Grupo]");
					String groups = "";
					for (PlayerGroup group : PlayerGroup.values())
						groups += (!groups.isEmpty() ? "§c, " : "") + group.getColoredName();
					player.sendMessage("§c§l[GRUPOS] " + groups);
				}
			} else
				player.sendMessage("§cÉ necessário ser [GERENTE] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}