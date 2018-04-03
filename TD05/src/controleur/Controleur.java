package controleur;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import modele.Modele;
import modele.Contact;
import modele.Group;


public class Controleur implements Initializable {
	//Icônes
	Image groupeIcon = new Image(getClass().getResourceAsStream("/img/group.png"));
	Image contactIcon = new Image(getClass().getResourceAsStream("/img/contact.png"));

	//Création du modèle
	private Modele modele;

	//Initialisation des composants
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
	private Button save;

	@FXML
	private Button load;

	@FXML
	private Button delete;

	@FXML
	private AnchorPane contactPane;

	@FXML
	private PieChart pie;

	@FXML
	private BarChart<String, Number> histvert;

	@FXML
	private BarChart<Number, String> histhor;

	// current tree item
	TreeItem<Object> currentItem = null;
	// parent of currentItem
	TreeItem<Object> parentItem = null;
	//root of the tree
	TreeItem<Object> rootNode = null;

	// List listener
	ListChangeListener<Group> groupListListener = null;
	ListChangeListener<Contact> contactListListener = null;
	MapChangeListener<String, String> errorListener;
	ListChangeListener<PieChart.Data> pieChartListener = null;

	Contact editingContact = new Contact();


	public Controleur(Modele m) {
		modele = m;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		modele.initialize();

		pays.setItems(modele.getCountries());

		rootNode = new TreeItem<>();		
		parentItem = rootNode;	//set parent item
		currentItem = rootNode;	//set current item as root
		rootNode.setValue("Fiche de contacts");
		rootNode.setExpanded(true);
		tree.setRoot(rootNode);
		tree.setEditable(true);
		tree.setCellFactory(param -> new TextFieldTreeCellImpl());

		//Listener pour traiter la sélection d'un tree node
		tree.getSelectionModel().selectedItemProperty().addListener(
				(obj, oldValue, newValue)->{

					if (newValue.getValue() instanceof String) {
						contactPane.visibleProperty().set(false);
					}

					//Si un groupe est sélectionné, le panneau d'édition n'est pas visible
					if(newValue.getValue() instanceof Group) {
						contactPane.visibleProperty().set(false);
					}

					//Si un contact est sélectionné, le panneau d'édition est visible
					if(newValue.getValue() instanceof Contact) {
						modele.setContact((Contact)newValue.getValue());
						contactPane.visibleProperty().set(true);
					}

					//					System.out.println("Control: selected = " 
					//							+ newValue +
					//							" " + newValue.getClass().getName());
					currentItem = newValue;
					//					System.out.println(currentItem.getValue() instanceof String);

					//Définition du node parent
					if(currentItem.equals(rootNode)) {
						parentItem = rootNode;
					}
					else {
						parentItem = currentItem.getParent();
					}

				}
				);


		//Biding des champs du panneau d'édition
		prenom.textProperty().bindBidirectional(modele.getPrenom());
		nom.textProperty().bindBidirectional(modele.getNom());
		adresse.textProperty().bindBidirectional(modele.getAdresse());
		postal.textProperty().bindBidirectional(modele.getPostal());
		ville.textProperty().bindBidirectional(modele.getVille());
		add.setOnAction(evt -> modele.create(currentItem));
		delete.setOnAction(evt -> modele.delete(currentItem));
		save.setOnAction(evt -> modele.save());

		sexeF.selectedProperty().bindBidirectional(modele.getSexeF());
		sexeM.selectedProperty().bindBidirectional(modele.getSexeM());


		//Comportement du bouton "load"
		load.setOnAction(evt -> {			
			currentItem = rootNode;
			parentItem = rootNode;
			modele.load();
		});

		//Comportement du bouton "valider"
		valider.setOnAction(evt -> {
			if (modele.validation((Contact)currentItem.getValue())){
				TreeModificationEvent<Object> event = new  TreeModificationEvent<>(TreeItem.valueChangedEvent(), currentItem);
				Event.fireEvent(currentItem, event);
			}
		});

		//Biding du champ "date de naissance"
		naissance.valueProperty().addListener(
				(obj,oldValue,newValue)->{
					if(newValue.toString() == null)
						newValue = LocalDate.now();
					modele.setNaissance(newValue.toString());
				}
				);

		modele.getNaissance().addListener(
				(obj,oldValue,newValue)->{
					//					System.out.println("naissance" + newValue);
					if (!newValue.isEmpty())
						naissance.setValue(LocalDate.parse(newValue));
					else
						naissance.setValue(LocalDate.now());
				}
				);

		//Biding du champ "pays"
		pays.valueProperty().addListener(	
				(obj,oldValue,newValue)->{
					modele.setPays(newValue);
				});

		modele.getPays().addListener(
				(obj,oldValue,newValue)->{
					pays.setValue(newValue);
				}
				);


		// Save error listener
		errorListener = changed -> {
			if(changed.wasAdded()) {
				printErrorMsg(changed.getKey(), changed.getValueAdded());
			}

			if(changed.wasRemoved()) {
				removeErrorMsg(changed.getKey());
			}
		};
		modele.errorMsgs.addListener(errorListener);


		// Contact change listener
		contactListListener = changed ->{
			changed.next();			
			if (changed.wasAdded()) {
				changed.getAddedSubList().forEach(item -> addTreeItem(item));
			}

			if (changed.wasRemoved()) {
				changed.getRemoved().forEach(item -> removeCurrentTreeItem());
			}
		};


		// Group change listener
		groupListListener = changed ->{
			changed.next();
			if (changed.wasAdded()) {
				changed.getAddedSubList().forEach(item -> {
					addTreeItem(item);
					item.contacts.addListener(contactListListener);	
					if(item.contacts.size()!=0){
						//when load a file, add all the contact items
						for(Contact c : item.contacts){
							addTreeItem(c, item);
						}
					}
				}
						);
			}

			if (changed.wasRemoved()) {
				if(modele.groupes.size() == 0){
					//resolve the problem witch happened when clear the list of groups
					//treeView wasn't cleared 
					parentItem.getChildren().clear();
				}
				else{
					changed.getRemoved().forEach(item -> removeCurrentTreeItem());
				}
			}
		};
		modele.groupes.addListener(groupListListener);

		//Biding de l'histogramme vertical
		modele.getChartHistVertProperty().addListener(
				(obj, oldValue, newValue)->{
					histvert.getData().clear();
					histvert.getData().add(newValue);
				}
				);

		//Biding de l'histogramme horizontal
		modele.getChartHistHorProperty().addListener(
				(obj, oldValue, newValue)->{
					histhor.getData().clear();
					histhor.getData().addAll(newValue);
				}
				);

		//Biding du graphique circulaire
		pieChartListener = changed ->{
			changed.next();
			if (changed.wasAdded()) {
				pie.setData(modele.groupeCirculaire);
			}
		};
		modele.groupeCirculaire.addListener(pieChartListener);

	}

	//Erreur lors d'un valeur non fourni
	private void printErrorMsg(String key, String msg) {
		switch (key) {
		case "adresse":
			adresse.setStyle("-fx-border-color: red ;");
			adresse.setTooltip(new Tooltip(msg));
			break;

		case "postal":
			postal.setStyle("-fx-border-color: red ;");
			postal.setTooltip(new Tooltip(msg));
			break;

		case "ville":
			ville.setStyle("-fx-border-color: red ;");
			ville.setTooltip(new Tooltip(msg));
			break;

		case "prenom":
			prenom.setStyle("-fx-border-color: red ;");
			prenom.setTooltip(new Tooltip(msg));
			break;

		case "nom":
			nom.setStyle("-fx-border-color: red ;");
			nom.setTooltip(new Tooltip(msg));
			break;
		}
	}

	//Nettoye des messages d'erreur
	private void removeErrorMsg(String key) {
		switch (key) {
		case "adresse":
			adresse.setStyle(null);
			adresse.setTooltip(null);
			break;

		case "postal":
			postal.setStyle(null);
			postal.setTooltip(null);
			break;

		case "ville":
			ville.setStyle(null);
			ville.setTooltip(null);
			break;

		case "prenom":
			prenom.setStyle(null);
			prenom.setTooltip(null);
			break;

		case "nom":
			nom.setStyle(null);
			nom.setTooltip(null);
			break;
		}
	}

	//Fonction pour ajouter un nouveau groupe
	private void addTreeItem(Group grp) {
		TreeItem<Object> grpItem = new TreeItem<Object>(grp, new ImageView(groupeIcon));
		currentItem.getChildren().add(grpItem);
		currentItem.setExpanded(true);
	}

	//Fonction pour ajouter un nouveau contact
	private void addTreeItem(Contact contact) {
		TreeItem<Object> contactItem = new TreeItem<Object>(contact, new ImageView(contactIcon));
		currentItem.getChildren().add(contactItem);
	}

	//Fonction pour ajouter un nouveau groupe et contact
	private void addTreeItem(Contact contact, Group grp) {
		//add contact by using his group
		TreeItem<Object> contactItem = new TreeItem<Object>(contact, new ImageView(contactIcon));
		TreeItem<Object> groupItem = null;
		for(TreeItem<Object> g : rootNode.getChildren()){
			if(((Group)g.getValue()).equals(grp)){
				groupItem = g;
				break;
			}
		}
		contact.setGroup(grp);	//set parent group for each contact
		groupItem.getChildren().add(contactItem);
	}

	//Fonction pour supprimer un groupe ou contact
	private void removeCurrentTreeItem() {
		parentItem.getChildren().remove(currentItem);
	}

	//Classe pour permettre le changement du nom d'un groupe
	private final class TextFieldTreeCellImpl extends TreeCell<Object> {

		private TextField textField;

		public TextFieldTreeCellImpl() {
		}

		@Override
		public void startEdit() {
			if (!(getTreeItem().getValue() instanceof Group)) {
				return;
			}

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
			setText(((Group) getTreeItem().getValue()).toString());
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
						((Group)getTreeItem().getValue()).getGroupeProperty().set(textField.getText());
						commitEdit((Group)getTreeItem().getValue());
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
