package view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.ConnectionSqlite;
import model.Profile;

public class RegistrationController implements Initializable {
	private MainManager manager = new MainManager(null);

	public void setManager(MainManager manager) {
		this.manager = manager;

	}

	public void setPrimaryStage(Stage primaryStage) {
		// this.primaryStage = primaryStage;
	}
	 @FXML
	    private TextField txtfProfileName;

	    @FXML
	    private PasswordField passwordField;

	    @FXML
	    private TextField txtfWalletName;

	    @FXML
	    private Button btnWalletDirectory;

	    @FXML
	    private Button btnAccept;
	    
	    @FXML
	    private Label lblSelectedDirectory;
	    
	   Profile profile = new Profile();  
	    @FXML
	    void setWalletDirectory(ActionEvent event) {
	    	DirectoryChooser directoryChooser = new DirectoryChooser();
	    	directoryChooser.setTitle("wybierz folder");
	    	directoryChooser.setInitialDirectory(new File (System.getProperty("user.home")) );
	    	
	    	 File selectedDirectory =   directoryChooser.showDialog(new Stage());
              
             if(selectedDirectory == null){
                 lblSelectedDirectory.setText("No Directory selected");
            	 btnAccept.setDisable(true);
             }else{
            	 lblSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
            	 profile.setDirectoryPath(selectedDirectory.getAbsolutePath());
            	 btnAccept.setDisable(false);
             }
	    }

	    @FXML
	    void showStart(ActionEvent event) {
	   
	    	
	    	if(!txtfProfileName.getText().isEmpty() && !passwordField.getText().isEmpty() && !profile.getDirectoryPath().isEmpty() && !txtfWalletName.getText().isEmpty() ){
	    		profile.setProfileName(txtfProfileName.getText());
	    		profile.setPassword(passwordField.getText());
	    		profile.setWalletName(txtfWalletName.getText());
	    		profile.createProfileDB();
	    		profile.createWalletDB(1);
			manager.showStart();
	    	} else {
	    		lblSelectedDirectory.setText("Wypełnij poprawnie formularz");
			}
	    }

	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnAccept.setDisable(true);
	}

}