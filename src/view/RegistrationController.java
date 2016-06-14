package view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
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
	    private String profileName;
	    private String password; 
	    private String walletName; 
	    private String directoryPath;
	    @FXML
	    void setWalletDirectory(ActionEvent event) {
	    	DirectoryChooser directoryChooser = new DirectoryChooser();
	    	directoryChooser.setTitle("wybierz folder");
	    	directoryChooser.setInitialDirectory(new File (System.getProperty("user.home")) );
	    	
	    	 File selectedDirectory =   directoryChooser.showDialog(new Stage());
              
             if(selectedDirectory == null){
                 lblSelectedDirectory.setText("No Directory selected");
             }else{
            	 lblSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
            	 directoryPath= selectedDirectory.getAbsolutePath();
             }
	    }

	    @FXML
	    void showStart(ActionEvent event) {
	    	profileName = txtfProfileName.getText();
	    	password= passwordField.getText();
	    	walletName=txtfWalletName.getText();
	    	
	    	if(!profileName.isEmpty() && !password.isEmpty() && !directoryPath.isEmpty() && !walletName.isEmpty() ){
	    		createDB();
			manager.showStart();
	    	} else {
	    		lblSelectedDirectory.setText("Wypełnij poprawnie formularz");
			}
			// manager.showMainView(root, "z start");
	    }
	    static Connection conn=  (Connection) ConnectionSqlite.Connector();
	    void createDB() {
			if (conn == null) {

				System.out.println("connection not successful");
				System.exit(1);
			}
			try {
				Statement mySta = conn.createStatement();
				
//				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS tran (idTransaction INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , date DATETIME NOT NULL , description TEXT  , accTransaction TEXT NOT NULL, debet DOUBLE DEFAULT 0, credit DOUBLE DEFAULT 0, balance DOUBLE )");
//				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS tran (idTransaction INTEGER PRIMARY KEY  UNIQUE  NOT NULL , date DATETIME NOT NULL , description TEXT  , accTransaction TEXT NOT NULL, debet DOUBLE DEFAULT 0, credit DOUBLE DEFAULT 0, balance DOUBLE )");
//				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS account (idAccount INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT NOT NULL , parent TEXT NOT NULL , balance DOUBLE DEFAULT 0, type INTEGER NOT NULL )");
//				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS config (firstRead BOOL NOT NULL DEFAULT TRUE)");
				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS profile (idProfile INTEGER PRIMARY KEY  UNIQUE  NOT NULL , profileName TEXT NOT NULL , password TEXT NOT NULL)");
				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS wallet (idWallet INTEGER PRIMARY KEY  UNIQUE  NOT NULL , walletName TEXT NOT NULL , filePath TEXT NOT NULL, idProfile INTEGER NOT NULL, FOREIGN KEY(idProfile) REFERENCES profile(idProfile))");
			
				mySta.executeUpdate("INSERT INTO profile (profileName, password) VALUES ('"+profileName+"','"+password+"')");	
				mySta.executeUpdate("INSERT INTO wallet (walletName, filePath, idProfile) VALUES ('"+walletName+"','"+directoryPath+"',1)");	
				
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}