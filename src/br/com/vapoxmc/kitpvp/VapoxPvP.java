package br.com.vapoxmc.kitpvp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;

import br.com.vapoxmc.kitpvp.commands.ActionBarCommand;
import br.com.vapoxmc.kitpvp.commands.AddMoneyCommand;
import br.com.vapoxmc.kitpvp.commands.AdminCommand;
import br.com.vapoxmc.kitpvp.commands.AplicarCommand;
import br.com.vapoxmc.kitpvp.commands.AvisoCommand;
import br.com.vapoxmc.kitpvp.commands.BuildCommand;
import br.com.vapoxmc.kitpvp.commands.ChatCommand;
import br.com.vapoxmc.kitpvp.commands.CheckCommand;
import br.com.vapoxmc.kitpvp.commands.ClickTestCommand;
import br.com.vapoxmc.kitpvp.commands.CrashCommand;
import br.com.vapoxmc.kitpvp.commands.DiscordCommand;
import br.com.vapoxmc.kitpvp.commands.EventoCommand;
import br.com.vapoxmc.kitpvp.commands.FlyCommand;
import br.com.vapoxmc.kitpvp.commands.GameModeCommand;
import br.com.vapoxmc.kitpvp.commands.GroupCommand;
import br.com.vapoxmc.kitpvp.commands.HeadCommand;
import br.com.vapoxmc.kitpvp.commands.InfoCommand;
import br.com.vapoxmc.kitpvp.commands.InvseeCommand;
import br.com.vapoxmc.kitpvp.commands.KitCommand;
import br.com.vapoxmc.kitpvp.commands.LastLoginCommand;
import br.com.vapoxmc.kitpvp.commands.MoneyCommand;
import br.com.vapoxmc.kitpvp.commands.OnlineCommand;
import br.com.vapoxmc.kitpvp.commands.PingCommand;
import br.com.vapoxmc.kitpvp.commands.RankCommand;
import br.com.vapoxmc.kitpvp.commands.RefreshCommand;
import br.com.vapoxmc.kitpvp.commands.RemoveMoneyCommand;
import br.com.vapoxmc.kitpvp.commands.ReportCommand;
import br.com.vapoxmc.kitpvp.commands.ReportTeleportCommand;
import br.com.vapoxmc.kitpvp.commands.ResetKitCommand;
import br.com.vapoxmc.kitpvp.commands.ScoreCommand;
import br.com.vapoxmc.kitpvp.commands.ServerCommand;
import br.com.vapoxmc.kitpvp.commands.SetMoneyCommand;
import br.com.vapoxmc.kitpvp.commands.ShopCommand;
import br.com.vapoxmc.kitpvp.commands.SorteioCommand;
import br.com.vapoxmc.kitpvp.commands.SpawnCommand;
import br.com.vapoxmc.kitpvp.commands.StaffChatCommand;
import br.com.vapoxmc.kitpvp.commands.StatusCommand;
import br.com.vapoxmc.kitpvp.commands.TagCommand;
import br.com.vapoxmc.kitpvp.commands.TeleportAllCommand;
import br.com.vapoxmc.kitpvp.commands.TeleportCommand;
import br.com.vapoxmc.kitpvp.commands.TeleportHereCommand;
import br.com.vapoxmc.kitpvp.commands.TellCommand;
import br.com.vapoxmc.kitpvp.commands.TransferirCommand;
import br.com.vapoxmc.kitpvp.commands.WarpCommand;
import br.com.vapoxmc.kitpvp.commands.YouTuberCommand;
import br.com.vapoxmc.kitpvp.gui.SeusKitsGUI;
import br.com.vapoxmc.kitpvp.gui.ShopGUI;
import br.com.vapoxmc.kitpvp.gui.ShopKDRGUI;
import br.com.vapoxmc.kitpvp.gui.ShopKitsGUI;
import br.com.vapoxmc.kitpvp.gui.StatusGUI;
import br.com.vapoxmc.kitpvp.gui.WarpsGUI;
import br.com.vapoxmc.kitpvp.gui.YouTuberGUI;
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
import br.com.vapoxmc.kitpvp.listeners.AdminListeners;
import br.com.vapoxmc.kitpvp.listeners.BuildListeners;
import br.com.vapoxmc.kitpvp.listeners.ChatListeners;
import br.com.vapoxmc.kitpvp.listeners.CombatLogListeners;
import br.com.vapoxmc.kitpvp.listeners.JumpListeners;
import br.com.vapoxmc.kitpvp.listeners.KitPvPListeners;
import br.com.vapoxmc.kitpvp.listeners.PlayerListeners;
import br.com.vapoxmc.kitpvp.listeners.WorldListeners;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.player.PlayerGroup;
import br.com.vapoxmc.kitpvp.player.PlayerRank;
import br.com.vapoxmc.kitpvp.player.Sidebar;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.Strings;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;
import br.com.vapoxmc.kitpvp.warp.EventoWarp;
import br.com.vapoxmc.kitpvp.warp.FPSWarp;
import br.com.vapoxmc.kitpvp.warp.FishermanWarp;
import br.com.vapoxmc.kitpvp.warp.KnockbackWarp;
import br.com.vapoxmc.kitpvp.warp.LavaChallengeWarp;
import br.com.vapoxmc.kitpvp.warp.PotPvPWarp;
import br.com.vapoxmc.kitpvp.warp.SpawnWarp;
import br.com.vapoxmc.kitpvp.warp.UMvUMWarp;
import br.com.vapoxmc.kitpvp.warp.Warp;

public final class VapoxPvP extends JavaPlugin {

	private static boolean chat = true;
	private static final List<UUID> ignoreStaffChat = new ArrayList<>(), tellDisabled = new ArrayList<>(),
			spyingTell = new ArrayList<>(), build = new ArrayList<>(), admin = new ArrayList<>(),
			useReport = new ArrayList<>(), protection = new ArrayList<>();
	private static final Map<UUID, ItemStack[]> armorMap = new HashMap<>(), inventoryMap = new HashMap<>();
	private static final Map<UUID, Integer> clickTestMap = new HashMap<>();

	private static boolean eventoActive = false, eventoPvP = false, eventoBuild = false, eventoOpen = false;
	private static final List<UUID> players = new ArrayList<>();
	private static final List<Block> placedBlocks = new ArrayList<>();

	private static final Map<UUID, Sidebar> sidebarMap = new HashMap<>();
	private static Sidebar defaultSidebar;

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

	public static boolean getChat() {
		return chat;
	}

	public static void setChat(boolean value) {
		chat = value;
	}

	public static boolean isIgnoreStaffChat(Player player) {
		return ignoreStaffChat.contains(player.getUniqueId());
	}

	public static void addIgnoreStaffChat(Player player) {
		if (!isIgnoreStaffChat(player))
			ignoreStaffChat.add(player.getUniqueId());
	}

	public static void removeIgnoreStaffChat(Player player) {
		ignoreStaffChat.remove(player.getUniqueId());
	}

	public static boolean isTellDisabled(Player player) {
		return tellDisabled.contains(player.getUniqueId());
	}

	public static void addTellDisabled(Player player) {
		if (!isTellDisabled(player))
			tellDisabled.add(player.getUniqueId());
	}

	public static void removeTellDisabled(Player player) {
		tellDisabled.remove(player.getUniqueId());
	}

	public static boolean isSpyingTell(Player player) {
		return spyingTell.contains(player.getUniqueId());
	}

	public static void addSpyingTell(Player player) {
		if (!isSpyingTell(player))
			spyingTell.add(player.getUniqueId());
	}

	public static void removeSpyingTell(Player player) {
		spyingTell.remove(player.getUniqueId());
	}

	public static boolean hasBuild(Player player) {
		return build.contains(player.getUniqueId());
	}

	public static void addBuild(Player player) {
		if (!hasBuild(player))
			build.add(player.getUniqueId());
	}

	public static void removeBuild(Player player) {
		build.remove(player.getUniqueId());
	}

	public static List<Player> getAdmins() {
		List<Player> list = new ArrayList<>();
		admin.forEach(uuid -> {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null)
				list.add(player);
			else
				admin.remove(uuid);
		});
		return list;
	}

	public static boolean hasAdmin(Player player) {
		return admin.contains(player.getUniqueId());
	}

	public static void addAdmin(Player player) {
		if (!hasAdmin(player))
			admin.add(player.getUniqueId());
	}

	public static boolean hasUseReport(Player player) {
		return useReport.contains(player.getUniqueId());
	}

	public static void addUseReport(Player player) {
		if (!hasUseReport(player))
			useReport.add(player.getUniqueId());
	}

	public static void removeUseReport(Player player) {
		useReport.remove(player.getUniqueId());
	}

	public static boolean hasProtection(Player player) {
		return protection.contains(player.getUniqueId());
	}

	public static void addProtection(Player player) {
		if (!hasProtection(player))
			protection.add(player.getUniqueId());
	}

	public static void removeProtection(Player player) {
		protection.remove(player.getUniqueId());
	}

	public static boolean hasArmorAndInventory(Player player) {
		return armorMap.containsKey(player.getUniqueId()) && inventoryMap.containsKey(player.getUniqueId());
	}

	public static ItemStack[] getArmor(Player player) {
		return armorMap.get(player.getUniqueId());
	}

	public static ItemStack[] getInventory(Player player) {
		return inventoryMap.get(player.getUniqueId());
	}

	public static void setArmorAndInventory(Player player, ItemStack[] armor, ItemStack[] inventory) {
		armorMap.put(player.getUniqueId(), armor);
		inventoryMap.put(player.getUniqueId(), inventory);
	}

	public static void removeArmorAndInventory(Player player) {
		inventoryMap.remove(player.getUniqueId());
		armorMap.remove(player.getUniqueId());
	}

	public static boolean hasClickTest(Player player) {
		return clickTestMap.containsKey(player.getUniqueId());
	}

	public static int getClickTest(Player player) {
		return clickTestMap.getOrDefault(player.getUniqueId(), 0);
	}

	public static void setClickTest(Player player, int i) {
		clickTestMap.put(player.getUniqueId(), i);
	}

	public static int removeClickTest(Player player) {
		return clickTestMap.remove(player.getUniqueId());
	}

	public static void removeAdmin(Player player) {
		admin.remove(player.getUniqueId());
	}

	public static boolean isEventoActive() {
		return eventoActive;
	}

	public static void setEventoActive(boolean value) {
		eventoActive = value;
	}

	public static boolean getEventoPvP() {
		return eventoPvP;
	}

	public static void setEventoPvP(boolean value) {
		eventoPvP = value;
	}

	public static boolean getEventoBuild() {
		return eventoBuild;
	}

	public static void setEventoBuild(boolean value) {
		eventoBuild = value;
	}

	public static boolean isEventoOpen() {
		return eventoOpen;
	}

	public static void setEventoOpen(boolean value) {
		eventoOpen = value;
	}

	public static List<Player> getEventoPlayers() {
		List<Player> list = new ArrayList<>();
		players.forEach(uuid -> {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null)
				list.add(player);
			else
				players.remove(uuid);
		});
		return list;
	}

	public static boolean hasEventoPlayer(Player player) {
		return players.contains(player.getUniqueId());
	}

	public static void addEventoPlayer(Player player) {
		if (!hasEventoPlayer(player))
			players.add(player.getUniqueId());
	}

	public static void removeEventoPlayer(Player player) {
		players.remove(player.getUniqueId());
	}

	public static List<Block> getPlacedblocks() {
		return placedBlocks;
	}

	public static Sidebar getDefaultSidebar() {
		return defaultSidebar;
	}

	public static boolean hasSidebar(Player player) {
		return sidebarMap.containsKey(player.getUniqueId());
	}

	public static Sidebar getSidebar(Player player) {
		return sidebarMap.get(player.getUniqueId());
	}

	public static void setSidebar(Player player, Sidebar sidebar) {
		sidebar.sendSidebar(player);
		sidebarMap.put(player.getUniqueId(), sidebar);
		updateSidebar(player);
	}

	public static void updateSidebar(Player player) {
		if (hasSidebar(player))
			getSidebar(player).update(player);
	}

	public static Sidebar removeSidebar(Player player) {
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		return sidebarMap.remove(player.getUniqueId());
	}

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
			VapoxPvP.removeProtection(player);
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
		return 1;
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

		pm.registerEvents(new AdminListeners(), this);
		pm.registerEvents(new BuildListeners(), this);
		pm.registerEvents(new ChatListeners(), this);
		pm.registerEvents(new CombatLogListeners(), this);
		pm.registerEvents(new JumpListeners(), this);
		pm.registerEvents(new KitPvPListeners(), this);
		pm.registerEvents(new PlayerListeners(), this);
		pm.registerEvents(new WorldListeners(), this);

		pm.registerEvents(new SeusKitsGUI(), this);
		pm.registerEvents(new ShopGUI(), this);
		pm.registerEvents(new ShopKDRGUI(), this);
		pm.registerEvents(new ShopKitsGUI(), this);
		pm.registerEvents(new StatusGUI(), this);
		pm.registerEvents(new WarpsGUI(), this);
		pm.registerEvents(new YouTuberGUI(), this);

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
		pm.registerEvents(new EventoWarp(), this);

		this.getCommand("actionbar").setExecutor(new ActionBarCommand());
		this.getCommand("addmoney").setExecutor(new AddMoneyCommand());
		this.getCommand("admin").setExecutor(new AdminCommand());
		this.getCommand("aplicar").setExecutor(new AplicarCommand());
		this.getCommand("aviso").setExecutor(new AvisoCommand());
		this.getCommand("build").setExecutor(new BuildCommand());
		this.getCommand("chat").setExecutor(new ChatCommand());
		this.getCommand("check").setExecutor(new CheckCommand());
		this.getCommand("clicktest").setExecutor(new ClickTestCommand());
		this.getCommand("crash").setExecutor(new CrashCommand());
		this.getCommand("discord").setExecutor(new DiscordCommand());
		this.getCommand("evento").setExecutor(new EventoCommand());
		this.getCommand("fly").setExecutor(new FlyCommand());
		this.getCommand("gamemode").setExecutor(new GameModeCommand());
		this.getCommand("group").setExecutor(new GroupCommand());
		this.getCommand("head").setExecutor(new HeadCommand());
		this.getCommand("infocommand").setExecutor(new InfoCommand());
		this.getCommand("invsee").setExecutor(new InvseeCommand());
		this.getCommand("kit").setExecutor(new KitCommand());
		this.getCommand("lastlogin").setExecutor(new LastLoginCommand());
		this.getCommand("money").setExecutor(new MoneyCommand());
		this.getCommand("online").setExecutor(new OnlineCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("refresh").setExecutor(new RefreshCommand());
		this.getCommand("remmoney").setExecutor(new RemoveMoneyCommand());
		this.getCommand("report").setExecutor(new ReportCommand());
		this.getCommand("rtp").setExecutor(new ReportTeleportCommand());
		this.getCommand("resetkit").setExecutor(new ResetKitCommand());
		this.getCommand("score").setExecutor(new ScoreCommand());
		this.getCommand("server").setExecutor(new ServerCommand());
		this.getCommand("setmoney").setExecutor(new SetMoneyCommand());
		this.getCommand("shop").setExecutor(new ShopCommand());
		this.getCommand("sorteio").setExecutor(new SorteioCommand());
		this.getCommand("spawn").setExecutor(new SpawnCommand());
		this.getCommand("sc").setExecutor(new StaffChatCommand());
		this.getCommand("status").setExecutor(new StatusCommand());
		this.getCommand("tag").setExecutor(new TagCommand());
		this.getCommand("tpall").setExecutor(new TeleportAllCommand());
		this.getCommand("teleport").setExecutor(new TeleportCommand());
		this.getCommand("teleporthere").setExecutor(new TeleportHereCommand());
		this.getCommand("tell").setExecutor(new TellCommand());
		this.getCommand("transferir").setExecutor(new TransferirCommand());
		this.getCommand("warp").setExecutor(new WarpCommand());
		this.getCommand("youtuber").setExecutor(new YouTuberCommand());

		defaultSidebar = new Sidebar("Principal", Strings.getName()) {
			@Override
			public void update(Player player) {
				DecimalFormat df = new DecimalFormat("###,###.##");
				this.updateLine(player, "§fCargo: §a", PlayerGroup.getGroup(player).getColoredName());
				this.updateLine(player, "§fRank: §a", PlayerRank.getRank(player).getColoredName());
				this.updateLine(player, "§fKills: §a", "" + df.format(PlayerAccount.getGeral().getAbates(player)));
				this.updateLine(player, "§fDeaths: §a", "" + df.format(PlayerAccount.getGeral().getMortes(player)));
				this.updateLine(player, "§fKillStreak: §a",
						"" + df.format(PlayerAccount.getGeral().getKillStreak(player)));
				this.updateLine(player, "§fMoedas: §a", "" + df.format(PlayerAccount.getGeral().getMoedas(player)));
				this.updateLine(player, "§fPontos: §a", "" + df.format(PlayerAccount.getGeral().getPontos(player)));
			}
		};
		defaultSidebar.addLine(" ");
		defaultSidebar.addLine("§fCargo: §a");
		defaultSidebar.addLine("§fRank: §a");
		defaultSidebar.addLine(" ");
		defaultSidebar.addLine("§fKills: §a");
		defaultSidebar.addLine("§fDeaths: §a");
		defaultSidebar.addLine("§fKillStreak: §a");
		defaultSidebar.addLine(" ");
		defaultSidebar.addLine("§fMoedas: §a");
		defaultSidebar.addLine("§fPontos: §a");
		defaultSidebar.addLine(" ");
		defaultSidebar.addLine("§7vapoxmc.com.br");

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
		getWarps().add(new EventoWarp());

		PlayerAccount.createConnection();

		Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach(players -> {
			updateSidebar(players);
			if (VapoxPvP.hasAdmin(players))
				VapoxUtils.sendActionBar(players, "§fVocê está no modo §cADMIN§f.");
			VapoxUtils.sendTab(players, Strings.getName() + "\n§fServidor: §aKitPvP\n",
					"\n§fDiscord: §a" + Strings.getDiscord() + "\n§fJogadores: §a" + Bukkit.getOnlinePlayers().size());
			if (!players.hasPermission("ciphen.comandos.admin"))
				getAdmins().stream().filter(admins -> players.canSee(admins))
						.forEach(admins -> players.hidePlayer(admins));
		}), 0L, 40L);

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