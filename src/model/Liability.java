package model;

public class Liability {

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


	   private final SimpleStringProperty name;
	    private final SimpleStringProperty parent;
	    private final SimpleDoubleProperty balance;

	    public Liability(String name, String parent, Double balance) {
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