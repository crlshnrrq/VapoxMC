package br.com.vapoxmc.kitpvp.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.commons.medalhas.Medalha;
import br.com.vapoxmc.kitpvp.commons.medalhas.MedalhaAPI;
import br.com.vapoxmc.kitpvp.player.PlayerRank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class ChatListeners implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage(), format = "§7(" + PlayerRank.getRank(player).getColoredSymbol() + "§7) §r"
				+ player.getDisplayName() + " §7» ";

		if (player.hasPermission("chat.colorido"))
			message = ChatColor.translateAlternateColorCodes('&', message).replace("§k", "&k");
		if (player.hasPermission("chat.destaque"))
			format += "§f" + message;
		else
			format += "§7" + message.toLowerCase();

		if (!VapoxPvP.getChat() && !player.hasPermission("chat.speakoff")) {
			event.setCancelled(true);
			player.sendMessage("§c§l[CHAT] §fO chat está §c§ldesativado§f!");
		}

		if (!event.isCancelled()) {
			event.setCancelled(true);
			TextComponent text = new TextComponent("");

			if (MedalhaAPI.hasMedalha(player)) {
				Medalha medalha = MedalhaAPI.getMedalha(player);

				ArrayList<String> lines = new ArrayList<>();
				String line = "";
				for (String word : medalha.getDescription().split(" ")) {
					if (line.split(" ").length == 9 || line.length() >= 40) {
						lines.add(line);
						line = "";
					}
					line += (line.isEmpty() ? "" : " ") + word;
				}
				lines.add(line);

				String description = "";
				for (String linha : lines)
					description += (description.isEmpty() ? "" : "\n") + "  §f" + linha + "   ";

				text.addExtra(" ");
				TextComponent medal = new TextComponent(medalha.getColoredSymbol());
				medal.setColor(medalha.getColor().asBungee());
				medal.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new ComponentBuilder(medalha.getColor() + "Medalha " + medalha.getName() + " "
								+ medalha.getSymbol() + "   \n  §8Medalha " + medalha.getCategoria().getName()
								+ "   \n\n" + description).create()));
				text.addExtra(medal);
				text.addExtra(" ");
			}
			text.addExtra(format);

			Bukkit.getConsoleSender().sendMessage("[CHAT] " + text.toLegacyText());
			event.getRecipients().forEach(players -> players.spigot().sendMessage(text));
		}
	}
}