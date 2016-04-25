package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import model.Account;
import model.Transaction;

public class MainController implements Initializable{
	private MainManager manager;
	
	public void setManager(MainManager manager) {
		this.manager = manager;

	}

    private ObservableList<Transaction> data =
            FXCollections.observableArrayList(
            		new Transaction( "data", "opis", "transfer", 0, 0, 0, 0),
            		new Transaction("data", "opis2", "transfer", 0, 0, 0, 0), 
            		new Transaction("data", "opis3", "transfer", 0, 0, 0, 0)
            		);
    // dodac do metody
//    data.add(new Transaction("data", "opis", "transfer", 0, 0, 0, 0));
    @FXML
    private TreeView<Account> accTree;

    @FXML
    private Label lblBalance;

    @FXML
    private TableView<Transaction> tableTransaction;

    @FXML
    private TableColumn<Transaction, String> tcDate;

    @FXML
    private TableColumn<Transaction, String> tcDescription;

    @FXML
    private TableColumn<Transaction, String> tcAccount;

    @FXML
    private TableColumn<Transaction, Number> tcDebet;

    @FXML
    private TableColumn<Transaction, Number> tcCredit;

    @FXML
    private TableColumn<Transaction, Number> tcBalance;

    @FXML
    private Button btnAddTransaction;

    @FXML
    private Button btnEditTransaction;

    @FXML
    private Button btnRemoveTransaction;
    
    @FXML
	public void showAddTransaction(){
		System.out.println("metoda addTransaction");
		manager.showAddTransaction();
	} 
    @FXML
	public void showEditTransaction(){
		System.out.println("metoda editTransaction");
//		manager.showEditTransaction();
	}
    @FXML
    public void deleteTransaction(){
    	ObservableList<Transaction> transactionSelected, allTransactions;
        allTransactions = tableTransaction.getItems();
        transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();

        transactionSelected.forEach(allTransactions::remove);
    }
    
//    TableColumn lastNameCol = new TableColumn("Last Name");
//    tcDate.setCellValueFactory(
//            new PropertyValueFactory<>("Data"));
//
//    tableTransaction.setItems(data);
//    tableTransaction.getColumns().addAll(firstNameCol, lastNameCol);
//
//    final Button addButton = new Button("Add");
//    addButton.setOnAction((ActionEvent e) -> {
//        data.add(new Person("Z","X"));
//     });
//    
    public ObservableList<Transaction> getTransactionData() {
        return data;
    }

@Override
public void initialize(URL location, ResourceBundle resources) {
	tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    tcDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    tcAccount.setCellValueFactory(cellData -> cellData.getValue().accTransactionProperty());
    tcDebet.setCellValueFactory(cellData -> cellData.getValue().debetProperty());
    tcCredit.setCellValueFactory(cellData -> cellData.getValue().creditProperty());
    tcBalance.setCellValueFactory(cellData -> cellData.getValue().balanceProperty());
    tableTransaction.setItems(data);
	
}
}
