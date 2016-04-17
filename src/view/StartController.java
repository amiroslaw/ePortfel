package view;

import java.net.URL;
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
import model.Asset;

public class StartController  implements Initializable  {
	private MainManager manager;
	public void setManager(MainManager manager) {
		this.manager = manager;

	}
//	private Stage primaryStage; 
	public void setPrimaryStage(Stage primaryStage) {
//		this.primaryStage = primaryStage;
	}
	
	@FXML
	void showMain(){
		System.out.println( assets.toString());
//		for (Asset asset : assets) {
//			System.out.println(asset.getName()+" parent "+asset.getParent());
//		}
		System.out.println(treeView.getRoot().getChildren());
		manager.showMenu();
		manager.showMainView();
	}
	@FXML
	private Label labStart;
	@FXML
	private Button btnShowMain; 
	@FXML
	private   TreeView <String> treeView;
	 
	List<Asset> assets = Arrays.<Asset> asList(
			new Asset( "Emma Jones", "1", 0.0),
			new Asset("Michael Brown", "1", 0.0), 
			new Asset("Anna Black", "2", 0.0),
			new Asset("Rodger York", "2", 0.0),
			new Asset("Susan Collins", "2", 0.0)
			);

		  final TreeItem<String> root = new TreeItem<>("Aktywa" );
//		  final TreeItem<Asset> root = new TreeItem<>(new Asset( "Aktywa", "" ));

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			labStart.setText("przykładowy tekst:");

			
			root.setExpanded(true);
			//poprawic na asd trzewa
			for (Asset asset : assets) {
			    TreeItem<String> empLeaf = new TreeItem<String>(asset.getName());
			    boolean found = false;
			    for (TreeItem<String> depNode : root.getChildren()) {
			        if (depNode.getValue().contentEquals(asset.getParent())){
			            depNode.getChildren().add(empLeaf);
			            found = true;
			            System.out.println(depNode.getValue());
			            break;
			        }
			    }
			    if (!found) {
			        TreeItem depNode = new TreeItem(asset.getParent());
			        root.getChildren().add(depNode);
			        depNode.getChildren().add(empLeaf);
			    }
					}
			treeView.setShowRoot(true);
			treeView.setEditable(true);
			treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>(){
			    @Override
			    public TreeCell<String> call(TreeView<String> p) {
			        return new TreeCellImpl();
			    }
			});
//			startBorderPane.setLeft(treeView);
			treeView.setRoot(root);
			}

}