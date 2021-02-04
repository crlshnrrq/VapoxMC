package br.com.vapoxmc.vapoxpvp.sidebarssystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import br.com.vapoxmc.vapoxpvp.sidebarssystem.Sidebar;

public class PlayerUpdateSidebarEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private Sidebar sidebar;
	private boolean cancelled;

	public PlayerUpdateSidebarEvent(Player who, Sidebar sidebar) {
		super(who);
		this.sidebar = sidebar;
		this.cancelled = false;
	}

	public Sidebar getSidebar() {
		return this.sidebar;
	}

	public void setSidebar(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}