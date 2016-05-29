package view;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;
import model.ConnectionSqlite;
import model.Structure;

public class StartController implements Initializable {
	private MainManager manager;

	public void setManager(MainManager manager) {
		this.manager = manager;

	}

	public void setPrimaryStage(Stage primaryStage) {
		// this.primaryStage = primaryStage;
	}
//	private Structure structure = new Structure(); 
	@FXML
	void showMain() {
		manager.getStructure().treeToList(root);
		manager.getStructure().saveAccount();
//		structure.treeToList(root);
//		structure.saveAccount();
//		structure.proba();
		
		manager.showMenu();
//		manager.showMainView(root, "z start");
		manager.showMainView();
	}

	@FXML
	private Label labStart;
	@FXML
	private Button btnShowMain;
	@FXML
	protected TreeView<Account> treeView;

	private  List<Account> account = Arrays.<Account> asList(
			//tutaj nie wazne id
			new Account("Aktywa bieżące", "Aktywa", 0.0, 1, 0),
			new Account("Gotówka", "Aktywa bieżące", 0.0, 1, 1),
			new Account("Konto bankowe", "Aktywa bieżące", 0.0, 1, 2), new Account("Inwestycje", "Aktywa", 0.0, 1, 3),
			new Account("Lokaty", "Inwestycje", 0.0, 1, 4), new Account("Akcje", "Inwestycje", 0.0, 1, 5),
			new Account("Obligacje", "Inwestycje", 0.0, 1, 6), new Account("Karta kredytowa", "Pasywa", 0.0, 2, 7),
			new Account("Kredyty", "Pasywa", 0.0, 2, 8), new Account("Pensja", "Przychody", 0.0, 3, 9),
			new Account("Inne przychody", "Przychody", 0.0, 3, 10),
			new Account("Przychód z odsetek", "Przychody", 0.0, 3, 11), new Account("Premia", "Przychody", 0.0, 3, 12),
			new Account("Odsetki z lokat", "Przychód z odsetek", 0.0, 3, 13),
			new Account("Odsetki z akcji", "Przychód z odsetek", 0.0, 3, 14),
			new Account("Czynsz", "Wydatki", 0.0, 4, 15), new Account("Hobby", "Wydatki", 0.0, 4, 16),
			new Account("Jedzenie", "Wydatki", 0.0, 4, 17), new Account("Odsetki", "Wydatki", 0.0, 4, 18),
			new Account("Odsetki z kredytów", "Odsetki", 0.0, 4, 19), new Account("Podatki", "Wydatki", 0.0, 4, 20),
			new Account("Krajowe", "Podatki", 0.0, 4, 21), new Account("Socjalne", "Podatki", 0.0, 4, 22),
			new Account("Wydatki medyczne", "Wydatki", 0.0, 4, 23));

private	 TreeItem<Account> root = new TreeItem<Account>(new Account("root", null, 0.0, -1, 0) );

	private void readDefaultData (){
		TreeItem<Account> aktywa = new TreeItem<Account>(new Account("Aktywa", "root", 0.0, 1, 1) );
		TreeItem<Account> pasywa = new TreeItem<Account>(new Account("Pasywa", "root", 0.0, 2, 1));
		TreeItem<Account> przychody =  new TreeItem<Account>(new Account("Przychody", "root", 0.0, 4, 1));
		TreeItem<Account> wydatki =  new TreeItem<Account>(new Account("Wydatki", "root", 0.0, 4, 1));
		root.getChildren().addAll(aktywa, pasywa, przychody, wydatki);
		// tworzenie drzewa z listy wypisanej domyslnie
		for (Account acc : account) {
			TreeItem<Account> empLeaf = new TreeItem<Account>(acc);
			int type = acc.getType();
			if (acc.getParent().equals("Aktywa") || acc.getParent().equals("Pasywa")
					|| acc.getParent().equals("Przychody") || acc.getParent().equals("Wydatki")) {
				root.getChildren().get(acc.getType()-1).getChildren().add(empLeaf);
			} else {
				boolean found = false;
				for (TreeItem<Account> depNode : root.getChildren().get(type-1).getChildren()) {
					// jesli rodzic acc jest taki sam jak dziecko glownej kat.
					if (depNode.getValue().getName().contentEquals(acc.getParent())) {
						depNode.getChildren().add(empLeaf);
						found = true;
//						System.out.println(depNode.getValue());
						break;
					}
				}
				// chyba niepotrzebne
				if (!found) {
					TreeItem depNode = new TreeItem(acc);
					root.getChildren().get(type-1).getChildren().add(depNode);
					depNode.getChildren().add(empLeaf);
					System.out.println("not found in ini start");
				}
			}
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labStart.setText("instrukcja");
		readDefaultData();
		// wybieranie elementu z drzewa
		 treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
	        	@Override
	            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
	                TreeItem treeItem = (TreeItem)newValue;
	                System.out.println("Selected item is" + treeItem);
	            }
	        });
		
		root.setExpanded(true);
		treeView.setShowRoot(false);
		treeView.setEditable(true);

		treeView.setCellFactory(new Callback<TreeView<Account>, TreeCell<Account>>() {

			@Override
			public TreeCell<Account> call(TreeView<Account> param) {
				return new TreeCellImpl();
			}
		});
		treeView.setRoot(root);
		
	}

}