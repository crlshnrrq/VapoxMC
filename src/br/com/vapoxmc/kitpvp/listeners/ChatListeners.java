package br.com.vapoxmc.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatListeners implements Listener {

	@EventHandler
	private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage().replace("%", "%%"),
				format = player.getDisplayName() + " §7» §7" + message.toLowerCase();

		if (player.hasPermission("ciphen.chat.colorido"))
			format = player.getDisplayName() + " §7» §f" + message;

		event.setFormat(format);
	}
}