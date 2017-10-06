package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ScrollPaneDemo extends Application {
	private String scrollPaneTheme = getClass().getResource("application.css").toExternalForm();

	@Override
	public void start(Stage primaryStage) {

		ScrollPane scrollPane = new ScrollPane();

		Button buttonSet = new Button("Set Style");
		Button buttonRemove = new Button("Remove Style");

		HBox box = new HBox();
		box.setSpacing(10);
		box.getChildren().addAll(buttonSet, buttonRemove);
		box.setAlignment(Pos.CENTER);

		scrollPane.setContent(box);
		scrollPane.setStyle(".scroll-bar:vertical .thumb {" + " -fx-background-color:black;"
				+ "-fx-background-radius: 18.0 18.0 18.0 18.0;" + "}");
		Scene scene = new Scene(scrollPane, 300, 200);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		buttonSet.setOnAction(e -> {
			scrollPane.setStyle("-color:red;");
		});
		buttonRemove.setOnAction(e -> {
			scene.getStylesheets().remove(scrollPaneTheme);
		});
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		primaryStage.setTitle("ScrollPane Demo");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}