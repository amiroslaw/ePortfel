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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;
import model.Structure;
import model.Transaction;

// TODO: wyciagnac balance z acclist i powiazac ta zmienna z label lblBalance
// dopiero po zmianie konta na tree dodaje do mapy- nie mozna zapisac aktualnych wartosci do BD jak tego nie zrobimy
public class MainController  implements Initializable {
	private MainManager manager = new MainManager(null);
//	private MainManager manager; 

	public void setManager(MainManager manager) {
		this.manager = manager;

	}
//	private Structure structure= new Structure() ; 
//
//	public Structure getStructure() {
//		return structure;
//	}
//
//	public void setStructure(Structure structure) {
//		this.structure = structure;
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
	private Button btnTest;
	@FXML
	public void Test(){
		// TODO: zapis, start i ogarnięcie tej struktury
//		observableToMap("Akcje");
		manager.getStructure().saveTransactions();
	}
	@FXML
	private HBox hboxEditTransaction;
	
	private String selectedAccount= "";

	@FXML
	public void showAddTransaction() {
		System.out.println("metoda addTransaction");
		// nie potrzebne jak structure jest static
//		manager.showAddTransaction(manager.getStructure().getAccList());
		manager.showAddTransaction(selectedAccount);
	}

	@FXML
	public void showEditTransaction() {
		System.out.println("metoda editTransaction");
	
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();
		if(transactionSelected.get(0)==null){
			System.out.println("wybierz transakcję");
		}else{
		if(transactionSelected.get(0).getDebet()<0.0){
			String amount =Double.toString(transactionSelected.get(0).getDebet());
			manager.showAddTransaction(transactionSelected.get(0).getDate(),transactionSelected.get(0).getDescription(),
					transactionSelected.get(0).getAccTransaction(), amount, transactionSelected.get(0).getId(), selectedAccount);
		System.out.println("wybrany amount: "+ amount );
		
		} else {
			String amount = Double.toString(transactionSelected.get(0).getCredit());
			manager.showAddTransaction(transactionSelected.get(0).getDate(),transactionSelected.get(0).getDescription(),
					transactionSelected.get(0).getAccTransaction(),amount, transactionSelected.get(0).getId(), selectedAccount);
			System.out.println("get acc"+ manager.getStructure().getAccList().size());
		}}
		// usuwa gdy zamkniemy okno - moze inny sposob na update → table edit
//		transactionSelected.forEach(allTransactions::remove);
//		structure.showList();
		System.out.println(manager.getStructure().getAccList().size()+" show edit size accList");
	}

	@FXML
	public void deleteTransaction() {
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();

		transactionSelected.forEach(allTransactions::remove);
		int id = transactionSelected.get(0).getId();
		manager.getStructure().getMap().get(selectedAccount).remove(id);
	}

	// przekierowanie root z start
	public void setRoot(TreeItem<Account> root){
		this.root=root;
		 accTree.setRoot(root);
	}
	
	void readTestData(){
		ArrayList<Transaction> temporary = new ArrayList<Transaction>();
		ArrayList<Transaction> tranList = new ArrayList<Transaction>();
		ArrayList<Transaction> tranList2 = new ArrayList<Transaction>(
				Arrays.asList(	new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 0, 1),
						new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 0, 0),
						new Transaction(LocalDate.now(), "tran3", "transfer", 0, 0, 0, 0)
						)
				);
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		manager.getStructure().getMap().put("Gotówka", tranList);
		manager.getStructure().getMap().put("Inwestycje", tranList2);
		manager.getStructure().showMap();
	}
	
	


	void observableToMap(String accountName){
		ArrayList<Transaction> arrayList= new ArrayList<Transaction>();
		for (Transaction transaction : manager.getTransactionData()) {
			arrayList.add(transaction);
			System.out.println(transaction.getDescription());
		}
			// nadpisze istniejaca wartosc
			 manager.getStructure().getMap().put(accountName, arrayList);
	}
	public void initialize(URL location, ResourceBundle resources) {
//		manager.setProba("z mainCtr");
//		MainManager.structure.test="main controll";
		
		tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		tcDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		tcAccount.setCellValueFactory(cellData -> cellData.getValue().accTransactionProperty());
		tcDebet.setCellValueFactory(cellData -> cellData.getValue().debetProperty());
		tcCredit.setCellValueFactory(cellData -> cellData.getValue().creditProperty());
		tcBalance.setCellValueFactory(cellData -> cellData.getValue().balanceProperty());
		tableTransaction.setItems(manager.getTransactionData());	
//		firstNameCol.setSortType(TableColumn.SortType.ASCENDING);
		tableTransaction.getSortOrder().add(tcDate);	
	
		manager.getStructure().readAccount();
		manager.getStructure().readTransactions();
		
		readTestData();
		
		root = manager.getStructure().listToTree();
		 accTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	        accTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
	        	@Override
	            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
	                TreeItem treeItem = (TreeItem)newValue;
	                TreeItem oldTreeItem = (TreeItem) oldValue; 
	                
//	                ArrayList<Transaction> ar = (ArrayList<Transaction>) data; 
//	                structure.getMap().put(oldTreeItem.getValue().toString(), ar);
	                
	                //blad null point
//	                if (oldTreeItem!=null) {
//	                System.out.println("oldValue: " + oldTreeItem.getValue().toString());
//	                observableToMap(oldTreeItem.getValue().toString());
//					}
	                
	                Account acc= (Account) treeItem.getValue();
	                System.out.println("Selected item is" + acc.getType());
	                selectedAccount= acc.getName();
//	                setTransactionData(treeItem.getValue().toString());
	                if(acc.getType()<3){
	               	tableTransaction.setVisible(true);
	                	hboxEditTransaction.setVisible(true);
						lblBalance.setText(" konta "+acc.getName()+" to "+acc.getBalance());
	                manager.setTransactionData(acc.getName());
	                }
	                else {
	                	tableTransaction.setVisible(false);
	                	hboxEditTransaction.setVisible(false);
						lblBalance.setText(" konta "+acc.getName()+" to "+acc.getBalance());
					}
	            }
	        });
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
		
	}
	
}
