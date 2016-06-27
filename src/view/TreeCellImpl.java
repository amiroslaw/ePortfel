package view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Account;

final class TreeCellImpl extends TreeCell<Account> {

	private TextField textMenu;
	private ContextMenu contextMenu = new ContextMenu();
	private MenuItem addItem;
	private MenuItem deleteItem;

	@SuppressWarnings("unchecked")
	public TreeCellImpl() {
		addItem = new MenuItem("Dodaj rachunek");
		contextMenu.getItems().add(addItem);
		addItem.setOnAction(new EventHandler() {
			public void handle(Event t) {
				TreeItem newAccount = new TreeItem<Account>(new Account("nowy rachunek",
						getTreeItem().getValue().getName(), 0.0, getTreeItem().getValue().getType(), 1));
				getTreeItem().getChildren().add(newAccount);
			}
		});
		deleteItem = new MenuItem("Usuń rachunek");
		contextMenu.getItems().add(deleteItem);
		deleteItem.setOnAction(new EventHandler() {
			public void handle(Event t) {

				if (getTreeItem().getParent().getParent() != null) {
					TreeItem<Account> parent = getTreeItem().getParent();
					parent.getChildren().remove(getTreeItem());
				} else {

					System.out.println("Nie można usunąć tego elementu");
				}
			}
		});
	}

	@Override
	public void startEdit() {
		super.startEdit();

		if (textMenu == null) {
			createTextField();
		}
		setText(null);
		setGraphic(textMenu);
		textMenu.selectAll();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		setText((String) getItem().getName());
		setGraphic(getTreeItem().getGraphic());
	}

	@Override
	public void updateItem(Account item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textMenu != null) {
					textMenu.setText(getString());
				}
				setText(null);
				setGraphic(textMenu);
			} else {
				setText(getString());
				setGraphic(getTreeItem().getGraphic());
				setContextMenu(contextMenu);

				if (getTreeItem().getParent().getParent() != null) {
					if (getTreeItem().getParent().getParent().getParent() != null) {
						if (getTreeItem().getParent().getParent().getParent().getParent() == null) {
							addItem.setDisable(true);
						} else {
							addItem.setDisable(false);
						}
					}
				}
				// blokowanie usuwania kont glownych
				if (getTreeItem().getParent().getParent() == null) {
					deleteItem.setDisable(true);
				} else {
					deleteItem.setDisable(false);
				}
			}
		}
	}

	private void createTextField() {
		textMenu = new TextField(getString());
		textMenu.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(new Account(textMenu.getText(), getTreeItem().getParent().getValue().getName(), 0.0,
							getTreeItem().getParent().getValue().getType(), 0));
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});

	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}