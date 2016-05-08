package application;

	

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import model.ConnectionSqlite;
import view.MainManager;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		MainManager manager = new MainManager(primaryStage);
		manager.init();
	}
	static Connection conn=  (Connection) ConnectionSqlite.Connector();
	static void createDB() {
		if (conn == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conn.createStatement();
			
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS tran (idTransaction INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , date DATETIME NOT NULL , description TEXT  , accTransaction TEXT NOT NULL, debet DOUBLE DEFAULT 0, credit DOUBLE DEFAULT 0, balance DOUBLE )");
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS account (idAccount INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , name TEXT NOT NULL , parent TEXT NOT NULL , balance DOUBLE DEFAULT 0, type INTEGER NOT NULL )");

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	public static void main(String[] args) {
		createDB();
		launch(args);
	}
}
