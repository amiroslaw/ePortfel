package model;

import com.sun.javafx.tk.Toolkit.Task;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Asset {

//		StringProperty balance = new SimpleStringProperty(); 
//		public StringProperty getBalance() {
//			return balance;
//		}
//		public void setBalance(StringProperty balance) {
//			this.balance = balance;
//		}
//		ObservableList<Asset> assetName = FXCollections.observableArrayList();
	   private final SimpleStringProperty name;
	    private final SimpleStringProperty parent;
	    private final SimpleDoubleProperty balance;
//
//	    public SimpleStringProperty nameProperty() {
//	      if (name == null) {
//	        name = new SimpleStringProperty(this, "name");
//	      }
//	      return name;
//	    }
//
//	    public StringProperty balanceProperty() {
//	      if (balance == null) {
//	        balance = new SimpleStringProperty(this, "balance");
//	      }
//	      return balance;
//	    }
//	    public Asset (String name){
//	    	
//	    }
//
//	    public Asset(String name, String parent) {
//	      this.name = new SimpleStringProperty(name);
//	      this.parent = new SimpleStringProperty(parent);
//	    }
	    public Asset(String name, String parent, Double balance) {
	      this.name = new SimpleStringProperty(name);
	      this.parent = new SimpleStringProperty(parent);
	      this.balance = new SimpleDoubleProperty(balance);
	    }
	    public Double getBalance() {
	      return balance.get();
	    }

	    public void setBalance(Double fBalance) {
	      balance.set(fBalance);
	    }
	    public String getName() {
	      return name.get();
	    }

	    public void setName(String fName) {
	      name.set(fName);
	    }

	    public String getParent() {
	      return parent.get();
	    }

	    public void setParent(String fParent) {
	      parent.set(fParent);
	    }
}
