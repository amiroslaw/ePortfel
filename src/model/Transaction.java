package model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
	// dodac w jakim koncie ma byc ta transakcja - id i balance
	// id powinien byc punktowany w konstrukcie?
	// nie wiem czy zrezygnowac z podzialu na debet i credit
	private final SimpleObjectProperty<LocalDate> date;  
	private final SimpleStringProperty description;  
	private final SimpleStringProperty accTransaction;  
	private final SimpleDoubleProperty debet; 
	private final SimpleDoubleProperty credit; 
	private final SimpleDoubleProperty balance;
	private static int idTransaction=0;
	public Transaction(LocalDate date, String discripion, String accTransaction,
			double debet, double credit, double balance, int id) {
		this.date = new SimpleObjectProperty<LocalDate>(date);
		this.description = new SimpleStringProperty(discripion);
		this.accTransaction = new SimpleStringProperty(accTransaction);
		this.debet = new SimpleDoubleProperty(debet);
		this.credit = new SimpleDoubleProperty(credit);
		this.balance = new SimpleDoubleProperty(balance);
		this.idTransaction= id; 
	}
	public int getId(){
		return idTransaction; 
	}
	public LocalDate getDate() {
		return date.get();
	}
	public String getDescription() {
		return description.get();
	}
	public String getAccTransaction() {
		return accTransaction.get();
	}
	public double getDebet() {
		return debet.get();
	}
	public double getCredit() {
		return credit.get();
	}
	public double getBalance() {
		return balance.get();
	}
	public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
	public StringProperty descriptionProperty() {
        return description;
    }
	public StringProperty accTransactionProperty() {
        return accTransaction;
    }
	public DoubleProperty debetProperty() {
        return debet;
    }
	public DoubleProperty creditProperty() {
        return credit;
    }
	public DoubleProperty balanceProperty() {
        return balance;
    }
}
