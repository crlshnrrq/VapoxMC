package br.com.vapoxmc.kitpvp.commons.medalhas;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public final class MedalhaAPI {

	private static final HashMap<UUID, Medalha> medalhaMap = new HashMap<>();

	public static boolean hasMedalha(Player player) {
		return getMedalha(player) != Medalha.NENHUMA;
	}

	public static Medalha getMedalha(Player player) {
		return medalhaMap.getOrDefault(player.getUniqueId(), Medalha.NENHUMA);
	}

	public static boolean setMedalha(Player player, Medalha medalha) {
		try {
			medalhaMap.put(player.getUniqueId(), medalha);
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public static Medalha removeMedalha(Player player) {
		if (medalhaMap.containsKey(player.getUniqueId()))
			return medalhaMap.remove(player.getUniqueId());
		return Medalha.NENHUMA;
	}
}