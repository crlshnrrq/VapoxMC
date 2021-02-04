package br.com.vapoxmc.vapoxpvp.sidebarssystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import br.com.vapoxmc.vapoxpvp.sidebarssystem.Sidebar;

public class PlayerSidebarEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private Sidebar sidebar;

	public PlayerSidebarEvent(Player who, Sidebar sidebar) {
		super(who);
		this.sidebar = sidebar;
	}

	public Sidebar getSidebar() {
		return this.sidebar;
	}

	public void setSidebar(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}