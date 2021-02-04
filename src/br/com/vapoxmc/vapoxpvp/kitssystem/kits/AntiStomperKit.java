package br.com.vapoxmc.vapoxpvp.kitssystem.kits;

import org.bukkit.Material;

import br.com.vapoxmc.kitpvp.utils.Stack;
import br.com.vapoxmc.vapoxpvp.kitssystem.Kit;

public final class AntiStomperKit extends Kit {

	public AntiStomperKit() {
		super("AntiStomper", "Não seja esmagado por stompers.", new Stack(Material.DIAMOND_HELMET), true);
	}
}