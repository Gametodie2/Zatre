package domein;

public class Spelbord {
	private Vak[][] vakken;

	// --------------------- CONSTRUCTORS ----------------------------

	public Spelbord() {
		setVakken(15, 15);
	}

	// --------------------- SETTERS ----------------------------

	/**
	 * Maakt het spelbord aan
	 * 
	 * @param aantalKolommen
	 * @param aantalRijen
	 */
	private void setVakken(int aantalKolommen, int aantalRijen) {
		vakken = new Vak[aantalKolommen][aantalRijen];
		for (int i = 0; i < vakken.length; i++) {
			for (int j = 0; j < vakken.length; j++) {
				vakken[i][j] = new Vak("zwart", 0);
			}
		}
		for (int i = 1; i <= 9; i++) {
			if (i % 4 == 0) {
				for (int j = 0; j < 3; j++) {
					if (i + j == 6 || i + j == 8) {
						vakken[i + j][0] = new Vak("grijs", 0);
						vakken[i + j][14] = new Vak("grijs", 0);
						vakken[0][i + j] = new Vak("grijs", 0);
						vakken[14][i + j] = new Vak("grijs", 0);
					} else {
						vakken[i + j][0] = new Vak("wit", 0);
						vakken[i + j][14] = new Vak("wit", 0);
						vakken[0][i + j] = new Vak("wit", 0);
						vakken[14][i + j] = new Vak("wit", 0);
					}
				}
			}
		}
		for (int j = 1; j < 14; j++) {
			for (int i = 1; i < 14; i++) {
				if (i == j || i + j == 14)
					vakken[i][j] = new Vak("grijs", 0);
				else
					vakken[i][j] = new Vak("wit", 0);
			}
		}
	}

	/**
	 * Zet een waarde om het meegegeven vak
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param waarde
	 */
	public void setBezet(int xCoord, int yCoord, int waarde) {
		Vak[][] vakken = getVakken();
		vakken[xCoord][yCoord].setWaarde(waarde);
	}

	// --------------------- GETTERS ----------------------------

	/**
	 * Returned de vakken als vakObjecten
	 * 
	 * @return
	 */
	public Vak[][] getVakken() {
		return vakken;
	}

	// -------------------- isBezet ----------------------

	/**
	 * Returned de waarde van het meegegeven vak
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @return
	 */
	public int isBezet(int xCoord, int yCoord) {
		Vak[][] vakken = getVakken();
		return vakken[xCoord][yCoord].getWaarde();
	}
}
