package br.com.vapoxmc.kitpvp.commons.medalhas;

import org.bukkit.plugin.java.JavaPlugin;

import br.com.vapoxmc.kitpvp.commons.medalhas.commands.MedalhaCommand;

public final class MedalhasAddon {

	private static JavaPlugin plugin;

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public MedalhasAddon(JavaPlugin instance) {
		plugin = instance;
	}

	public void onEnable() {
		getPlugin().getCommand("medalha").setExecutor(new MedalhaCommand());
	}

	public void onDisable() {
	}
}