package br.com.vapoxmc.kitpvp.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class ReportCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length > 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target != null) {
					if (!VapoxPvP.hasUseReport(player)) {
						if (player.getName().equals(target.getName())) {
							String reason = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
							VapoxPvP.addUseReport(player);
							player.sendMessage("§a§l[REPORT] §aSua denúncia sobre " + target.getName()
									+ " foi enviada com sucesso!");
							Bukkit.getOnlinePlayers().stream()
									.filter(players -> players.hasPermission("ciphen.comandos.report"))
									.forEach(players -> {
										players.playSound(players.getLocation(), Sound.ANVIL_LAND, 10F, 10F);
										players.playSound(players.getLocation(), Sound.FIREWORK_LARGE_BLAST, 10F, 10F);
										VapoxUtils.sendActionBar(players, "§c§lALERTA DE REPORT! §7(Veja o chat)");
										players.sendMessage(" ");
										players.sendMessage("   §c§lALERTA DE REPORT!");
										players.sendMessage("§fReportado: §c" + target.getName() + " §7("
												+ ((CraftPlayer) target).getHandle().ping + "ms | "
												+ VapoxPvP.getKit(target).getName() + ")");
										players.sendMessage("§fMotivo: §a" + reason);
										players.sendMessage("§fAutor: §a" + player.getName() + " §7("
												+ ((CraftPlayer) player).getHandle().ping + "ms | "
												+ VapoxPvP.getKit(player).getName() + ")");
										TextComponent teleport = new TextComponent(
												"§7[Clique aqui para ir até §f" + target.getName() + "§7]");
										teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
												new ComponentBuilder("§aClique para teleportar-se!").create()));
										teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
												"/rtp " + target.getName()));
										players.spigot().sendMessage(teleport);
										players.sendMessage(" ");
									});
							Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(),
									() -> VapoxPvP.removeUseReport(player), 300L);
						} else
							player.sendMessage("§c§l[REPORT] §cVocê não pode reportar a sí mesmo.");
					} else
						player.sendMessage("§c§l[REPORT] §cUm staff já está vendo sua denúncia, aguarde!");
				} else
					player.sendMessage("§c§l[REPORT] §cO jogador \"" + args[0] + " \" não foi encontrado!");
			} else
				player.sendMessage("§cComando incorreto, utilize /" + label + " [Jogador] [Motivo]");
		} else
			sender.sendMessage("§cVocê não pode executar este comando!");
		return true;
	}
}