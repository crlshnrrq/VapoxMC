package br.com.vapoxmc.kitpvp.commons.medalhas;

import java.util.Arrays;
import java.util.List;

public enum MedalhaPacote {
	NENHUM(Arrays.asList(Medalha.NENHUMA, Medalha.SEASON_1_TOP_ABATES, Medalha.SEASON_1_TOP_PONTOS, Medalha.ALPHA,
			Medalha.BETA)),
	FELICIDADE(Arrays.asList(Medalha.CARINHA_FELIZ, Medalha.CARINHA_TRISTE, Medalha.TSU, Medalha.SHI,
			Medalha.CARINHA_SORRIDENTE)),
	EXTRAS(Arrays.asList(Medalha.ATENCAO, Medalha.EXCLAMACAO_DE_CORACAO, Medalha.CLICKBAIT, Medalha.COPYRIGHT,
			Medalha.TRADEMARK, Medalha.WAVE, Medalha.EMAIL)),
	CARTAS(Arrays.asList(Medalha.COPAS, Medalha.OUROS, Medalha.PAUS, Medalha.ESPADAS)),
	SEXY(Arrays.asList(Medalha.FEMININO, Medalha.MASCULINO, Medalha.INDEFINIDO)),
	SINFONIA(Arrays.asList(Medalha.NOTA_MUSICAL, Medalha.QUARTA_NOTA, Medalha.SEMI_OITAVAS, Medalha.OITAVA_NOTA,
			Medalha.OITAVAS_NOTAS_TRANSFERIDAS)),
	MOEDAS(Arrays.asList(Medalha.CENTS, Medalha.YENE, Medalha.EURO)),
	CRUZES(Arrays.asList(Medalha.CRUZ, Medalha.CRUZ_DE_JERUSALEM, Medalha.CRUZ_DE_LORENA, Medalha.CRUZ_ORTODOXA_RUSSA,
			Medalha.CRUZ_LATINA, Medalha.CRUZ_DE_MALTA, Medalha.CRUZ_GREGA_PESADA));

	private final String name;
	private final List<Medalha> medalhas;

	private MedalhaPacote(List<Medalha> medalhas) {
		this.name = this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
		this.medalhas = medalhas;
	}

	private MedalhaPacote(String name, List<Medalha> medalhas) {
		this.name = name;
		this.medalhas = medalhas;
	}

	public String getName() {
		return this.name;
	}

	public List<Medalha> getMedalhas() {
		return medalhas;
	}
}