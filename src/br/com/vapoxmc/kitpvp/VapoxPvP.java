package br.com.vapoxmc.kitpvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.crlshnrrq.screenshareplugin.ScreenSharePlugin;

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
import br.com.vapoxmc.kitpvp.commands.HeadCommand;
import br.com.vapoxmc.kitpvp.commands.InfoCommand;
import br.com.vapoxmc.kitpvp.commands.InvseeCommand;
import br.com.vapoxmc.kitpvp.commands.LastLoginCommand;
import br.com.vapoxmc.kitpvp.commands.MoneyCommand;
import br.com.vapoxmc.kitpvp.commands.OnlineCommand;
import br.com.vapoxmc.kitpvp.commands.PingCommand;
import br.com.vapoxmc.kitpvp.commands.RankCommand;
import br.com.vapoxmc.kitpvp.commands.RemoveMoneyCommand;
import br.com.vapoxmc.kitpvp.commands.ReportCommand;
import br.com.vapoxmc.kitpvp.commands.ReportTeleportCommand;
import br.com.vapoxmc.kitpvp.commands.ResetKitCommand;
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
import br.com.vapoxmc.kitpvp.commons.medalhas.MedalhasAddon;
import br.com.vapoxmc.kitpvp.gui.ShopGUI;
import br.com.vapoxmc.kitpvp.gui.ShopKDRGUI;
import br.com.vapoxmc.kitpvp.gui.ShopKitsGUI;
import br.com.vapoxmc.kitpvp.gui.StatusGUI;
import br.com.vapoxmc.kitpvp.gui.WarpsGUI;
import br.com.vapoxmc.kitpvp.gui.YouTuberGUI;
import br.com.vapoxmc.kitpvp.listeners.AdminListeners;
import br.com.vapoxmc.kitpvp.listeners.BuildListeners;
import br.com.vapoxmc.kitpvp.listeners.ChatListeners;
import br.com.vapoxmc.kitpvp.listeners.CombatLogListeners;
import br.com.vapoxmc.kitpvp.listeners.JumpListeners;
import br.com.vapoxmc.kitpvp.listeners.KitPvPListeners;
import br.com.vapoxmc.kitpvp.listeners.PlayerListeners;
import br.com.vapoxmc.kitpvp.listeners.WorldListeners;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Strings;
import br.com.vapoxmc.kitpvp.utils.VapoxUtils;
import br.com.vapoxmc.vapoxpvp.KitPvP;

public final class VapoxPvP extends JavaPlugin {

	private static KitPvP kitpvp;

	public static KitPvP getKitPvP() {
		return kitpvp;
	}

	private static ScreenSharePlugin screenSharePlugin;

	public static ScreenSharePlugin getScreenSharePlugin() {
		return screenSharePlugin;
	}

	private static MedalhasAddon medalhasAddon;

	public static MedalhasAddon getMedalhasAddon() {
		return medalhasAddon;
	}

	private static boolean chat = true;
	private static final List<UUID> ignoreStaffChat = new ArrayList<>(), tellDisabled = new ArrayList<>(),
			spyingTell = new ArrayList<>(), build = new ArrayList<>(), admin = new ArrayList<>(),
			useReport = new ArrayList<>(), protection = new ArrayList<>();
	private static final Map<UUID, ItemStack[]> armorMap = new HashMap<>(), inventoryMap = new HashMap<>();
	private static final Map<UUID, Integer> clickTestMap = new HashMap<>();

	private static boolean eventoActive = false, eventoPvP = false, eventoBuild = false, eventoOpen = false;
	private static final List<UUID> players = new ArrayList<>();
	private static final List<Block> placedBlocks = new ArrayList<>();

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
			timeMap.put(player.getUniqueId(), 30);
			enemyMap.put(player.getUniqueId(), enemy.getUniqueId());
			new BukkitRunnable() {
				@Override
				public void run() {
					if (player != null && !player.getName().equals(enemy.getName())) {
						if (timeMap.containsKey(player.getUniqueId()) && timeMap.get(player.getUniqueId()) > 0) {
							timeMap.put(player.getUniqueId(), timeMap.get(player.getUniqueId()) - 1);
							if (timeMap.get(player.getUniqueId()) <= 0)
								removeCombat(player);
						} else {
							this.cancel();
							timeMap.remove(player.getUniqueId());
						}
					}
				}
			}.runTaskTimer(VapoxPvP.getInstance(), 20L, 20L);
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
		kitpvp = new KitPvP();
		kitpvp.onLoad();
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

		pm.registerEvents(new ShopKDRGUI(), this);
		pm.registerEvents(new ShopKitsGUI(), this);
		pm.registerEvents(new ShopGUI(), this);
		pm.registerEvents(new StatusGUI(), this);
		pm.registerEvents(new WarpsGUI(), this);
		pm.registerEvents(new YouTuberGUI(), this);

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
		this.getCommand("head").setExecutor(new HeadCommand());
		this.getCommand("info").setExecutor(new InfoCommand());
		this.getCommand("invsee").setExecutor(new InvseeCommand());
		this.getCommand("lastlogin").setExecutor(new LastLoginCommand());
		this.getCommand("money").setExecutor(new MoneyCommand());
		this.getCommand("online").setExecutor(new OnlineCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("remmoney").setExecutor(new RemoveMoneyCommand());
		this.getCommand("report").setExecutor(new ReportCommand());
		this.getCommand("rtp").setExecutor(new ReportTeleportCommand());
		this.getCommand("resetkit").setExecutor(new ResetKitCommand());
		this.getCommand("server").setExecutor(new ServerCommand());
		this.getCommand("setmoney").setExecutor(new SetMoneyCommand());
		this.getCommand("shop").setExecutor(new ShopCommand());
		this.getCommand("sorteio").setExecutor(new SorteioCommand());
		this.getCommand("spawn").setExecutor(new SpawnCommand());
		this.getCommand("sc").setExecutor(new StaffChatCommand());
		this.getCommand("status").setExecutor(new StatusCommand());
		this.getCommand("tag").setExecutor(new TagCommand());
		this.getCommand("teleportall").setExecutor(new TeleportAllCommand());
		this.getCommand("teleport").setExecutor(new TeleportCommand());
		this.getCommand("teleporthere").setExecutor(new TeleportHereCommand());
		this.getCommand("tell").setExecutor(new TellCommand());
		this.getCommand("transferir").setExecutor(new TransferirCommand());
		this.getCommand("warp").setExecutor(new WarpCommand());
		this.getCommand("youtuber").setExecutor(new YouTuberCommand());

		PlayerAccount.createConnection();

		medalhasAddon = new MedalhasAddon(this);
		getMedalhasAddon().onEnable();

		screenSharePlugin = new ScreenSharePlugin(this);
		getScreenSharePlugin().onEnable();

		Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach(players -> {
			if (VapoxPvP.hasAdmin(players))
				VapoxUtils.sendActionBar(players, "§fVocê está no modo §cADMIN§f.");
			VapoxUtils.sendTab(players, Strings.getName() + "\n§fServidor: §aKitPvP\n",
					"\n§fDiscord: §a" + Strings.getDiscord() + "\n§fJogadores: §a" + Bukkit.getOnlinePlayers().size());
			if (!players.hasPermission("ciphen.comandos.admin"))
				getAdmins().stream()
						.filter(admins -> !players.hasPermission("staff.viewadmins") && players.canSee(admins))
						.forEach(admins -> players.hidePlayer(admins));
		}), 0L, 40L);

		kitpvp.onEnable();

		Bukkit.getConsoleSender().sendMessage(
				Strings.getPrefix() + " §aPlugin habilitado (§7" + this.getDescription().getVersion() + "§a).");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		getMedalhasAddon().onDisable();
		getScreenSharePlugin().onDisable();
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		kitpvp.onDisable();
		Bukkit.getConsoleSender().sendMessage(
				Strings.getPrefix() + " §cPlugin desabilitado (§7" + this.getDescription().getVersion() + "§c).");
	}
}