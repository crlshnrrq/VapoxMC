package br.com.vapoxmc.kitpvp.commons.medalhas;

public enum MedalhaCategoria {
	UNICA("Única"),
	ESPECIAL("Especial"),
	RARA("Rara"),
	COMUM("Comum");

	private final String name;

	private MedalhaCategoria(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}