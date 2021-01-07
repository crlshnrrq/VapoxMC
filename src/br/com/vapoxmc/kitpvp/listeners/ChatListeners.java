package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import br.com.vapoxmc.kitpvp.player.PlayerRank;

public final class ChatListeners implements Listener {

	@EventHandler
	private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage().replace("%", "%%"),
				format = "§7(" + PlayerRank.getRank(player).getColoredSymbol() + "§7) §r" + player.getDisplayName()
						+ " §7» §7" + message.toLowerCase();

		if (player.hasPermission("ciphen.chat.colorido"))
			format += "§f" + message;
		else
			format += "§7" + message.toLowerCase();

		event.setFormat(format);
	}
}