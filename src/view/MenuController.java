package view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuController {
	private Stage primaryStage;
	private MainManager manager;

	@FXML
	private MenuItem about; 
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

	@FXML
	public void showAbout(){
		System.out.println("metoda about");
		manager.showAboutDialog();
	} 
}
