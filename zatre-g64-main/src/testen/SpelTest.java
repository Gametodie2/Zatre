package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Spel;
import domein.Speler;

class SpelTest {
	private List<Speler> spelers = new ArrayList<>();
	private Spel s;
	private Speler speler1;
	private Speler speler2;

	@BeforeEach
	void init() {
		speler1 = new Speler("warre", 2003);
		speler2 = new Speler("yanid", 2002);
		spelers.add(speler1);
		spelers.add(speler2);
		Spel s = new Spel(spelers);
	}

	@Test
	void spelAanmaken_verlaagdSpeelkansenMet1() {
		assertEquals(4, speler1.getSpeelkansen());
		assertEquals(4, speler2.getSpeelkansen());
	}
}
