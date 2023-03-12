package domein;

import java.time.LocalDate;

public class Speler {

	private String gebruikersnaam;
	private int geboortejaar;
	private int speelkansen;

	// --------------------- CONSTRUCTORS ----------------------------

	public Speler(String gebruikersnaam, int geboortejaar, int speelKansen) {
		setGeboortejaar(geboortejaar);
		setGebruikersnaam(gebruikersnaam);
		setSpeelkansen(speelKansen);
	}

	public Speler(String gebruikersnaam, int geboortejaar) {
		setGeboortejaar(geboortejaar);
		setGebruikersnaam(gebruikersnaam);
		this.speelkansen = 5;
	}

	// --------------------- GETTERS ----------------------------

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	public int getSpeelkansen() {
		return speelkansen;
	}

	// --------------------- SETTERS ----------------------------

	public void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam == null || gebruikersnaam.isEmpty() || gebruikersnaam.length() < 5)
			throw new IllegalArgumentException("De gebruikersnaam moet langer dan 4 karakters zijn.");
		this.gebruikersnaam = gebruikersnaam;
	}

	public void setGeboortejaar(int geboortejaar) {
		if (LocalDate.now().getYear() - geboortejaar < 6)
			throw new IllegalArgumentException("Speler moet 6 jaar oud zijn of worden in dit jaar.");
		this.geboortejaar = geboortejaar;
	}

	public void setSpeelkansen(int speelkansen) {
		this.speelkansen = speelkansen;
	}

	// --------------------- TO STRING ----------------------------

	@Override
	public String toString() {
		return String.format("Gebruikersnaam: %s, Geboortejaar: %d, Speelkansen: %d", getGebruikersnaam(),
				getGeboortejaar(), getSpeelkansen());
	}
}