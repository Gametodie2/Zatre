package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Spelbord;

class SpelbordTest {
	private Spelbord spelBord;

	private final int waarde = 1;

	@BeforeEach
	void init() {
		spelBord = new Spelbord();
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 7, 13 })
	void spelBordAanmaken_setBezet_waardeWordOpVakGezet(int coords) {
		spelBord.setBezet(coords, coords, waarde);
		assertEquals(waarde, spelBord.isBezet(coords, coords));
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 14 })
	void spelBordAanmaken_setBezet_vakIsNull(int coords) {
		assertNull(spelBord.getVakken()[coords][coords]);
	}

	@Test
	void spelBordAanmaken_maaktVakkenAan() {
		assertEquals(false, spelBord.getVakken().length == 0);
	}
}
