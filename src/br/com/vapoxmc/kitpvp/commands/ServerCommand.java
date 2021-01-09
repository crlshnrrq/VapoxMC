package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.Strings;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public final class ServerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("ciphen.comandos.server")) {
				int PING = 0, PLAYERS = Bukkit.getOnlinePlayers().size();
				for (Player players : Bukkit.getOnlinePlayers())
					PING += ((CraftPlayer) players).getHandle().ping;
				PING /= PLAYERS;

				long RAM_TOTAL = Runtime.getRuntime().totalMemory(),
						RAM_USED = RAM_TOTAL - Runtime.getRuntime().freeMemory();
				double RAM_USED_PERCENTAGE = RAM_USED * 1D / RAM_TOTAL * 100D;

				double TPS = Math.min((double) Math.round(MinecraftServer.getServer().recentTps[0] * 100.0) / 100.0,
						20.0);
				String tpsQuality = "";
				if (TPS >= 20)
					tpsQuality = "§aÓtimo";
				else if (TPS >= 19 && TPS < 20)
					tpsQuality = "§eMédio";
				else
					tpsQuality = "§cPéssimo";

				player.sendMessage("§7###############################################");
				player.sendMessage("§7# §e§lVapoxMC - Informações do servidor");
				player.sendMessage("§7#");
				player.sendMessage("§7# §fServidor: §a" + Bukkit.getServerName());
				player.sendMessage("§7# §fIP: §avapoxmc.com.br §7| §fPorta: §a25565");
				player.sendMessage("§7# §fOriginal: §a" + (Bukkit.getOnlineMode() ? "§aSim" : "§cNão"));
				player.sendMessage("§7# §fWhitelist: " + (Bukkit.hasWhitelist() ? "§aON" : "§cOFF"));
				player.sendMessage("§7# ");
				player.sendMessage("§7# §fSeu ping: §a" + ((CraftPlayer) player).getHandle().ping);
				player.sendMessage("§7# §fPing médio: §a" + PING + "§7(" + PLAYERS + " jogadores)");
				player.sendMessage("§7# ");
				player.sendMessage("§7# §fTPS: §a" + TPS + "/20 §7(" + tpsQuality + ")");
				player.sendMessage("§7# Memória RAM: §a" + (RAM_USED / 1048576L) + "mb§f/§a" + (RAM_TOTAL / 1048576L)
						+ "mb §7(" + String.valueOf(RAM_USED_PERCENTAGE).substring(0, 5) + "%");
				player.sendMessage("§7#");
				player.sendMessage("§7# §fMotd: " + Bukkit.getMotd());
				player.sendMessage("§7# §fMotd §7(ServerListeners)§f: §a" + Strings.getMotd());
				player.sendMessage("§7# §fOnline: §a" + PLAYERS + "/" + Bukkit.getMaxPlayers());
				player.sendMessage("§7###############################################");
			} else
				player.sendMessage("§cÉ necessário ser [ADMINISTRADOR] ou superior para executar este comando!");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}