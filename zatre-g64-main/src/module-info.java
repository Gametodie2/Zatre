module zatre {
	requires java.sql;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
	requires javafx.base;
	requires javafx.media;
	requires java.desktop;

	opens gui to javafx.fxml, javafx.graphics;
}