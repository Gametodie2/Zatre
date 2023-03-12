package cui;

import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController; //test

public class RegistratieApp {
	private DomeinController dc;

	public RegistratieApp(DomeinController dc) {
		this.dc = dc;
	}

	public void startRegistratie() {
		Scanner invoer = new Scanner(System.in);
		String naam = "";
		int geboortejaar = 0;
		boolean fout = true;
		ResourceBundle r = ZatreApp.getResourceBundle();
		Locale currentLocale = ZatreApp.getCurrentLocale();

		System.out.println(r.getString("RegistratieStart"));

		do {
			try {
				System.out.println(r.getString("VraagNaam") + "?");
				naam = invoer.next();
				System.out.println();
				if (naam == null || naam.isEmpty() || naam.length() < 5)
					throw new IllegalArgumentException("De gebruikersnaam moet langer dan 4 karakters zijn.");
				fout = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (fout);
		fout = true;
		do {
			try {
				System.out.println(r.getString("VraagGeboortejaar") + "?");
				geboortejaar = invoer.nextInt();
				if (LocalDate.now().getYear() - geboortejaar < 6)
					throw new IllegalArgumentException("Speler moet 6 jaar oud zijn of worden in dit jaar.");
				fout = false;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (fout);

		dc.registreer(naam, geboortejaar);

		System.out.printf("%s %s 5 %s", naam, r.getString("Geregistreerd"),
				currentLocale.getLanguage() == "en" ? r.getString("Speelkans").toLowerCase() + "s"
						: r.getString("Speelkans").toLowerCase() + "en");

		invoer.close();
	}
}
