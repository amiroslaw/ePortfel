package model;

import java.io.File;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Profile {
	private String profileName;
	private String password;
	// private String walletName;
	// private String walletDirectoryPath;
	// private int idProfile;

	public static String walletDirectoryPath;
	public static String walletName;
	public static int idProfile;
	// private ArrayList<String> wallets = new ArrayList<String>();
	// public void setWallets(ArrayList<String> wallets) {
	// this.wallets = wallets;
	// }
	// public ArrayList<String> getWallets() {
	// return wallets;
	// }
	private ArrayList<String[]> wallets = new ArrayList<String[]>();

	public ArrayList<String[]> getWallets() {
		return wallets;
	}

	public void setWallets(ArrayList<String[]> wallets) {
		this.wallets = wallets;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWalletName() {
		return walletName;
	}

	// public void setWalletName(String walletName) {
	// this.walletName = walletName;
	// }

	public String getDirectoryPath() {
		return walletDirectoryPath;
	}

	// public void setDirectoryPath(String directoryPath) {
	// this.walletDirectoryPath = directoryPath;
	// }

	public void createProfileDB() {
		Connection conn = (Connection) ConnectionSqlite.Connector();
		if (conn == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conn.createStatement();

			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS profile (idProfile INTEGER PRIMARY KEY  UNIQUE  NOT NULL ,"
					+ " profileName TEXT NOT NULL, password TEXT NOT NULL)");
			// mySta.executeUpdate("CREATE TABLE IF NOT EXISTS wallet (idWallet
			// INTEGER PRIMARY KEY UNIQUE NOT NULL , walletName TEXT NOT NULL ,
			// filePath TEXT NOT NULL, idProfile INTEGER NOT NULL, FOREIGN
			// KEY(idProfile) REFERENCES profile(idProfile))");

			mySta.executeUpdate(
					"INSERT INTO profile (profileName, password) VALUES ('" + profileName + "','" + password + "')");
			// mySta.executeUpdate("INSERT INTO wallet (walletName, filePath,
			// idProfile) VALUES ('"+walletName+"','"+directoryPath+"',1)");

			ResultSet rs = mySta.executeQuery("SELECT COUNT(*) AS rowcount FROM profile");
			rs.next();
			idProfile = rs.getInt("rowcount");
			rs.close();
			System.out.println("MyTable has " + idProfile + " row(s).");

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createWalletDB() {
		Connection conn = (Connection) ConnectionSqlite.Connector();
		if (conn == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			System.out.println("profile " + profileName + " pass: " + password + " wallet " + walletName + " path: "
					+ walletDirectoryPath);
			Statement mySta = conn.createStatement();

			// mySta.executeUpdate("CREATE TABLE IF NOT EXISTS profile
			// (idProfile INTEGER PRIMARY KEY UNIQUE NOT NULL , profileName TEXT
			// NOT NULL , password TEXT NOT NULL)");
			mySta.executeUpdate(
					"CREATE TABLE IF NOT EXISTS wallet (idWallet INTEGER PRIMARY KEY  UNIQUE  NOT NULL , walletName TEXT NOT NULL ,"
							+ " filePath TEXT NOT NULL, idProfile INTEGER NOT NULL, FOREIGN KEY(idProfile) REFERENCES profile(idProfile))");

			// mySta.executeUpdate("INSERT INTO profile (profileName, password)
			// VALUES ('"+profileName+"','"+password+"')");
			mySta.executeUpdate("INSERT INTO wallet (walletName, filePath, idProfile) VALUES ('" + walletName + "','"
					+ walletDirectoryPath + "','" + idProfile + "')");

			conn.close();
		} catch (SQLException e) {
			System.out.println("exception: connection not successful");
			e.printStackTrace();
		}
	}

	public boolean isLogin() throws SQLException {
		Connection conn = (Connection) ConnectionSqlite.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM profile WHERE profileName = ? AND password = ?";
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, profileName);
			preparedStatement.setString(2, password);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				idProfile = resultSet.getInt("idProfile");
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}

	public void getWalletsDB() throws SQLException {
		Connection conn = (Connection) ConnectionSqlite.Connector();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT walletName, filePath FROM profile, wallet WHERE profile.idProfile=wallet.idProfile AND profile.profileName = ?";
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, profileName);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				wallets.add(new String[] { resultSet.getString("walletName"), resultSet.getString("filePath") });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}

	public void deleteProfile() {
		deleteWallet();
		Connection conn = (Connection) ConnectionSqlite.Connector();
		System.out.println("id profile delete " + idProfile);

		Statement mySta;
		try {
			mySta = conn.createStatement();
			String query = "DELETE FROM profile WHERE idProfile = " + idProfile;
			System.out.println(query);
			mySta.executeUpdate(query);
			mySta.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteWallet() {
		// TODO dodac wybór?
		try {
			File file = new File(walletName + "_ePortfel.sqlite");
			
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editProfile(String newData, int type) {
		Connection conn = (Connection) ConnectionSqlite.Connector();
		System.out.println("edit profile id" + idProfile);
		Statement mySta;
		String query="";
		try {
			mySta = conn.createStatement();
			if (type==1) {
			 query = "UPDATE profile SET profileName='" + newData+"' WHERE idProfile="+idProfile;
			} else {
			 query = "UPDATE profile SET password='" + newData+"' WHERE idProfile="+idProfile;
			}
			System.out.println(query);
			mySta.executeUpdate(query);
			
			mySta.close();
//		PreparedStatement preparedStatement = null;
//		String query = "UPDATE profile SET ? = ? WHERE ? = ?";
//		try {
//			preparedStatement = conn.prepareStatement(query);
//			if (type==1) {
//				preparedStatement.setString(1, "profileName");
//			} else {
//				preparedStatement.setString(1, "password");
//			}
//				preparedStatement.setString(2, newData);
//				preparedStatement.setString(3, "idProfile");
//			preparedStatement.setInt(4, idProfile);
//			
// System.out.println("prepaerd editProfile"+preparedStatement.toString());
//			preparedStatement.executeUpdate();
//
//				preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

}
