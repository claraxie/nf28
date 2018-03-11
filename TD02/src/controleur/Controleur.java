package controleur;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import modele.Modele;


public class Controleur implements Initializable {
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
    		tooltip.setText(str + " doit ¨ºtre renseign¨¦");
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
		
	}
	
	
}
