package br.com.vapoxmc.vapoxpvp.generalsystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class PlayerKillEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private Player killer;

	public PlayerKillEvent(Player who, Player killer) {
		super(who);
		this.killer = killer;
	}

	public Player getKiller() {
		return this.killer;
	}

	public void setKiller(Player killer) {
		this.killer = killer;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}