package view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import model.Account;
import model.Structure;
import model.Transaction;

public class MainController  implements Initializable {

//	private StartController start;
	private MainManager manager;

	public void setManager(MainManager manager) {
		this.manager = manager;

	}
	private Structure structure= new Structure() ; 
//	public Structure getStructure(){
//		return structure; 
//	}
	private TreeItem<Account> root;
	
	String proba; 
	public void setProba(String proba){
		this.proba=proba;
	}
	

	// dodac do metody
	// data.add(new Transaction("data", "opis", "transfer", 0, 0, 0, 0));
	@FXML
	private TreeView<Account> accTree;

	@FXML
	private Label lblBalance;

	@FXML
	private TableView<Transaction> tableTransaction;

	@FXML
	private TableColumn<Transaction, LocalDate> tcDate;

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
	public void showAddTransaction() {
		System.out.println("metoda addTransaction");
		manager.showAddTransaction();
	}

	@FXML
	public void showEditTransaction() {
		System.out.println("metoda editTransaction");
		// manager.showEditTransaction();
		System.out.println("edit clicked");
		data.add(new Transaction(LocalDate.now(), "przycisk edit", "transfer", 0, 0, 0, 0));
		 System.out.println("proba"+ proba);
		 System.out.println("proba"+ root.getChildren().get(0).getValue().getName());
	}

	@FXML
	public void deleteTransaction() {
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();

		transactionSelected.forEach(allTransactions::remove);
	}

	// TableColumn lastNameCol = new TableColumn("Last Name");
	// tcDate.setCellValueFactory(
	// new PropertyValueFactory<>("Data"));
	//
	// tableTransaction.setItems(data);
	// tableTransaction.getColumns().addAll(firstNameCol, lastNameCol);
	//
	// final Button addButton = new Button("Add");
	// addButton.setOnAction((ActionEvent e) -> {
	// data.add(new Person("Z","X"));
	// });
	//
	// przekierowanie root z start
	public void setRoot(TreeItem<Account> root){
		this.root=root;
		 accTree.setRoot(root);
	}
	private static ObservableList<Transaction> data = FXCollections.observableArrayList(
			new Transaction(LocalDate.now(), "opis", "transfer", 0, 0, 0, 0),
			new Transaction(LocalDate.now(), "opis2", "transfer", 0, 0, 0, 0),
			new Transaction(LocalDate.now(), "opis3", "transfer", 0, 0, 0, 0));
	
	public static ObservableList<Transaction> getTransactionData() {
		return data;
	}
//	TreeItem<Account> root = new TreeItem<Account>(); 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		tcDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		tcAccount.setCellValueFactory(cellData -> cellData.getValue().accTransactionProperty());
		tcDebet.setCellValueFactory(cellData -> cellData.getValue().debetProperty());
		tcCredit.setCellValueFactory(cellData -> cellData.getValue().creditProperty());
		tcBalance.setCellValueFactory(cellData -> cellData.getValue().balanceProperty());
		tableTransaction.setItems(data);	
		
		structure.accList.clear();
		structure.readTree();
		root = structure.listToTree();
		// nie było bledu z 
//		accTree.setRoot(structure.listToTree());
		root.setExpanded(true);
		accTree.setShowRoot(false);
		accTree.setEditable(true);
		accTree.setRoot(root);
	}

	
}
