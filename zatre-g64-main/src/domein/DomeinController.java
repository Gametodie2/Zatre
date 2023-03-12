package domein;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
	private List<Speler> spelers = new ArrayList<>();
	private final SpelerRepository spelerRepository;
	Speler nieuweSpeler;
	private int counterNieuweSelectie = 0;
	private Spel spel;
	String[][] deGeselecteerdeSpelers;
	private Spelbord spelbord;

	// --------------------- CONSTRUCTORS ----------------------------

	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}

	// --------------------- REGISTREER SPELER ----------------------------

	/**
	 * Registreert een speler en voegt die toe in de database
	 * 
	 * @param gebruikersnaam
	 * @param geboortejaar
	 */
	public void registreer(String gebruikersnaam, int geboortejaar) {
		nieuweSpeler = new Speler(gebruikersnaam, geboortejaar);
		spelerRepository.voegToe(nieuweSpeler);
	}

	// --------------------- SELECTEER SPELER ----------------------------

	/**
	 * Voegt een speler toe in de selectie
	 * 
	 * @param gebruikersnaam
	 * @param geboortejaar
	 */
	public void selecteerSpeler(String gebruikersnaam, int geboortejaar) {
		spelers.add(spelerRepository.selecteerSpeler(gebruikersnaam, geboortejaar));
		counterNieuweSelectie++;

	}

	/**
	 * Returned de geselecteerde spelers als 2d array
	 * 
	 * @return
	 */
	public String[][] getSpelers() {
		deGeselecteerdeSpelers = new String[counterNieuweSelectie][3];
		int counter = 0;
		for (Speler speler : spelers) {
			deGeselecteerdeSpelers[counter][0] = speler.getGebruikersnaam();
			deGeselecteerdeSpelers[counter][1] = Integer.toString(speler.getSpeelkansen());
			deGeselecteerdeSpelers[counter][2] = Integer.toString(speler.getGeboortejaar());
			counter++;
		}
		return deGeselecteerdeSpelers;
	}

	/**
	 * Returned de geselecteerde spelers als spelerObjecten
	 * 
	 * @return
	 */
	public List<Speler> lijstSpelersOp() {
		return spelers;
	}

	/**
	 * Verwijdert alle geselecteerde spelers
	 */
	public void leegLijst() {
		spelers = new ArrayList<>();
		counterNieuweSelectie = 0;
		spelerRepository.aantalGeselecteerdespelers = 0;
	}

	// --------------------- GET GEBRUIKERSNAAM ----------------------------

	/**
	 * Returned de namen van alle geselecteerde spelers
	 * 
	 * @return
	 */
	public List<String> geefGebruikersnamen() {
		List<String> namen = new ArrayList<>();
		for (int i = 0; i < spelers.size(); i++) {
			namen.add(i, spelers.get(i).getGebruikersnaam());
		}
		return namen;
	}

	// --------------------- GET SPEELKANSEN ----------------------------

	/**
	 * Returned de speelkansen van alle geselecteerde spelers
	 * 
	 * @return
	 */
	public List<Integer> geefAantalSpeelkansen() {
		List<Integer> speelkansen = new ArrayList<>();
		for (int i = 0; i < spelers.size(); i++) {
			speelkansen.add(i, spelers.get(i).getSpeelkansen());
		}
		return speelkansen;
	}

	// --------------------- SPEL ----------------------------

	/**
	 * Start een nieuw spel op en vermindert de speelkansen van de geselecteerde
	 * spelers
	 */
	public void startNieuwSpel() {
		setSpel(new Spel(spelers));
		for (Speler speler : spelers) {
			spelerRepository.verminderSpeelkansVanSpeler(speler);
		}
	}

	/**
	 * Zet de field gelijk aan de parameter spel
	 * 
	 * @param spel
	 */
	private void setSpel(Spel spel) {
		this.spel = spel;
	}

	/**
	 * Returned de boolean waarde van de methode die aangeroepen wordt uit de Spel
	 * klasse
	 * 
	 * @return
	 */
	public Boolean isEindeSpel() {
		return spel.isEindeSpel();
	}

	/**
	 * Roept de methode resetGeplaatsteStenen aan uit de Spel klasse
	 */
	public void resetGeplaatsteStenen() {
		spel.resetGeplaatsteStenen();
	}

	/**
	 * Maakt de lijst van geselecteerde spelerobjecten leeg
	 */
	public void resetSpelers() {
		spelers = new ArrayList<>();
		counterNieuweSelectie = 0;
		spelerRepository.aantalGeselecteerdespelers = 0;
	}

	// --------------------- PLAATS STEEN ----------------------------

	/**
	 * Roept de methode plaatsSteen uit de Spel klasse aan
	 * 
	 * @param xCoord
	 * @param yCoord
	 * @param waarde
	 */
	public void plaatsSteen(int xCoord, int yCoord, int waarde) {
		spel.plaatsSteen(xCoord, yCoord, waarde);
	}

	/**
	 * Roept de methode getSteen uit de Spel klasse aan
	 * 
	 * @return
	 */
	public int getSteen() {
		return spel.getSteen();
	}

	/**
	 * Roept de methode voegSteenToe uit de Spel klasse aan
	 * 
	 * @param waarde
	 */
	public void voegSteenToe(int waarde) {
		spel.voegSteenToe(waarde);
	}

	// --------------------- GET SPELBORD ----------------------------

	/**
	 * Returned de kleur van de vakken op het spelbord
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public String getKleur(int x, int y) {
		spelbord = spel.getSpelbord();
		return spelbord.getVakken()[x][y].getKleur();
	}

	// --------------------- BEPAAL BEURT ----------------------------

	/**
	 * Roept de methode bepaalBeurt uit de Spel klasse aan
	 */
	public void bepaalBeurt() {
		spel.bepaalBeurt();
	}

	/**
	 * Returned de speler aan beurt als Integer
	 * 
	 * @return
	 */
	public int geefSpelerAanBeurt() {
		Speler s = spel.getBeurt();
		for (int i = 0; i <= spelers.size() - 1; i++) {
			if (s.equals(spelers.get(i))) {
				return i;
			}
		}
		return 0;
	}
}
