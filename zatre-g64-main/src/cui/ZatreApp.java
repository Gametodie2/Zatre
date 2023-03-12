package cui;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class ZatreApp {

	private static ResourceBundle r;
	private static Locale currentLocale;
	private DomeinController dc;

	public ZatreApp(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {

		Scanner invoer = new Scanner(System.in);
		int keuze;
		int keuzeTaal;

		do {
			System.out.println("--=========-+-========--");
			System.out.println("In what language would you like to play Zatre?");
			System.out.println("Press 1 for English");
			System.out.println("Druk 2 voor Nederlands");
			System.out.println("Drücken Sie 3 für Deutsch");
			System.out.println("Appuyez sur 4 pour le français");
			System.out.println("--=========-+-========--");
			keuzeTaal = invoer.nextInt();
			if (keuzeTaal < 1 || keuzeTaal > 4) {
				System.out.println("Invalid choice!");
			}
		} while (keuzeTaal < 1 || keuzeTaal > 4);

		if (keuzeTaal == 1) {
			currentLocale = new Locale("en");
			r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_en", currentLocale);
		} else if (keuzeTaal == 2) {
			currentLocale = new Locale("nl");
			r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_nl", currentLocale);
		} else if (keuzeTaal == 3) {
			currentLocale = new Locale("de");
			r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_de", currentLocale);
		} else if (keuzeTaal == 4) {
			currentLocale = new Locale("fr");
			r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_fr", currentLocale);
		}

		System.out.println(r.getString("Welkom") + "!\n");
		System.out.println("1. " + r.getString("Registreren"));
		System.out.println("2. " + r.getString("Selecteren") + "\n");

		do {

			System.out.print(r.getString("Nummer") + " > ");
			keuze = invoer.nextInt();
			if (keuze != 1 && keuze != 2) {
				System.out.println("Ongeldige keuze!");
			}

		} while (keuze != 1 && keuze != 2);

		if (keuze == 1) {
			RegistratieApp ra = new RegistratieApp(dc);
			ra.startRegistratie();
		}
		if (keuze == 2) {
			SelecteerSpelerApp ssa = new SelecteerSpelerApp(dc);
			ssa.startSelectie();
		}
		invoer.close();
	}

	public static Locale getCurrentLocale() {
		return currentLocale;
	}

	public static ResourceBundle getResourceBundle() {
		return r;
	}
}