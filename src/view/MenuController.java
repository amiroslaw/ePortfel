package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Profile;

public class MenuController {
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private MainManager manager;

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

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
	private MenuItem miReport6;

	@FXML
	private MenuItem miReport7;

	@FXML
	private MenuItem miProfilCreate;

	@FXML
	private MenuItem miProfilChengePass;

	@FXML
	private MenuItem miProfilChangeName;

	@FXML
	private MenuItem miProfilDelete;

	@FXML
	private MenuItem miWalletCreate;

	@FXML
	private MenuItem miWalletDelete;

	Profile profile = new Profile();

	void clearData() {
		manager.getStructure().getMap().clear();
		manager.getStructure().getAccList().clear();
		MainManager.getTransactionData().clear();
	}

	@FXML
	void createProfile(ActionEvent event) {
		clearData();
		manager.showRegistration();
	}

	@FXML
	void deleteProfile(ActionEvent event) {
		clearData();
		profile.deleteProfile();
		manager.showLoginView();
	}

	@FXML
	void changeProfilePass(ActionEvent event) {
		manager.showEditProfile(2);
	}

	@FXML
	void changeProfileName(ActionEvent event) {
		manager.showEditProfile(1);
	}

	@FXML
	void createWallet(ActionEvent event) {
		clearData();
		manager.showEditProfile(4);
	}

	@FXML
	void deleteWallet(ActionEvent event) {
		clearData();

		profile.deleteWallet(1);
		manager.showLoginView();
	}

	@FXML
	void showReport(ActionEvent event) {
		String sourceName = "";
		Object source = event.getSource();
		
		if (source instanceof MenuItem) {
			MenuItem itemSecected = (MenuItem) source;
			sourceName = itemSecected.getId();
		}
		switch (sourceName) {
		case "miReport1":
			manager.showReport("Arkusz bilansowy", 1);
			break;
		case "miReport2":
			manager.showReport("Bilans próbny", 2);
			break;
		case "miReport3":
			manager.showReport("Wyciąg z przychodów i wydatków", 3);
			break;
		case "miReport4":
			manager.showReport("Wykres aktywów", 4);
			break;
		case "miReport5":
			manager.showReport("Wykres pasywów", 5);
			break;
		case "miReport6":
			manager.showReport("Wykres przychodów", 6);
			break;
		case "miReport7":
			manager.showReport("Wykres wydatków", 7);
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
	void close() {
		primaryStage.close();
	}

	@FXML
	public void showAbout() {
		System.out.println("metoda about");
		manager.showAboutDialog();
	}

}
