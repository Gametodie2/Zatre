package gui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SelecteerSpelerSchermController {
	@FXML
	private Button fxHomeBtn;
	@FXML
	private Label fxTitel;
	@FXML
	private Label fxUsername;
	@FXML
	private TextField fxUsernameSpScreen;
	@FXML
	private TextField fxDateOfBirthSpScreen;
	@FXML
	private Button fxSelectpBtn;
	@FXML
	private Label fxYearOfBirth;
	@FXML
	private Label fxAmountOfPlayersSelected;
	@FXML
	private Button fxCancelPlayerSelectionBtn;
	@FXML
	private Button fxStartBtn;

	Alert a = new Alert(AlertType.NONE);
	private WelkomSchermController wsc;
	private DomeinController dc;
	private ResourceBundle r;
	private Locale currentLocale;

	int yearOfBirth;

	// --------------------- CONSTRUCTORS ----------------------------

	public SelecteerSpelerSchermController(DomeinController dc, WelkomSchermController wsc) {
		this.dc = dc;
		this.wsc = wsc;
		r = wsc.getResourceBundle();
		currentLocale = wsc.getCurrentLocale();
	}

	// --------------------- GET YEAR OF BIRTH ----------------------------

	/**
	 * Returned geboortejaar
	 * 
	 * @return
	 */
	private int getYearOfBirth() {
		try {
			yearOfBirth = Integer.parseInt(fxDateOfBirthSpScreen.getText());
		} catch (NumberFormatException e) {
			System.out.println("Het Geboortejaar moet een geldig cijfer zijn.");
		}
		return yearOfBirth;
	}

	// --------------------- ON SELECT-PLAYER BUTTON PRESS ------------------------

	// Event Listener on Button[#fxSelectpBtn].onAction
	@FXML
	public void fxSelectpBtnOnAction(ActionEvent event) throws IOException {

		String username = fxUsernameSpScreen.getText();
		int dateOfBirth = getYearOfBirth();
		String labelTexts = fxAmountOfPlayersSelected.getText();
		int amountOfSP = Character.getNumericValue(labelTexts.charAt(labelTexts.length() - 1));

		try {
			if (!isSpelerAlGeselecteerd(username, dateOfBirth)) {
				// set alert type
				a.setAlertType(AlertType.WARNING);

				// set title
				a.setTitle("WARNING - Incorrect input");

				// set header text
				a.setHeaderText("WARNING - Incorrect input");

				// set content text
				a.setContentText(r.getString("warningMessageAlreadySelected"));

				// show the dialog
				a.show();
				fxUsernameSpScreen.selectAll();
				fxDateOfBirthSpScreen.selectAll();
				fxUsernameSpScreen.requestFocus();
			} else {
				dc.selecteerSpeler(username, dateOfBirth);
				changeAmountOfSelectedPlayers();
				displayStartButton();
				fxUsernameSpScreen.clear();
				fxDateOfBirthSpScreen.clear();
				fxUsernameSpScreen.requestFocus();
			}

		} catch (IllegalArgumentException e) {

			if (e.getMessage() == "Je mag maar 4 spelers selecteren") {
				// set alert type
				a.setAlertType(AlertType.WARNING);

				// set title
				a.setTitle("WARNING - Incorrect input");

				// set header text
				a.setHeaderText("WARNING - Incorrect input");

				// set content text
				a.setContentText(r.getString("warningMessageMaxSelected"));

				// show the dialog
				a.show();
			} else if (e.getMessage() == "Gebruiker niet gevonden.") {
				// set alert type
				a.setAlertType(AlertType.WARNING);

				// set title
				a.setTitle("WARNING - Incorrect input");

				// set header text
				a.setHeaderText("WARNING - Incorrect input");

				// set content text
				a.setContentText(r.getString("warningMessageUserNotFound"));

				// show the dialog
				a.show();
			} else {
				System.out.println(e);
			}
		}
	}
	// --------------------- ON HOME BUTTON PRESS ----------------------------

	@FXML
	public void fxHomeBtnOnAction(ActionEvent event) throws IOException {
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WelkomScherm.fxml"));
		WelkomSchermController controller = new WelkomSchermController(dc);
		loader.setController(controller);
		GridPane gridpane = loader.load();
		Scene scene = new Scene(gridpane);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// --------------------- ON CANCEL BUTTON PRESS ----------------------------

	// Event Listener on Button[#fxCancelPlayerSelectionBtn].onAction
	@FXML
	public void fxCancelPlayerSelectionBtnOnAction(ActionEvent event) throws IOException {
		fxUsernameSpScreen.clear();
		fxDateOfBirthSpScreen.clear();
		dc.leegLijst();
		zetLabelOpNull();
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WelkomScherm.fxml"));
		WelkomSchermController controller = new WelkomSchermController(dc);
		loader.setController(controller);
		GridPane gridpane = loader.load();
		Scene scene = new Scene(gridpane);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	// --------------------- CHECK IF PLAYER SELECTED ----------------------------

	/**
	 * Returned false als de meegegeven speler als geselecteerd is
	 * 
	 * @param naam
	 * @param geboortejaar
	 * @return
	 */
	private boolean isSpelerAlGeselecteerd(String naam, int geboortejaar) {
		for (int i = 0; i < dc.getSpelers().length; i++) {
			if (dc.getSpelers()[i][0].equals(naam) && dc.getSpelers()[i][2].equals(Integer.toString(geboortejaar))) {
				return false;
			}
		}
		return true;
	}

	// --------------------- CHANGE LABEL TEXT ----------------------------

	/**
	 * Verandert de label text naar het aantal geselecteerde spelers
	 */
	private void changeAmountOfSelectedPlayers() {
		String labelText = fxAmountOfPlayersSelected.getText();
		int amountOfSelectedPlayers = Character.getNumericValue(labelText.charAt(labelText.length() - 1));

		String substring = labelText.substring(0, labelText.length() - 1);

		if (amountOfSelectedPlayers < 4) {
			amountOfSelectedPlayers++;
			String newLabelText = substring + amountOfSelectedPlayers;
			fxAmountOfPlayersSelected.setText(newLabelText);
		}
	}

	/**
	 * Verandert de label text naar 0
	 */
	private void zetLabelOpNull() {
		String labelText = fxAmountOfPlayersSelected.getText();
		String substring = labelText.substring(0, labelText.length() - 1);
		String newLabelText = substring + 0;
		fxAmountOfPlayersSelected.setText(newLabelText);
	}

	@FXML
	public void initialize() {
		fxUsername.setText(r.getString("Gebruikersnaam") + ":");
		fxTitel.setText(r.getString("Selecteren"));
		fxYearOfBirth.setText(r.getString("Geboortejaar") + ":");
		fxCancelPlayerSelectionBtn.setText(r.getString("Annuleren"));
		fxSelectpBtn.setText(r.getString("Selecteren"));
		fxStartBtn.setVisible(false); // Hide Start Button on StartUp;
		fxUsernameSpScreen.setPromptText("Enter your first name.");
		fxDateOfBirthSpScreen.setPromptText("Enter your year of birth.");
	}

	// --------------------- START BUTTON ----------------------------

	// Display Start Button

	/**
	 * Toont de startknop als er minstens 2 spelers zijn geselecteerd
	 */
	private void displayStartButton() {
		String labelText = fxAmountOfPlayersSelected.getText();
		int amountOfSelectedPlayers = Character.getNumericValue(labelText.charAt(labelText.length() - 1));

		if (amountOfSelectedPlayers >= 2) {
			// Display button if 2 or more players are selected.
			fxStartBtn.setText(r.getString("start"));
			fxStartBtn.setVisible(true);
		}
	}

	// On Start Button Press
	// Event Listener on Button[#fxStartBtn].onAction
	@FXML
	public void faxStartBtnOnAction(ActionEvent event) throws IOException {
		String selectedPlayers = "";

		// set alert type
		a.setAlertType(AlertType.INFORMATION);

		for (int row = 0; row < dc.getSpelers().length; row++)// Cycles through rows
		{

			selectedPlayers += String.format("%s: %s, %s %s%s: %s%n", r.getString("Gebruikersnaam"),
					dc.getSpelers()[row][0], r.getString("Aantal").toLowerCase(),
					r.getString("Speelkans").toLowerCase(), currentLocale.getLanguage() == "en" ? "s" : "en",
					dc.getSpelers()[row][1]);
		}
		// set content text
		a.setContentText(selectedPlayers);

		// show the dialog
		dc.startNieuwSpel();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BordScherm.fxml"));
		BordSchermController controller = new BordSchermController(dc, wsc);
		loader.setController(controller);
		AnchorPane anchPane = loader.load();
		Scene scene = new Scene(anchPane);
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
		a.show();
	}
}
