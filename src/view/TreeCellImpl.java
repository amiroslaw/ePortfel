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

final class TreeCellImpl extends TreeCell<String> {

    private TextField textMenu;
    private ContextMenu addMenu = new ContextMenu();

    @SuppressWarnings("unchecked")
	public TreeCellImpl() {
        MenuItem addItem = new MenuItem("Dodaj rachunek");
        addMenu.getItems().add(addItem);
        addItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                TreeItem newAccount = 
                    new TreeItem<String>("Nowy rachunek");
                getTreeItem().getChildren().add(newAccount);            
                
            
                
//            	System.out.println(getTreeItem().getParent().getValue().equals("Aktywa")+" -value parent");
            }
        });
        MenuItem deleteItem = new MenuItem("Usuń rachunek");
        addMenu.getItems().add(deleteItem);
        deleteItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	
                if (getTreeItem().getParent()!= null){                        
            	TreeItem<String> parent = getTreeItem().getParent(); 
                parent.getChildren().remove(getTreeItem());                 
                } else{

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

        setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(String item, boolean empty) {
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
//                if(!(getTreeItem().getParent()!=null&&!getTreeItem().getParent().getValue().equals("Aktywa"))){
                //maksymalnie 2 poziomy
                if(getTreeItem().getParent()!=null){
                    TreeItem<String> tempParent = getTreeItem().getParent(); 
                    	if(tempParent.getParent()==null){
							setContextMenu(addMenu);
							}
                    } else{
							setContextMenu(addMenu);
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
                    commitEdit(textMenu.getText());
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