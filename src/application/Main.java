package application;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	private static Main instance;
	public static int numMembers = 7;
	static Thread progressBarThread;

	@Override
	public void start(Stage primaryStage) {
		instance = this;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("OPIGTools.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			String css = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			      
	        InputStream iconStream = getClass().getResourceAsStream("hat.png");
            Image icon = new Image(iconStream);
            primaryStage.getIcons().add(icon);
	        
			// Calculate the background image URL dynamically
	        String backgroundImageURL = getClass().getResource("woodBG2.jpg").toExternalForm();
	        root.setStyle("-fx-background-image: url('" + backgroundImageURL + "');" +
	                      "-fx-background-repeat: repeat;" +
	                      "-fx-background-size: cover;");
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("One Piece Idle Game Tools");
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Main getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
