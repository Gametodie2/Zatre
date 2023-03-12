package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.PotStenen;

class PotStenenTest {
	private PotStenen potStenen;

	@BeforeEach
	void init() {
		potStenen = new PotStenen();
	}

	@Test
	void potStenenAanmaken_maaktStenenAan_methodeGeeftFalse() {
		assertEquals(false, potStenen.isLijstLeeg());
	}

	@Test
	void potStenenAanmaken_lijstIsLeeg_methodeGeeftTrue() {
		potStenen.getStenen().clear();
		assertEquals(true, potStenen.isLijstLeeg());
	}
}
