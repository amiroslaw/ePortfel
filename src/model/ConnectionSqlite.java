package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionSqlite {
	public static Connection Connector() {
		try {
			String path= System.getProperty("user.home"); 
			path +="/Dokumenty/Ustawienia/sync/grywalizacja.sqlite";
			Class.forName("org.sqlite.JDBC");
			//mozna dodac sciezke do w ktorej chce sie trzymac plik sqlite
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path);
			Connection conn = DriverManager.getConnection("jdbc:sqlite:eportfel.sqlite");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
