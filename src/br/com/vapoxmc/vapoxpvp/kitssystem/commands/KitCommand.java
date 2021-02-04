package br.com.vapoxmc.vapoxpvp.kitssystem.commands;

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
		Kit kit = system.getKitByName(args[0]);
		if (kit == null) {
			sender.sendMessage("§c• §fO Kit §c§n" + args[0] + "§r §fnão foi encontrado.");
			return true;
		}
		if (args.length == 1) {
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
				player.sendMessage("§c• §fVocê já está usando o §c§nKit " + system.getKit(player).getName() + "§f.");
				return true;
			}
			if (!kit.isEnable() && !player.hasPermission("command.kit.manage")) {
				player.sendMessage("§c• §fO Kit §c§n" + kit.getName() + " §festá §cdesabilitado§f.");
				return true;
			}

			system.setKit(player, kit);
			return true;
		}
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("Info")) {
				sender.sendMessage("§e• §fInformações do Kit §e§n" + kit.getName() + "§f:");
				if (sender.hasPermission("command.kit.manage"))
					sender.sendMessage(" §6• §fPermissão: §7" + kit.getPermission());
				sender.sendMessage(" §6• §fDescrição: §7" + kit.getDescription());

				String icon = kit.getIcon().getType().name() + ", " + kit.getIcon().getAmount() + ", "
						+ kit.getIcon().getDurability();
				sender.sendMessage(" §6• §fÍcone: §7" + icon);

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
			if (args[1].equalsIgnoreCase("Enable")) {
				if (!sender.hasPermission("command.kit.manage")) {
					sender.sendMessage(
							"§c• §fVocê não possui Permissão para §chabilitar §fo Kit §c§n" + kit.getName() + "§f.");
					return true;
				}
				if (kit.isEnable()) {
					sender.sendMessage("§e• §fO Kit §e§n" + kit.getName() + " §fjá está §ehabilitado§f.");
					return true;
				}

				kit.setEnable(true);
				sender.sendMessage("§a• §fO Kit §a§n" + kit.getName() + " §ffoi §ahabilitado§f.");
				return true;
			}
			if (args[1].equalsIgnoreCase("Disable")) {
				if (!sender.hasPermission("command.kit.manage")) {
					sender.sendMessage(
							"§c• §fVocê não possui Permissão para §chabilitar §fo Kit §c§n" + kit.getName() + "§f.");
					return true;
				}
				if (!kit.isEnable()) {
					sender.sendMessage("§e• §fO Kit §e§n" + kit.getName() + " §fjá está §edesabilitado§f.");
					return true;
				}

				kit.setEnable(false);
				sender.sendMessage("§a• §fO Kit §a§n" + kit.getName() + " §ffoi §adesabilitado§f.");
				return true;
			}

			sender.sendMessage("§c• §fO Argumento §c§n" + args[1].toUpperCase() + "§r §fnão foi encontrado.");
			return true;
		}
		return true;
	}
}