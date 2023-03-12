package cui;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class SelecteerSpelerApp {
	private DomeinController dc;

	public SelecteerSpelerApp(DomeinController dc) {
		this.dc = dc;
	}

	public void startSelectie() {
		Scanner invoer = new Scanner(System.in);
		int keuze;
		String naam;
		int geboortejaar;
		boolean blijvenSelecteren = true;
		int counterGeselecteerden = 0;
		boolean nietfout = true;
		boolean stop = false;
		ResourceBundle r = ZatreApp.getResourceBundle();
		Locale currentLocale = ZatreApp.getCurrentLocale();

		System.out.println(r.getString("SelectieStart") + ":");
		do {
			nietfout = true;
			try {
				do {
					do {
						System.out.println(r.getString("VraagNaam") + "?");
						naam = invoer.next();
					} while (naam.isEmpty() || naam == null);

					do {
						System.out.println(r.getString("VraagGeboortejaar") + "?");
						geboortejaar = invoer.nextInt();
					} while (geboortejaar <= 0);
					if (!checkalsspeleralsisgeselecteerd(naam, geboortejaar)) {
						throw new IllegalStateException("Deze speler is al gekozen");
					} else {

					}

					dc.selecteerSpeler(naam, geboortejaar);
					counterGeselecteerden++;
					if (counterGeselecteerden < 4 && counterGeselecteerden >= 2) {
						System.out.println(r.getString("SelectieVraag"));

						if ((invoer.next().toLowerCase()).equals(r.getString("Nee"))) {
							blijvenSelecteren = false;
							stop = true;

						}
					}

					if (counterGeselecteerden == 4) {
						blijvenSelecteren = false;
					}
				} while (blijvenSelecteren);
			} catch (InputMismatchException e) {
				System.out.println("Het Geboortejaar moet een geldig cijfer zijn ");
				invoer.nextLine();

				nietfout = false;
			} catch (IllegalArgumentException e) {
				System.out.println("Gebruiker niet gevonden, probeer opnieuw");

				nietfout = false;

			} catch (IllegalStateException e) {
				System.out.println("Deze speler is al geselecteerd probeer opnieuw");

				nietfout = false;

			}
		} while (!nietfout);
		for (int row = 0; row < dc.getSpelers().length; row++)// Cycles through rows
		{

			System.out.printf("%s: %s, %s %s%s: %s", r.getString("Gebruikersnaam"), dc.getSpelers()[row][0],
					r.getString("Aantal").toLowerCase(), r.getString("Speelkans").toLowerCase(),
					currentLocale.getLanguage() == "en" ? "s" : "en", dc.getSpelers()[row][1]);

			System.out.println(); // Makes a new row
		}

		invoer.close();
	}

	private boolean checkalsspeleralsisgeselecteerd(String naam, int geboortejaar) {

		for (int i = 0; i < dc.getSpelers().length; i++) {
			if (dc.getSpelers()[i][0].equals(naam) && dc.getSpelers()[i][2].equals(Integer.toString(geboortejaar))) {
				return false;
			}
		}
		return true;
	}
}