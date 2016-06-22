package model;

import java.util.ArrayList;

import view.MainManager;

public class Report {
	private MainManager manager;
	String title;
	ArrayList<Account> accountList;

	public Report(String title, ArrayList<Account> accountList) {
		this.title = title;
		this.accountList = accountList;

	}

	public Report(String title) {
		this.title = title;

	}

	String htmlHead = "<!DOCTYPE html> <html lang=\"pl\"> <head> <meta charset=\"utf-8\"> <title>" + title + "</title>"
			+ "<style type=\"text/css\"> table { margin: 0 auto 0 auto; } tr th, "
			+ "tr.important { font-weight: bold; font-size: large; } "
			+ "td { text-align: right; width: 100px; } td:first-child { width: 200px; text-align: left; } "
			+ "tr.important td{ border-top: 1px solid black; } </style> </head>";
	String htmlBody;

	double assetsSum = 0;
	double liabilitiesSum = 0;
	double expenseSum = 0;
	double incomeSum = 0;
	double creditSum = 0;
	double debetSum = 0;

	public String createHTML(int type) {
		htmlBody = " <body> <h3>" + title + "</h3>";
		switch (type) {
		case 1:
			htmlBody += " <table> <tr> <th>Aktywa</th><th></th><th></th><th></th> </tr>";
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 1) {
					assetsSum += accountList.get(i).getBalance();
					if (accountList.get(i).getBalance() < 0) {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td></td><td>"
								+ accountList.get(i).getBalance() + "zł</td><td></td> </tr>";
					} else {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td>"
								+ accountList.get(i).getBalance() + "zł</td><td></td><td></td> </tr>";
					}

				}
			}
			htmlBody += "	<tr class=\"important\"> <td>Aktywa razem</td><td></td><td></td><td>" + assetsSum
					+ "zł</td> </tr> </table> <hr />";
			htmlBody += " <table> <tr> <th>Pasywa</th><th></th><th></th><th></th> </tr>";
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 2) {
					liabilitiesSum += accountList.get(i).getBalance();
					if (accountList.get(i).getBalance() < 0) {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td></td><td>"
								+ accountList.get(i).getBalance() + "zł</td><td></td> </tr>";
					} else {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td>"
								+ accountList.get(i).getBalance() + "zł</td><td></td><td></td> </tr>";
					}

				}
			}
			htmlBody += "	<tr class=\"important\"> <td>Pasywa razem</td><td></td><td></td><td>" + liabilitiesSum
					+ "zł</td> </tr> </table></body></html>";

			break;
		case 2:
			htmlBody += " <table> <tr> <th>Nazwa konta</th><th>Winien</th><th>Ma</th><th></th> </tr>";
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 1 || accountList.get(i).getType() == 4) {
					debetSum += accountList.get(i).getBalance();
					// if (accountList.get(i).getBalance()<0) {
					htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td>"
							+ accountList.get(i).getBalance() + " zł</td><td></td> <td></td></tr>";
					// } else {
					// htmlBody += "<tr> <td>" + accountList.get(i).getName() +
					// "</td><td>"
					// + accountList.get(i).getBalance() +
					// "zł</td><td></td><td></td> </tr>";
					// }

				} else {
					creditSum += accountList.get(i).getBalance();
					// if (accountList.get(i).getBalance()<0) {
					htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td></td><td>"
							+ accountList.get(i).getBalance() + " zł</td><td></td> </tr>";
				}
			}
			//
			htmlBody += "	<tr class=\"important\"> <td>Suma</td><td>" + debetSum + " zł</td><td>" + creditSum
					+ " zł</td><td></td> </tr> </table></body></html>";
			break;
		case 3:
			htmlBody += " <table> <tr> <th>Przychody</th><th></th><th></th><th></th> </tr>";
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 3) {
					incomeSum += accountList.get(i).getBalance();
					if (accountList.get(i).getBalance() < 0) {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td></td><td>"
								+ accountList.get(i).getBalance() + "zł</td><td></td> </tr>";
					} else {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td>"
								+ accountList.get(i).getBalance() + "zł</td><td></td><td></td> </tr>";
					}
				}
			}
			htmlBody += "	<tr class=\"important\"> <td>Przychody razem</td><td></td><td></td><td>" + incomeSum
					+ "zł</td> </tr> </table> <hr />";
			htmlBody += " <table> <tr> <th>Wydatki</th><th></th><th></th><th></th> </tr>";
			for (int i = 0; i < accountList.size(); i++) {
				if (accountList.get(i).getType() == 4) {
					expenseSum += accountList.get(i).getBalance();
					if (accountList.get(i).getBalance() < 0) {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td></td><td>"
								+ accountList.get(i).getBalance() + " zł</td><td></td> </tr>";
					} else {
						htmlBody += "<tr> <td>" + accountList.get(i).getName() + "</td><td>"
								+ accountList.get(i).getBalance() + " zł</td><td></td><td></td> </tr>";
					}

				}
			}
			htmlBody += "	<tr class=\"important\"> <td>Wydatki razem</td><td></td><td></td><td>" + expenseSum
					+ " zł</td> </tr> <hr />";
			double profit = incomeSum-expenseSum;
			htmlBody += "	<tr class=\"important\"> <td>Zysk netto</td><td></td><td></td><td>"
					+ profit+" zł</td> </tr> </table></body></html>";
			break;
		default:
			htmlBody = "error";
			break;
		}
		return htmlHead + htmlBody;
	}

}
