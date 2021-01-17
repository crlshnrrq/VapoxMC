package br.com.vapoxmc.kitpvp.commons.medalhas;

import org.bukkit.ChatColor;

public enum Medalha {
	NENHUMA(ChatColor.GRAY, "", "Medalha padrão.", "", MedalhaCategoria.COMUM),
	SEASON_1_TOP_ABATES("Season #1 Top Abates", ChatColor.DARK_RED, "§lⓋ",
			"Medalha concedida o jogador Top 1 de Abates da Season #1.", "vapoxmc.medal.season1.topabates",
			MedalhaCategoria.UNICA),
	SEASON_1_TOP_PONTOS("Season #1 Top Pontos", ChatColor.GREEN, "§o§lⓋ",
			"Medalha concedida o jogador Top 1 de Pontos da Season #1.", "vapoxmc.medal.season1.toppontos",
			MedalhaCategoria.UNICA),
	ALPHA(ChatColor.GOLD, "α", "Medalha concedida aos jogadores que entraram na Reabertura do Servidor.",
			"vapoxmc.medal.especial.alpha", MedalhaCategoria.ESPECIAL),
	BETA(ChatColor.YELLOW, "β", "Medalha concedida aos jogadores que participaram da Fase Beta do Servidor.",
			"vapoxmc.medal.especial.beta", MedalhaCategoria.ESPECIAL),
	CARINHA_FELIZ("Carinha Feliz", ChatColor.GOLD, "☺", "Medalha adiquirida dentro do Pacote Felicidade.",
			"vapoxmc.medal.felicidade.carinhafeliz", MedalhaCategoria.RARA),
	CARINHA_TRISTE("Carinha Triste", ChatColor.YELLOW, " ☹ ", "Medalha adiquirida dentro do Pacote Felicidade.",
			"vapoxmc.medal.felicidade.carinhatriste", MedalhaCategoria.RARA),
	TSU(ChatColor.GREEN, "ツ", "Medalha adiquirida dentro do Pacote Felicidade.", "vapoxmc.medal.felicidade.tsu",
			MedalhaCategoria.RARA),
	SHI(ChatColor.DARK_GREEN, "シ", "Medalha adiquirida dentro do Pacote Felicidade.", "vapoxmc.medal.felicidade.shi",
			MedalhaCategoria.RARA),
	CARINHA_SORRIDENTE(ChatColor.AQUA, "😁", "Medalha adiquirida dentro do Pacote Felicidade.",
			"vapoxmc.medal.felicidade.carinhasorridente", MedalhaCategoria.RARA),
	ATENCAO("Atenção!", ChatColor.RED, "‼", "Medalha adiquirida dentro do Pacote Extras.",
			"vapoxmc.medal.extras.atencao", MedalhaCategoria.RARA),
	EXCLAMACAO_DE_CORACAO("Exclamação de Coração", ChatColor.DARK_RED, "❣",
			"Medalha adiquirida dentro do Pacote Extras.", "vapoxmc.medal.extras.exclamacaodecoracao",
			MedalhaCategoria.RARA),
	CLICKBAIT("Clickbait", ChatColor.AQUA, "⁉", "Medalha adiquiquirida dentro do Pacote Extras.",
			"vapoxmc.medal.extras.clickbait", MedalhaCategoria.RARA),
	COPYRIGHT(ChatColor.WHITE, "©", "Medalha adiquirida dentro do Pacote Extras.", "vapoxmc.medal.extras.copyright",
			MedalhaCategoria.RARA),
	TRADEMARK("TradeMark", ChatColor.DARK_GREEN, "™", "Medalha adiquirida dentro do Pacote Extras.",
			"vapoxmc.medal.extras.trademark", MedalhaCategoria.RARA),
	WAVE(ChatColor.AQUA, "≈", "Medalha adiquirida dentro do Pacote Extras.", "vapoxmc.medal.extras.wave",
			MedalhaCategoria.RARA),
	EMAIL("E-Mail", ChatColor.YELLOW, "✉", "Medalha adiquirida dentro do Pacote Extras.", "vapoxmc.medal.extras.email",
			MedalhaCategoria.RARA),
	COPAS(ChatColor.DARK_RED, "♥", "Medalha adiquirida dentro do Pacote Cartas.", "vapoxmc.medal.cartas.copas",
			MedalhaCategoria.RARA),
	OUROS(ChatColor.DARK_RED, "♦", "Medalha adiquirida dentro do Pacote Cartas.", "vapoxmc.medal.cartas.ouros",
			MedalhaCategoria.RARA),
	PAUS(ChatColor.BLACK, "♣", "Medalha adiquirida dentro do Pacote Cartas.", "vapoxmc.medal.cartas.paus",
			MedalhaCategoria.RARA),
	ESPADAS(ChatColor.BLACK, "♠", "Medalha adiquirida dentro do Pacote Cartas.", "vapoxmc.medal.cartas.espadas",
			MedalhaCategoria.RARA),
	FEMININO(ChatColor.LIGHT_PURPLE, "♀", "Medalha adiquirida dentro do Pacote Sexy.", "vapoxmc.medal.sexy.feminino",
			MedalhaCategoria.RARA),
	MASCULINO(ChatColor.BLUE, "♂", "Medalha adiquirida dentro do Pacote Sexy.", "vapoxmc.medal.sexy.masculino",
			MedalhaCategoria.RARA),
	INDEFINIDO(ChatColor.DARK_PURPLE, "⚥", "Medalha adiquirida dentro do Pacote Sexy.", "vapoxmc.medal.sexy.indefinido",
			MedalhaCategoria.RARA),
	NOTA_MUSICAL("Nota Musical", ChatColor.RED, "♭", "Medalha adiquirida dentro do Pacote Sinfonia.",
			"vapoxmc.medal.sinfonia.notamusical", MedalhaCategoria.RARA),
	QUARTA_NOTA("Quarta Nota", ChatColor.BLACK, "♩", "Medalha adiquirida dentro do Pacote Sinfonia.",
			"vapoxmc.medal.sinfonia.quartanota", MedalhaCategoria.RARA),
	SEMI_OITAVAS("Semi-Oitavas", ChatColor.DARK_AQUA, "♬", "Medalha adiquirida dentro do Pacote Sinfonia.",
			"vapoxmc.medal.sinfonia.semioitavas", MedalhaCategoria.RARA),
	OITAVA_NOTA("Oitava Nota", ChatColor.DARK_GRAY, "♪", "Medalha adiquirida dentro do Pacote Sinfonia.",
			"vapoxmc.medal.sinfonia.oitavanota", MedalhaCategoria.RARA),
	OITAVAS_NOTAS_TRANSFERIDAS("Oitavas Notas Transferidas", ChatColor.AQUA, "♫",
			"Medalha adiquirida dentro do Pacote Sinfonia.", "vapoxmc.medal.sinfonia.oitavasnotastransferidas",
			MedalhaCategoria.RARA),
	CENTS(ChatColor.GRAY, "¢", "Medalha adiquirida dentro do Pacote Moedas.", "vapoxmc.medal.moedas.cents",
			MedalhaCategoria.RARA),
	YENE(ChatColor.DARK_RED, "¥", "Medalha adiquirida dentro do Pacote Moedas.", "vapoxmc.medal.moedas.yene",
			MedalhaCategoria.RARA),
	EURO(ChatColor.GOLD, "€", "Medalha adiquirida dentro do Pacote Euro.", "vapoxmc.medal.moedas.euro",
			MedalhaCategoria.RARA),
	CRUZ(ChatColor.WHITE, "✞", "Medalha adiquirida dentro do Pacote Cruzes.", "vapoxmc.medal.cruzes.cruz",
			MedalhaCategoria.RARA),
	CRUZ_DE_JERUSALEM("Cruz de Jerusalém", ChatColor.RED, "☩", "Medalha adiquirida dentro do Pacote Cruzes.",
			"vapoxmc.medal.cruzes.cruzdejerusalem", MedalhaCategoria.RARA),
	CRUZ_DE_LORENA("Cruz de Lorena", ChatColor.GOLD, "☨", "Medalha adiquirida dentro do Pacote Cruzes.",
			"vapoxmc.medal.cruzes.cruzdelorena", MedalhaCategoria.RARA),
	CRUZ_ORTODOXA_RUSSA("Cruz Ortodoxa Russa", ChatColor.DARK_PURPLE, "☦",
			"Medalha adiquirida dentro do Pacote Cruzes.", "vapoxmc.medal.cruzes.cruzortodoxarussa",
			MedalhaCategoria.RARA),
	CRUZ_LATINA("Cruz Latina", ChatColor.BLACK, "✝", "Medalha adiquirida dentro do Pacote Cruzes.",
			"vapoxmc.medal.cruzes.cruzlatina", MedalhaCategoria.RARA),
	CRUZ_DE_MALTA("Cruz de Malta", ChatColor.DARK_GREEN, "✠", "Medalha adiquirida dentro do Pacote Cruzes.",
			"vapoxmc.medal.cruzes.cruzdemalta", MedalhaCategoria.RARA),
	CRUZ_GREGA_PESADA("Cruz Grega Pesada", ChatColor.DARK_RED, "✚", "Medalha adiquirida dentro do Pacote Cruzes.",
			"vapoxmc.medal.cruzes.cruzgregapesada", MedalhaCategoria.RARA);

	private final String name, symbol, description, permission;
	private final ChatColor color;
	private final MedalhaCategoria categoria;

	private Medalha(ChatColor color, String symbol, String description, MedalhaCategoria categoria) {
		this(color, symbol, description, "", categoria);
	}

	private Medalha(String name, ChatColor color, String symbol, String description, MedalhaCategoria categoria) {
		this(name, color, symbol, description, "", categoria);
	}

	private Medalha(ChatColor color, String symbol, String description, String permission, MedalhaCategoria categoria) {
		this.name = (this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase()).replace("_",
				" ");
		this.color = color;
		this.symbol = symbol;
		this.description = description;
		this.permission = permission;
		this.categoria = categoria;
	}

	private Medalha(String name, ChatColor color, String symbol, String description, String permission,
			MedalhaCategoria categoria) {
		this.name = name;
		this.color = color;
		this.symbol = symbol;
		this.description = description;
		this.permission = permission;
		this.categoria = categoria;
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

	public MedalhaCategoria getCategoria() {
		return this.categoria;
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