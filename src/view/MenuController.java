package view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Account;

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
	private MenuItem miReport1;
	
	@FXML
	private MenuItem miReport2;
	
	@FXML
	private MenuItem miReport3;
	
	@FXML
	private MenuItem miReport4;
	
	@FXML
	private MenuItem miReport5;
	

	@FXML
	private MenuItem miTest;
	@FXML
	void showReport(ActionEvent event){
		String sourceName=""; 
		Object source = event.getSource();
		if (source instanceof MenuItem) { 
		MenuItem itemSecected =(MenuItem) source; 
		sourceName= itemSecected.getId();
		System.out.println("menu klick "+ itemSecected.getId());
		}
		switch (sourceName) {
		case "miReport1":
			manager.showReport("Arkusz bilansowy", 1);
			break;
		case "miReport2":
			manager.showReport("Bilans próbny", 2);
			break;
		case "miReport3":
			manager.showReport("Arkusz bilansowy", 1);
			break;
		case "miReport4":
			manager.showReport("Arkusz bilansowy", 1);
			break;
		case "miReport5":
			manager.showReport("Arkusz bilansowy", 1);
			break;
		default:
			manager.showReport("error", 1);
			break;
		}
		
	}
	
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
	@FXML
	void test() {
		ArrayList<Account> testList = new ArrayList<Account>(manager.getStructure().getAccList());
		for (int i = 0; i < testList.size(); i++) {
			System.out.println(testList.get(i).getName());
		}
	}
		
}
