package gui;

import java.io.IOException;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class BordSchermController {
	@FXML
	private AnchorPane biggrid;
	@FXML
	private GridPane fxGridPane;
	@FXML
	private Button fxHomeBtn;
	@FXML
	private ImageView ImagePlayer1, ImagePlayer2, ImagePlayer3, ImagePlayer4;
	@FXML
	private Label lblSpeler1, lblSpeler2, lblSpeler3, lblSpeler4, lblronde;
	@FXML
	private ImageView fxImageSteen;
	@FXML
	private Button fxBtnSteen1, fxBtnSteen2, fxBtnSteen3, fxButtonVerwijderen;
	@FXML
	private ImageView fxImgSteen1, fxImgSteen2, fxImgSteen3;
	@FXML
	private HBox fxHBoxStenen;

	private DomeinController dc;
	private ResourceBundle r;
	Alert a = new Alert(AlertType.NONE);
	private int steenWaarde1, steenWaarde2, steenWaarde3;
	int width = 30;
	private int waarde;
	private Button buttonSelectSteen;
	private int rondeCounter = 0;
	private int geplaatsteStenenInRonde = 0;
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	private int x3 = 0;
	private int y3 = 0;
	private Button btn1;
	private Button btn2;
	private Button btn3;

	// --------------------- CONSTRUCTORS ----------------------------

	public BordSchermController(DomeinController dc, WelkomSchermController wsc) {
		this.dc = dc;
		r = wsc.getResourceBundle();

		toonEersteSpeler();
	}

	// --------------------- STEENTJES ----------------------------

	/**
	 * Verandert de getoonde waarde van de steentjes die de speler krijgt
	 */
	private void waardeStenenVeranderen() {
		fxBtnSteen1.setText(Integer.toString(steenWaarde1) == "0" ? "empty" : Integer.toString(steenWaarde1));
		fxBtnSteen2.setText(Integer.toString(steenWaarde2) == "0" ? "empty" : Integer.toString(steenWaarde2));
		fxBtnSteen3.setText(Integer.toString(steenWaarde3) == "0" ? "empty" : Integer.toString(steenWaarde3));
	}

	// --------------------- MAAK BORD ----------------------------

	/**
	 * Maakt het bord van buttons aan en geeft de buttons een onActionEvent en een
	 * plaats op de gridpane
	 */
	private void maakBord() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Button btn = new Button("");
				btn.getStyleClass().add("blackBtn");
				fxGridPane.add(btn, i, j, 1, 1);
			}
		}
		for (int i = 1; i <= 9; i++) {
			if (i % 4 == 0) {
				for (int j = 0; j < 3; j++) {
					if (i + j == 6 || i + j == 8) {
						Button btn = new Button("");
						btn.getStyleClass().add("grayBtn");
						fxGridPane.add(btn, i + j, 0, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
						btn = new Button("");
						btn.getStyleClass().add("grayBtn");
						fxGridPane.add(btn, i + j, 14, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
						btn = new Button("");
						btn.getStyleClass().add("grayBtn");
						fxGridPane.add(btn, 0, i + j, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
						btn = new Button("");
						btn.getStyleClass().add("grayBtn");
						fxGridPane.add(btn, 14, i + j, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
					} else {
						Button btn = new Button("");
						btn.getStyleClass().add("whiteBtn");
						fxGridPane.add(btn, i + j, 0, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
						btn = new Button("");
						btn.getStyleClass().add("whiteBtn");
						fxGridPane.add(btn, i + j, 14, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
						btn = new Button("");
						btn.getStyleClass().add("whiteBtn");
						fxGridPane.add(btn, 0, i + j, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
						btn = new Button("");
						btn.getStyleClass().add("whiteBtn");
						fxGridPane.add(btn, 14, i + j, 1, 1);
						btn.setOnAction(event -> plaatsSteen(event));
					}
				}
			}
		}
		for (int j = 1; j < 14; j++) {
			for (int i = 1; i < 14; i++) {
				if (i == j || i + j == 14) {
					Button btn = new Button("");
					btn.getStyleClass().add("grayBtn");
					fxGridPane.add(btn, i, j, 1, 1);
					btn.setOnAction(event -> plaatsSteen(event));
				} else {
					Button btn = new Button("");
					btn.getStyleClass().add("whiteBtn");
					fxGridPane.add(btn, i, j, 1, 1);
					btn.setOnAction(event -> plaatsSteen(event));
				}
			}
		}
	}

	// --------------------- HOME BUTTON ----------------------------

	// Event Listener on Button[#fxHomeBtn].onAction
	@FXML
	public void fxHomeBtnOnAction(ActionEvent event) throws IOException {
		// set alert type
		a.setAlertType(AlertType.CONFIRMATION);
		// set content text
		a.setContentText(r.getString("Stoppen"));
		Optional<ButtonType> result = a.showAndWait(); // Wait for answer

		// if answer ok, return to homescreen
		if (result.get() == ButtonType.OK) {
			dc.leegLijst();
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
	}

	// --------------------- SPELER ----------------------------

	/**
	 * Toont de eerste speler in een alert
	 */
	public void toonEersteSpeler() {
		String playerOne = dc.getSpelers()[0][0];
		// set alert type
		a.setAlertType(AlertType.INFORMATION);

		// set content text
		a.setContentText(String.format("%s %s", playerOne, r.getString("eersteSpeler")));

		// show the dialog
		a.show();
	}

	// --------------------- PLAYERNAMES ----------------------------

	/**
	 * Toont de geselecteerde spelers op de GUI
	 */
	private void toonNamen() {
		if (dc.getSpelers().length == 2) {
			lblSpeler1.setText(dc.getSpelers()[0][0]);
			lblSpeler2.setText(dc.getSpelers()[1][0]);
			ImagePlayer3.setVisible(false);
			ImagePlayer4.setVisible(false);

		} else if (dc.getSpelers().length == 3) {
			lblSpeler1.setText(dc.getSpelers()[0][0]);
			lblSpeler2.setText(dc.getSpelers()[1][0]);
			lblSpeler3.setText(dc.getSpelers()[2][0]);
			ImagePlayer4.setVisible(false);
		} else if (dc.getSpelers().length == 4) {
			lblSpeler1.setText(dc.getSpelers()[0][0]);
			lblSpeler2.setText(dc.getSpelers()[1][0]);
			lblSpeler3.setText(dc.getSpelers()[2][0]);
			lblSpeler4.setText(dc.getSpelers()[3][0]);
		}
	}

	// --------------------- PLAATS STEEN ---------------------------

	/**
	 * @param event Plaatst een steen op het spelbord als de geplaatste steen aan
	 *              alle spelregels voldoet
	 */
	public void plaatsSteen(ActionEvent event) {
		if (waarde != 0) {
			Button source = (Button) event.getSource();
			int column = GridPane.getColumnIndex(source);
			int row = GridPane.getRowIndex(source);
			boolean fout = true;
			do {
				try {
					fout = true;
					dc.plaatsSteen(column, row, waarde);
					source.setText(Integer.toString(waarde));
					source.setDisable(true);
					buttonSelectSteen.setText("");
					geplaatsteStenenInRonde++;
					if (geplaatsteStenenInRonde == 1)
						btn1 = source;
					else if (geplaatsteStenenInRonde == 2)
						btn2 = source;
					else
						btn3 = source;

					waarde = 0;
					isEindeSpel();

					if (rondeCounter == 1) {
						if (geplaatsteStenenInRonde == 3) {
							geefSteenWaarde();
							dc.bepaalBeurt();
							veranderSpeler();
							geplaatsteStenenInRonde = 0;
						}
					} else {
						if (geplaatsteStenenInRonde == 2) {
							geefSteenWaarde();
							dc.bepaalBeurt();
							veranderSpeler();
							geplaatsteStenenInRonde = 0;
						}
					}
				} catch (IllegalArgumentException e) {
					if (e.getMessage().equals("De steen moet geplaatst worden naast een andere steen.")) {
						resetStenen();
						DialogPane dialogPane = a.getDialogPane();
						a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
						// set alert type
						a.setAlertType(AlertType.INFORMATION);

						// set content text
						a.setContentText(r.getString("warningMessageWrongPlacement"));

						// show the dialog
						a.show();
					} else if (e.getMessage().equals("De eerste zet moet in het midden gelegd worden")) {
						resetStenen();
						DialogPane dialogPane = a.getDialogPane();
						a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
						// set alert type
						a.setAlertType(AlertType.INFORMATION);

						// set content text
						a.setContentText(r.getString("warningMessageFirstStonePlaced"));

						// show the dialog
						a.show();
					} else if (e.getMessage().equals("Je steen moet naast een steen van een andere ronde liggen.")) {
						resetStenen();
						DialogPane dialogPane = a.getDialogPane();
						a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
						// set alert type
						a.setAlertType(AlertType.INFORMATION);

						// set content text
						a.setContentText(r.getString("warningMessageWrongPlacement2"));

						// show the dialog
						a.show();
					} else if (e.getMessage().equals(
							"Als je jouw steen op een grijs vakje wil plaatsten, moet de score minstens 10 zijn.")) {
						resetStenen();
						DialogPane dialogPane = a.getDialogPane();
						a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
						// set alert type
						a.setAlertType(AlertType.INFORMATION);

						// set content text
						a.setContentText(r.getString("warningMessageGrijsVak"));

						// show the dialog
						a.show();
					} else if (e.getMessage().equals("De score mag niet groter zijn dan 12 op een lijn/kolom.")) {
						resetStenen();
						DialogPane dialogPane = a.getDialogPane();
						a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
						// set alert type
						a.setAlertType(AlertType.INFORMATION);

						// set content text
						a.setContentText(r.getString("warningMessageScoreTeHoog"));

						// show the dialog
						a.show();
					}
				}
				fout = false;
			} while (fout);

		}
	}

	/**
	 * Reset de geplaatste stenen van in die ronde
	 */
	private void resetStenen() {
		dc.resetGeplaatsteStenen();
		if (geplaatsteStenenInRonde == 1) {
			btn1.setText("");
			btn1.setDisable(false);
			fxBtnSteen1.setText(Integer.toString(steenWaarde1));
			fxBtnSteen2.setText(Integer.toString(steenWaarde2));
			fxBtnSteen3.setText(Integer.toString(steenWaarde3));
			geplaatsteStenenInRonde = 0;
			waarde = 0;
		}
		if (geplaatsteStenenInRonde == 2) {
			btn1.setText("");
			btn1.setDisable(false);
			btn2.setText("");
			btn2.setDisable(false);
			fxBtnSteen1.setText(Integer.toString(steenWaarde1));
			fxBtnSteen2.setText(Integer.toString(steenWaarde2));
			fxBtnSteen3.setText(Integer.toString(steenWaarde3));
			geplaatsteStenenInRonde = 0;
			waarde = 0;
		}
	}

	/**
	 * Checkt als de pot stenen leeg is en laat de spelers dit weten met een alert
	 */
	private void isEindeSpel() {
		if (dc.isEindeSpel()) {
			// set alert type
			a.setAlertType(AlertType.INFORMATION);
			// set content text
			DialogPane dialogPane = a.getDialogPane(); // get alert

			a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			a.setContentText(r.getString("EindeSpel"));
			// show the dialog
			a.show();

			fxBtnSteen1.setDisable(true);
			fxBtnSteen2.setDisable(true);
			fxBtnSteen3.setDisable(true);
			waarde = 0;
		}
	}

	/**
	 * Toont welke speler aan de beurt is met de images
	 */
	private void veranderSpeler() {
		resetOpacity();
		switch (dc.geefSpelerAanBeurt()) {
		case 0 -> ImagePlayer1.setOpacity(1);
		case 1 -> ImagePlayer2.setOpacity(1);
		case 2 -> ImagePlayer3.setOpacity(1);
		case 3 -> ImagePlayer4.setOpacity(1);
		}
	}

	/**
	 * Reset de opacity van de speler images
	 */
	private void resetOpacity() {
		ImagePlayer1.setOpacity(0.5);
		ImagePlayer2.setOpacity(0.5);
		ImagePlayer3.setOpacity(0.5);
		ImagePlayer4.setOpacity(0.5);

	}

	/** Steekt een steen terug in de pot */
	public void verwijderSteen(ActionEvent event) {
		if (!buttonSelectSteen.getText().isEmpty() && rondeCounter != 1) {
			DialogPane dialogPane = a.getDialogPane();
			a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			// set alert type
			a.setAlertType(AlertType.CONFIRMATION);
			// set content text
			a.setContentText(r.getString("warningMessageGeenPlaatsMeer"));
			Optional<ButtonType> result = a.showAndWait(); // Wait for answer
			if (result.get() == ButtonType.OK) {
				dc.voegSteenToe(Integer.parseInt(buttonSelectSteen.getText()));
				buttonSelectSteen.setText("");
				if (buttonSelectSteen.getId().equals("fxBtnSteen1")) {
					steenWaarde1 = 0;
					fxBtnSteen1.setText("");
					geplaatsteStenenInRonde++;
				} else if (buttonSelectSteen.getId().equals("fxBtnSteen2")) {
					steenWaarde2 = 0;
					fxBtnSteen2.setText("");
					geplaatsteStenenInRonde++;
				} else if (buttonSelectSteen.getId().equals("fxBtnSteen3")) {
					steenWaarde3 = 0;
					fxBtnSteen3.setText("");
					geplaatsteStenenInRonde++;
				}
				if (rondeCounter == 1) {
					if (geplaatsteStenenInRonde == 3) {
						geefSteenWaarde();
						dc.bepaalBeurt();
						veranderSpeler();
						geplaatsteStenenInRonde = 0;
					}
				} else {
					if (geplaatsteStenenInRonde == 2) {
						geefSteenWaarde();
						dc.bepaalBeurt();
						veranderSpeler();
						geplaatsteStenenInRonde = 0;
					}
				}
			}
		}
	}

	/** Selecteert de waarde van de geselecteerde steen */
	public void selecteerSteen(ActionEvent event) {
		buttonSelectSteen = (Button) event.getSource();
		if (buttonSelectSteen.getId().equals("fxBtnSteen1")) {
			waarde = steenWaarde1;
		} else if (buttonSelectSteen.getId().equals("fxBtnSteen2")) {
			waarde = steenWaarde2;
		} else if (buttonSelectSteen.getId().equals("fxBtnSteen3")) {
			waarde = steenWaarde3;
		}
	}

	// --------------------- INITIALIZE ----------------------------

	@FXML
	public void initialize() {
		geefSteenWaarde();
		maakBord();
		toonNamen();
		dc.bepaalBeurt();
		veranderSpeler();
		fxButtonVerwijderen.setText(r.getString("pot"));
	}

	/** Geeft nieuwe stenen aan de speler als er een nieuwe ronde begint */
	private void geefSteenWaarde() {
		rondeCounter++;
		lblronde.setText(String.format("%s: %s", r.getString("ronde"), Integer.toString(rondeCounter)));
		if (rondeCounter == 1) {
			steenWaarde1 = dc.getSteen();
			steenWaarde2 = dc.getSteen();
			steenWaarde3 = dc.getSteen();
			waardeStenenVeranderen();
		} else {
			steenWaarde1 = dc.getSteen();
			steenWaarde2 = dc.getSteen();
			fxBtnSteen3.setVisible(false);
			fxBtnSteen3.setDisable(true);
			steenWaarde3 = 0;
			waardeStenenVeranderen();
		}
	}
}
