package br.com.vapoxmc.vapoxpvp;

import br.com.vapoxmc.vapoxpvp.generalsystem.GeneralSystem;

public final class KitPvP {

	private static GeneralSystem generalSystem;

	public static GeneralSystem getGeneralSystem() {
		return generalSystem;
	}

	public void onLoad() {
		generalSystem = new GeneralSystem();
	}

	public void onEnable() {
		getGeneralSystem().onEnable();
	}

	public void onDisable() {
		getGeneralSystem().onDisable();
	}
}