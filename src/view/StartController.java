package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;

public class StartController implements Initializable {
	private MainManager manager;

	public void setManager(MainManager manager) {
		this.manager = manager;

	}

	// private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
		// this.primaryStage = primaryStage;
	}

	@FXML
	void showMain() {
		// for (Asset asset : assets) {
		// System.out.println(asset.getName()+" parent "+asset.getParent());
		// }
		// private void printChildren(TreeItem<String> root){
		// System.out.println("Current Parent :" + root.getValue());
		// for(TreeItem<String> child: root.getChildren()){
		// if(child.getChildren().isEmpty()){
		// System.out.println(child.getValue());
		// } else {
		// printChildren(child);
		// }
		// }
		// }

		int id = 0;
		ArrayList<Account> accList = new ArrayList<Account>();
		for (int i = 0; i < 4; i++) {
			accList.add(new Account(root.getChildren().get(i).getValue(), "root", 0.0, i, id));
			id++;
			for (int j = 0; j < root.getChildren().get(i).getChildren().size(); j++) {
				accList.add(new Account(root.getChildren().get(i).getChildren().get(j).getValue(),
						root.getChildren().get(i).getValue(), 0.0, i, id));
				id++;
				if (!root.getChildren().get(i).getChildren().get(j).isLeaf()) {
					for (int k = 0; k < root.getChildren().get(i).getChildren().get(j).getChildren().size(); k++) {
						accList.add(new Account(
								root.getChildren().get(i).getChildren().get(j).getChildren().get(k).getValue(),
								root.getChildren().get(i).getChildren().get(j).getValue(), 0.0, i, id));
						id++;
					}
				}
			}
			root.getChildren().size();

		}
		for (Account account : accList) {
			System.out.println(account.getName() + " " + account.getParent() + " " + account.getIdAccount());
		}
		System.out.println("root children" + treeView.getRoot().getChildren());
		manager.showMenu();
		manager.showMainView();
	}

	@FXML
	private Label labStart;
	@FXML
	private Button btnShowMain;
	@FXML
	private TreeView<String> treeView;

	List<Account> account = Arrays.<Account> asList(new Account("Aktywa bieżące", "Aktywa", 0.0, 0, 0),
			new Account("Gogówka", "Aktywa bieżące", 0.0, 0, 1),
			new Account("Konto bankowe", "Aktywa bieżące", 0.0, 0, 2), new Account("Inwestycje", "Aktywa", 0.0, 0, 3),
			new Account("Lokaty", "Inwestycje", 0.0, 0, 4), new Account("Akcje", "Inwestycje", 0.0, 0, 5),
			new Account("Obligacje", "Inwestycje", 0.0, 0, 6), new Account("Karta kredytowa", "Pasywa", 0.0, 1, 7),
			new Account("Kredyty", "Pasywa", 0.0, 1, 8), new Account("Pensja", "Przychody", 0.0, 2, 9),
			new Account("Inne przychody", "Przychody", 0.0, 2, 10),
			new Account("Przychód z odsetek", "Przychody", 0.0, 2, 11), new Account("Premia", "Przychody", 0.0, 2, 12),
			new Account("Odsetki z lokat", "Przychód z odsetek", 0.0, 2, 13),
			new Account("Odsetki z akcji", "Przychód z odsetek", 0.0, 2, 14),
			new Account("Czynsz", "Wydatki", 0.0, 3, 15), new Account("Hobby", "Wydatki", 0.0, 3, 16),
			new Account("Jedzenie", "Wydatki", 0.0, 3, 17), new Account("Odsetki", "Wydatki", 0.0, 3, 18),
			new Account("Odsetki z kredytów", "Odsetki", 0.0, 3, 19), new Account("Podatki", "Wydatki", 0.0, 3, 20),
			new Account("Krajowe", "Podatki", 0.0, 3, 21), new Account("Socjalne", "Podatki", 0.0, 3, 22),
			new Account("Wydatki medyczne", "Wydatki", 0.0, 3, 23));

	final TreeItem<String> root = new TreeItem<>("root");
	// final TreeItem<Asset> root = new TreeItem<>(new Asset( "Aktywa", "" ));
//	TreeView <String> getTree(){
//		return treeView; 
//	}
	public TreeItem <String> getTree(){
		return root; 
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(root.getChildren().size());
		labStart.setText("przykładowy tekst:" + account.get(0).getType());
		if ("".equals(null)) {
			System.out.println("puste == null");
		} else {
			System.out.println("nie równe");
		}
		TreeItem<String> aktywa = new TreeItem<String>("Aktywa");
		TreeItem<String> pasywa = new TreeItem<String>("Pasywa");
		TreeItem<String> przychody = new TreeItem<String>("Przychody");
		TreeItem<String> wydatki = new TreeItem<String>("Wydatki");

		root.getChildren().addAll(aktywa, pasywa, przychody, wydatki);
		for (Account acc : account) {
			TreeItem<String> empLeaf = new TreeItem<String>(acc.getName());
			int type = acc.getType();
			if (acc.getParent().equals("Aktywa") || acc.getParent().equals("Pasywa")
					|| acc.getParent().equals("Przychody") || acc.getParent().equals("Wydatki")) {
				root.getChildren().get(acc.getType()).getChildren().add(empLeaf);
			} else {
				boolean found = false;
				for (TreeItem<String> depNode : root.getChildren().get(type).getChildren()) {
					// jesli rodzic acc jest taki sam jak dziecko glownej kat.
					if (depNode.getValue().contentEquals(acc.getParent())) {
						depNode.getChildren().add(empLeaf);
						found = true;
						System.out.println(depNode.getValue());
						break;
					}
				}
				if (!found) {
					TreeItem depNode = new TreeItem(acc.getParent());
					root.getChildren().get(type).getChildren().add(depNode);
					depNode.getChildren().add(empLeaf);
				}

			}
			// switch (acc.getParent()) {
			// case "Aktywa":
			//
			// break;
			//
			// default:
			// break;
			// }
		}
		root.setExpanded(true);
		treeView.setShowRoot(false);
		treeView.setEditable(true);
		treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			@Override
			public TreeCell<String> call(TreeView<String> p) {
				return new TreeCellImpl();
			}
		});
		treeView.setRoot(root);

	}

}