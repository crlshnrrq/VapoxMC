package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.commons.medalhas.MedalhaAPI;
import br.com.vapoxmc.kitpvp.player.PlayerRank;
import net.md_5.bungee.api.ChatColor;

public final class ChatListeners implements Listener {

	@EventHandler
	private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage().replace("%", "%%").replace("&k", ""), format = "§7("
				+ PlayerRank.getRank(player).getColoredSymbol() + "§7) §r" + player.getDisplayName() + " §7» ";

		if (MedalhaAPI.hasMedalha(player))
			format = " " + MedalhaAPI.getMedalha(player).getColoredSymbol() + " " + format;

		if (player.hasPermission("ciphen.chat.colorido"))
			message = ChatColor.translateAlternateColorCodes('&', message);
		if (player.hasPermission("ciphen.chat.destaque"))
			format += "§f" + message;
		else
			format += "§7" + message.toLowerCase();

		if (!VapoxPvP.getChat() && !player.hasPermission("ciphen.chat.bypass")) {
			event.setCancelled(true);
			player.sendMessage("§c§l[CHAT] §fO chat está §c§ldesativado§f!");
		}

		event.setFormat(format);
	}
}