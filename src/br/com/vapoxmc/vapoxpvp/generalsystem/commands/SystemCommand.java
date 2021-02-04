package br.com.vapoxmc.vapoxpvp.generalsystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.system.System;

public final class SystemCommand extends Command {

	public SystemCommand() {
		super("System");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.hasPermission("command.system")) {
			sender.sendMessage("§e• §fVocê não possui §ePermissão §fpara executar este §eComando§f.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage("§c• §fSintaxe incorreta, use:");
			sender.sendMessage(" §4• §c/" + label + " <System Name> <Info|Enable|Disable>");
			sender.sendMessage(" §4• §c/" + label + " <List>");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("List")) {
				sender.sendMessage("§e• §fLista de Sistemas:");
				KitPvP.getGeneralSystem().getSystems().forEach(system -> sender
						.sendMessage(" " + (system.isEnable() ? "§2•" : "§4•") + " §f" + system.getName() + " "));
				sender.sendMessage(" ");
				return true;
			}
		}

		System system = KitPvP.getGeneralSystem().getSystemByName(args[0]);
		if (system == null) {
			sender.sendMessage("§c• §fO Sistema §c§n" + args[0] + "§r §fnão foi encontrado.");
			return true;
		}
		if (args.length == 1) {
			sender.sendMessage("§e• §fArgumentos: §eInfo§f, §eEnable§f, §eDisable");
			return true;
		}

		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("Info")) {
				sender.sendMessage("§e• §fInformações do Sistema §e§n" + system.getName() + "§f:");
				sender.sendMessage(" §6• §fHabilitado: " + (system.isEnable() ? "§aSim" : "§cNão"));
				sender.sendMessage(" ");
				return true;
			}

			if (args[1].equalsIgnoreCase("Enable")) {
				if (system.isEnable()) {
					sender.sendMessage("§e• §fO Sistema §e§n" + system.getName() + "§r §fjá está §ehabilitado§f.");
					return true;
				}

				system.onEnable();
				sender.sendMessage("§a• §fO Sistema §a§n" + system.getName() + "§r §ffoi §ahabilitado§f.");
				return true;
			}
			if (args[1].equalsIgnoreCase("Disable")) {
				if (!system.isEnable()) {
					sender.sendMessage("§e• §fO Sistema §e§n" + system.getName() + "§r §fjá está §edesabilitado§f.");
					return true;
				}

				system.onDisable();
				sender.sendMessage("§a• §fO Sistema §a§n" + system.getName() + "§r §ffoi §adesabilitado§f.");
				return true;
			}

			sender.sendMessage("§c• §fO Argumento §c§n" + args[1] + "§r §fnão foi encontrado.");
			return true;
		}
		return true;
	}
}