package br.com.vapoxmc.kitpvp.commons.medalhas;

import org.bukkit.ChatColor;

public enum Medalha {
	NENHUMA("Nenhuma", ChatColor.WHITE, "", "", ""),
	SEASON_1_TOP_ABATES("Season1TopAbates", ChatColor.GREEN, "§o§lⓋ",
			"Medalha dada o jogador Top 1 de Abates da Season #1.", "vapoxmc.medal.season1.topabates"),
	SEASON_1_TOP_PONTOS("Season1TopPontos", ChatColor.DARK_RED, "§lⓋ",
			"Medalha dada o jogador Top 1 de Pontos da Season #1.", "vapoxmc.medal.season1.toppontos");

	private final String name, symbol, description, permission;
	private final ChatColor color;

	private Medalha(String name, ChatColor color, String symbol, String description) {
		this(name, color, symbol, description, "");
	}

	private Medalha(String name, ChatColor color, String symbol, String description, String permission) {
		this.name = name;
		this.color = color;
		this.symbol = symbol;
		this.description = description;
		this.permission = permission;
	}

	public String getName() {
		return this.name;
	}

	public ChatColor getColor() {
		return this.color;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public String getDescription() {
		return this.description;
	}

	public boolean hasPermission() {
		return !this.getPermission().isEmpty();
	}

	public String getPermission() {
		return this.permission;
	}

	public String getColoredName() {
		return this.getColor() + this.getName();
	}

	public String getColoredSymbol() {
		return this.getColor() + this.getSymbol();
	}

	public static Medalha getByName(String name) {
		for (Medalha medals : values()) {
			if (medals.getName().equalsIgnoreCase(name))
				return medals;
		}
		return null;
	}
}