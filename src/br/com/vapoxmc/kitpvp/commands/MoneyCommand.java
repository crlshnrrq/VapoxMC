package br.com.vapoxmc.kitpvp.commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class MoneyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			DecimalFormat df = new DecimalFormat("###,###.##");
			if (args.length > 0) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					player.sendMessage("§a§l[MOEDAS] §fO saldo de §a" + target.getName() + " §fé de: §a"
							+ df.format(PlayerAccount.getGeral().getMoedas(target)) + " §fmoedas!");
					TextComponent transfer = new TextComponent(
							"§e§l[DICA] §fClique §e§lAQUI §fpara transferir moedas para §a" + target.getName());
					transfer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("§aO comando de transferir será digitado no seu chat.").create()));
					transfer.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
							"/transferir " + target.getName() + " x"));
					player.spigot().sendMessage(transfer);
				} else
					player.sendMessage("§cJogador \"" + args[0] + "\" não encontrado.");
			} else {
				player.sendMessage("§a§l[MOEDAS] §fSeu saldo é de: §a"
						+ df.format(PlayerAccount.getGeral().getMoedas(player)) + " §fmoedas!");
				player.sendMessage("§e§l[DICA] §fUtilize §a/transferir [Jogador] [Moedas] §fpara enviar moedas!");
			}
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}