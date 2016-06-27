package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Account {

	private final SimpleStringProperty name;

	private final SimpleStringProperty parent;

	private final SimpleDoubleProperty balance;

	private final int idAccount;

	private int type;

	public Account() {
		this.name = new SimpleStringProperty("cons name");
		this.parent = new SimpleStringProperty("root");
		this.balance = new SimpleDoubleProperty(0.0);
		this.type = 1;
		this.idAccount = 66;
	}

	public Account(String name, String parent, Double balance, int type, int id) {
		this.name = new SimpleStringProperty(name);
		this.parent = new SimpleStringProperty(parent);
		this.balance = new SimpleDoubleProperty(balance);
		this.type = type;
		this.idAccount = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIdAccount() {
		return idAccount;
	}

	public Double getBalance() {
		return balance.get();
	}

	public void setBalance(Double fBalance) {
		balance.set(fBalance);
	}

	public DoubleProperty balanceProperty() {
		return balance;
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

	@Override
	public String toString() {
		return getName();
	}
}
