package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Profile;

public class LoginController {
	private MainManager manager = new MainManager(null);

	private Stage primaryStage;

	public void setManager(MainManager manager) {
		this.manager = manager;

	}

	public void setPrimaryStage(Stage primaryStage) {
		 this.primaryStage = primaryStage;
	}

	@FXML
	private TextField txtfProfileName;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button btnLogIn;

	@FXML
	private Button btnAccept;

	@FXML
	private Label lblSelectedDirectory;

	@FXML
	private ComboBox<String> comboBoxWallet;

	@FXML
	private Button btnCreateUser;

	@FXML
	private Label lblMessage;

	@FXML
	private Label lblPickWallet;

	Profile profile = new Profile();

	@FXML
	void createUser(ActionEvent event) {
		manager.showRegistration();
	}

	@FXML
	void logIn(ActionEvent event) throws SQLException {
		profile.setProfileName(txtfProfileName.getText());
		profile.setPassword(passwordField.getText());
		if (profile.isLogin()) {
			lblMessage.setText("Wybierz swój portfel");
			profile.getWalletsDB();
			
			passwordField.setDisable(true);
			txtfProfileName.setDisable(true);
			btnLogIn.setDisable(true);
			btnCreateUser.setDisable(true);
			lblPickWallet.setDisable(false);
			btnAccept.setDisable(false);
			comboBoxWallet.setDisable(false);
			
			createComboBox();
		} else {
			lblMessage.setText("Dane logowania są nieprawidłowe");
		}

	}

	public void createComboBox() {
		ObservableList<String> observableList = FXCollections.observableArrayList();
		for (int i = 0; i < profile.getWallets().size(); i++) {
			observableList.add(profile.getWallets().get(i)[0]);
		}
		comboBoxWallet.setItems(observableList);
	}

	@FXML
	void showMainView(ActionEvent event) {
		if (comboBoxWallet != null) {
			for (int i = 0; i < profile.getWallets().size(); i++) {
				if(profile.getWallets().get(i)[0].equals(comboBoxWallet.getValue())){
					Profile.walletDirectoryPath=profile.getWallets().get(i)[1];
				}
			}
			Profile.walletName= comboBoxWallet.getValue();
			manager.showMenu();
			manager.showMainView();
		}

	}

	public void initialize(URL location, ResourceBundle resources) {

	}
}
