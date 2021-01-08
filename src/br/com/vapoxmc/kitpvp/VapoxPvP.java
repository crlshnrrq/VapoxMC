package br.com.vapoxmc.kitpvp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import br.com.vapoxmc.kitpvp.commands.ActionBarCommand;
import br.com.vapoxmc.kitpvp.commands.AplicarCommand;
import br.com.vapoxmc.kitpvp.commands.AvisoCommand;
import br.com.vapoxmc.kitpvp.commands.CrashCommand;
import br.com.vapoxmc.kitpvp.commands.DiscordCommand;
import br.com.vapoxmc.kitpvp.commands.GameModeCommand;
import br.com.vapoxmc.kitpvp.commands.HeadCommand;
import br.com.vapoxmc.kitpvp.commands.KitCommand;
import br.com.vapoxmc.kitpvp.commands.OnlineCommand;
import br.com.vapoxmc.kitpvp.commands.RankCommand;
import br.com.vapoxmc.kitpvp.commands.ResetKitCommand;
import br.com.vapoxmc.kitpvp.commands.SpawnCommand;
import br.com.vapoxmc.kitpvp.commands.WarpCommand;
import br.com.vapoxmc.kitpvp.gui.SeusKitsGUI;
import br.com.vapoxmc.kitpvp.gui.WarpsGUI;
import br.com.vapoxmc.kitpvp.kit.AjninKit;
import br.com.vapoxmc.kitpvp.kit.AnchorKit;
import br.com.vapoxmc.kitpvp.kit.AntiStomperKit;
import br.com.vapoxmc.kitpvp.kit.ArcherKit;
import br.com.vapoxmc.kitpvp.kit.CriticalKit;
import br.com.vapoxmc.kitpvp.kit.FishermanKit;
import br.com.vapoxmc.kitpvp.kit.KangarooKit;
import br.com.vapoxmc.kitpvp.kit.Kit;
import br.com.vapoxmc.kitpvp.kit.MagmaKit;
import br.com.vapoxmc.kitpvp.kit.MonkKit;
import br.com.vapoxmc.kitpvp.kit.NinjaKit;
import br.com.vapoxmc.kitpvp.kit.PvPKit;
import br.com.vapoxmc.kitpvp.kit.ScoutKit;
import br.com.vapoxmc.kitpvp.kit.SnailKit;
import br.com.vapoxmc.kitpvp.kit.StomperKit;
import br.com.vapoxmc.kitpvp.kit.ThorKit;
import br.com.vapoxmc.kitpvp.kit.UrgalKit;
import br.com.vapoxmc.kitpvp.kit.VikingKit;
import br.com.vapoxmc.kitpvp.kit.ViperKit;
import br.com.vapoxmc.kitpvp.listeners.ChatListeners;
import br.com.vapoxmc.kitpvp.listeners.CombatLogListeners;
import br.com.vapoxmc.kitpvp.listeners.JumpListeners;
import br.com.vapoxmc.kitpvp.listeners.KitPvPListeners;
import br.com.vapoxmc.kitpvp.listeners.PlayerListeners;
import br.com.vapoxmc.kitpvp.listeners.WorldListeners;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.Strings;
import br.com.vapoxmc.kitpvp.warp.FPSWarp;
import br.com.vapoxmc.kitpvp.warp.FishermanWarp;
import br.com.vapoxmc.kitpvp.warp.KnockbackWarp;
import br.com.vapoxmc.kitpvp.warp.LavaChallengeWarp;
import br.com.vapoxmc.kitpvp.warp.PotPvPWarp;
import br.com.vapoxmc.kitpvp.warp.SpawnWarp;
import br.com.vapoxmc.kitpvp.warp.UMvUMWarp;
import br.com.vapoxmc.kitpvp.warp.Warp;

public final class VapoxPvP extends JavaPlugin {

	private static final List<Kit> kits = new ArrayList<>();
	private static final Map<UUID, String> kitMap = new HashMap<>();
	private static final Map<UUID, Long> longMap = new HashMap<>();
	private static final Map<UUID, BukkitTask> taskMap = new HashMap<>();
	private static Kit noneKit, defaultKit;

	private static final List<Warp> warps = new ArrayList<>();
	private static final Map<UUID, String> warpMap = new HashMap<>();
	private static Warp noneWarp, defaultWarp;

	private static final Map<UUID, UUID> enemyMap = new HashMap<>();
	private static final Map<UUID, Integer> timeMap = new HashMap<>();

	public static List<Kit> getKits() {
		return kits;
	}

	public static Kit getKitByName(String name) {
		return getKits().stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public static Kit getNoneKit() {
		return noneKit;
	}

	public static Kit getDefaultKit() {
		return defaultKit;
	}

	public static boolean hasKit(Player player) {
		return kitMap.containsKey(player.getUniqueId());
	}

	public static Kit getKit(Player player) {
		if (hasKit(player))
			return getKitByName(kitMap.get(player.getUniqueId()));
		return getNoneKit();
	}

	public static boolean setKit(Player player, Kit kit) {
		try {
			player.teleport(Arrays.asList(new Location(Bukkit.getWorlds().get(0), -44, 67, 0),
					new Location(Bukkit.getWorlds().get(0), -63, 64, 34),
					new Location(Bukkit.getWorlds().get(0), 11, 67, -33),
					new Location(Bukkit.getWorlds().get(0), 51, 67, -12)).stream().findAny().get());

			kit.applyKit(player);
			VapoxPvP.removeCombat(player);
			removeWarp(player);
			kitMap.put(player.getUniqueId(), kit.getName());
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public static Kit removeKit(Player player) {
		Kit kit = getKitByName(kitMap.remove(player.getUniqueId()));
		if (kit instanceof AjninKit)
			((AjninKit) kit).tegratMap.remove(player.getUniqueId());
		if (kit instanceof NinjaKit)
			((NinjaKit) kit).targetMap.remove(player.getUniqueId());
		removeKitCooldown(player);
		VapoxPvP.removeCombat(player);
		return kit;
	}

	public static boolean hasKitCooldown(Player player) {
		return longMap.containsKey(player.getUniqueId());
	}

	public static String getFormattedKitCooldown(Player player) {
		String str = new String();
		int seconds = getKitCooldown(player);
		if (seconds > 0)
			str += seconds + " segundo" + (seconds == 1 ? "" : "s");
		return str;
	}

	public static int getKitCooldown(Player player) {
		if (hasKitCooldown(player))
			return (int) ((longMap.getOrDefault(player.getUniqueId(), System.currentTimeMillis() + 1000L)
					- System.currentTimeMillis()) / 1000L);
		return 0;
	}

	public static void addKitCooldown(Player player, int seconds) {
		longMap.put(player.getUniqueId(), (seconds * 1000L) + System.currentTimeMillis());
		taskMap.put(player.getUniqueId(), Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
			if (hasKitCooldown(player))
				removeKitCooldown(player);
		}, seconds * 20L));
	}

	public static void removeKitCooldown(Player player) {
		longMap.remove(player.getUniqueId());
		if (taskMap.containsKey(player.getUniqueId()))
			taskMap.remove(player.getUniqueId()).cancel();
	}

	public static List<Warp> getWarps() {
		return warps;
	}

	public static Warp getWarpByName(String name) {
		return getWarps().stream().filter(warp -> warp.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public static Warp getNoneWarp() {
		return noneWarp;
	}

	public static Warp getDefaultWarp() {
		return defaultWarp;
	}

	public static int getPlayersInWarp(Warp warp) {
		return (int) warpMap.values().stream().filter(string -> string.equals(warp.getName())).count();
	}

	public static Warp getWarp(Player player) {
		if (warpMap.containsKey(player.getUniqueId()))
			return getWarpByName(warpMap.get(player.getUniqueId()));
		return getNoneWarp();
	}

	public static boolean setWarp(Player player, Warp warp) {
		try {
			player.teleport(warp.getLocation());
			warp.giveItems(player);
			VapoxPvP.removeCombat(player);
			removeKit(player);
			((UMvUMWarp) getWarpByName("1v1")).removeEnemy(player);
			warpMap.put(player.getUniqueId(), warp.getName());
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public static Warp removeWarp(Player player) {
		Warp warp = getWarpByName(warpMap.remove(player.getUniqueId()));
		VapoxPvP.removeCombat(player);
		if (warp instanceof UMvUMWarp)
			((UMvUMWarp) warp).removeEnemy(player);
		return warp;
	}

	public static boolean isInCombat(Player player) {
		return enemyMap.containsKey(player.getUniqueId());
	}

	public static Player getCombatEnemy(Player player) {
		if (isInCombat(player))
			return Bukkit.getPlayer(enemyMap.get(player.getUniqueId()));
		return null;
	}

	public static boolean setCombatTime(Player player, int time) {
		if (isInCombat(player)) {
			timeMap.put(player.getUniqueId(), time);
			return true;
		}
		return false;
	}

	public static void addCombat(Player player, Player enemy) {
		if (!timeMap.containsKey(player.getUniqueId())) {
			timeMap.put(player.getUniqueId(), 10);
			enemyMap.put(player.getUniqueId(), enemy.getUniqueId());
			Bukkit.getScheduler().runTaskLater(VapoxPvP.getInstance(), () -> {
				if (player != null && !player.getName().equals(enemy.getName())) {
					if (timeMap.getOrDefault(player.getUniqueId(), 0) > 0) {
						timeMap.put(player.getUniqueId(), timeMap.get(player.getUniqueId()) - 1);
						if (timeMap.get(player.getUniqueId()) <= 0)
							removeCombat(player);
					} else
						timeMap.put(player.getUniqueId(), 1);
				}
			}, 20L);
		}
	}

	public static void removeCombat(Player player) {
		enemyMap.remove(player.getUniqueId());
		timeMap.remove(player.getUniqueId());
	}

	public static VapoxPvP getInstance() {
		return getPlugin(VapoxPvP.class);
	}

	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void onEnable() {
		super.onEnable();

		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new ChatListeners(), this);
		pm.registerEvents(new CombatLogListeners(), this);
		pm.registerEvents(new JumpListeners(), this);
		pm.registerEvents(new KitPvPListeners(), this);
		pm.registerEvents(new PlayerListeners(), this);
		pm.registerEvents(new WorldListeners(), this);

		pm.registerEvents(new SeusKitsGUI(), this);
		pm.registerEvents(new WarpsGUI(), this);

		pm.registerEvents(new AjninKit(), this);
		pm.registerEvents(new AnchorKit(), this);
		pm.registerEvents(new ArcherKit(), this);
		pm.registerEvents(new CriticalKit(), this);
		pm.registerEvents(new FishermanKit(), this);
		pm.registerEvents(new KangarooKit(), this);
		pm.registerEvents(new MagmaKit(), this);
		pm.registerEvents(new MonkKit(), this);
		pm.registerEvents(new NinjaKit(), this);
		pm.registerEvents(new ScoutKit(), this);
		pm.registerEvents(new SnailKit(), this);
		pm.registerEvents(new StomperKit(), this);
		pm.registerEvents(new ThorKit(), this);
		pm.registerEvents(new UrgalKit(), this);
		pm.registerEvents(new VikingKit(), this);
		pm.registerEvents(new ViperKit(), this);

		pm.registerEvents(new FishermanWarp(), this);
		pm.registerEvents(new FPSWarp(), this);
		pm.registerEvents(new KnockbackWarp(), this);
		pm.registerEvents(new LavaChallengeWarp(), this);
		pm.registerEvents(new PotPvPWarp(), this);
		pm.registerEvents(new SpawnWarp(), this);
		pm.registerEvents(new UMvUMWarp(), this);

		this.getCommand("actionbar").setExecutor(new ActionBarCommand());
		this.getCommand("aplicar").setExecutor(new AplicarCommand());
		this.getCommand("aviso").setExecutor(new AvisoCommand());
		this.getCommand("crash").setExecutor(new CrashCommand());
		this.getCommand("discord").setExecutor(new DiscordCommand());
		this.getCommand("gamemode").setExecutor(new GameModeCommand());
		this.getCommand("head").setExecutor(new HeadCommand());
		this.getCommand("kit").setExecutor(new KitCommand());
		this.getCommand("online").setExecutor(new OnlineCommand());
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("resetkit").setExecutor(new ResetKitCommand());
		this.getCommand("spawn").setExecutor(new SpawnCommand());
		this.getCommand("warp").setExecutor(new WarpCommand());

		noneKit = new Kit("Nenhum", "Sem descrição.", new Stack(Material.STAINED_GLASS_PANE));
		getKits().clear();
		getKits().add(defaultKit = new PvPKit());
		getKits().add(new AjninKit());
		getKits().add(new AnchorKit());
		getKits().add(new AntiStomperKit());
		getKits().add(new ArcherKit());
		getKits().add(new CriticalKit());
		getKits().add(new FishermanKit());
		getKits().add(new KangarooKit());
		getKits().add(new MagmaKit());
		getKits().add(new MonkKit());
		getKits().add(new NinjaKit());
		getKits().add(new ScoutKit());
		getKits().add(new SnailKit());
		getKits().add(new StomperKit());
		getKits().add(new ThorKit());
		getKits().add(new UrgalKit());
		getKits().add(new VikingKit());
		getKits().add(new ViperKit());

		noneWarp = new Warp("Nenhuma", new Stack(Material.STAINED_GLASS_PANE), null);
		getWarps().clear();
		getWarps().add(defaultWarp = new SpawnWarp());
		getWarps().add(new FishermanWarp());
		getWarps().add(new FPSWarp());
		getWarps().add(new KnockbackWarp());
		getWarps().add(new LavaChallengeWarp());
		getWarps().add(new PotPvPWarp());
		getWarps().add(new UMvUMWarp());

		PlayerAccount.createConnection();

		Bukkit.getConsoleSender().sendMessage(
				Strings.getPrefix() + " §aPlugin habilitado (§7" + this.getDescription().getVersion() + "§a).");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		Bukkit.getConsoleSender().sendMessage(
				Strings.getPrefix() + " §cPlugin desabilitado (§7" + this.getDescription().getVersion() + "§c).");
	}
}