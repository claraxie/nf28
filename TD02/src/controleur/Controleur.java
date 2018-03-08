package controleur;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
	
	public Controleur(Modele m) {
		modele = m;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		prenom.textProperty().bindBidirectional(modele.c1.getPrenom());
		nom.textProperty().bindBidirectional(modele.c1.getNom());
		adresse.textProperty().bindBidirectional(modele.c1.getAdresse());
		postal.textProperty().bindBidirectional(modele.c1.getPostal());
		ville.textProperty().bindBidirectional(modele.c1.getVille());
		pays.setItems(modele.getCountries());
		
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
	}
}
