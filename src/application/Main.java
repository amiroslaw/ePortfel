package application;

	

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainManager;
import view.StartController;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
//		MainManager manager = new MainManager(primaryStage);
//		manager.init();
		MainManager manager = new MainManager(primaryStage);
		manager.init();
//		StartController start = new StartController(primaryStage); 
//		start.showStart();

//		try {
//			ScrollPane root = new ScrollPane(); 
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
