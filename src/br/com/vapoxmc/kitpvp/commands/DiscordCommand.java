package br.com.vapoxmc.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.Strings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public final class DiscordCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage(" ");
			TextComponent discord = new TextComponent("§fClique §e§lAQUI §fpara acessar o §5§ldiscord§f.");
			discord.setClickEvent(new ClickEvent(Action.OPEN_URL, Strings.getDiscord()));
			player.spigot().sendMessage(discord);
			player.sendMessage("§fOu, se preferir, o §alink direto §fé: §a" + Strings.getDiscord());
			player.sendMessage(" ");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}