package br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.player.PlayerGroup;
import br.com.vapoxmc.kitpvp.player.PlayerRank;
import br.com.vapoxmc.kitpvp.utils.Strings;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.Sidebar;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public final class EventoSidebar extends Sidebar {

	public EventoSidebar() {
		super("Evento", Strings.getName());
		this.addLine(" ");
		this.addLine("§fCargo: §a");
		this.addLine("§fRank: §a");
		this.addLine(" ");
		this.addLine("§fWarp: §a");
		this.addLine(" ");
		this.addLine("§fMoedas: §a");
		this.addLine("§fPontos: §a");
		this.addLine(" ");
		this.addLine("§7vapoxmc.com.br");
	}

	@Override
	public void update(Player player) {
		NumberFormat nf = DecimalFormat.getIntegerInstance();
		super.update(player);

		this.updateLine(player, "§fCargo: §a", PlayerGroup.getGroup(player).getColoredName());
		this.updateLine(player, "§fRank: §a", PlayerRank.getRank(player).getColoredName());

		WarpsSystem warps = (WarpsSystem) KitPvP.getGeneralSystem().getSystemByName("Warps");
		if (warps != null && warps instanceof WarpsSystem && warps.isEnable())
			this.updateLine(player, "§fWarp: §a", warps.getWarp(player).getName());

		this.updateLine(player, "§fMoedas: §a", nf.format(PlayerAccount.getMoedas(player)));
		this.updateLine(player, "§fPontos: §a", nf.format(PlayerAccount.getPontos(player)));
	}
}