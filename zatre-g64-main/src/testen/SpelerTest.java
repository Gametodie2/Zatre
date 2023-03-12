package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;

class SpelerTest {
	private static final String CORRECTENAAM = "Warre";
	private static final int CORRECTGEBOORTEJAAR = 2000;

	@Test
	void spelerAanmaken_MaaktSpelerAanMetGegevenInfo() {
		Speler s = new Speler(CORRECTENAAM, CORRECTGEBOORTEJAAR);
		assertEquals(CORRECTENAAM, s.getGebruikersnaam());
		assertEquals(CORRECTGEBOORTEJAAR, s.getGeboortejaar());
		assertEquals(5, s.getSpeelkansen());
	}

	@ParameterizedTest
	@ValueSource(ints = { 2022, 2020, 2017, })
	void spelerAanmaken_FouteLeeftijden_ThrowException(int geboortejaar) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(CORRECTENAAM, geboortejaar));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "Bo", "Woow", "bro" })
	void spelerAanmaken_FouteGebruikersnaam_ThrowException(String gebruikersnaam) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(gebruikersnaam, CORRECTGEBOORTEJAAR));
	}
}
