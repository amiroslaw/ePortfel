package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import application.Main;

public class MainManager {
	private BorderPane menuLayout;
	private final Stage primaryStage; 
	
	public void init (){
		System.err.println("FXML resource: " + Main.class.getResource("/view/MenuView.fxml"));
		System.err.println("FXML resource2: " + getClass().getResource("/view/MenuView.fxml"));
		showMenu();
		showMainView();
	}
	public MainManager(Stage primaryStage) {
		this.primaryStage= primaryStage; 
		
	}
	public void showMenu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/MenuView.fxml"));
			menuLayout = (BorderPane) loader.load();

			Scene scene = new Scene(menuLayout);
			primaryStage.setScene(scene);

			MenuController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(application.Main.class.getResource("/view/MainView.fxml"));
			ScrollPane mainView = (ScrollPane) loader.load(); 
			menuLayout.setCenter(mainView);

			MainController controller = loader.getController();
			controller.setManager(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//    public boolean setScreen(final String name) {       
//        Stage stage; 
//        Parent root;
//        if(event.getSource()==btn1){
//           //get reference to the button's stage         
//           stage=(Stage) btn1.getScene().getWindow();
//           //load up OTHER FXML document
//     root = FXMLLoader.load(getClass().getResource("FXML2.fxml"));
//         }
//        else{
//          stage=(Stage) btn2.getScene().getWindow();
//     root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//         }
//        //create a new scene with root and set the stage
//         Scene scene = new Scene(root);
//         stage.setScene(scene);
//         stage.show();
//       
//                        getChildren().remove(0);                    //remove the displayed screen
//                        getChildren().add(0, screens.get(name));     //add the screen
//        
//        } 

}
