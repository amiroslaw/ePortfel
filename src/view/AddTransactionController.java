package view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Transaction;
import view.*;

public class AddTransactionController implements Initializable{
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
	private ComboBox<String> comboBox;

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
	// rozbic na czy poprawnie kwota i czy podane dane
	// sprawdzic poprawnosc combo i picker
	public void addTransaction() {
//		if (isDouble(txtAmount.getText()) && !comboBox.getValue().toString().isEmpty() && !dataPicker.getValue().toString().isEmpty()) {
		if (isDouble(txtAmount.getText()) && comboBox.getValue()!=null && dataPicker.getValue()!=null) {
			amount = Double.parseDouble(txtAmount.getText());
			if (amount < 0) {
				MainController.getTransactionData()
						.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), comboBox.getValue(), amount, 0, 0, 0));
				System.out.println("ok add transaction");
			} else {
				MainController.getTransactionData()
						.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), comboBox.getValue(), 0, amount, 0, 0));
			}
			dialogStage.close();
		}else {
		txtAmount.clear();
		lblWarning.setText("Źle podane wartości");
			
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
			lblWarning.setText("Źle podana kwota transakcji");
			return false;
		}
	}

	@FXML
	public void cancel() {
		dialogStage.close();
	}

	public void  setInput(LocalDate date, String description, String transaction, String amount){
		dataPicker.setValue(date);
		txtAmount.setText(amount);
		txtDescription.setText(description);
		comboBox.setValue(transaction);
	}
//	ArrayList<Account> accList = new ArrayList<Account>(); 
	public void getAccoutList(ArrayList<Account> acc){
//		System.out.println("get acc list "+acc.size());
	ObservableList<String> observableList = FXCollections.observableArrayList();
	for (int i = 0; i < acc.size(); i++) {
		observableList.add(acc.get(i).getName());
	}
	comboBox.setItems(observableList);
	
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
