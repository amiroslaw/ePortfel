package view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuController {
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private MainManager manager;

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

	private MainController mainCtr = new MainController();
	@FXML
	private MenuItem miAbout;

	@FXML
	private MenuItem miSave;

	@FXML
	private MenuItem miClose;

	@FXML
	void save() {
		manager.getStructure().saveTransactions();
		manager.getStructure().saveAccount();
	}

	@FXML
	public void showAbout() {
		System.out.println("metoda about");
		manager.showAboutDialog();
	}

}
