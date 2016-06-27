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
	
	static Connection conn=  (Connection) ConnectionSqlite.Connector();
	
	public static void main(String[] args) {
		createDB();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		MainManager manager = new MainManager(primaryStage);
		if(firstRead){
			manager.showRegistration();		
			
		} else {
			manager.showLoginView();
			
		}
	}
	
	static void createDB() {
		if (conn == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conn.createStatement();
			
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS config (firstRead BOOL NOT NULL DEFAULT TRUE)");
			
			ResultSet rs = mySta.executeQuery("SELECT * FROM config WHERE rowid=1");
			if (rs.next()) {
				firstRead= false;
				System.out.println(rs.getBoolean("firstRead"));
			} else {
				firstRead= true;
				System.out.println("sql null");
			mySta.executeUpdate("INSERT INTO config (firstRead) VALUES (1)");	
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
}
