package view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import edu.rit.se.fpts.controller.Manager;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import model.Account;
import model.Profile;
import model.Transaction;

// dopiero po zmianie konta na tree dodaje do mapy- nie mozna zapisac aktualnych wartosci do BD jak tego nie zrobimy
public class MainController implements Initializable {
	private MainManager manager = new MainManager(null);

	public void setManager(MainManager manager) {
		this.manager = manager;

	}

	private TreeItem<Account> root;

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
	public void Test() {
		manager.getStructure().saveTransactions();
	}

	@FXML
	private HBox hboxEditTransaction;

	private String selectedAccountName = "";
	Account selectedAccount;

	@FXML
	public void showAddTransaction() {
		System.out.println("metoda addTransaction");
		// TODO: dodanie warunku gdy nie zostalo wybrane konto
		manager.showAddTransaction(selectedAccountName, -1);
	}

	@FXML
	public void showEditTransaction() {
		System.out.println("metoda editTransaction");

		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();
		if (transactionSelected.get(0) == null) {
			System.out.println("wybierz transakcję");
		} else {
			if (transactionSelected.get(0).getDebet() < 0.0) {
				String amount = Double.toString(transactionSelected.get(0).getDebet());
				manager.showAddTransaction(transactionSelected.get(0).getDate(),
						transactionSelected.get(0).getDescription(), transactionSelected.get(0).getAccTransaction(),
						amount, transactionSelected.get(0).getId(), selectedAccountName);
				System.out.println("wybrany amount: " + amount);

			} else {
				String amount = Double.toString(transactionSelected.get(0).getCredit());
				manager.showAddTransaction(transactionSelected.get(0).getDate(),
						transactionSelected.get(0).getDescription(), transactionSelected.get(0).getAccTransaction(),
						amount, transactionSelected.get(0).getId(), selectedAccountName);
			}
		}

	}

	@FXML
	public void deleteTransaction() {
		System.out.println("delete transaction");
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();
		System.out.println("transactionSelected name " + transactionSelected.get(0).getAccountName() + " desc: "
				+ transactionSelected.get(0).getDescription());
		
		manager.getStructure().showMap();
		
		// transfer
		int id = manager.getStructure().searchIDtransfer(transactionSelected.get(0).getDate(),
				transactionSelected.get(0).getDescription(), transactionSelected.get(0).getAccTransaction());
		int index = manager.getStructure().searchTransferIndex(id, transactionSelected.get(0).getAccTransaction());
		System.out.println("transactionSelected name " + transactionSelected.get(0).getAccountName() + " desc: "
				+ transactionSelected.get(0).getDescription());
		manager.getStructure().getMap().get(transactionSelected.get(0).getAccTransaction()).remove(index);
		manager.getStructure().updateBalance(transactionSelected.get(0).getAccTransaction());

		// transaction
		id = transactionSelected.get(0).getId();
		// int id =
		// manager.getStructure().getMap().get(key)transactionSelected.get(0).getId();
		index = manager.getStructure().searchTransferIndex(id, selectedAccountName);
		transactionSelected.forEach(allTransactions::remove);
		manager.getStructure().getMap().get(selectedAccountName).remove(index);

//		manager.setTransactionData(selectedAccountName);
		manager.getStructure().updateBalance(selectedAccountName);
	}

	// przekierowanie root z start
	public void setRoot(TreeItem<Account> root) {
		this.root = root;
		accTree.setRoot(root);
	}



	public void initialize(URL location, ResourceBundle resources) {

		tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		tcDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		tcAccount.setCellValueFactory(cellData -> cellData.getValue().accTransactionProperty());
		tcDebet.setCellValueFactory(cellData -> cellData.getValue().debetProperty());
		tcCredit.setCellValueFactory(cellData -> cellData.getValue().creditProperty());
		tableTransaction.setItems(manager.getTransactionData());
		// firstNameCol.setSortType(TableColumn.SortType.ASCENDING);
		tableTransaction.getSortOrder().add(tcDate);

		manager.getStructure().readAccount();
		manager.getStructure().readTransactions();
		manager.getStructure().showMap();

		// problem z tymi samymi id i wczytywaniem przykladowych danych
		// readTestData();

		root = manager.getStructure().listToTree();
		accTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		accTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem treeItem = (TreeItem) newValue;
				TreeItem oldTreeItem = (TreeItem) oldValue;

				selectedAccount = (Account) treeItem.getValue();
				 System.out.println("Selected item saldo" + selectedAccount.getBalance());
				selectedAccountName = selectedAccount.getName();
				if (selectedAccount != null) {
					hboxEditTransaction.setVisible(true);
					lblBalance.textProperty().bind(Bindings.convert(selectedAccount.balanceProperty()));
				}
					manager.setTransactionData(selectedAccount.getName());
				// setTransactionData(treeItem.getValue().toString());
//				if (selectedAccount.getType() < 3) {
//					tableTransaction.setVisible(true);
//					hboxEditTransaction.setVisible(true);
//					// lblBalance.setText(" konta " + selectedAccount.getName()
//					// + " to " + selectedAccount.getBalance());
//				} else {
//					tableTransaction.setVisible(false);
//					hboxEditTransaction.setVisible(false);
//					// lblBalance.setText(" konta " + selectedAccount.getName()
//					// + " to " + selectedAccount.getBalance());
//				}
			}
		});
		root.setExpanded(true);
		accTree.setShowRoot(false);
		accTree.setEditable(false);
		// edycja drzewa
		// accTree.setCellFactory(new Callback<TreeView<Account>,
		// TreeCell<Account>>() {
		//
		// @Override
		// public TreeCell<Account> call(TreeView<Account> param) {
		// return new TreeCellImpl();
		// }
		// });
		accTree.setRoot(root);
		// test
//		manager.getStructure().showList();
		// System.out.println("ścieżka do DB portfela "+
		// MainManager.walletDirectoryPath);
		
		System.out.println("show main " +Profile.walletName);
	}

	String proba;
//	public void setProba(String proba) {
//		this.proba = proba;
//	}
	
//	void readTestData() {
//	ArrayList<Transaction> temporary = new ArrayList<Transaction>();
//	ArrayList<Transaction> tranList = new ArrayList<Transaction>();
//	ArrayList<Transaction> tranList2 = new ArrayList<Transaction>(
//			Arrays.asList(new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 1, "Akcje"),
//					new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 1, "czynsz"),
//					new Transaction(LocalDate.now(), "tran3", "transfer", 0, 0, 0, "Akcje")));
//	tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, "Akcje"));
//	tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, "Akcje"));
//	tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, "Akcje"));
//	tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, "Akcje"));
//	manager.getStructure().getMap().put("Gotówka", tranList);
//	manager.getStructure().getMap().put("Inwestycje", tranList2);
//	// manager.getStructure().showMap();
//}
// chyba nieuzywana
//void observableToMap(String accountName) {
//	ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
//	for (Transaction transaction : manager.getTransactionData()) {
//		arrayList.add(transaction);
////		System.out.println("observable to map" + transaction.getDescription());
//	}
//	// nadpisze istniejaca wartosc
//	manager.getStructure().getMap().put(accountName, arrayList);
//}
}
