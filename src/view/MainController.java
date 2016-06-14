package view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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
import model.Transaction;

// dopiero po zmianie konta na tree dodaje do mapy- nie mozna zapisac aktualnych wartosci do BD jak tego nie zrobimy
public class MainController implements Initializable {
	private MainManager manager = new MainManager(null);

	public void setManager(MainManager manager) {
		this.manager = manager;

	}

	// private Structure structure= new Structure() ;
	//
	// public Structure getStructure() {
	// return structure;
	// }
	//
	// public void setStructure(Structure structure) {
	// this.structure = structure;
	// }
	private TreeItem<Account> root;

	String proba;

	public void setProba(String proba) {
		this.proba = proba;
	}

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
		// manager.showAddTransaction(manager.getStructure().getAccList());
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
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();

		System.out.println("show edit id" + transactionSelected.get(0).getId());
		System.out.println("balance delete" + transactionSelected.get(0).getBalance());
		int id = transactionSelected.get(0).getId();
		transactionSelected.forEach(allTransactions::remove);

		manager.getStructure().getMap().get(selectedAccountName).remove(id);
		manager.getStructure().updateBalance(selectedAccountName);
	}

	// przekierowanie root z start
	public void setRoot(TreeItem<Account> root) {
		this.root = root;
		accTree.setRoot(root);
	}

	void readTestData() {
		ArrayList<Transaction> temporary = new ArrayList<Transaction>();
		ArrayList<Transaction> tranList = new ArrayList<Transaction>();
		ArrayList<Transaction> tranList2 = new ArrayList<Transaction>(
				Arrays.asList(new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 0, 1),
						new Transaction(LocalDate.now(), "tran2", "transfer", 0, 0, 0, 0),
						new Transaction(LocalDate.now(), "tran3", "transfer", 0, 0, 0, 0)));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		tranList.add(new Transaction(LocalDate.now(), "arraylist", "transfer", 0, 0, 0, 0));
		manager.getStructure().getMap().put("Gotówka", tranList);
		manager.getStructure().getMap().put("Inwestycje", tranList2);
		manager.getStructure().showMap();
	}

	void observableToMap(String accountName) {
		ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
		for (Transaction transaction : manager.getTransactionData()) {
			arrayList.add(transaction);
			System.out.println(transaction.getDescription());
		}
		// nadpisze istniejaca wartosc
		manager.getStructure().getMap().put(accountName, arrayList);
	}

	public void initialize(URL location, ResourceBundle resources) {
	
		tcDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		tcDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		tcAccount.setCellValueFactory(cellData -> cellData.getValue().accTransactionProperty());
		tcDebet.setCellValueFactory(cellData -> cellData.getValue().debetProperty());
		tcCredit.setCellValueFactory(cellData -> cellData.getValue().creditProperty());
//		tcBalance.setCellValueFactory(cellData -> cellData.getValue().balanceProperty());
		tableTransaction.setItems(manager.getTransactionData());
		// firstNameCol.setSortType(TableColumn.SortType.ASCENDING);
		tableTransaction.getSortOrder().add(tcDate);

		manager.getStructure().readAccount();
		manager.getStructure().readTransactions();

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
				System.out.println("Selected item is" + selectedAccount.getType());
				selectedAccountName = selectedAccount.getName();
				if (selectedAccount != null) {
					lblBalance.textProperty().bind(Bindings.convert(selectedAccount.balanceProperty()));
				}
				// setTransactionData(treeItem.getValue().toString());
				if (selectedAccount.getType() < 3) {
					tableTransaction.setVisible(true);
					hboxEditTransaction.setVisible(true);
					// lblBalance.setText(" konta " + selectedAccount.getName()
					// + " to " + selectedAccount.getBalance());
					manager.setTransactionData(selectedAccount.getName());
				} else {
					tableTransaction.setVisible(false);
					hboxEditTransaction.setVisible(false);
					// lblBalance.setText(" konta " + selectedAccount.getName()
					// + " to " + selectedAccount.getBalance());
				}
			}
		});
		root.setExpanded(true);
		accTree.setShowRoot(false);
		accTree.setEditable(false);
		accTree.setCellFactory(new Callback<TreeView<Account>, TreeCell<Account>>() {

			@Override
			public TreeCell<Account> call(TreeView<Account> param) {
				return new TreeCellImpl();
			}
		});
		accTree.setRoot(root);
//test
		manager.getStructure().showList();
		System.out.println("ścieżka do DB portfela "+ MainManager.walletDirectoryPath);

	}

}
