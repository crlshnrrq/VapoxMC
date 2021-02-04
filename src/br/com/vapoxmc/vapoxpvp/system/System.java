package br.com.vapoxmc.vapoxpvp.system;

public interface System {

	public String getName();

	public boolean isEnable();

	public void onEnable();

	public void onDisable();
}