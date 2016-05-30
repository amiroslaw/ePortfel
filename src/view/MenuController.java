package view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.Structure;

public class MenuController {
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	private MainManager manager;
	public void setManager(MainManager manager) {
		this.manager = manager;
	}
	private MainController mainCtr= new MainController(); 
	@FXML
	private MenuItem miAbout; 
	

    @FXML
    private MenuItem miSave;

    @FXML
    private MenuItem miClose;

    @FXML
    void save() {
//    	System.out.println("zapis z menu "+ manager.getProba());
//    	System.out.println("string main manager "+manager.proba);
//    	structure.showList();
//    	mainCtr.getStructure().showMap();
//    	mainCtr.getStructure().saveTransactions();
    	System.out.println(manager.getStructure().test);
    	manager.getStructure().saveTransactions();
    	System.out.println("size accList: "+manager.getStructure().getAccList().size());
  
    }



	@FXML
	public void showAbout(){
		System.out.println("metoda about");
		manager.showAboutDialog();
	}


//	Structure structure = new Structure();
//	public void setStructure(Structure structure) {
//		this.structure=structure;
//		
//	} 
}
