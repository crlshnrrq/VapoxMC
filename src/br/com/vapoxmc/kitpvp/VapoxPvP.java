package br.com.vapoxmc.kitpvp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.vapoxmc.kitpvp.kit.Ajnin;
import br.com.vapoxmc.kitpvp.kit.Anchor;
import br.com.vapoxmc.kitpvp.kit.AntiStomper;
import br.com.vapoxmc.kitpvp.kit.Archer;
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
import br.com.vapoxmc.kitpvp.utils.Strings;

public final class VapoxPvP extends JavaPlugin {

	private static final List<Kit> kits = new ArrayList<>();
	private static Kit defaultKit;

	public static List<Kit> getKits() {
		return kits;
	}

	public static Kit getKitByName(String name) {
		return getKits().stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public static Kit getDefaultKit() {
		return defaultKit;
	}

	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void onEnable() {
		super.onEnable();

		PluginManager pm = Bukkit.getPluginManager();

		getKits().clear();
		getKits().add(defaultKit = new PvP());
		getKits().add(new Ajnin());
		getKits().add(new Anchor());
		getKits().add(new AntiStomper());
		getKits().add(new Archer());
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