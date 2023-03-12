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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WelkomSchermController {
	@FXML
	private Label fxTitelLbl;
	@FXML
	private Button fxRegisterBtnHomeScreen;
	@FXML
	private Button fxSelectPlayerBtn;
	@FXML
	private Button fxEnglishBtn;
	@FXML
	private Button fxDutchBtn;
	@FXML
	private MenuButton fxTalenKeuzeMenu;

	// -------------------------------------------------

	private Locale currentLocale = new Locale("en");
	private ResourceBundle r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_en", currentLocale);
	private DomeinController dc;

	// --------------------- CONSTRUCTORS ----------------------------

	public WelkomSchermController(DomeinController dc) {
		this.dc = dc;
	}

	// --------------------- ON REGISTER BUTTON PRESS ----------------------------

	// Event Listener on Button[#fxRegisterBtnHomeScreen].onAction
	@FXML
	public void RegisterBtnHsOnAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistratieScherm.fxml"));

		RegistratieSchermController controller = new RegistratieSchermController(dc, this);
		loader.setController(controller);
		GridPane gridpane = loader.load();
		Scene scene = new Scene(gridpane);
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	// ----------------- ON PLAYER-SELECT BUTTON PRESS ---------------------------

	// Event Listener on Button[#fxSelectPlayerBtn].onAction
	@FXML
	public void SelectPlayerBtnHsOnAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelecteerSpelerScherm.fxml"));
		SelecteerSpelerSchermController controller = new SelecteerSpelerSchermController(dc, this);
		loader.setController(controller);
		GridPane gridpane = loader.load();
		Scene scene = new Scene(gridpane);
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	// --------------------- ON ENGLISH BUTTON PRESS ----------------------------

	// Event Listener on Button[#fxEnglishBtn].onAction
	@FXML
	public void EnglishBtnOnAction(ActionEvent event) {
		currentLocale = new Locale("en");
		r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_en", currentLocale);
		fxRegisterBtnHomeScreen.setText(r.getString("Registreren"));
		fxSelectPlayerBtn.setText(r.getString("Selecteren"));
		fxTitelLbl.setText(r.getString("Welkom"));

	}

	// --------------------- ON DUTCH BUTTON PRESS ----------------------------

	// Event Listener on Button[#fxDutchBtn].onAction
	@FXML
	public void DutchBtnOnAction(ActionEvent event) {
		currentLocale = new Locale("nl");
		r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_nl", currentLocale);
		fxRegisterBtnHomeScreen.setText(r.getString("Registreren"));
		fxSelectPlayerBtn.setText(r.getString("Selecteren"));
		fxTitelLbl.setText(r.getString("Welkom"));
	}

	@FXML
	private void GermanBtnOnAction(ActionEvent event) {
		currentLocale = new Locale("de");
		r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_de", currentLocale);
		fxRegisterBtnHomeScreen.setText(r.getString("Registreren"));
		fxSelectPlayerBtn.setText(r.getString("Selecteren"));
		fxTitelLbl.setText(r.getString("Welkom"));
	}

	@FXML
	private void FrenchBtnOnAction(ActionEvent event) {
		currentLocale = new Locale("fr");
		r = ResourceBundle.getBundle("resourcebundles/MessagesBundle_fr", currentLocale);
		fxRegisterBtnHomeScreen.setText(r.getString("Registreren"));
		fxSelectPlayerBtn.setText(r.getString("Selecteren"));
		fxTitelLbl.setText(r.getString("Welkom"));
	}

	// --------------------- LANGUAGES ----------------------------

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public ResourceBundle getResourceBundle() {
		return r;
	}

	@FXML
	public void initialize() {
		fxRegisterBtnHomeScreen.setText(r.getString("Registreren"));
		fxSelectPlayerBtn.setText(r.getString("Selecteren"));
		fxTitelLbl.setText(r.getString("Welkom"));
	}
}
