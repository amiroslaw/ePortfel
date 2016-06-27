package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSqlite {
	
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:eportfelMainDB.sqlite");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static Connection Connector(String pathDB) {
		try {
			String path = pathDB + "/" + Profile.walletName + "_ePortfel.sqlite";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
