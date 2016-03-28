package view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartController {
	private MainManager manager;
	public void setManager(MainManager manager) {
		this.manager = manager;

	}
	public void setPrimaryStage(Stage primaryStage) {
//		this.primaryStage = primaryStage;
	}
//	private Stage primaryStage; 
//	private BorderPane bpStart; 
	
	@FXML
	void showMain(){
		manager.showMenu();
		manager.showMainView();
	}
//	public StartController(Stage primaryStage) {
//		this.primaryStage = primaryStage;
//	}	
	
//	public void showStart() {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Main.class.getResource("/view/Start.fxml"));
//			bpStart = (BorderPane) loader.load();
//
//			Scene scene = new Scene(bpStart);
//			
//			Stage startStage = new Stage(); 
//			startStage.setTitle("Tworzenie konta");
//			startStage.setScene(scene);
//
////			StartController controller = loader.getController();
////			controller.setPrimaryStage(this.primaryStage);
////			controller.setManager(this);
//
//			startStage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
//public boolean showLoginDialog() {
//	try {
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(Main.class.getResource("view/LoginDialog.fxml"));
//		AnchorPane pane = (AnchorPane) loader.load();
//		Stage dialogStage = new Stage();
//		dialogStage.setTitle("Login to FPTS");
//		dialogStage.initModality(Modality.WINDOW_MODAL);
//		dialogStage.initOwner(primaryStage);
//		Scene scene = new Scene(pane);
//		dialogStage.setScene(scene);
//		LoginDialogController controller = loader.getController();
//		controller.setDialogStage(dialogStage);
//		controller.setManager(this);
//		dialogStage.showAndWait();
//		return controller.success();
//	} catch (IOException e) {
//		e.printStackTrace();
//		return false;
//	}