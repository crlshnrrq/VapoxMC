package br.com.vapoxmc.kitpvp.utils;

import java.util.ArrayList;

public final class Strings {

	public static String getName() {
		return "§4§lVapox§f§lMC";
	}

	public static String getPrefix() {
		return getName() + " §7»";
	}

	public static ArrayList<String> getMOTDs() {
		ArrayList<String> motds = new ArrayList<>();
		motds.add("                         " + getName() + "\n     §aVenha treinar e se divertir com seus amigos!");
		motds.add("                         " + getName() + "\n     §eEstamos com novidades, venha conferir!");
		motds.add("                         " + getName() + "\n     §bO Servidor reabriu, jogue conosco!");
		return motds;
	}

	public static String getDiscord() {
		return "https://discord.gg/KgfUdYH";
	}
}