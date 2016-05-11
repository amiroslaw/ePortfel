package view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;
import model.Structure;
import model.Transaction;

public class MainController  implements Initializable {

	private MainManager manager;

	public void setManager(MainManager manager) {
		this.manager = manager;

	}
	private Structure structure= new Structure() ; 

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
		manager.showAddTransaction(structure.getAccList());
	}

	@FXML
	public void showEditTransaction() {
		System.out.println("metoda editTransaction");
		// manager.showEditTransaction();
//		data.add(new Transaction(LocalDate.now(), "przycisk edit", "transfer", 0, 0, 0, 0));
//		 System.out.println("proba"+ root.getChildren().get(0).getValue().getName());
//		 setTransactionData(tranList);
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();
		if(transactionSelected.get(0)==null){
			System.out.println("wybierz transakcję");
		}else{
		if(transactionSelected.get(0).getDebet()<0.0){
			String amount =Double.toString(transactionSelected.get(0).getDebet());
			manager.showAddTransaction(transactionSelected.get(0).getDate(),transactionSelected.get(0).getDescription(),
					transactionSelected.get(0).getAccTransaction(), amount, structure.getAccList());
		System.out.println("wybrany amount: "+ amount );
		
		} else {
			String amount = Double.toString(transactionSelected.get(0).getCredit());
			manager.showAddTransaction(transactionSelected.get(0).getDate(),transactionSelected.get(0).getDescription(),
					transactionSelected.get(0).getAccTransaction(),amount, structure.getAccList());
			System.out.println("get acc"+ structure.getAccList().size());
		}}
		// usuwa gdy zamkniemy okno - moze inny sposob na update → table edit
		transactionSelected.forEach(allTransactions::remove);
//		structure.showList();
		System.out.println(structure.getAccList().size()+" show edit size accList");
	}

	@FXML
	public void deleteTransaction() {
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();

		transactionSelected.forEach(allTransactions::remove);
	}

	// przekierowanie root z start
	public void setRoot(TreeItem<Account> root){
		this.root=root;
		 accTree.setRoot(root);
	}
	private static ObservableList<Transaction> data = FXCollections.observableArrayList(
			
			new Transaction(LocalDate.now(), "opis", "transfer", 0, 0, 0, 0),
			new Transaction(LocalDate.now(), "opis2", "transfer", 0, 0, 0, 0),
			new Transaction(LocalDate.now(), "opis3", "transfer", 0, 0, 0, 0)
			);
	private HashMap<String, ArrayList<Transaction>> map = new HashMap<String,ArrayList<Transaction>>();
//private static ObservableMap<Transaction> data2 = FXCollections.observableHashMap()();
//
//// Now add observability by wrapping it with ObservableList.
//ObservableMap<String,String> observableMap = FXCollections.observableMap(map);
//observableMap.addListener(new MapChangeListener() {}
	public static ObservableList<Transaction> getTransactionData() {
		return data;
	}
	
	private ArrayList<Transaction> temporary = new ArrayList<Transaction>();
	private ArrayList<Transaction> tranList = new ArrayList<Transaction>();
	private ArrayList<Transaction> tranList2 = new ArrayList<Transaction>(
			Arrays.asList(	new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 0, 0),
					new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 0, 0),
					new Transaction(LocalDate.now(), "tran3", "transfer", 0, 0, 0, 0)
					)
			);
	
	private void setTransactionData(ArrayList<Transaction> tran){
		data.clear();
		for (int i = 0; i < tran.size(); i++) {
			data.add(tran.get(i)); 
		}
	}
//	private HashMap<String, ArrayList<Transaction>> hashTransaction = new HashMap<String, ArrayList<Transaction>>();
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
//		firstNameCol.setSortType(TableColumn.SortType.ASCENDING);
		tableTransaction.getSortOrder().add(tcDate);	
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		
		map.put("lista1", tranList);
		map.put("lista2", tranList2);
		
//		structure.accList.clear();
		structure.readTree();
		root = structure.listToTree();
		 accTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	        accTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
	        	@Override
	            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
	                TreeItem treeItem = (TreeItem)newValue;
	                System.out.println("Selected item is" + treeItem);
	            }
	        });
		// nie było bledu z 
//		accTree.setRoot(structure.listToTree());
		root.setExpanded(true);
		accTree.setShowRoot(false);
		accTree.setEditable(true);
		accTree.setCellFactory(new Callback<TreeView<Account>, TreeCell<Account>>() {

			@Override
			public TreeCell<Account> call(TreeView<Account> param) {
				return new TreeCellImpl();
			}
		});
		accTree.setRoot(root);
		
		System.out.println("structure list: "+ structure.accList.size());
	}

	
}
