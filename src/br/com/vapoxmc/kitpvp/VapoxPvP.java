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
import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.kitpvp.utils.Strings;

public final class VapoxPvP extends JavaPlugin {

	private static final List<Kit> kits = new ArrayList<>();
	private static final Map<UUID, String> kitMap = new HashMap<>();
	private static Kit noneKit, defaultKit;

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
		return getKitByName(kitMap.remove(player.getUniqueId()));
	}

	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void onEnable() {
		super.onEnable();

		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new SeusKitsGUI(), this);

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