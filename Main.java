package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Packing.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Packing List Generation (PHC/UPHC)");
			primaryStage.setResizable(false);

			primaryStage.setScene(scene);
			Image I = new Image(getClass().getResourceAsStream("laboratory-test.png"));
			primaryStage.getIcons().add(I);
			primaryStage.show();

			primaryStage.setOnCloseRequest(event -> {
				javafx.application.Platform.exit();
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
