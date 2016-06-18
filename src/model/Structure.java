package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javafx.scene.control.TreeItem;
import view.MainManager;

public class Structure {
	private HashMap<String, ArrayList<Transaction>> map = new HashMap<String, ArrayList<Transaction>>();

	public HashMap<String, ArrayList<Transaction>> getMap() {
		return map;
	}

	public void setMap(HashMap<String, ArrayList<Transaction>> map) {
		this.map = map;
	}

	private ArrayList<Account> accList = new ArrayList<Account>();

	public ArrayList<Account> getAccList() {
		return accList;
	}

	public void setAccList(ArrayList<Account> accList) {
		this.accList = accList;
	}

//	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
//
//	public ArrayList<Transaction> getTransactionList() {
//		return transactionList;
//	}
//
//	public void setTransactionList(ArrayList<Transaction> transactionList) {
//		this.transactionList = transactionList;
//	}

//	int id;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
	public int searchIDtransfer(LocalDate date, String description, String accountTransfer){
//		ArrayList<Transaction> transactionList = new ArrayList<>(manager.getStructure().getMap().get(accountTransfer));
		System.out.println("size map"+map.get(accountTransfer).size());
		System.out.println("acc name "+accountTransfer);
		System.out.println("desc "+description);
		for (int i = 0; i < map.get(accountTransfer).size(); i++) {
//			if(map.get(accountTransfer).get(i).getDate().equals(date)&& map.get(accountTransfer).get(i).equals(description)){
			System.out.println("for map "+map.get(accountTransfer).get(i).getDescription());
			if(map.get(accountTransfer).get(i).getDescription().equals(description)){
				return map.get(accountTransfer).get(i).getId();
//				return i; 
			}
		}
		System.out.println("nie znaleziono id");
		return -1;
	} 
	public int searchTransferIndex(Integer id, String accountName){
//		ArrayList<Transaction> transactionList = new ArrayList<>(manager.getStructure().getMap().get(accountTransfer));
		for (int i = 0; i < map.get(accountName).size(); i++) {
			if(map.get(accountName).get(i).getId()==id){
				return i; 
			}
		}
		System.out.println("nie znaleziono index");
		return -1;
	} 
//	public int searchTransferIndex(String accountName){
////		ArrayList<Transaction> transactionList = new ArrayList<>(manager.getStructure().getMap().get(accountTransfer));
//		for (int j = 0; j < map.size(); j++) {
//			
//		
//		for (int i = 0; i < map.get(accountName).size(); i++) {
//			if(map.get(accountName).get(i).getId()==id){
//				return i; 
//			}
//		}
//		}
//		return -1;
//	} 
	public int searchAccountType(String accName){
//		ArrayList<Account> accList = new ArrayList<>(manager.getStructure().getAccList());
		for (int i = 0; i < accList.size(); i++) {
			if(accList.get(i).getName().equals(accName)){
				return accList.get(i).getType(); 
			}
		}
		return -1;
	} 
	// nie powinno byc return arrayList?
	public void treeToList(TreeItem<Account> root) {
		// accList.add(root.getValue());
		for (TreeItem<Account> childRoot : root.getChildren()) {
//			System.out.println("treeToList" + childRoot);
			accList.add(childRoot.getValue());
			for (TreeItem<Account> nodes1 : childRoot.getChildren()) {
//				System.out.println("treeToList" + nodes1);
				accList.add(nodes1.getValue());
				for (TreeItem<Account> nodes2 : nodes1.getChildren()) {
//					System.out.println("treeToList" + nodes2);
					accList.add(nodes2.getValue());

				}
			}
		}
	}

	public TreeItem<Account> listToTree() {
		TreeItem<Account> root = new TreeItem<Account>(new Account("root", null, 0.0, 0, 0));
		// dodanie glownych kategorii
		for (Account account : accList) {
			if (account.getParent().equals("root")) {
				TreeItem<Account> mainCategory = new TreeItem<Account>(account);
				root.getChildren().add(mainCategory);
			}
		}

		for (Account acc : accList) {
			TreeItem<Account> empLeaf = new TreeItem<Account>(acc);
			int type = acc.getType();
			if (acc.getParent().equals("Aktywa") || acc.getParent().equals("Pasywa")
					|| acc.getParent().equals("Przychody") || acc.getParent().equals("Wydatki")) {
				root.getChildren().get(acc.getType() - 1).getChildren().add(empLeaf);
//				System.out.println( "listToTree: 1s " + empLeaf.getValue().getName() + " typ: " + empLeaf.getValue().getType());
			} else {
				boolean found = false;
				for (TreeItem<Account> depNode : root.getChildren().get(type - 1).getChildren()) {
					// jesli rodzic acc jest taki sam jak dziecko glownej kat.
					// np. dodanie lokaty jesli sa podpiete inwestycje
					if (depNode.getValue().getName().contentEquals(acc.getParent())) {
						depNode.getChildren().add(empLeaf);
						found = true;
//						System.out.println("listToTree: 2s " + empLeaf.getValue().getName() + " typ: " + empLeaf.getValue().getType());
						break;
					}
				}
				// nie wiem czy dziala niepotrzebne jesli wszystko jest po
				// koleji nie ma elementu wyzej w hierarchi wczesniej na liscie
				// trzeba ten element przenisc na koniec listy
				// if (!found) {
				// TreeItem depNode = new TreeItem(acc);
				// root.getChildren().get(type - 1).getChildren().add(depNode);
				// depNode.getChildren().add(empLeaf);
				// System.out.println(
				// "listToTree: ?? " + empLeaf.getValue().getName() + " typ: " +
				// empLeaf.getValue().getType());
				// }

			}

		}

		return root;
	}

	public void updateBalance(String accName) {
		int indexOfAccount = -1;
		
		for (int i = 0; i < accList.size(); i++) {
			if (accList.get(i).getName().equals(accName)) {
				indexOfAccount = i;
			}
			if (indexOfAccount != -1) {
				break;
			}
		}
		ArrayList<Transaction> tranList = new ArrayList<Transaction>(map.get(accName));
		double balance = 0.0;
		for (int i = 0; i < tranList.size(); i++) {
			balance += tranList.get(i).getCredit();
			balance -= tranList.get(i).getDebet();
		}
		System.out.println("updateBalance balance"+ balance );
		accList.get(indexOfAccount).setBalance(balance);
	}

	Connection connection;

	// public void saveTree(ArrayList<Account> list) {
	public void saveAccount() {
		connection = (Connection) ConnectionSqlite.Connector(MainManager.walletDirectoryPath);
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			// usuwanie kont
			mySta.executeUpdate("DELETE  FROM account");
			// // zapisywanie zmiennych do kont
			String query;
			String sql = "";
			System.out.println("size acclist"+accList.size());
			for (int i = 0; i < accList.size(); i++) {
//				System.out.println("save acc "+accList.get(i).getName());
				query = "INSERT INTO account VALUES (" + accList.get(i).getIdAccount() + ",'" + accList.get(i).getName() + "','"
						+ accList.get(i).getParent() + "'," + accList.get(i).getBalance() + ","
						+ accList.get(i).getType() + ");";
				sql += query;
			}
			System.out.println(sql);
			mySta.executeUpdate(sql);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void readAccount() {
		if(accList.size()==0){
		connection = (Connection) ConnectionSqlite.Connector(MainManager.walletDirectoryPath);
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from account");
			while (rs.next()) {
				accList.add(new Account(rs.getString("name"), rs.getString("parent"), rs.getDouble("balance"),
						rs.getInt("idType"), rs.getInt("idAccount")));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}}
			
		
	}

	public void readTransactions() {
		connection = (Connection) ConnectionSqlite.Connector(MainManager.walletDirectoryPath);
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {

			Statement mySta = connection.createStatement();
			for (int i = 0; i < accList.size(); i++) {
				ResultSet rs = mySta.executeQuery("select * from transfer where accountName= '" + accList.get(i).getName() + "'");
				ArrayList<Transaction> transactions = new ArrayList<Transaction>();
				while (rs.next()) {
					LocalDate date = LocalDate.parse(rs.getString("date"));
					transactions.add(new Transaction(date, rs.getString("description"), rs.getString("accTransaction"),
							rs.getDouble("debet"), rs.getDouble("credit"), 
							rs.getInt("idTransfer"), rs.getString("accountName")));
				}
				map.put(accList.get(i).getName(), transactions);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveTransactions() {
		connection = (Connection) ConnectionSqlite.Connector(MainManager.walletDirectoryPath);
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			// Get keys.
			Set<String> keys = map.keySet();

			// Loop over String keys.
			for (String key : keys) {
//				System.out.println(key);
				mySta.executeUpdate("DELETE  FROM transfer WHERE accountName='" + key + "'");

				String query;
				String sql = "";
				for (int i = 0; i < map.get(key).size(); i++) {

					query = "INSERT INTO transfer VALUES (NULL,'" + map.get(key).get(i).getDate() + "'," + "'"
							+ map.get(key).get(i).getDescription() + "','" + map.get(key).get(i).getAccTransaction()
							+ "'," + map.get(key).get(i).getDebet() + "," + map.get(key).get(i).getCredit() + ",'"+key+ "');";
					sql += query;
				}
				System.out.println(sql);
				mySta.executeUpdate(sql);
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	 public void createTablesDB() {
		connection = (Connection) ConnectionSqlite.Connector(MainManager.walletDirectoryPath);
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			// type
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS type (idType INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
					+ " typeName TEXT NOT NULL)");
			// // zapisywanie zmiennych do kont
			mySta.executeUpdate("INSERT INTO type VALUES (1, 'aktywa'), (2, 'pasywa'), (3, 'przychody'), (4,'wydatki')");

//			account
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS account (idAccount INTEGER   NOT NULL , "
					+ "name TEXT  PRIMARY KEY NOT NULL  , parent TEXT NOT NULL , balance DOUBLE DEFAULT 0, "
					+ "idType INTEGER NOT NULL, FOREIGN KEY(idType) REFERENCES type(idType) )");
				
//			transfer
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS  transfer (idTransfer INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
					+ "date DATETIME NOT NULL , description TEXT, accTransaction TEXT NOT NULL, debet DOUBLE DEFAULT 0, "
					+ "credit DOUBLE DEFAULT 0, accountName TEXT NOT NULL, FOREIGN KEY(accountName) REFERENCES account(name) )");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public String test = "structure";

	public void proba() {
		System.out.println("structure " + accList.get(0).getName());
	}

	public void showList() {
		System.out.println("showList u update balance");
		for (Account account : accList) {
			System.out.println(account.getName());
//			updateBalance(account.getName());
		}
	}

	public void showMap() {
		System.out.println("showMap");

		// Get all values from the HashMap.
		Collection<ArrayList<Transaction>> values = map.values();
		for (ArrayList<Transaction> ar : values) {
			// System.out.println(ar);
//			if (ar.size() != 0) {
			for (int i = 0; i < ar.size(); i++) {
				System.out.println("desc " + ar.get(i).getDescription() + " transfer: "
						+ ar.get(i).getAccTransaction() + " data: " + ar.get(i).getDate()+ " id: "+ ar.get(i).getId()+ " acc: "+ar.get(i).getAccountName());
			}
		}
		// Loop over String keys.
		//
		// Set<String> keys = map.keySet();
		// for (String key : keys) {
		// System.out.println(key);
		// ArrayList<Transaction> ar = map.get(key);
		// if(ar.get(0)!=null){
		// System.out.println("pierwsza tran- des:
		// "+ar.get(0).getDescription()+" tran: "+ ar.get(0).getAccTransaction()
		// +" data: " +ar.get(0).getDate());
		// }
		// }
	}
}