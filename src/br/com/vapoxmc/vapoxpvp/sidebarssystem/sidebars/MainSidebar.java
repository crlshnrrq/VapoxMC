package br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.bukkit.entity.Player;

import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.player.PlayerGroup;
import br.com.vapoxmc.kitpvp.player.PlayerRank;
import br.com.vapoxmc.kitpvp.utils.Strings;
import br.com.vapoxmc.vapoxpvp.KitPvP;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.Sidebar;

public final class MainSidebar extends Sidebar {

	public MainSidebar() {
		super("Principal", Strings.getName());
		this.addLine(" ");
		this.addLine("§fCargo: §a");
		this.addLine("§fRank: §a");
		this.addLine(" ");
		this.addLine("§fAbates: §a");
		this.addLine("§fMortes: §a");
		this.addLine("§fKillStreak: §a");
		this.addLine(" ");
		this.addLine("§fKit: §a");
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
		this.updateLine(player, "§fAbates: §a", nf.format(PlayerAccount.getAbates(player)));
		this.updateLine(player, "§fMortes: §a", nf.format(PlayerAccount.getMortes(player)));
		this.updateLine(player, "§fKillStreak: §a", nf.format(PlayerAccount.getKillStreak(player)));

		KitsSystem kits = (KitsSystem) KitPvP.getGeneralSystem().getSystemByName("Kits");
		if (kits != null && kits instanceof KitsSystem && kits.isEnable())
			this.updateLine(player, "§fKit: §a", kits.getKit(player).getName());

		this.updateLine(player, "§fMoedas: §a", nf.format(PlayerAccount.getMoedas(player)));
		this.updateLine(player, "§fPontos: §a", nf.format(PlayerAccount.getPontos(player)));
	}
}