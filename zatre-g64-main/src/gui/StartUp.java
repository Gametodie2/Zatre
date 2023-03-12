package gui;

import cui.ZatreApp;
import domein.DomeinController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartUp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		DomeinController dc = new DomeinController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WelkomScherm.fxml"));
		WelkomSchermController controller = new WelkomSchermController(dc);
		loader.setController(controller);
		GridPane gridpane = loader.load();
		Scene scene = new Scene(gridpane);
		stage.setScene(scene);
		stage.getIcons().add(new Image(StartUp.class.getResourceAsStream("/images/icon.png")));
		stage.setMaximized(true);
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);

		DomeinController dc = new DomeinController();
		ZatreApp za = new ZatreApp(dc);
		za.start();
	}
}
