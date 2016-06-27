package view;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import model.Account;
import model.Transaction;

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
	private HBox hboxEditTransaction;

	private String selectedAccountName = "";
	Account selectedAccount;

	@FXML
	public void showAddTransaction() {
		manager.showAddTransaction(selectedAccountName, -1);
	}

	@FXML
	public void showEditTransaction() {
		ObservableList<Transaction> transactionSelected, allTransactions;
		allTransactions = tableTransaction.getItems();
		transactionSelected = tableTransaction.getSelectionModel().getSelectedItems();
		if (transactionSelected.get(0) == null) {
		} else {
			if (transactionSelected.get(0).getDebet() < 0.0) {
				String amount = Double.toString(transactionSelected.get(0).getDebet());
				manager.showAddTransaction(transactionSelected.get(0).getDate(),
						transactionSelected.get(0).getDescription(), transactionSelected.get(0).getAccTransaction(),
						amount, transactionSelected.get(0).getId(), selectedAccountName);
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

		// transfer
		int id = manager.getStructure().searchIDtransfer(transactionSelected.get(0).getDate(),
				transactionSelected.get(0).getDescription(), transactionSelected.get(0).getAccTransaction());
		int index = manager.getStructure().searchTransferIndex(id, transactionSelected.get(0).getAccTransaction());
		manager.getStructure().getMap().get(transactionSelected.get(0).getAccTransaction()).remove(index);
		manager.getStructure().updateBalance(transactionSelected.get(0).getAccTransaction());

		// transaction
		id = transactionSelected.get(0).getId();
		index = manager.getStructure().searchTransferIndex(id, selectedAccountName);
		transactionSelected.forEach(allTransactions::remove);
		manager.getStructure().getMap().get(selectedAccountName).remove(index);
		manager.getStructure().updateBalance(selectedAccountName);
	}

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
		tableTransaction.setItems(MainManager.getTransactionData());
		tableTransaction.getSortOrder().add(tcDate);

		manager.getStructure().readAccount();
		manager.getStructure().readTransactions();

		root = manager.getStructure().listToTree();
		accTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		accTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem treeItem = (TreeItem) newValue;
				TreeItem oldTreeItem = (TreeItem) oldValue;
				selectedAccount = (Account) treeItem.getValue();
				selectedAccountName = selectedAccount.getName();
				if (selectedAccount != null) {
					hboxEditTransaction.setVisible(true);
					lblBalance.textProperty().bind(Bindings.convert(selectedAccount.balanceProperty()));
				}
				manager.setTransactionData(selectedAccount.getName());
			}
		});
		root.setExpanded(true);
		accTree.setShowRoot(false);
		accTree.setEditable(false);
		accTree.setRoot(root);
	}
}
