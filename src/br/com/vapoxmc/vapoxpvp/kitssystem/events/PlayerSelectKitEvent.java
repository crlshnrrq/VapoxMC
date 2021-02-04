package br.com.vapoxmc.vapoxpvp.kitssystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;

public class PlayerSelectKitEvent extends PlayerKitEvent implements Cancellable {

	private boolean cancelled;

	public PlayerSelectKitEvent(Player who, Kit kit) {
		super(who, kit);
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