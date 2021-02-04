package br.com.vapoxmc.vapoxpvp.warpssystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import br.com.vapoxmc.vapoxpvp.warpssystem.Warp;

public final class PlayerTeleportWarpEvent extends PlayerWarpEvent implements Cancellable {

	private boolean cancelled;

	public PlayerTeleportWarpEvent(Player who, Warp warp) {
		super(who, warp);
		this.cancelled = false;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}