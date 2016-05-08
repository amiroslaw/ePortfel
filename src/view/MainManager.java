package view;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;
import application.Main;

public class MainManager {
	private BorderPane menuLayout;
	public final Stage primaryStage; 
	
	public void init (){
		System.err.println("FXML resource: " + Main.class.getResource("/view/MenuView.fxml"));
		System.err.println("FXML resource2: " + getClass().getResource("/view/MenuView.fxml"));
//		showMenu();
//		showMainView();
		showStart();
	}
	public MainManager(Stage primaryStage) {
		this.primaryStage= primaryStage; 
		
	}
	public void showMenu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Menu.fxml"));
			menuLayout = (BorderPane) loader.load();

			Scene scene = new Scene(menuLayout);
			primaryStage.setScene(scene);

			MenuController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showMainView(TreeItem<Account> root, String pr) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(application.Main.class.getResource("/view/MainView.fxml"));
			ScrollPane mainView = (ScrollPane) loader.load(); 
			
			AnchorPane anchor = (AnchorPane) menuLayout.getChildren().get(1);
			AnchorPane.setLeftAnchor(mainView, 10.0);
			AnchorPane.setRightAnchor(mainView, 10.0);
			AnchorPane.setTopAnchor(mainView, 10.0);
			AnchorPane.setBottomAnchor(mainView, 10.0);
			anchor.getChildren().add(mainView);
			MainController controller = loader.getController();
//			controller.setRoot(root);
//			controller.setProba(pr);
			controller.setManager(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(application.Main.class.getResource("/view/MainView.fxml"));
			ScrollPane mainView = (ScrollPane) loader.load(); 
			
			AnchorPane anchor = (AnchorPane) menuLayout.getChildren().get(1);
			AnchorPane.setLeftAnchor(mainView, 10.0);
			AnchorPane.setRightAnchor(mainView, 10.0);
			AnchorPane.setTopAnchor(mainView, 10.0);
			AnchorPane.setBottomAnchor(mainView, 10.0);
			anchor.getChildren().add(mainView);
			MainController controller = loader.getController();
			controller.setManager(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showStart() {
	try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(application.Main.class.getResource("/view/Start.fxml"));
		BorderPane bpStart = (BorderPane) loader.load();
//		 bpStart = (BorderPane) loader.load();

		Scene scene = new Scene(bpStart);
		
//		Stage startStage = new Stage(); 
//		startStage.setTitle("Tworzenie konta");
//		startStage.setScene(scene);
		primaryStage.setScene(scene);
		StartController controller = loader.getController();
		controller.setPrimaryStage(this.primaryStage);
		controller.setManager(this);
		
		primaryStage.show();
//		startStage.show();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	public void showAboutDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/About.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("About");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AboutController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			
			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showAddTransaction() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/AddTransaction.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Dodaj transakcje");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AddTransactionController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			
			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
