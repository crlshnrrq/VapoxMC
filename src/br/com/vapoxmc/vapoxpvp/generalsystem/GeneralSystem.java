package br.com.vapoxmc.vapoxpvp.generalsystem;

import java.util.ArrayList;

import br.com.vapoxmc.kitpvp.VapoxPvP;
import br.com.vapoxmc.vapoxpvp.generalsystem.commands.SystemCommand;
import br.com.vapoxmc.vapoxpvp.kitssystem.KitsSystem;
import br.com.vapoxmc.vapoxpvp.sidebarssystem.SidebarsSystem;
import br.com.vapoxmc.vapoxpvp.system.BukkitSystem;
import br.com.vapoxmc.vapoxpvp.system.System;
import br.com.vapoxmc.vapoxpvp.warpssystem.WarpsSystem;

public class GeneralSystem extends BukkitSystem {

	private ArrayList<System> systems;

	public ArrayList<System> getSystems() {
		return this.systems;
	}

	public boolean containsSystem(System system) {
		return this.getSystems().contains(system);
	}

	public void addSystem(System system) {
		if (!this.containsSystem(system)) {
			this.getSystems().add(system);
			system.onEnable();
		}
	}

	public void removeSystem(System system) {
		if (this.containsSystem(system))
			system.onDisable();
		this.getSystems().remove(system);
	}

	public System getSystemByName(String name) {
		return this.getSystems().stream().filter(system -> system.getName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);
	}

	public GeneralSystem() {
		super("Geral", VapoxPvP.getInstance());
	}

	@Override
	public boolean isEnable() {
		return true;
	}

	@Override
	public void onEnable() {
		if (this.getSystems() == null)
			this.systems = new ArrayList<>();
		this.addSystem(new KitsSystem(this.getPlugin()));
		this.addSystem(new WarpsSystem(this.getPlugin()));
		this.addSystem(new SidebarsSystem(this.getPlugin()));

		this.addCommand(new SystemCommand());

		super.onEnable();
	}

	@Override
	public void onDisable() {
		if (this.getSystems() != null) {
			this.getSystems().clear();
			this.systems = null;
		}

		super.onDisable();
	}
}