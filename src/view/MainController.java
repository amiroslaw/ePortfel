package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
	private MainManager manager;
	@FXML
	private Button btnTemp; 
	public void setManager(MainManager manager) {
		this.manager = manager;

	}
	@FXML
	public void temp(){
		System.out.println("btn temp");
	}
}
