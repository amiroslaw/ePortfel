package view;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Transaction;
import view.*;

public class AddTransactionController {
	private Stage dialogStage;
	private MainManager manager;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setManager(MainManager manager) {
		this.manager = manager;
	}

	ObservableList<Transaction> data;
	@FXML
	private DatePicker dataPicker;

	@FXML
	private ChoiceBox<String> choiceBox;

	@FXML
	private TextField txtAmount;

	@FXML
	private TextField txtDescription;
	@FXML
	private Button tbnCancel;

	@FXML
	private Button btnOK;
	@FXML
	private Label lblWarning;
	
	double amount;

	@FXML
	public void addTransaction() {
		if (isDouble(txtAmount.getText())) {
			amount = Double.parseDouble(txtAmount.getText());
			if (amount < 0) {
				MainController.getTransactionData()
						.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), "transfer", amount, 0, 0, 0));
				System.out.println("ok add transaction");
			} else {
				MainController.getTransactionData()
						.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), "transfer", 0, amount, 0, 0));
			}
			
		}else {
		txtAmount.clear();
		lblWarning.setText("Źle podana kwota");
			
		}
//		txtDescription.clear();
//		dataPicker.cle
//		choiceBox.c
	}

	boolean isDouble(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@FXML
	public void cancel() {
		dialogStage.close();
	}
}
