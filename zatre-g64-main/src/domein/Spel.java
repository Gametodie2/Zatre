package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Spel {

	private Spelbord spelbord;
	private Speler beurt;
	private PotStenen pot;
	private List<Speler> spelers;
	private List<Scoreblad> scorebladen = new ArrayList<Scoreblad>();
	private int ronde;
	private int beurtCounter;
	private boolean eersteSteen = true;
	private int stenenGeplaatst = 0;
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	private int x3 = 0;
	private int y3 = 0;

	Scoreblad scoreblad = new Scoreblad();

	// --------------------- CONSTRUCTORS ----------------------------

	public Spel(List<Speler> spelers) {
		spelbord = new Spelbord();
		this.spelers = spelers;
		this.beurtCounter = 0;
		this.ronde = 0;
		this.scorebladen = scorebladen;
		maakPotStenen();
		randomizeSpelers();
		verlaagSpeelkansen();
		maakScoreBladPerSpeler();
	}

	// --------------------- SETTERS ----------------------------

	// Spelbord
	public Spelbord getSpelbord(Spelbord spelbord) {
		return spelbord;
	}

	// Beurt
	public void setBeurt(Speler speler) {
		this.beurt = speler;
	}

	// Pot stenen
	private void maakPotStenen() {
		pot = new PotStenen();
	}

	// --------------------- GETTERS ----------------------------

	// Beurt
	public Speler getBeurt() {
		return beurt;
	}

	// Spelbord
	public Spelbord getSpelbord() {
		return spelbord;
	}

	// --------------------- STENEN ----------------------------

	/**
	 * Checkt de spelregels en als de geplaatste steen aan alle regels voldoet,
	 * wordt de steen geplaatst op het spelbord
	 * 
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 */
	public void plaatsSteen(int xCoords, int yCoords, int waarde) {
		if (checkVorigeVakken(xCoords, yCoords)) {
			resetValues();
			throw new IllegalArgumentException("Je steen moet naast een steen van een andere ronde liggen.");
		} else if (eersteSteen && ronde == 1 && !(xCoords == 7 && yCoords == 7)) {
			resetValues();
			throw new IllegalArgumentException("De eerste zet moet in het midden gelegd worden");
		} else if (!checkOmgeving(xCoords, yCoords) && !eersteSteen) {
			resetValues();
			throw new IllegalArgumentException("De steen moet geplaatst worden naast een andere steen.");
		} else if (!checkGrijsGroote(xCoords, yCoords, waarde) && !eersteSteen) {
			resetValues();
			throw new IllegalArgumentException(
					"Als je jouw steen op een grijs vakje wil plaatsten, moet de score minstens 10 zijn.");
		} else if (!lijnenIsFout(xCoords, yCoords, waarde) && !eersteSteen) {
			resetValues();
			throw new IllegalArgumentException("De score mag niet groter zijn dan 12 op een lijn/kolom.");
		} else {
			stenenGeplaatst++;
			if (stenenGeplaatst == 1) {
				x1 = xCoords;
				y1 = yCoords;
			} else if (stenenGeplaatst == 2) {
				x2 = xCoords;
				y2 = yCoords;
			} else {
				x3 = xCoords;
				y3 = yCoords;
			}
			eersteSteen = false;
			spelbord.setBezet(xCoords, yCoords, waarde);
			changeScoreblad(xCoords, yCoords, waarde, ronde, stenenGeplaatst);
			if ((ronde == 1 && stenenGeplaatst == 3) || (ronde != 1 && stenenGeplaatst == 2)) {
				setTotaalScoreRij();
				showScoreBlad();
				resetValues();
			}
		}
	}

	/**
	 * Voegt een steen toe aan de pot stenen
	 * 
	 * @param waarde
	 */
	public void voegSteenToe(int waarde) {
		pot.getStenen().add(waarde);
	}

	/**
	 * Reset de waarden van de x en y coordinaten en de stenen die geplaatst zijn
	 */
	private void resetValues() {
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		x3 = 0;
		y3 = 0;
		stenenGeplaatst = 0;
	}

	/**
	 * Reset de geplaatste stenen op het bord van in die ronde
	 */
	public void resetGeplaatsteStenen() {
		Vak[][] vakken = spelbord.getVakken();
		vakken[x1][y1].setWaarde(0);
		vakken[x2][y2].setWaarde(0);
		if (ronde == 1) {
			vakken[x3][y3].setWaarde(0);
			eersteSteen = true;
		}
	}

	/**
	 * Returned true als de meegegeven steen geplaatst wordt naast een andere steen,
	 * returned false als de meegegeven steen niet naast een andere steen ligt
	 * 
	 * @param xCoords
	 * @param yCoords
	 * @return
	 */
	private boolean checkOmgeving(int xCoords, int yCoords) {
		if (xCoords < 14 && yCoords < 14 && xCoords > 0 && yCoords > 0) {
			if (spelbord.isBezet(xCoords, yCoords + 1) == 0 && spelbord.isBezet(xCoords, yCoords - 1) == 0
					&& spelbord.isBezet(xCoords + 1, yCoords) == 0 && spelbord.isBezet(xCoords - 1, yCoords) == 0)
				return false;
			else
				return true;

		} else if (xCoords == 0) {
			if (spelbord.isBezet(xCoords, yCoords + 1) == 0 && spelbord.isBezet(xCoords, yCoords - 1) == 0
					&& spelbord.isBezet(xCoords + 1, yCoords) == 0)
				return false;
			else
				return true;

		} else if (xCoords == 14) {
			if (spelbord.isBezet(xCoords, yCoords + 1) == 0 && spelbord.isBezet(xCoords, yCoords - 1) == 0
					&& spelbord.isBezet(xCoords - 1, yCoords) == 0)
				return false;
			else
				return true;

		} else if (yCoords == 0) {
			if (spelbord.isBezet(xCoords, yCoords + 1) == 0 && spelbord.isBezet(xCoords, yCoords - 1) == 0
					&& spelbord.isBezet(xCoords + 1, yCoords) == 0)
				return false;
			else
				return true;

		} else if (yCoords == 14) {
			if (spelbord.isBezet(xCoords + 1, yCoords) == 0 && spelbord.isBezet(xCoords, yCoords - 1) == 0
					&& spelbord.isBezet(xCoords - 1, yCoords) == 0)
				return false;
			else
				return true;
		}
		return false;
	}

	/**
	 * Returned true als de meegegeven steen enkel naast een andere steen van
	 * diezelfde ronde ligt, returned false als het ronde 1 is of als de meegegeven
	 * steen naast een steen van de vorige ronde ligt
	 * 
	 * @param xCoords
	 * @param yCoords
	 * @return
	 */
	private boolean checkVorigeVakken(int xCoords, int yCoords) {
		boolean rechtsVanSteen = (xCoords + 1 == x1 && y1 == yCoords) || (xCoords + 1 == x2 && y2 == yCoords)
				|| (xCoords + 1 == x3 && y3 == yCoords && ronde == 2);
		boolean linksVanSteen = (xCoords - 1 == x1 && y1 == yCoords) || (xCoords - 1 == x2 && yCoords == y2)
				|| (xCoords - 1 == x3 && yCoords == y3 && ronde == 2);
		boolean bovenSteen = (yCoords + 1 == y1 && xCoords == x1) || (yCoords + 1 == y2 && xCoords == x2)
				|| (yCoords + 1 == y3 && xCoords == x3 && ronde == 2);
		boolean onderSteen = (yCoords - 1 == y1 && xCoords == x1) || (yCoords - 1 == y2 && xCoords == x2)
				|| (yCoords - 1 == y3 && xCoords == x3 && ronde == 2);
		if (ronde == 1) {
			return false;
		} else if (linksVanSteen && spelbord.isBezet(xCoords, yCoords - 1) == 0
				&& spelbord.isBezet(xCoords, yCoords + 1) == 0 && spelbord.isBezet(xCoords + 1, yCoords) == 0) {
			return true;
		} else if (spelbord.isBezet(xCoords, yCoords - 1) == 0 && spelbord.isBezet(xCoords, yCoords + 1) == 0
				&& spelbord.isBezet(xCoords - 1, yCoords) == 0 && rechtsVanSteen) {
			return true;
		} else if (bovenSteen && spelbord.isBezet(xCoords, yCoords - 1) == 0
				&& spelbord.isBezet(xCoords + 1, yCoords) == 0 && spelbord.isBezet(xCoords - 1, yCoords) == 0) {
			return true;
		} else if (spelbord.isBezet(xCoords, yCoords + 1) == 0 && spelbord.isBezet(xCoords + 1, yCoords) == 0
				&& spelbord.isBezet(xCoords - 1, yCoords) == 0 && onderSteen) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returned false als de meegegeven steen een score geeft van minder dan 10 en
	 * op een grijs vakje is gelegd, returned true als dit niet zo is
	 * 
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 * @return
	 */
	private boolean checkGrijsGroote(int xCoords, int yCoords, int waarde) {
		if ((spelbord.getVakken()[xCoords][yCoords].getKleur().equals("grijs"))
				&& (berekenScoreVerticaal(xCoords, yCoords, waarde) < 10
						&& berekenScoreHorizontaal(xCoords, yCoords, waarde) < 10)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * returned false als de steen niet mag gelegd worden omdat er dan een score
	 *  gemaakt wordt die groter is dan 12
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 * @return
	 */
	private boolean lijnenIsFout(int xCoords, int yCoords, int waarde) {
		if (berekenScoreVerticaal(xCoords, yCoords, waarde) > 12
				|| berekenScoreHorizontaal(xCoords, yCoords, waarde) > 12) {
			return false;
		} else {

			return true;
		}
	}

	/**
	 * returned de opgetelde waarde van de steentjes die al op het 
	 * bord liggen (horizontaal links van waar de nieuwe steen komt)
	 * @param xCoords
	 * @param yCoords
	 * @return
	 */
	public int checktHorizontaalLinks(int xCoords, int yCoords) {
		boolean checker = true;
		int counter = 1;
		int score = 0;

		while (checker) {
			if (xCoords == 0) {
				score += spelbord.isBezet(xCoords, yCoords);
				checker = false;
			} else {
				if (spelbord.isBezet(xCoords - counter, yCoords) == 0) {
					checker = false;
				} else {
					score += spelbord.isBezet(xCoords - counter, yCoords);
				}
			}
			counter++;
		}
		return score;
	}

	/**
	 * * returned de opgetelde waarde van de steentjes die al op het bord liggen 
	 * (horizontaal rechts van waar de nieuwe steen komt)
	 * @param xCoords
	 * @param yCoords
	 * @return
	 */
	public int checktHorizontaalRechts(int xCoords, int yCoords) {
		boolean checker = true;
		int counter = 1;
		int score = 0;

		while (checker) {
			if (xCoords == 14) {
				score += spelbord.isBezet(xCoords, yCoords);
				checker = false;
			} else {
				if (spelbord.isBezet(xCoords + counter, yCoords) == 0) {
					checker = false;
				} else {

					score += spelbord.isBezet(xCoords + counter, yCoords);

				}
			}

			counter++;
		}
		return score;
	}

	/**
	 * * returned de opgetelde waarde van de steentjes die al op het bord liggen 
	 * (verticaal onder van waar de nieuwe steen komt)
	 * @param xCoords
	 * @param yCoords
	 * @return
	 */
	public int checktVerticaalOnder(int xCoords, int yCoords) {
		boolean checker = true;
		int counter = 1;
		int score = 0;

		while (checker) {
			if (yCoords == 14) {
				score += spelbord.isBezet(xCoords, yCoords);
				checker = false;
			} else {
				if (spelbord.isBezet(xCoords, yCoords + counter) == 0) {
					checker = false;
				} else {
					score += spelbord.isBezet(xCoords, yCoords + counter);
				}
			}
			counter++;
		}
		return score;
	}

	/**
	 * * returned de opgetelde waarde van de steentjes die al op het bord liggen 
	 * (verticaal boven van waar de nieuwe steen komt)
	 * @param xCoords
	 * @param yCoords
	 * @return
	 */
	public int checktVerticaalBoven(int xCoords, int yCoords) {
		boolean checker = true;
		int counter = 1;
		int score = 0;

		while (checker) {
			if (yCoords == 0) {
				score += spelbord.isBezet(xCoords, yCoords);
				checker = false;
			} else {
				if (spelbord.isBezet(xCoords, yCoords - counter) == 0) {
					checker = false;
				} else {
					score += spelbord.isBezet(xCoords, yCoords - counter);
				}
			}
			counter++;
		}
		return score;
	}

	/**
	 * Returned de waarde van de eerstevolgende steen in de pot
	 * 
	 * @return
	 */
	public int getSteen() {
		if (pot.getStenen().size() != 0) {
			int waarde = pot.getStenen().get(0);
			pot.getStenen().remove(0);
			return waarde;
		}
		return 0;
	}

	// --------------------- SPELER ----------------------------

	/**
	 * Verlaagd de speelkansen van alle geselecteerde spelers
	 */
	public void verlaagSpeelkansen() {
		for (int i = 0; i < spelers.size(); i++) {
			int aantalKansen = spelers.get(i).getSpeelkansen();
			aantalKansen--;
			spelers.get(i).setSpeelkansen(aantalKansen);
		}
	}

	/**
	 * Shuffeled de lijst van geselecteerde spelers
	 */
	public void randomizeSpelers() {
		Collections.shuffle(spelers);
	}

	/**
	 * Returned de geselecteerde spelers als spelerObjecten
	 * 
	 * @return
	 */
	public List<Speler> getSpelers() {
		return spelers;
	}

	// --------------------- SCORE ----------------------------

	/**
	 * returned de totale score van de omliggende stenen die op al op het bord liggen 
	 * horizontaal van waar de nieuwe steen komt.
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 * @return
	 */
	public int berekenScoreHorizontaal(int xCoords, int yCoords, int waarde) {
		int scoreHorizontaal = 0;
		scoreHorizontaal += waarde;

		scoreHorizontaal += checktHorizontaalRechts(xCoords, yCoords);
		scoreHorizontaal += checktHorizontaalLinks(xCoords, yCoords);

		return scoreHorizontaal;
	}

	/**
	 * 	 * returned de totale score van de omliggende stenen die op al op het bord liggen 
	 * verticaal van waar de nieuwe steen komt.
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 * @return
	 */
	public int berekenScoreVerticaal(int xCoords, int yCoords, int waarde) {
		int scoreVerticaal = 0;
		scoreVerticaal += waarde;

		scoreVerticaal += checktVerticaalBoven(xCoords, yCoords);
		scoreVerticaal += checktVerticaalOnder(xCoords, yCoords);

		return scoreVerticaal;
	}

	/**
	 * returned de totale score (zowel horizontaal als verticaal van de omliggende stenen
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 * @return
	 */
	public int berekenTotaalScore(int xCoords, int yCoords, int waarde) {
		int horizontaal = berekenScoreHorizontaal(xCoords, yCoords, waarde);
		int verticaal = berekenScoreVerticaal(xCoords, yCoords, waarde);

		int score = horizontaal + verticaal;

		return score;
	}

	/**
	 * returned true als de speler een steentje op een grijs vakje legt.
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 * @return
	 */
	public boolean isVakGrijs(int xCoords, int yCoords, int waarde) {
		if (lijnenIsFout(xCoords, yCoords, waarde)
				&& (spelbord.getVakken()[xCoords][yCoords].getKleur().equals("grijs"))) {
			return true;
		} else
			return false;
	}

	// --------------------- SCOREBLAD ----------------------------

	/**
	 * Maakt scoreblad aan per speler en stopt deze in een lijst
	 */
	private void maakScoreBladPerSpeler() {
		for (int i = 0; i < spelers.size(); i++) {
			scorebladen.add(new Scoreblad());
		}
	}

	/**
	 * @return returnt de index van de speler die aan de beurt is
	 */
	public int getIndexScoreBladSpelerAanBeurt() {
		Speler spelerAanBeurt = getBeurt();

		for (int i = 0; i <= spelers.size() - 1; i++) {
			if (spelerAanBeurt == (spelers.get(i))) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * @return returnt het scoreblad van de speler die aan de beurt is
	 */
	public Scoreblad getScorebladSpelerAanBeurt() {
		return scorebladen.get(getIndexScoreBladSpelerAanBeurt());
	}

	/**
	 * Zet totaal Score per rij in het scoreblad
	 */
	public void setTotaalScoreRij() {
		Scoreblad scoreblad = getScorebladSpelerAanBeurt();
		scoreblad.setTotaalScoreRij();
	}

	/**
	 * @param xCoords
	 * @param yCoords
	 * @param waarde
	 * @param ronde
	 * @param aantalGelegdeSteentjes Verander de scores in het Scoreblad
	 */
	public void changeScoreblad(int xCoords, int yCoords, int waarde, int ronde, int aantalGelegdeSteentjes) {
		System.out.printf("Horizontale score: %d, Verticale score: %d%n",
				berekenScoreHorizontaal(xCoords, yCoords, waarde), berekenScoreVerticaal(xCoords, yCoords, waarde));
		Scoreblad scoreblad = getScorebladSpelerAanBeurt();
		scoreblad.setValues(berekenScoreHorizontaal(xCoords, yCoords, waarde),
				berekenScoreVerticaal(xCoords, yCoords, waarde), isVakGrijs(xCoords, yCoords, waarde), ronde,
				aantalGelegdeSteentjes);
	}

	/**
	 * Toon het scoreblad in de console
	 */
	private void showScoreBlad() {
		Scoreblad scoreblad = getScorebladSpelerAanBeurt();
		scoreblad.showScoreBlad(getIndexScoreBladSpelerAanBeurt());
	}

	// --------------------- SPEL ----------------------------

	/**
	 * Returned true als de pot stenen leeg is
	 * 
	 * @return
	 */
	public boolean isEindeSpel() {
		if (pot.isLijstLeeg())
			return true;
		return false;
	}

	// --------------------- BEPAAL BEURT ----------------------------

	/**
	 * Bepaalt welke speler aan de beurt is om te spelen
	 */
	public void bepaalBeurt() {
		ronde++;

		resetValues();

		if (beurtCounter > spelers.size() - 1) {
			beurtCounter = 0;
		}
		if (ronde == 1) {
			setBeurt(spelers.get(0));
		} else {
			switch (beurtCounter) {
			case 0 -> setBeurt(spelers.get(0));
			case 1 -> setBeurt(spelers.get(1));
			case 2 -> setBeurt(spelers.get(2));
			case 3 -> setBeurt(spelers.get(3));
			}
		}
		beurtCounter++;
	}
}