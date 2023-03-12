package gui;

import java.io.IOException;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistratieSchermController extends GridPane {

	// -------------------------------------------------

	@FXML
	private Label fxRegistreerLbl;
	@FXML
	private Label fxGebruikersnaamLbl;
	@FXML
	private TextField fxUsername;
	@FXML
	private TextField fxDateOfBirth;
	@FXML
	private Button fxRegisterBtn;
	@FXML
	private Label fxGeboortejaarLbl;
	@FXML
	private Button fxHomeBtn;

	private DomeinController dc;
	private ResourceBundle r;

	Alert a = new Alert(AlertType.NONE);

	// --------------------- CONSTRUCTORS ----------------------------

	public RegistratieSchermController(DomeinController dc, WelkomSchermController wsc) {
		this.dc = dc;
		r = wsc.getResourceBundle();
	}

	// --------------------- ON HOME BUTTON PRESS ----------------------------

	// Event Listener on Button[#fxHomeBtn].onAction
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

	// --------------------- ON REGISTER BUTTON PRESS ----------------------------

	// Event Listener on Button[#fxRegisterBtn].onAction
	@FXML
	public void fxRegisterBtnOnActionAi(ActionEvent event) throws IOException {
		try {
			String username = fxUsername.getText();
			int dateOfBirth = Integer.parseInt(fxDateOfBirth.getText());
			dc.registreer(username, dateOfBirth);

			// set alert type
			a.setAlertType(AlertType.INFORMATION);

			// set content text
			a.setContentText(
					String.format(r.getString("geregistreerd1") + " %s%n" + r.getString("metKansen"), username));

			// show the dialog
			a.show();
			fxUsername.clear();
			fxDateOfBirth.clear();

		} catch (IllegalArgumentException e) {
			if (e.getMessage() == "De gebruikersnaam moet langer dan 4 karakters zijn.") {
				// set alert type
				a.setAlertType(AlertType.WARNING);

				// set title
				a.setTitle("WARNING - Incorrect input");

				// set header text
				a.setHeaderText("WARNING - Incorrect input");

				// set content text
				a.setContentText(r.getString("warningMessageLongerUsername"));

				// show the dialog
				a.show();

				// ------ STYLE ALERT ---------

				DialogPane dialogPane = a.getDialogPane();

				fxUsername.selectAll();
				fxUsername.requestFocus();
			} else if (e.getMessage() == "Speler moet 6 jaar oud zijn of worden in dit jaar.") {
				// set alert type
				a.setAlertType(AlertType.WARNING);

				// set title
				a.setTitle("WARNING - Incorrect input");

				// set header text
				a.setHeaderText("WARNING - Incorrect input");

				// set content text
				a.setContentText(r.getString("warningMessageLowAge"));

				// show the dialog
				a.show();
				fxDateOfBirth.selectAll();
				fxDateOfBirth.requestFocus();
			} else {

				a.setAlertType(AlertType.WARNING);

				// set content text
				a.setContentText(r.getString("warningMessageAlreadyExists"));

				// show the dialog
				a.show();

				fxUsername.clear();
				fxDateOfBirth.clear();
			}
		}
	}

	@FXML
	public void initialize() {
		fxGeboortejaarLbl.setText(r.getString("Geboortejaar") + ":");
		fxGebruikersnaamLbl.setText(r.getString("Gebruikersnaam") + ":");
		fxRegistreerLbl.setText(r.getString("Registreer"));
		fxRegisterBtn.setText(r.getString("Registreer"));
		fxUsername.setPromptText("Enter your first name.");
		fxDateOfBirth.setPromptText("Enter your year of birth.");
	}
}