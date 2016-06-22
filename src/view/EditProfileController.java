package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Profile;

public class EditProfileController {
	private Stage dialogStage;
	private MainManager manager;
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

    @FXML
    private Label lblRecommendation;

    @FXML
    private TextField tfNewData;

    @FXML
    private Label lblNewData;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnClose;

 private int type; 
 private Profile profile =new Profile();
    
    @FXML
    void accept(ActionEvent event) {
    	if (!tfNewData.getText().isEmpty()) {
     String newData=tfNewData.getText(); 
     System.out.println("accept edit profile newdata "+newData +" type "+type);
			switch (type) {
			case 1:
				profile.editProfile(newData, 1);
				break;
			case 2:
				profile.editProfile(newData, 2);
				break;
			case 3:
				profile.deleteWallet();
				Profile.walletName=newData;
				break;
			case 4:
				Profile.walletName=newData;
		
				manager.showStart();
				break;
			default:
				System.out.println("brak danych");
				break;
			}
			dialogStage.close();
		}
    	
    }

    @FXML
    void close(ActionEvent event) {
    	dialogStage.close();
    }
	
	public void init(int type) {
		this.type=type;
		switch (type) {
		case 1:
			lblRecommendation.setText("Podaj nową nazwę profilu");
			lblNewData.setText("Nazwa");
			break;
		case 2:
			lblRecommendation.setText("Podaj nowe hasło");
			lblNewData.setText("Hasło");
			break;
// zmiana nazwy
		case 3:
			lblRecommendation.setText("Podaj nową nazwę portfela");
			lblNewData.setText("Nazwa");
			break;
			//tworzenie
		case 4:
			lblRecommendation.setText("Podaj nazwę portfela");
			lblNewData.setText("Nazwa");
			break;
		default:
			lblRecommendation.setText("error");
		
		}
	}
}
