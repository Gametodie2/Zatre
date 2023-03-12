package domein;

public class Vak {

	private String kleur;
	private boolean bezet;
	private int waarde;

	// --------------------- CONSTRUCTORS ----------------------------

	public Vak(String kleur, int waarde) {
		setKleur(kleur);
		setWaarde(waarde);
	}

	// --------------------- GETTERS ----------------------------

	public String getKleur() {
		return kleur;
	}

	public int getWaarde() {
		return waarde;
	}

	// --------------------- SETTERS ----------------------------

	private void setKleur(String kleur) {
		this.kleur = kleur;
	}

	public void setWaarde(int waarde) {
		this.waarde = waarde;
	}
}