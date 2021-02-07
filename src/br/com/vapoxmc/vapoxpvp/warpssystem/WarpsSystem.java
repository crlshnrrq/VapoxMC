package br.com.vapoxmc.vapoxpvp.warpssystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.warp.EventoWarp;
import br.com.vapoxmc.vapoxpvp.system.BukkitSystem;
import br.com.vapoxmc.vapoxpvp.warpssystem.commands.WarpCommand;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerRemoveWarpEvent;
import br.com.vapoxmc.vapoxpvp.warpssystem.events.PlayerTeleportWarpEvent;
import br.com.vapoxmc.vapoxpvp.warpssystem.guis.WarpsDesabilitadasGUI;
import br.com.vapoxmc.vapoxpvp.warpssystem.guis.WarpsGUI;
import br.com.vapoxmc.vapoxpvp.warpssystem.guis.WarpsHabilitadasGUI;
import br.com.vapoxmc.vapoxpvp.warpssystem.warps.FPSWarp;
import br.com.vapoxmc.vapoxpvp.warpssystem.warps.FishermanWarp;
import br.com.vapoxmc.vapoxpvp.warpssystem.warps.KnockbackWarp;
import br.com.vapoxmc.vapoxpvp.warpssystem.warps.LavaChallengeWarp;
import br.com.vapoxmc.vapoxpvp.warpssystem.warps.SpawnWarp;
import br.com.vapoxmc.vapoxpvp.warpssystem.warps.SumoWarp;
import br.com.vapoxmc.vapoxpvp.warpssystem.warps.UMvUMWarp;

public final class WarpsSystem extends BukkitSystem {

	private Warp noneWarp, defaultWarp;
	private ArrayList<Warp> warps;
	private HashMap<UUID, Warp> warpMap;

	public Warp getNoneWarp() {
		return this.noneWarp;
	}

	public Warp getDefaultWarp() {
		return this.defaultWarp;
	}

	public ArrayList<Warp> getWarps() {
		return this.warps;
	}

	public boolean containsWarp(Warp warp) {
		return this.getWarps().contains(warp);
	}

	public void addWarp(Warp warp) {
		if (!this.containsWarp(warp))
			this.getWarps().add(warp);
		if (warp instanceof Listener)
			this.addListener((Listener) warp);
	}

	public void removeWarp(Warp warp) {
		this.getWarps().remove(warp);
		if (warp instanceof Listener)
			this.removeListener((Listener) warp);
	}

	public Warp getWarpByName(String name) {
		return this.getWarps().stream().filter(warp -> warp.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public WarpsSystem(Plugin plugin) {
		super("Warps", plugin);
	}

	@Override
	public void onEnable() {
		if (this.getNoneWarp() == null)
			this.noneWarp = new Warp("Nenhuma", "Sem descrição.", new Stack(Material.STAINED_GLASS_PANE), null,
					new ArrayList<>(), true);
		if (this.getDefaultWarp() == null)
			this.defaultWarp = new SpawnWarp();
		if (this.getWarps() == null)
			this.warps = new ArrayList<>();
		if (this.warpMap == null)
			this.warpMap = new HashMap<>();

		this.addListener(new WarpsGUI());
		this.addListener(new WarpsHabilitadasGUI());
		this.addListener(new WarpsDesabilitadasGUI());

		this.addWarp(this.getDefaultWarp());
		this.addWarp(new SumoWarp());
		this.addWarp(new UMvUMWarp());
		this.addWarp(new FPSWarp());
		this.addWarp(new LavaChallengeWarp());
		this.addWarp(new FishermanWarp());
		this.addWarp(new KnockbackWarp());
		this.addWarp(new EventoWarp());

		this.addListener(new WarpsListeners());

		this.addCommand(new WarpCommand());

		super.onEnable();
	}

	@Override
	public void onDisable() {
		if (this.getNoneWarp() != null) {
			this.removeWarp(this.getNoneWarp());
			this.noneWarp = null;
		}
		if (this.getDefaultWarp() != null) {
			this.removeWarp(this.getDefaultWarp());
			this.defaultWarp = null;
		}
		if (this.getWarps() != null) {
			this.getWarps().clear();
			this.warps = null;
		}
		if (this.warpMap != null) {
			this.warpMap.clear();
			this.warpMap = null;
		}

		super.onDisable();
	}

	public boolean hasWarp(Player player) {
		return this.warpMap.containsKey(player.getUniqueId());
	}

	public Warp getWarp(Player player) {
		return this.warpMap.getOrDefault(player.getUniqueId(), this.getNoneWarp());
	}

	public boolean setWarp(Player player, Warp warp) {
		PlayerTeleportWarpEvent event = new PlayerTeleportWarpEvent(player, warp);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			player.teleport(warp.getLocation(), TeleportCause.PLUGIN);
			warp.giveItems(player);
			warp.addPlayer(player);
			this.warpMap.put(player.getUniqueId(), warp);
			return true;
		}
		return false;
	}

	public Warp removeWarp(Player player) {
		PlayerRemoveWarpEvent event = new PlayerRemoveWarpEvent(player, this.getWarp(player));
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled()) {
			event.getWarp().removePlayer(player);
			this.warpMap.remove(player.getUniqueId());
		}

		return event.getWarp();
	}
}