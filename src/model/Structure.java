package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javafx.scene.control.TreeItem;

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

	public int searchIDtransfer(LocalDate date, String description, String accountTransfer) {
		for (int i = 0; i < map.get(accountTransfer).size(); i++) {
			if (map.get(accountTransfer).get(i).getDescription().equals(description)) {
				return map.get(accountTransfer).get(i).getId();
			}
		}
		System.out.println("nie znaleziono id");
		return -1;
	}

	public int searchTransferIndex(Integer id, String accountName) {
		for (int i = 0; i < map.get(accountName).size(); i++) {
			if (map.get(accountName).get(i).getId() == id) {
				return i;
			}
		}
		System.out.println("nie znaleziono index");
		return -1;
	}

	public int searchAccountType(String accName) {
		for (int i = 0; i < accList.size(); i++) {
			if (accList.get(i).getName().equals(accName)) {
				return accList.get(i).getType();
			}
		}
		return -1;
	}

	public void treeToList(TreeItem<Account> root) {
		for (TreeItem<Account> childRoot : root.getChildren()) {
			accList.add(childRoot.getValue());
			for (TreeItem<Account> nodes1 : childRoot.getChildren()) {
				accList.add(nodes1.getValue());
				for (TreeItem<Account> nodes2 : nodes1.getChildren()) {
					accList.add(nodes2.getValue());
				}
			}
		}
	}

	public TreeItem<Account> listToTree() {
		TreeItem<Account> root = new TreeItem<Account>(new Account("root", null, 0.0, 0, 0));
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
			} else {
				boolean found = false;
				for (TreeItem<Account> depNode : root.getChildren().get(type - 1).getChildren()) {
					if (depNode.getValue().getName().contentEquals(acc.getParent())) {
						depNode.getChildren().add(empLeaf);
						found = true;
						break;
					}
				}
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
		accList.get(indexOfAccount).setBalance(balance);
	}

	Connection connection;

	public void saveAccount() {
		connection = (Connection) ConnectionSqlite.Connector(Profile.walletDirectoryPath);
		if (connection == null) {
			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			mySta.executeUpdate("DELETE  FROM account");
			String query;
			String sql = "";
			for (int i = 0; i < accList.size(); i++) {
				query = "INSERT INTO account VALUES (" + accList.get(i).getIdAccount() + ",'" + accList.get(i).getName()
						+ "','" + accList.get(i).getParent() + "'," + accList.get(i).getBalance() + ","
						+ accList.get(i).getType() + ");";
				sql += query;
			}
			mySta.executeUpdate(sql);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void readAccount() {
		if (accList.size() == 0) {
			connection = (Connection) ConnectionSqlite.Connector(Profile.walletDirectoryPath);
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
			}
		}
	}

	public void readTransactions() {
		connection = (Connection) ConnectionSqlite.Connector(Profile.walletDirectoryPath);
		if (connection == null) {
			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			for (int i = 0; i < accList.size(); i++) {
				ResultSet rs = mySta
						.executeQuery("select * from transfer where accountName= '" + accList.get(i).getName() + "'");
				ArrayList<Transaction> transactions = new ArrayList<Transaction>();
				while (rs.next()) {
					LocalDate date = LocalDate.parse(rs.getString("date"));
					transactions.add(new Transaction(date, rs.getString("description"), rs.getString("accTransaction"),
							rs.getDouble("debet"), rs.getDouble("credit"), rs.getInt("idTransfer"),
							rs.getString("accountName")));
				}
				map.put(accList.get(i).getName(), transactions);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveTransactions() {
		connection = (Connection) ConnectionSqlite.Connector(Profile.walletDirectoryPath);
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			Set<String> keys = map.keySet();
			for (String key : keys) {
				mySta.executeUpdate("DELETE  FROM transfer WHERE accountName='" + key + "'");
				String query;
				String sql = "";
				for (int i = 0; i < map.get(key).size(); i++) {
					query = "INSERT INTO transfer VALUES (NULL,'" + map.get(key).get(i).getDate() + "'," + "'"
							+ map.get(key).get(i).getDescription() + "','" + map.get(key).get(i).getAccTransaction()
							+ "'," + map.get(key).get(i).getDebet() + "," + map.get(key).get(i).getCredit() + ",'" + key
							+ "');";
					sql += query;
				}
				mySta.executeUpdate(sql);
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void createTablesDB() {
		connection = (Connection) ConnectionSqlite.Connector(Profile.walletDirectoryPath);
		if (connection == null) {
			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS type (idType INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
					+ " typeName TEXT NOT NULL)");
			mySta.executeUpdate(
					"INSERT INTO type VALUES (1, 'aktywa'), (2, 'pasywa'), (3, 'przychody'), (4,'wydatki')");
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS account (idAccount INTEGER   NOT NULL , "
					+ "name TEXT  PRIMARY KEY NOT NULL  , parent TEXT NOT NULL , balance DOUBLE DEFAULT 0, "
					+ "idType INTEGER NOT NULL, FOREIGN KEY(idType) REFERENCES type(idType) )");
			mySta.executeUpdate(
					"CREATE TABLE IF NOT EXISTS  transfer (idTransfer INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
							+ "date DATETIME NOT NULL , description TEXT, accTransaction TEXT NOT NULL, debet DOUBLE DEFAULT 0, "
							+ "credit DOUBLE DEFAULT 0, accountName TEXT NOT NULL, FOREIGN KEY(accountName) REFERENCES account(name) )");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}