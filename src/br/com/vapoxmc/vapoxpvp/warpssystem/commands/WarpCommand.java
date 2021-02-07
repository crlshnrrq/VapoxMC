package br.com.vapoxmc.vapoxpvp.warpssystem.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class WarpCommand extends Command {

	public WarpCommand() {
		super("Warp");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		String insert = StringUtils.join(Arrays.copyOfRange(args, 0, args.length), " ").toLowerCase();

		WarpsSystem system = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (system == null || !(system instanceof WarpsSystem)) {
			sender.sendMessage("§c• §fO Sistema responsável por este §cComando §fnão foi encontrado.");
			return true;
		}
		if (!system.isEnable()) {
			sender.sendMessage("§c• §fO Sistema responsável por este §cComando §festá §cdesabilitado§f.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage("§c• §fSintaxe incorreta, use:");
			if (sender.hasPermission("command.warp.manage"))
				sender.sendMessage(" §4• §c/" + label + " <Warp Name> <Enable|Disable>");
			sender.sendMessage(" §4• §c/" + label + " <Warp Name> [Info]");
			sender.sendMessage(" §4• §c/" + label + " <Warp Name>");
			return true;
		}

		int length = args.length;
		Warp warp = null;
		while (warp == null) {
			if (length > 0) {
				warp = system.getWarpByName(StringUtils.join(Arrays.copyOfRange(args, 0, length), " "));
				length--;
			} else
				break;
		}
		if (warp == null) {
			sender.sendMessage("§c• §fA Warp §c§n" + args[0] + "§r §fnão foi encontrada.");
		}
		insert = insert.replace(warp.getName().toLowerCase(), "").trim();

		if (insert.isEmpty()) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§e• §fApenas §eJogadores §fpodem executar este §eComando§f.");
				return true;
			}
			Player player = (Player) sender;

			if (system.hasWarp(player) && system.getWarp(player).getName().equals(warp.getName())) {
				player.sendMessage("§c• §fVocê já está na Warp §c§n" + warp.getName() + "§f.");
				return true;
			}
			if (!warp.isEnable() && !player.hasPermission("command.warp.manage")) {
				player.sendMessage("§c• §fO Kit §c§n" + warp.getName() + "§r §festá §cdesabilitado§f.");
				return true;
			}

			system.setWarp(player, warp);
			return true;
		}

		if (insert.startsWith("info")) {
			insert = insert.replace("info", "").trim();

			sender.sendMessage("§e• §fInformações da Warp §e§n" + warp.getName() + "§f:");
			sender.sendMessage(" §6• §fDescrição: §7" + warp.getDescription());

			Stack icon = warp.getIcon();
			sender.sendMessage(
					" §6• §fÍcone: §7" + icon.getType() + ", " + icon.getAmount() + ", " + icon.getDurability());

			if (sender.hasPermission("command.warp.manage"))
				sender.sendMessage(" §6• §fHabilitado: " + (warp.isEnable() ? "§aSim" : "§cNão"));
			sender.sendMessage(" ");
			return true;
		}
		if (insert.startsWith("enable")) {
			insert = insert.replace("enable", "").trim();

			if (!sender.hasPermission("command.warp.manage")) {
				sender.sendMessage(
						"§c• §fVocê não possui Permissão para §chabilitar §fa Warp §c§n" + warp.getName() + "§f.");
				return true;
			}
			if (warp.isEnable()) {
				sender.sendMessage("§e• §fA Warp §e§n" + warp.getName() + "§r §fjá está §ehabilitada§f.");
				return true;
			}

			warp.setEnable(true);
			sender.sendMessage("§a• §fA Warp §a§n" + warp.getName() + "§r §ffoi §ahabilitada§f.");
			return true;
		}
		if (insert.startsWith("disable")) {
			insert = insert.replace("disable", "").trim();

			if (!sender.hasPermission("command.warp.manage")) {
				sender.sendMessage(
						"§c• §fVocê não possui Permissão para §cdesabilitar §fa Warp §c§n" + warp.getName() + "§f.");
				return true;
			}
			if (system.getDefaultWarp().getName().equals(warp.getName())) {
				sender.sendMessage("§c• §fA Warp §c§nPadrão§r §fnão pode ser §cdesativada§f.");
				return true;
			}
			if (!warp.isEnable()) {
				sender.sendMessage("§e• §fA Warp §e§n" + warp.getName() + "§r §fjá está §edesabilitada§f.");
				return true;
			}

			warp.setEnable(false);
			sender.sendMessage("§a• §fA Warp §a§n" + warp.getName() + "§r §ffoi §adesabilitada§f.");
			return true;
		}

		sender.sendMessage("§c• §fO Argumento §c§n" + insert + "§r §fnão foi encontrado.");
		return true;
	}
}