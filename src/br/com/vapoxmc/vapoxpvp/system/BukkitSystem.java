package br.com.vapoxmc.vapoxpvp.system;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.vapoxmc.vapoxpvp.utils.BukkitUtils;

public class BukkitSystem implements System {

	private final String name;
	private final Plugin plugin;
	private boolean enable;
	private final ArrayList<Listener> listeners;
	private final ArrayList<Command> commands;

	public void info(String message) {
		this.getPlugin().getLogger().log(Level.INFO, message);
	}

	public BukkitSystem(String name, Plugin plugin) {
		this.name = name;
		this.plugin = plugin;
		this.enable = false;
		this.listeners = new ArrayList<>();
		this.commands = new ArrayList<>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	public Plugin getPlugin() {
		return this.plugin;
	}

	@Override
	public boolean isEnable() {
		return this.enable;
	}

	@Override
	public void onEnable() {
		this.getListeners().forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this.getPlugin()));
		this.getCommands().forEach(command -> BukkitUtils.registerCommand(command));

		this.enable = true;
		this.info("Sistema " + this.getName() + " habilitado.");
	}

	@Override
	public void onDisable() {
		this.getListeners().forEach(listener -> HandlerList.unregisterAll(listener));
		this.getListeners().clear();

		this.getCommands().forEach(command -> BukkitUtils.unregisterCommand(command));
		this.getCommands().clear();

		this.enable = false;
		this.info("Sistema " + this.getName() + " desabilitado.");
	}

	public ArrayList<Listener> getListeners() {
		return this.listeners;
	}

	public boolean hasListener(Listener listener) {
		return this.getListeners().contains(listener);
	}

	public void addListener(Listener listener) {
		if (!hasListener(listener))
			this.getListeners().add(listener);
	}

	public void removeListener(Listener listener) {
		this.getListeners().remove(listener);
	}

	public ArrayList<Command> getCommands() {
		return this.commands;
	}

	public boolean hasCommand(Command command) {
		return this.getCommands().contains(command);
	}

	public void addCommand(Command command) {
		if (!this.hasCommand(command))
			this.getCommands().add(command);
	}

	public void removeCommand(Command command) {
		this.getCommands().remove(command);
	}
}