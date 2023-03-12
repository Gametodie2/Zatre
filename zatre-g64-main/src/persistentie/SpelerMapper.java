package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Speler;

public class SpelerMapper {

	// SQLstatement voor het toevoegen van een speler
	private static final String INSERT_SPELER = "INSERT INTO ID372290_G64.Speler (gebruikersnaam, geboortejaar, speelkansen)"
			+ "VALUES  (?, ?, ?)";
	// SQLstatement voor de speelkansen van een speler te vernieuwen
	private static final String UPDATE_SPELER = "UPDATE ID372290_G64.Speler SET speelkansen = ? WHERE gebruikersnaam = ? and geboortejaar = ?";

	// SQLstatement voor het selecteren van een speler met de geboortejaar en
	// gebruikersnaam
	private static final String SELECT_SPELER = "SELECT * FROM ID372290_G64.Speler WHERE gebruikersnaam = ? and geboortejaar = ?";

	/**
	 * Toevoegen van speler in database
	 * 
	 * @param speler
	 */
	public void voegToe(Speler speler) {

		try (Connection conn = DriverManager.getConnection(DatabaseConnectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			// Parameters instellen
			query.setString(1, speler.getGebruikersnaam());
			query.setInt(2, speler.getGeboortejaar());
			query.setInt(3, speler.getSpeelkansen());

			// Uitvoeren
			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * We kijken als de gebruiker met dezelfde gebruikersnaam en dezelfde
	 * geboortedatum als reeds in de database bestaat. Het returnt de speler en als
	 * die gevonden is dan returnt hij de speler als die niet gevonden is returnt
	 * hij null
	 * 
	 * @param gbnaam
	 * @param gbdatum
	 * @return
	 */
	public Speler bekijkAlsCombinatieBestaat(String gbnaam, int gbdatum) {
		Speler speler = null;

		try (Connection conn = DriverManager.getConnection(DatabaseConnectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELER)) {
			query.setString(1, gbnaam);
			query.setInt(2, gbdatum);
			// Parameters instellen
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					String gebruikersnaam = rs.getString("gebruikersnaam");
					int geboortedatum = rs.getInt("geboortejaar");
					// een nieuwe speler maken
					speler = new Speler(gebruikersnaam, geboortedatum);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		// returnt null of de speler die gevonden is
		return speler;
	}

	/**
	 * In deze methode spelecteren we een speler met de parameters gebruikersnaam en
	 * geboortejaar. We returnen een speler object met de speelkansen erbij
	 * 
	 * @param gebruikersnaam
	 * @param geboortejaar
	 * @return
	 * 
	 */
	public Speler selecteerSpeler(String gebruikersnaam, int geboortejaar) {
		Speler speler = null;

		try (Connection conn = DriverManager.getConnection(DatabaseConnectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELER)) {
			// parameters instellen
			query.setString(1, gebruikersnaam);
			query.setInt(2, geboortejaar);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					// nieuw speler object maken
					String gebruikersnaamspeler = rs.getString("gebruikersnaam");
					int geboortedatum = rs.getInt("geboortejaar");
					int aantalSpeelkansen = rs.getInt("speelkansen");
					speler = new Speler(gebruikersnaamspeler, geboortedatum, aantalSpeelkansen);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return speler;
	}

	/**
	 * Verlagen van de speelkansen van de meegeven speler.
	 * 
	 * @param speler
	 */
	public void verminderSpeelkansen(Speler speler) {
		try (Connection conn = DriverManager.getConnection(DatabaseConnectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(UPDATE_SPELER)) {
			// instellen van parameters
			query.setInt(1, speler.getSpeelkansen());
			query.setString(2, speler.getGebruikersnaam());
			query.setInt(3, speler.getGeboortejaar());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

}
