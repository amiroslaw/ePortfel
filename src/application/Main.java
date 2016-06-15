package application;

	

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import model.ConnectionSqlite;
import view.MainManager;


public class Main extends Application {
	static boolean firstRead; 
	@Override
	public void start(Stage primaryStage) {
		MainManager manager = new MainManager(primaryStage);
		if(firstRead){
//			manager.showStart();			
			manager.showRegistration();		
			
		} else {
			manager.showLoginView();
			
		}
	}
	static Connection conn=  (Connection) ConnectionSqlite.Connector();
	static void createDB() {
		if (conn == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conn.createStatement();
			
//			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS tran (idTransaction INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , date DATETIME NOT NULL , description TEXT  , accTransaction TEXT NOT NULL, debet DOUBLE DEFAULT 0, credit DOUBLE DEFAULT 0, balance DOUBLE )");
//			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS tran (idTransaction INTEGER PRIMARY KEY  UNIQUE  NOT NULL , date DATETIME NOT NULL , description TEXT  , accTransaction TEXT NOT NULL, debet DOUBLE DEFAULT 0, credit DOUBLE DEFAULT 0, balance DOUBLE )");
//			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS account (idAccount INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT NOT NULL , parent TEXT NOT NULL , balance DOUBLE DEFAULT 0, type INTEGER NOT NULL )");
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS config (firstRead BOOL NOT NULL DEFAULT TRUE)");
//			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS account (idAccount INTEGER PRIMARY KEY  UNIQUE  NOT NULL , name TEXT NOT NULL , parent TEXT NOT NULL , balance DOUBLE DEFAULT 0, type INTEGER NOT NULL )");
			
			ResultSet rs = mySta.executeQuery("SELECT * FROM config WHERE rowid=1");
			//sprawdzanie czy jest rekord jesli nie ma to nie bylo utworzonej bazy
			if (rs.next()) {
				firstRead= false;
				System.out.println(rs.getBoolean("firstRead"));
			} else {
				firstRead= true;
				System.out.println("sql null");
			mySta.executeUpdate("INSERT INTO config (firstRead) VALUES (1)");	
			}
//			mySta.executeUpdate("INSERT INTO config WHERE id=1 (firstRead) VALUES ('FALSE')");	
//			mySta.executeUpdate("UPDATE config SET firstRead='FALSE'");	

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	public static void main(String[] args) {
		// pierwsze wczyta się layout czy utworzy DB
		createDB();
		launch(args);
	}
}
