package br.com.vapoxmc.kitpvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import br.com.vapoxmc.kitpvp.gui.SeusKitsGUI;
import br.com.vapoxmc.kitpvp.kit.Ajnin;
import br.com.vapoxmc.kitpvp.kit.Anchor;
import br.com.vapoxmc.kitpvp.kit.AntiStomper;
import br.com.vapoxmc.kitpvp.kit.Archer;
import br.com.vapoxmc.kitpvp.kit.Critical;
import br.com.vapoxmc.kitpvp.kit.Fisherman;
import br.com.vapoxmc.kitpvp.kit.Kangaroo;
import br.com.vapoxmc.kitpvp.kit.Kit;
import br.com.vapoxmc.kitpvp.kit.Magma;
import br.com.vapoxmc.kitpvp.kit.Monk;
import br.com.vapoxmc.kitpvp.kit.Ninja;
import br.com.vapoxmc.kitpvp.kit.PvP;
import br.com.vapoxmc.kitpvp.kit.Scout;
import br.com.vapoxmc.kitpvp.kit.Snail;
import br.com.vapoxmc.kitpvp.kit.Stomper;
import br.com.vapoxmc.kitpvp.kit.Thor;
import br.com.vapoxmc.kitpvp.kit.Urgal;
import br.com.vapoxmc.kitpvp.kit.Viking;
import br.com.vapoxmc.kitpvp.kit.Viper;
import br.com.vapoxmc.kitpvp.listeners.KitPvPListeners;
import br.com.vapoxmc.kitpvp.player.PlayerAccount;
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.Strings;

public final class VapoxPvP extends JavaPlugin {

	private static final List<Kit> kits = new ArrayList<>();
	private static final Map<UUID, String> kitMap = new HashMap<>();
	private static final Map<UUID, Long> longMap = new HashMap<>();
	private static final Map<UUID, BukkitTask> taskMap = new HashMap<>();
	private static Kit noneKit, defaultKit;

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
			kit.applyKit(player);
			kitMap.put(player.getUniqueId(), kit.getName());
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public static Kit removeKit(Player player) {
		Ajnin.tegratMap.remove(player.getUniqueId());
		Ninja.targetMap.remove(player.getUniqueId());
		removeKitCooldown(player);
		return getKitByName(kitMap.remove(player.getUniqueId()));
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

	public static boolean isInCombat(Player player) {
		return enemyMap.containsKey(player.getUniqueId());
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

		pm.registerEvents(new KitPvPListeners(), this);

		pm.registerEvents(new SeusKitsGUI(), this);

		pm.registerEvents(new Ajnin(), this);
		pm.registerEvents(new Anchor(), this);
		pm.registerEvents(new Archer(), this);
		pm.registerEvents(new Critical(), this);
		pm.registerEvents(new Fisherman(), this);
		pm.registerEvents(new Kangaroo(), this);
		pm.registerEvents(new Magma(), this);
		pm.registerEvents(new Monk(), this);
		pm.registerEvents(new Ninja(), this);
		pm.registerEvents(new Scout(), this);
		pm.registerEvents(new Snail(), this);
		pm.registerEvents(new Stomper(), this);
		pm.registerEvents(new Thor(), this);
		pm.registerEvents(new Urgal(), this);
		pm.registerEvents(new Viking(), this);
		pm.registerEvents(new Viper(), this);

		noneKit = new Kit("Nenhum", "Sem descrição.", new Stack(Material.STAINED_GLASS_PANE));
		getKits().clear();
		getKits().add(defaultKit = new PvP());
		getKits().add(new Ajnin());
		getKits().add(new Anchor());
		getKits().add(new AntiStomper());
		getKits().add(new Archer());
		getKits().add(new Critical());
		getKits().add(new Fisherman());
		getKits().add(new Kangaroo());
		getKits().add(new Magma());
		getKits().add(new Monk());
		getKits().add(new Ninja());
		getKits().add(new Scout());
		getKits().add(new Snail());
		getKits().add(new Stomper());
		getKits().add(new Thor());
		getKits().add(new Urgal());
		getKits().add(new Viking());
		getKits().add(new Viper());

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