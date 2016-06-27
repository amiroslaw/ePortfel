package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Account;
import model.Report;

public class ReportController {
	private Stage dialogStage;
	private MainManager manager;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

	@FXML
	private WebView webView;

	private WebEngine engine;

	@FXML
	private PieChart pieChart;

	@FXML
	private Button btnExportHTML;

	@FXML
	private Button btnClose;

	private int type;

	private String title;

	private String html;

	Report report = new Report(title);

	public void initial(String title, int type) {
		this.type = type;
		this.title = title;
		if (type < 4) {
			Report report = new Report(title, manager.getStructure().getAccList());
			// drawReport();
			engine = webView.getEngine();
			html = report.createHTML(type);
			engine.loadContent(html);
		} else {
			btnExportHTML.setVisible(false);
			drawPieChart();
		}
	}

	@FXML
	void closeWindow(ActionEvent event) {
		dialogStage.close();
	}

	@FXML
	void exportHTML(ActionEvent event) throws FileNotFoundException {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("wybierz folder");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		File selectedDirectory = directoryChooser.showDialog(new Stage());

		if (selectedDirectory == null) {
		} else {
			PrintWriter write = new PrintWriter(selectedDirectory.getAbsolutePath() + "/" + title + ".html");
			write.println(html);
			write.close();

		}
	}

	private void drawPieChart() {
		ArrayList<Account> accountList = new ArrayList<Account>(manager.getStructure().getAccList());
		ObservableList<PieChart.Data> dataChart = FXCollections.observableArrayList();

		switch (type) {
		case 4:
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 1 && accountList.get(i).getBalance() > 0) {
					dataChart.add(new PieChart.Data(
							accountList.get(i).getName() + " " + accountList.get(i).getBalance() + " zł",
							accountList.get(i).getBalance()));
				}
			}
			break;
		case 5:
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 2 && accountList.get(i).getBalance() > 0) {
					dataChart.add(new PieChart.Data(
							accountList.get(i).getName() + " " + accountList.get(i).getBalance() + " zł",
							accountList.get(i).getBalance()));
				}
			}
			break;
		case 6:
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 3 && accountList.get(i).getBalance() > 0) {
					dataChart.add(new PieChart.Data(
							accountList.get(i).getName() + " " + accountList.get(i).getBalance() + " zł",
							accountList.get(i).getBalance()));
				}
			}
			break;
		case 7:
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 4 && accountList.get(i).getBalance() > 0) {
					dataChart.add(new PieChart.Data(
							accountList.get(i).getName() + " " + accountList.get(i).getBalance() + " zł",
							accountList.get(i).getBalance()));
				}
			}
			break;
		default:
			break;
		}
		pieChart.setTitle(title);
		pieChart.setData(dataChart);
	}

}
