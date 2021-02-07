package br.com.vapoxmc.vapoxpvp.kitssystem.commands;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;

public final class KitCommand extends Command {

	public KitCommand() {
		super("Kit");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		String insert = StringUtils.join(Arrays.copyOfRange(args, 0, args.length), " ").toLowerCase();

		KitsSystem system = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (system == null || !(system instanceof KitsSystem)) {
			sender.sendMessage("§c• §fO Sistema responsável por este §cComando §fnão foi encontrado.");
			return true;
		}
		if (!system.isEnable()) {
			sender.sendMessage("§c• §fO Sistema responsável por este §cComando §festá §cdesabilitado§f.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage("§c• §fSintaxe incorreta, use:");
			if (sender.hasPermission("command.kit.manage"))
				sender.sendMessage(" §4• §c/" + label + " <Kit Name> <Enable|Disable>");
			sender.sendMessage(" §4• §c/" + label + " <Kit Name> [Info]");
			sender.sendMessage(" §4• §c/" + label + " <Kit Name> ");
			return true;
		}

		int length = args.length;
		Kit kit = null;
		while (kit == null) {
			if (length > 0) {
				kit = system.getKitByName(StringUtils.join(Arrays.copyOfRange(args, 0, length), " "));
				length--;
			} else
				break;
		}
		if (kit == null) {
			sender.sendMessage("§c• §fO Kit §c§n" + args[0] + "§r §fnão foi encontrado.");
			return true;
		}
		insert = insert.replace(kit.getName().toLowerCase(), "").trim();

		if (insert.isEmpty()) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§e• §fApenas §eJogadores §fpodem executar este §eComando§f.");
				return true;
			}
			Player player = (Player) sender;

			if (!player.hasPermission(kit.getPermission())) {
				player.sendMessage("§c• §fVocê não possui acesso ao §c§nKit " + kit.getName() + "§f.");
				return true;
			}
			if (system.hasKit(player)) {
				player.sendMessage("§c• §fVocê já está usando o Kit §c§n" + system.getKit(player).getName() + "§f.");
				return true;
			}
			if (!kit.isEnable() && !player.hasPermission("command.kit.manage")) {
				player.sendMessage("§c• §fO Kit §c§n" + kit.getName() + "§r §festá §cdesabilitado§f.");
				return true;
			}

			system.setKit(player, kit);
			return true;
		}
		if (insert.startsWith("info")) {
			insert = insert.replace("info", "").trim();

			sender.sendMessage("§e• §fInformações do Kit §e§n" + kit.getName() + "§f:");
			if (sender.hasPermission("command.kit.manage"))
				sender.sendMessage(" §6• §fPermissão: §7" + kit.getPermission());
			sender.sendMessage(" §6• §fDescrição: §7" + kit.getDescription());

			Stack icon = kit.getIcon();
			sender.sendMessage(
					" §6• §fÍcone: §7" + icon.getType() + ", " + icon.getAmount() + ", " + icon.getDurability());

			String items = "";
			for (Stack item : kit.getItems())
				items += (items.isEmpty() ? "" : ", ") + (item.hasItemMeta() && item.getItemMeta().hasDisplayName()
						? item.getItemMeta().getDisplayName()
						: item.getType().name());
			sender.sendMessage(" §6• §fItens: §7" + items);
			if (sender.hasPermission("command.kit.manage"))
				sender.sendMessage(" §6• §fHabilitado: " + (kit.isEnable() ? "§aSim" : "§cNão"));
			sender.sendMessage(" ");
			return true;
		}
		if (insert.startsWith("enable")) {
			insert = insert.replace("enable", "").trim();

			if (!sender.hasPermission("command.kit.manage")) {
				sender.sendMessage(
						"§c• §fVocê não possui Permissão para §chabilitar §fo Kit §c§n" + kit.getName() + "§f.");
				return true;
			}
			if (kit.isEnable()) {
				sender.sendMessage("§e• §fO Kit §e§n" + kit.getName() + "§r §fjá está §ehabilitado§f.");
				return true;
			}

			kit.setEnable(true);
			sender.sendMessage("§a• §fO Kit §a§n" + kit.getName() + "§r §ffoi §ahabilitado§f.");
			return true;
		}
		if (insert.startsWith("disable")) {
			insert = insert.replace("disable", "").trim();

			if (!sender.hasPermission("command.kit.manage")) {
				sender.sendMessage(
						"§c• §fVocê não possui Permissão para §cdesabilitar §fo Kit §c§n" + kit.getName() + "§f.");
				return true;
			}
			if (system.getDefaultKit().getName().equals(kit.getName())) {
				sender.sendMessage("§c• §fO Kit §c§nPadrão§r §fnão pode ser §cdesativado§f.");
				return true;
			}
			if (!kit.isEnable()) {
				sender.sendMessage("§e• §fO Kit §e§n" + kit.getName() + "§r §fjá está §edesabilitado§f.");
				return true;
			}

			kit.setEnable(false);
			sender.sendMessage("§a• §fO Kit §a§n" + kit.getName() + "§r §ffoi §adesabilitado§f.");
			return true;
		}

		sender.sendMessage("§c• §fO Argumento §c§n" + insert + "§r §fnão foi encontrado.");
		return true;
	}
}