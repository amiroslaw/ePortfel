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
	private int idTransaction=-1; 
	@FXML
	// rozbic na czy poprawnie kwota i czy podane dane
	// TODO: sprawdzic poprawnosc combo i picker
	// TODO: co z przyszłą datą transakcji
	public void addTransaction() {
//		if (isDouble(txtAmount.getText()) && !comboBox.getValue().toString().isEmpty() && !dataPicker.getValue().toString().isEmpty()) {
		if (isDouble(txtAmount.getText()) && comboBox.getValue()!=null && dataPicker.getValue()!=null) {
			if(idTransaction!=-1){
			manager.getStructure().getMap().get(accountName).remove(idTransaction);
			}
			amount = Double.parseDouble(txtAmount.getText());
			setSaldo(amount);
			if (amount < 0) {
//				MainController.getTransactionData()
//						.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), comboBox.getValue(), amount, 0, 0, 0));
				manager.getStructure().getMap().get(accountName)
						.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), comboBox.getValue(), amount, 0, 0, 0));
			} else {
//				MainController.getTransactionData()
//						.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), comboBox.getValue(), 0, amount, 0, 0));
				manager.getStructure().getMap().get(accountName)
				.add(new Transaction(dataPicker.getValue(), txtDescription.getText(), comboBox.getValue(), 0, amount, 0, 0));
			}
			// aktualizacja  ObservableList
			 manager.setTransactionData(accountName);
			dialogStage.close();
		}else {
		txtAmount.clear();
		lblWarning.setText("Źle podane wartości");
			
		}
//		txtDescription.clear();
//		dataPicker.cle
//		choiceBox.c
	 	System.out.println("size list: "+manager.getStructure().getAccList().size());
	}
	private int setSaldo(double amount) {
		ArrayList<Account> acc = new ArrayList<Account>(manager.getStructure().getAccList());
		for (int i = 0; i < acc.size(); i++) {
			if (acc.get(i).getName().equals(accountName)) {
				manager.getStructure().getAccList().get(i).setBalance(amount);
		System.out.println("acc name "+accountName +" index "+i);
				return 1;
			}
		}
		return -1;
		
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
    	manager.getStructure().saveTransactions();
		dialogStage.close();
	}

	public void  setInput(LocalDate date, String description, String transaction, String amount, int id ){
		dataPicker.setValue(date);
		txtAmount.setText(amount);
		txtDescription.setText(description);
		comboBox.setValue(transaction);
		idTransaction=id;
	}
	private String accountName="";
	// zmienic nazwe
	public void getAccoutList(String accountName){
			this.accountName=accountName; 
		createComboBox();
	}
//	ArrayList<Account> accList = new ArrayList<Account>(); 
	// tworzenie comboBox alfabetycznie
//	public void createComboBox(ArrayList<Account> acc){
	public void createComboBox(){
		ArrayList<Account> acc = new ArrayList<Account>(manager.getStructure().getAccList());
//		System.out.println("get acc list "+acc.size());
	ObservableList<String> observableList = FXCollections.observableArrayList();
	for (int i = 0; i < acc.size(); i++) {
		observableList.add(acc.get(i).getName());
	}
	comboBox.setItems(observableList);
	}
	
//	public void getAccoutList(ArrayList<Account> acc){
//	ObservableList<String> observableList = FXCollections.observableArrayList();
//	for (int i = 0; i < acc.size(); i++) {
//		observableList.add(acc.get(i).getName());
//	}
//	comboBox.setItems(observableList);
//	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// metoda wywoływana przed tworzeniem manager
//		createComboBox(manager.getStructure().getAccList());
	
	}
}
