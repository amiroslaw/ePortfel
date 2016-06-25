package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Account;
import model.Structure;
import model.Transaction;

public class MainManager {
	private static Structure structure = new Structure();

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		MainManager.structure = structure;
	}

	private static ObservableList<Transaction> data = FXCollections.observableArrayList(
	// new Transaction(LocalDate.now(), "z main", "transfer", 0, 0, 0, 0),
	// new Transaction(LocalDate.now(), "z main", "transfer", 0, 0, 0, 0),
	// new Transaction(LocalDate.now(), "z main", "transfer", 0, 0, 0, 0)
	);

	public static ObservableList<Transaction> getTransactionData() {
		return data;
	}

	// private void setTransactionData(ArrayList<Transaction> tran){
	// ustawia liste transakcji w tablicy z mapy
	public void setTransactionData(String accountName) {
		data.clear();
		ArrayList<Transaction> tran = structure.getMap().get(accountName);
		for (int i = 0; i < tran.size(); i++) {
			data.add(tran.get(i));
		}
	}

	private BorderPane menuLayout;
	public final Stage primaryStage;
	String proba = new String();

	

	public void init() {
		showStart();
	}

	public MainManager(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

	public void showMenu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Menu.fxml"));
			menuLayout = (BorderPane) loader.load();

			Scene scene = new Scene(menuLayout);
			primaryStage.setTitle("ePortfel");
			primaryStage.setScene(scene);

			MenuController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);
			// controller.setStructure(structure);
			primaryStage.show();
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

	public void showRegistration() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(application.Main.class.getResource("/view/Registration.fxml"));
			AnchorPane apRegistration = (AnchorPane) loader.load();
			Scene scene = new Scene(apRegistration);

			primaryStage.setTitle("ePortfel");
			primaryStage.setScene(scene);
			RegistrationController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);

			primaryStage.show();
			// startStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLoginView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(application.Main.class.getResource("/view/Login.fxml"));
			AnchorPane apLogin = (AnchorPane) loader.load();
			Scene scene = new Scene(apLogin);

			primaryStage.setTitle("ePortfel");
			primaryStage.setScene(scene);
			LoginController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);

			primaryStage.show();
			// startStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showStart() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(application.Main.class.getResource("/view/Start.fxml"));
			BorderPane bpStart = (BorderPane) loader.load();
			Scene scene = new Scene(bpStart);

			// Stage startStage = new Stage();
			// startStage.setTitle("Tworzenie konta");
			// startStage.setScene(scene);
			primaryStage.setTitle("ePortfel");
			primaryStage.setScene(scene);
			StartController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);

			primaryStage.show();
			// startStage.show();
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
			dialogStage.setTitle("O programie");
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
	public void showEditProfile(int type) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/EditProfile.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Zmień dane");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			EditProfileController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			controller.init(type);

			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// public void showAddTransaction(ArrayList<Account> accList) {
	public void showAddTransaction(String acc, int idTransaction) {
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
			controller.getTransactionInfo(acc, idTransaction);
			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showAddTransaction(LocalDate date, String description, String transaction, String amount,
			int idTransaction, String acc) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/AddTransaction.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edytuj transakcje");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AddTransactionController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			controller.setInput(date, description, transaction, amount);
			controller.getTransactionInfo(acc, idTransaction);
			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showReport(String title, int type) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Report.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(title);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			ReportController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
			controller.initial(title, type);
			dialogStage.setResizable(true);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// nie uzywam
	// public void showMainView(TreeItem<Account> root, String pr) {
	// try {
	// FXMLLoader loader = new FXMLLoader();
	// loader.setLocation(application.Main.class.getResource("/view/MainView.fxml"));
	// ScrollPane mainView = (ScrollPane) loader.load();
	//
	// AnchorPane anchor = (AnchorPane) menuLayout.getChildren().get(1);
	// AnchorPane.setLeftAnchor(mainView, 10.0);
	// AnchorPane.setRightAnchor(mainView, 10.0);
	// AnchorPane.setTopAnchor(mainView, 10.0);
	// AnchorPane.setBottomAnchor(mainView, 10.0);
	// anchor.getChildren().add(mainView);
	//
	// menuLayout.setCenter(mainView);
	//
	// MainController controller = loader.getController();
	// controller.setRoot(root);
	// controller.setProba(pr);
	// controller.setManager(this);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	
//	public String getProba() {
//		return proba;
//	}
//
//	public void setProba(String s) {
//		proba = s;
//	}
	// chyba nie uzywam
//	private HashMap<String, ArrayList<Transaction>> mapTransaction = new HashMap<>();
//
//	public HashMap<String, ArrayList<Transaction>> getMapTransaction() {
//		return mapTransaction;
//	}
//
//	public void setMapTransaction(HashMap<String, ArrayList<Transaction>> mapTransaction) {
//		this.mapTransaction = mapTransaction;
//	}

}
