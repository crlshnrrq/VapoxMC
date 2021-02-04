package br.com.vapoxmc.vapoxpvp.kitssystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;

public class PlayerRemoveKitEvent extends PlayerKitEvent implements Cancellable {

	private boolean cancelled;

	public PlayerRemoveKitEvent(Player who, Kit kit) {
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