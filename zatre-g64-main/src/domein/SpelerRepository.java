package domein;

import persistentie.SpelerMapper;

public class SpelerRepository {

	private final SpelerMapper mapper;
	public int aantalGeselecteerdespelers = 0;

	// --------------------- CONSTRUCTORS ----------------------------

	public SpelerRepository() {
		mapper = new SpelerMapper();
	}

	// -------------------------------------------------

	/**
	 * Voegt een speler toe aan de mapper als die nog niet bestaat
	 * 
	 * @param speler
	 */
	public void voegToe(Speler speler) {

		if (bestaatSpeler(speler.getGebruikersnaam(), speler.getGeboortejaar()))
			throw new IllegalArgumentException();
		mapper.voegToe(speler);
	}

	/**
	 * Checkt of de meegegeven speler al bestaat in de mapper
	 * 
	 * @param gebruikersnaam
	 * @param geboortjaar
	 * @return
	 */
	private boolean bestaatSpeler(String gebruikersnaam, int geboortjaar) {
		return mapper.bekijkAlsCombinatieBestaat(gebruikersnaam, geboortjaar) != null;

	}

	/**
	 * Returned de meegegeven geselecteerde speler
	 * 
	 * @param gebruikersnaam
	 * @param geboortejaar
	 * @return
	 */
	public Speler selecteerSpeler(String gebruikersnaam, int geboortejaar) {
		if (aantalGeselecteerdespelers == 4) {
			throw new IllegalArgumentException("Je mag maar 4 spelers selecteren");
		}
		Speler gekozenspeler = mapper.selecteerSpeler(gebruikersnaam, geboortejaar);
		if (gekozenspeler == null) {
			throw new IllegalArgumentException("Gebruiker niet gevonden.");
		}
		if (gekozenspeler.getSpeelkansen() == 0) {
			throw new IllegalArgumentException("Deze speler zijn speelkansen zijn op");
		}
		aantalGeselecteerdespelers++;
		return gekozenspeler;
	}

	/**
	 * Vermindert de speelkansen van de geselecteerde speler
	 * 
	 * @param speler
	 */
	public void verminderSpeelkansVanSpeler(Speler speler) {

		mapper.verminderSpeelkansen(speler);
	}

}
