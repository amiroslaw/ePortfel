package view;

import javafx.stage.Stage;

public class AboutController {
	private Stage dialogStage;
	private MainManager manager; 
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}
}
