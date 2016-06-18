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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Transaction;

public class AddTransactionController implements Initializable {
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
	private DatePicker datePicker;

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

	private String accountName = "";
	int accountType; 
	private int idTransaction = -1;
	double amount;
	String description;
	LocalDate date;
//	String oldDescription;
//	LocalDate oldDate;

	String transferAccount;
//	String oldTransferAccount;
	int idTransfer; 

	int accTransferType; 
	
	// rozbic na czy poprawnie kwota i czy podane dane
	// TODO: sprawdzic poprawnosc combo i picker
	// TODO: co z przyszłą datą transakcji

	@FXML
	public void doTransaction() {
		//dane konta do ktorego przelewamy
	
		if (isDouble(txtAmount.getText()) && comboBox.getValue() != null && datePicker.getValue() != null) {
			System.out.println("addTrans- id: " + idTransaction);
			// edycja
			if (idTransaction != -1) {
				int index; 
				index= manager.getStructure().searchTransferIndex(idTransaction, accountName);
				manager.getStructure().getMap().get(accountName).remove(index);
				//transfer
				idTransfer=manager.getStructure().searchIDtransfer(date, description, transferAccount);
				index=manager.getStructure().searchTransferIndex(idTransfer, transferAccount);
				manager.getStructure().getMap().get(transferAccount).remove(index);
				transferAccount= comboBox.getValue();
			} else {
				// dodanie
				transferAccount= comboBox.getValue();
				// moga byc problemy z tym ze tutaj pobieramy id przez size
				idTransaction = manager.getStructure().getMap().get(accountName).size();
				idTransfer = manager.getStructure().getMap().get(transferAccount).size();
			}
			
			description = txtDescription.getText();
			date = datePicker.getValue();
			amount = Double.parseDouble(txtAmount.getText());
			accTransferType= manager.getStructure().searchAccountType(transferAccount);
			accountType=manager.getStructure().searchAccountType(accountName);
			// dodanie w zaznaczonym koncie
			addTransaction(date, description, transferAccount, amount, idTransaction, accountName);
// aktualizacja ObservableList
			manager.setTransactionData(accountName);
			
			manager.getStructure().updateBalance(accountName);
			
			// dodanie transakcji w koncie do ktorego przelewamy
			switch (accountType) {
			case 1:
				if(accTransferType==2 || accTransferType==3){
					// tak samo
					addTransaction(date, description, accountName, amount, idTransfer, transferAccount);
				}else {
					addTransaction(date, description, accountName, -1*amount, idTransfer, transferAccount);
				}
				break;
			case 2:
				if(accTransferType==1 || accTransferType==4){
					addTransaction(date, description, accountName, amount, idTransfer, transferAccount);
				}else {
					addTransaction(date, description, accountName, -1*amount, idTransfer, transferAccount);
				}
				break;
			case 3:
				if(accTransferType==1 || accTransferType==4){
					addTransaction(date, description, accountName, amount, idTransfer, transferAccount);
				}else {
					addTransaction(date, description, accountName, -1*amount, idTransfer, transferAccount);
				}
				break;
			case 4:
				if(accTransferType==2 || accTransferType==3){
					addTransaction(date, description, accountName, amount, idTransfer, transferAccount);
				}else {
					addTransaction(date, description, accountName, -1*amount, idTransfer, transferAccount);
				}
				break;
			default:
				break;
			}
			manager.getStructure().updateBalance(transferAccount);
			
			dialogStage.close();
		} else {
			txtAmount.clear();
			lblWarning.setText("Źle podane wartości");

		}
	
		
		System.out.println("size list: " + manager.getStructure().getAccList().size());
	}
	private void addTransaction(LocalDate date, String description, String transferName, double amount, int id, String accountName) {
		if(amount <0){
		manager.getStructure().getMap().get(accountName).add(new Transaction(date, description, transferName, -1*amount, 0, id, accountName));
		}else {
		manager.getStructure().getMap().get(accountName).add(new Transaction(date, description, transferName, 0, amount, id, accountName));
		}
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

	public void setInput(LocalDate date, String description, String transfer, String amount) {
		this.date=date;
		this.description=description;
		transferAccount=transfer;
		datePicker.setValue(date);
		txtAmount.setText(amount);
		txtDescription.setText(description);
		comboBox.setValue(transfer);

	}



	public void getTransactionInfo(String accountName, int id) {
		this.accountName = accountName;
		idTransaction = id;
		System.out.println("set input- id: " + idTransaction);
		createComboBox();
	}

	// tworzenie comboBox alfabetycznie
	// public void createComboBox(ArrayList<Account> acc){
	public void createComboBox() {
		ArrayList<Account> acc = new ArrayList<Account>(manager.getStructure().getAccList());
		// System.out.println("get acc list "+acc.size());
		ObservableList<String> observableList = FXCollections.observableArrayList();
		for (int i = 0; i < acc.size(); i++) {
			observableList.add(acc.get(i).getName());
		}
		comboBox.setItems(observableList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	//zastapione przez updateBalance
//		private int setSaldo(double amount) {
//			ArrayList<Account> acc = new ArrayList<Account>(manager.getStructure().getAccList());
//			for (int i = 0; i < acc.size(); i++) {
//				if (acc.get(i).getName().equals(accountName)) {
//					double oldAmount = manager.getStructure().getAccList().get(i).getBalance();
//					manager.getStructure().getAccList().get(i).setBalance(amount + oldAmount);
//					// System.out.println("acc name " + accountName + " index " +
//					// i);
//					return 1;
//				}
//			}
//			return -1;
//
//		}
}
