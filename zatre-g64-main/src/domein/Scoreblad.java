package domein;

import java.util.HashMap;
import java.util.Map;

public class Scoreblad {

	private int totaleScore;
	private int scorePerRij;
	private int bonus = 0;
	private int scoreBlokjeEen = 0;
	Map<Integer, Integer> dict = new HashMap<Integer, Integer>();
	String[][] scoreblad;
	private int nieuweRonde = 0;

	// --------------------- CONSTRUCTORS ----------------------------

	public Scoreblad() {
		setVakken(22, 6);
		setHashMap();
	}

	// --------------------- SETTERS ----------------------------

	/**
	 * Vult de Hashmap aan
	 */
	private void setHashMap() {
		dict.put(1, 1);
		dict.put(2, 2);
		dict.put(3, 4);
	}

	/**
	 * @param aantalKolommen
	 * @param aantalRijen    Maakt het aantal vakken van het scoreblad aan en vult
	 *                       de bonus kolom in
	 */
	private void setVakken(int aantalKolommen, int aantalRijen) {
		scoreblad = new String[aantalKolommen][aantalRijen];
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 6; j++) {
				if (j == 4) {
					if (i <= 3) {
						scoreblad[i][j] = "3";
					} else if (i <= 8) {
						scoreblad[i][j] = "4";
					} else if (i <= 11) {
						scoreblad[i][j] = "5";
					} else if (i <= 14) {
						scoreblad[i][j] = "6";
					}
				}
			}
		}
	}

	/**
	 * @param index Toont Scoreblad in de console
	 */
	public void showScoreBlad(int index) {
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.printf("%s ", scoreblad[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * Berkent de totale score per rij en plaats deze ook in het scoreblad
	 */
	public void setTotaalScoreRij() {
		int score = 0;
		outerloop: for (int i = 0; i < scoreblad.length; i++) {

			// Als Totaal score op rij i leeg is
			if (scoreblad[i][5] == null) {

				// Check of de rij volledig ingevuld is
				for (int j = (scoreblad[i].length) - 3; j > -1; j--) {

					// Niet volledig ingevuld -> stop
					if (scoreblad[i][j] == null) {
						break outerloop;
					}

					// Volledig ingevuld -> bereken Totaal score.
					else {
						if (j != 0) {
							score += dict.get(j) * (scoreblad[i][j]).length();
						}

						else {
							score *= 2;
							score += Integer.parseInt(scoreblad[i][4]);
						}

					}

				}

				// Zet totaal SCore in de laatste kolom
				scoreblad[i][5] = Integer.toString(score);
			}
		}
	}

	/**
	 * @param isGrijsVak Plaats een kruisje op het scoreblad wanneer de speler een
	 *                   steentje legt op een grijs vakje
	 *
	 */
	private void setValueX2(boolean isGrijsVak) {
		if (isGrijsVak) {
			for (int i = 0; i < scoreblad.length; i++) {
				if (scoreblad[i][0] == null) {
					scoreblad[i][0] = "x";
					break;
				}
			}
		}
	}

	/**
	 * @param horizontaleScore
	 * @param verticaleScore
	 * @param isGrijsVak
	 * @param ronde
	 * @param aantalZetten     Plaatst een kruisje op het scoreblad wanneer een
	 *                         speler een score van 10, 11 of 12 heeft.
	 */

	public void setValues(int horizontaleScore, int verticaleScore, boolean isGrijsVak, int ronde, int aantalZetten) {
		// lijn 0 = x2 | lijn 1 = 10 | lijn 2 = 11 | lijn 3 = 12 | lijn 4 = bonus |
		// lijn 5 = totaal

		if ((ronde == 1 && aantalZetten == 3) || (!(ronde == 1)) && aantalZetten == 2) {
			nieuweRonde++;
		}

		// Zet kruisje als het een grijs vakje is.
		setValueX2(isGrijsVak);

		// Zet kruisje voor andere waarden.
		outerloop: for (int i = 0; i < scoreblad.length; i++) { // Ga door elke rij van het scoreblad
			for (int j = 0; j < scoreblad[i].length; j++) { // Ga door elke kolom van het scoreblad

				// Als kolom index == 0, ga door naar volgende kolom.
				if (j == 0) {
					continue;
				}

				// Als kolom index == 1, 2 of 3
				else if (j < 4) {

					// Waarde kolom == score ?
					if (dict.get(j) == verticaleScore || dict.get(j) == horizontaleScore) {

						// Als vakje leeg is
						if (scoreblad[i][j] == null) {
							scoreblad[i][j] = "x";
							break outerloop;
						}

						// Als vakje al bezet is
						else {
							if (nieuweRonde == ronde) {
								scoreblad[i][j] = "xx";
								break outerloop;
							}

						}
					}
				}
			}

		}
	}
}