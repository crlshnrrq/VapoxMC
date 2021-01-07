package br.com.vapoxmc.kitpvp.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum PlayerRank {
	// NOME, SÍMBOLO, COR, PONTOS
	UNRANKED("Unranked", '-', ChatColor.WHITE, 2000), PRIMARY("Primary", '⚌', ChatColor.GREEN, 4000),
	ADVANCED("Advanced", '✳', ChatColor.GRAY, 6000), PLATINA("Platina", '✮', ChatColor.RED, 8000),
	GOLD("Gold", '✸', ChatColor.GOLD, 10000), DIAMANTE("Diamante", '❂', ChatColor.AQUA, 12000),
	ESMERALDA("Esmeralda", '✷', ChatColor.GREEN, 14000), RUBY("Ruby", '♦', ChatColor.RED, 16000),
	ELITE("Elite", '♜', ChatColor.RED, 18000), SAFIRA("Safira", '✡', ChatColor.AQUA, 20000),
	VAPOX("Vapox", '✪', ChatColor.DARK_RED, Integer.MAX_VALUE);

	private final String name;
	private final char symbol;
	private final ChatColor color;
	private final int pontos;

	private PlayerRank(String name, char symbol, ChatColor color, int pontos) {
		this.name = name;
		this.symbol = symbol;
		this.color = color;
		this.pontos = pontos;
	}

	public String getName() {
		return this.name;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public ChatColor getColor() {
		return this.color;
	}

	public int getPontos() {
		return this.pontos;
	}

	public String getColoredName() {
		return this.getColor() + this.getName();
	}

	public String getColoredSymbol() {
		return this.getColor().toString() + this.getSymbol();
	}

	public String getColoredSymbolName() {
		return this.getColoredSymbol() + " " + this.getName();
	}

	public static PlayerRank getRank(Player player) {
		int pontos = PlayerAccount.getGeral().getPontos(player);
		for (PlayerRank rank : values()) {
			if (pontos <= rank.getPontos())
				return rank;
		}
		return UNRANKED;
	}
}