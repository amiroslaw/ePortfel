package view;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Report;

public class ReportController  {
	private Stage dialogStage;
	private MainManager manager;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}
		
		@FXML
		private WebView webView = new WebView();
		private WebEngine engine; 
		
		@FXML
		private Button btnExportHTML; 
		
	    @FXML
	    private Button btnClose;

	    private int type;
	    private String title;
	    private String html;
	    
	    @FXML
	    void closeWindow(ActionEvent event) {
	    	dialogStage.close();
	    }
// zmienic nazwe jak nie bedzie mozna wykresow eksportowac do html
	    @FXML
	    void exportHTML(ActionEvent event) throws FileNotFoundException {
	    	DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("wybierz folder");
			directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

			File selectedDirectory = directoryChooser.showDialog(new Stage());

			if (selectedDirectory == null) {
//				lblSelectedDirectory.setText("No Directory selected");
			} else {
//				lblSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
				PrintWriter write = new PrintWriter(selectedDirectory.getAbsolutePath()+"/"+title+".html");
					write.println(html);
					write.close();
			
			
			}
		}
	    
			Report report= new Report(title);
		public void initial(String title, int type) {
			this.type=type;
			this.title=title;
			Report report= new Report(title,manager.getStructure().getAccList());
//			drawReport();
			engine = webView.getEngine();
			html= report.createHTML(type);
			engine.loadContent(html);
			System.out.println("report ctr title "+title+" type+ "+type);
		}
		void drawReport (){
		
		}

	
	}
