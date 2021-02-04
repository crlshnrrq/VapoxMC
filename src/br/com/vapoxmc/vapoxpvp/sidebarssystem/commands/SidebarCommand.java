package br.com.vapoxmc.vapoxpvp.sidebarssystem.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.Sidebar;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.SidebarsSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class SidebarCommand extends Command {

	public SidebarCommand() {
		super("Sidebar");
		this.setAliases(Arrays.asList("scoreboard", "score"));
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		SidebarsSystem system = (SidebarsSystem) KitPvP.getGeneralSystem().getSystemByName("Sidebars");
		if (system == null || !(system instanceof SidebarsSystem)) {
			sender.sendMessage("§c• §fO Sistema responsável por este §cComando §fnão foi encontrado.");
			return true;
		}
		if (!system.isEnable()) {
			sender.sendMessage("§c• §fO Sistema responsável por este §cComando §festá §cdesabilitado§f.");
			return true;
		}
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§e• §fApenas §eJogadores §fpodem executar este §eComando§f.");
				return true;
			}

			Player player = (Player) sender;
			if (system.hasSidebar(player)) {
				system.removeSidebar(player);
				player.sendMessage(" §e• §fVocê §edesabilitou §fsua §e§nSidebar§f.");
				return true;
			}

			Sidebar sidebar = null;
			WarpsSystem warps = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
			if (warps != null && warps instanceof WarpsSystem && warps.isEnable())
				sidebar = system.getSidebarByName(warps.getWarp(player).getName());
			if (sidebar == null)
				sidebar = system.getDefaultSidebar();

			system.setSidebar(player, sidebar);
			player.sendMessage(" §e• §fVocê §ehabilitou §fsua §e§nSidebar§f.");
		}
		return true;
	}
}