package br.com.vapoxmc.vapoxpvp.warpssystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;

public class PlayerWarpEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private Warp warp;

	public PlayerWarpEvent(Player who, Warp warp) {
		super(who);
		this.warp = warp;
	}

	public Warp getWarp() {
		return this.warp;
	}

	public void setWarp(Warp warp) {
		this.warp = warp;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}