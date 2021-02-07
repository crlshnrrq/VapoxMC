package br.com.vapoxmc.vapoxpvp.sidebarssystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.vapoxmc.kitpvp.utils.Strings;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.commands.SidebarCommand;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.events.PlayerChangeSidebarEvent;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.events.PlayerRemoveSidebarEvent;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.events.PlayerUpdateSidebarEvent;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars.FPSSidebar;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars.FishermanSidebar;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars.KnockbackSidebar;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars.LavaChallengeSidebar;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars.MainSidebar;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars.SumoSidebar;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.sidebars.UMvUMSidebar;
import br.com.vapoxmc.vapoxpvp.system.BukkitSystem;

public final class SidebarsSystem extends BukkitSystem {

	private Sidebar noneSidebar, defaultSidebar;
	private ArrayList<Sidebar> sidebars;
	private HashMap<UUID, Sidebar> sidebarMap;

	public Sidebar getNoneSidebar() {
		return this.noneSidebar;
	}

	public Sidebar getDefaultSidebar() {
		return this.defaultSidebar;
	}

	public ArrayList<Sidebar> getSidebars() {
		return this.sidebars;
	}

	public boolean containsSidebar(Sidebar sidebar) {
		return this.getSidebars().contains(sidebar);
	}

	public void addSidebars(Sidebar sidebar) {
		if (!this.containsSidebar(sidebar))
			this.getSidebars().add(sidebar);
	}

	public void removeSidebar(Sidebar sidebar) {
		this.getSidebars().remove(sidebar);
	}

	public Sidebar getSidebarByName(String name) {
		return this.getSidebars().stream().filter(sidebar -> sidebar.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);
	}

	public SidebarsSystem(Plugin plugin) {
		super("Sidebars", plugin);
	}

	@Override
	public void onEnable() {
		if (this.getNoneSidebar() == null)
			this.noneSidebar = new Sidebar("Nenhuma", Strings.getName());
		if (this.getDefaultSidebar() == null)
			this.defaultSidebar = new MainSidebar();
		if (this.getSidebars() == null)
			this.sidebars = new ArrayList<>();
		if (this.sidebarMap == null)
			this.sidebarMap = new HashMap<>();

		this.addSidebars(this.getDefaultSidebar());
		this.addSidebars(new FishermanSidebar());
		this.addSidebars(new FPSSidebar());
		this.addSidebars(new KnockbackSidebar());
		this.addSidebars(new LavaChallengeSidebar());
		this.addSidebars(new SumoSidebar());
		this.addSidebars(new UMvUMSidebar());

		this.addListener(new SidebarListeners());

		this.addCommand(new SidebarCommand());

		super.onEnable();
	}

	@Override
	public void onDisable() {
		if (this.getNoneSidebar() != null) {
			this.removeSidebar(this.getNoneSidebar());
			this.noneSidebar = null;
		}
		if (this.getDefaultSidebar() != null) {
			this.removeSidebar(this.getDefaultSidebar());
			this.defaultSidebar = null;
		}
		if (this.getSidebars() != null) {
			this.getSidebars().clear();
			this.sidebars = null;
		}
		if (this.sidebarMap != null) {
			this.sidebarMap.clear();
			this.sidebarMap = null;
		}

		super.onDisable();
	}

	public boolean hasSidebar(Player player) {
		return this.sidebarMap.containsKey(player.getUniqueId());
	}

	public Sidebar getSidebar(Player player) {
		return this.sidebarMap.getOrDefault(player.getUniqueId(), this.getNoneSidebar());
	}

	public void setSidebar(Player player, Sidebar sidebar) {
		PlayerChangeSidebarEvent event = new PlayerChangeSidebarEvent(player, sidebar);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			sidebar.sendSidebar(player);
			this.sidebarMap.put(player.getUniqueId(), sidebar);
			this.updateSidebar(player);
		}
	}

	public void updateSidebar(Player player) {
		PlayerUpdateSidebarEvent event = new PlayerUpdateSidebarEvent(player, this.getSidebar(player));
		if (!event.isCancelled())
			event.getSidebar().update(player);
	}

	public Sidebar removeSidebar(Player player) {
		PlayerRemoveSidebarEvent event = new PlayerRemoveSidebarEvent(player, this.getSidebar(player));
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			Scoreboard scoreboard = player.getScoreboard();
			Objective objective = scoreboard.getObjective("Sidebar");
			if (objective != null)
				objective.unregister();
			this.sidebarMap.remove(player.getUniqueId());
		}
		return event.getSidebar();
	}
}