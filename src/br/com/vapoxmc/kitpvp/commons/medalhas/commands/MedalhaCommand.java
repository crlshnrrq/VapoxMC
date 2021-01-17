package br.com.vapoxmc.kitpvp.commons.medalhas.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.commons.medalhas.Medalha;
import br.com.vapoxmc.kitpvp.commons.medalhas.MedalhaAPI;
import br.com.vapoxmc.kitpvp.commons.medalhas.MedalhaPacote;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class MedalhaCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 0) {
				String name = StringUtils.join(Arrays.copyOfRange(args, 0, args.length), " ");
				Medalha medalha = Medalha.getByName(name);
				if (medalha != null) {
					if (!medalha.hasPermission()
							|| (medalha.hasPermission() && player.hasPermission(medalha.getPermission()))) {
						MedalhaAPI.setMedalha(player, medalha);
						player.sendMessage("§aVocê alterou sua medalha para: " + medalha.getColoredSymbol());
					} else
						player.sendMessage("§cVocê não tem permissão para utilizar esta medalha!");
				} else
					player.sendMessage("§cA medalha \"" + name + "\" não existe.");
			} else {
				TextComponent text = new TextComponent("§e• §fMedalhas: ");

				for (MedalhaPacote pacote : MedalhaPacote.values())
					pacote.getMedalhas().forEach(medalha -> {
						if (!medalha.hasPermission()
								|| (medalha.hasPermission() && player.hasPermission(medalha.getPermission()))) {
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
							if (pacote != MedalhaPacote.NENHUM)
								description += "\n\n§5Pacote " + pacote.getName();

							TextComponent medal = new TextComponent(medalha.getColoredSymbol());
							if (medalha == Medalha.NENHUMA)
								medal.setText(medalha.getColoredName());
							medal.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									new ComponentBuilder(medalha.getColor() + "Medalha " + medalha.getName() + " "
											+ medalha.getSymbol() + "   \n  §8Medalha "
											+ medalha.getCategoria().getName() + "   \n\n" + description).create()));
							medal.setClickEvent(
									new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/medalha " + medalha.getName()));
							text.addExtra(medal);
							text.addExtra(" ");
						}
					});
				player.spigot().sendMessage(text);
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}