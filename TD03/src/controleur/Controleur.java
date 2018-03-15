package controleur;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import modele.Modele;
import modele.Group;


public class Controleur implements Initializable {
	Image groupeIcon = new Image(getClass().getResourceAsStream("/img/group.png"));
	Image contactIcon = new Image(getClass().getResourceAsStream("/img/contact.png"));
	
	TreeItem<Object> rootNode;
	
	private Modele modele;
	
	@FXML
	private Button valider;
	
	@FXML
	private TextField prenom;

	@FXML
	private TextField nom;

	@FXML
	private TextField adresse;

	@FXML
	private TextField postal;

	@FXML
	private TextField ville;

	@FXML
	private ChoiceBox<String> pays;

	@FXML
	private DatePicker naissance;
	
	@FXML
	private RadioButton sexeF;
	
	@FXML
	private RadioButton sexeM;
	
	@FXML
	private TreeView<Object> tree;
	
	@FXML
	private Button add;
	
	@FXML
	private void add() {
		modele.addGroupe("Défaut");
	}
	
	@FXML
	private void delete() {
		
	}
	
	@FXML
	private Button delete;
	
	@FXML
	private AnchorPane contactPane;
	 
    private Tooltip tooltip;
	
	private ArrayList<TextField> textField;
	
	private ArrayList<String> emptyElement;
	
    @FXML
    private void validation(ActionEvent e) {
    	textField = new ArrayList<TextField>();
    	emptyElement = new ArrayList<String>();
    	
    	textField.add(prenom);
    	textField.add(nom);
    	textField.add(adresse);
    	textField.add(postal);
    	textField.add(ville);
    	for (TextField field : textField) {
    		if (field.getText() == null)
    			emptyElement.add(field.getId());
    	}
    	
    	if (pays.getValue() == null)
    		emptyElement.add(pays.getId());
    	
    	if (naissance.getValue() == null)
    		emptyElement.add(naissance.getId());
    	
    	if (!sexeF.isSelected()&&!sexeM.isSelected())
    		emptyElement.add("sexe");
    	
    	if (!emptyElement.isEmpty()) {
    		valider.setStyle("-fx-border-color: red ;"); 
    		String str = String.join(", ", emptyElement);
    		tooltip = new Tooltip();
    		tooltip.setText(str + " doit ��tre renseign��");
    		valider.setTooltip(tooltip);
    	} else {
    		valider.setStyle("-fx-border-color: green;"); 
    		tooltip = new Tooltip();
    		tooltip.setText("Information Complete");
    		valider.setTooltip(tooltip);
    	}
    }
	
	public Controleur(Modele m) {
		modele = m;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootNode = new TreeItem<>();
		rootNode.setValue("Fiche de contacts");
		rootNode.setExpanded(true);
		tree.setRoot(rootNode);
		tree.setEditable(true);
		tree.setCellFactory(param -> new TextFieldTreeCellImpl());
		
		pays.setItems(modele.getCountries());
		
		prenom.textProperty().bindBidirectional(modele.c1.getPrenom());
		nom.textProperty().bindBidirectional(modele.c1.getNom());
		adresse.textProperty().bindBidirectional(modele.c1.getAdresse());
		postal.textProperty().bindBidirectional(modele.c1.getPostal());
		ville.textProperty().bindBidirectional(modele.c1.getVille());
		
		naissance.valueProperty().addListener(
				(obj,oldValue,newValue)->{
					modele.c1.setNaissance(newValue.toString());
				}
			);
	
		modele.c1.getNaissance().addListener(
				(obj,oldValue,newValue)->{
					naissance.setValue(LocalDate.parse(newValue));
				}
			);
		
		sexeF.selectedProperty().bindBidirectional(modele.c1.getSexeF());
		sexeM.selectedProperty().bindBidirectional(modele.c1.getSexeM());
		
		pays.getSelectionModel().selectedItemProperty().addListener(
				(obj, oldValue, newValue)-> {
					modele.c1.setPays(newValue.toString());
				});
		
		modele.c1.getPays().addListener(
				(obj,oldValue,newValue)->{
					pays.setValue(newValue);
				}
			);
		
		tree.getSelectionModel().selectedItemProperty().addListener(
				(obj, oldValue, newValue)->{
					TreeItem<Object> selectedItem = (TreeItem<Object>) newValue;
					if(modele.isContact(selectedItem))
						contactPane.visibleProperty().set(true);
					else
						contactPane.visibleProperty().set(false);
				}
			);
		
		ListChangeListener listen = change ->{
			change.next();
			List<Group> nouveau = change.getAddedSubList();
			
			TreeItem<Object> newNode;
			for(Group g : nouveau) {
				newNode = new TreeItem<Object>(g.getName(), new ImageView(groupeIcon));
				newNode.setExpanded(true);
				rootNode.getChildren().add(newNode);
			}
		};
		
		modele.groupes.addListener(listen);
		
		/*TreeItem<Object> amisNode;
		amisNode = new TreeItem<Object>("Amis", new ImageView(groupeIcon));
		amisNode.setExpanded(true);
		modele.addGroupe(amisNode);
		rootNode.getChildren().add(amisNode);
		
		TreeItem<Object> familleNode;
		familleNode = new TreeItem<Object>("Famille", new ImageView(groupeIcon));
		familleNode.setExpanded(true);
		modele.addGroupe(familleNode);
		rootNode.getChildren().add(familleNode);
		
		TreeItem<Object> travailNode;
		travailNode = new TreeItem<Object>("Travaille", new ImageView(groupeIcon));
		travailNode.setExpanded(true);
		modele.addGroupe(travailNode);
		rootNode.getChildren().add(travailNode);
		
		TreeItem<Object> contact1Node;
		contact1Node = new TreeItem<Object>("IA04 Etud1", new ImageView(contactIcon));
		contact1Node.setExpanded(true);
		modele.addContact("Travaille", contact1Node);
		travailNode.getChildren().add(contact1Node);
		
		TreeItem<Object> contact2Node;
		contact2Node = new TreeItem<Object>("IA04 Etud2", new ImageView(contactIcon));
		contact2Node.setExpanded(true);
		modele.addContact(travailNode, contact2Node);
		travailNode.getChildren().add(contact2Node);*/

	}
	
	private final class TextFieldTreeCellImpl extends TreeCell<Object> {
		 
        private TextField textField;
 
        public TextFieldTreeCellImpl() {
        }
 
        @Override
        public void startEdit() {
            super.startEdit();
            
            
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }
 
        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
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
	
}
