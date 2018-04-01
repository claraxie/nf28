package modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Modele {
	private Country listPays;
	public ObservableList<Group> groupes = FXCollections.observableArrayList();
	public ObservableMap<String,String> errorMsgs = FXCollections.observableHashMap();
	public ObservableList<PieChart.Data> groupeCirculaire = FXCollections.observableArrayList();
	public ObservableList<XYChart.Data<String, Number>> groupeHistVert = FXCollections.observableArrayList();
	public ObservableList<XYChart.Data<Number, String>> groupeHistHor = FXCollections.observableArrayList();
	private static final long serialVersionUID = 1L;
	
	StringProperty prenom = new SimpleStringProperty(null, "prenom", "");
	StringProperty nom = new SimpleStringProperty(null, "nom", "");
	StringProperty adresse = new SimpleStringProperty(null, "adresse", "");
	StringProperty postal = new SimpleStringProperty(null, "postal", "");
	StringProperty ville = new SimpleStringProperty(null, "ville", "");
	StringProperty pays = new SimpleStringProperty(null, "pays", "");
	StringProperty naissance = new SimpleStringProperty(null, "naissance", "");
	BooleanProperty sexeF = new SimpleBooleanProperty();
	BooleanProperty sexeM = new SimpleBooleanProperty();
	
	ObjectProperty<XYChart.Series<String, Number>> histVertSerie = new SimpleObjectProperty<XYChart.Series<String, Number>>();
	ObjectProperty<ArrayList<XYChart.Series<Number, String>>> histHorSerie = new SimpleObjectProperty<ArrayList<XYChart.Series<Number, String>>>();
		
	public void initialize() {
		// add listener
		adresse.addListener((observable, oldVal, newVal) -> removeFromMap(adresse.getName()));
		postal.addListener((observable, oldVal, newVal) -> removeFromMap(postal.getName()));
		ville.addListener((observable, oldVal, newVal) -> removeFromMap(ville.getName()));
		nom.addListener((observable, oldVal, newVal) -> removeFromMap(nom.getName()));
		prenom.addListener((observable, oldVal, newVal) -> removeFromMap(prenom.getName()));
	}
	
	public StringProperty getPrenom() {
		return prenom;
	}
	
	public StringProperty getNom() {
		return nom;
	}
	
	public StringProperty getAdresse() {
		return adresse;
	}
	
	public StringProperty getPostal() {
		return postal;
	}
	
	public StringProperty getVille() {
		return ville;
	}
	
	public StringProperty getPays() {
		return pays;
	}
		
	public StringProperty getNaissance() {
		return naissance;
	}
	
	public BooleanProperty getSexeF() {
		return sexeF;
	}
	
	public BooleanProperty getSexeM() {
		return sexeM;
	}
	
	public void setNaissance(String n) {
		naissance.set(n);
	}
	
	public void setPays(String p) {
		pays.set(p);
	}
	
	public ObjectProperty<XYChart.Series<String, Number>> getChartHistVertProperty(){
		return histVertSerie;
	}
	
	public ObjectProperty<ArrayList<XYChart.Series<Number, String>>> getChartHistHorProperty(){
		return histHorSerie;
	}
	
	public Modele() {
		listPays = new Country();
	}
	
	public ObservableList<String> getCountries() {
		return listPays.getCountries();
	}
	
	public boolean isGroup(TreeItem<Object> grp) {
		Group c = (Group)grp.getValue();
		return groupes.contains(c);
	}
	
    public boolean validation(Contact c) {
    	boolean complete = true;
    	// Address
    			String address = adresse.getValue().toString(); 
    			String pc = postal.getValue().toString();
    			String city = ville.getValue().toString(); 
    			
    			// Person
    			String firstname = prenom.getValue().toString();
    			String lastname = nom.getValue().toString(); 
    			
    			if (address.isEmpty()) {
    				errorMsgs.put(adresse.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (pc.isEmpty()) {
    				errorMsgs.put(postal.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (city.isEmpty()) {
    				errorMsgs.put(ville.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (firstname.isEmpty()) {
    				errorMsgs.put(prenom.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			if (lastname.isEmpty()) {
    				errorMsgs.put(nom.getName(), "Not empty!");
    				complete = false;
    			}
    			
    			// validate
    			if (complete) {
    				c.setNaissance(naissance.get());
    				c.setVille(ville.get());
    				c.setPays(pays.get());
    				c.setPrenom(prenom.get());
    				c.setNom(nom.get());
    				c.setAdresse(adresse.get());
    				c.setPostal(postal.get());
    				c.setSexeF(sexeF.getValue());
    				c.setSexeM(sexeM.getValue());   				
    			}    			
    			  			
    	return complete;
    	
    }
    
    private void removeFromMap(String key) {
		if (errorMsgs.containsKey(key))
			errorMsgs.remove(key);
	}
	
	public void setContact(Contact c) {
		adresse.set(c.getAdresse());
		naissance.set(c.getNaissance());
		nom.set(c.getNom());
		pays.set(c.getPays());
		postal.set(c.getPostal());
		prenom.set(c.getPrenom());
		sexeF.set(c.getSexeF());
		sexeM.set(c.getSexeM());
		ville.set(c.getVille());
	}
	
	public void create(TreeItem<Object> select) {
		if (select.getValue() instanceof String) {
			createGroup();
		}
		
		if (select.getValue() instanceof Group) {
			createContact((Group)select.getValue());
		}
		
		if (select.getValue() instanceof Contact) {
			createContact((Group)select.getParent().getValue());
		}
	}
	
	public void delete(TreeItem<Object> select) {
		
		if (select.getValue() instanceof String) {
			return;
		}
		
		if (select.getValue() instanceof Group) {
			groupes.remove((Group)select.getValue());

		}
		
		if (select.getValue() instanceof Contact) {
			Contact contact = (Contact)select.getValue();
			Group grp = contact.getGroup();
			grp.contacts.remove(contact);
		}
	}
	
	private void createGroup() {
		groupes.add(new Group());
	}
	
	public void createContact(Group grp) {
		grp.contacts.add(new Contact(grp));
	}
	
	public void save() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save File");		
		File selectedFile = fc.showOpenDialog(new Stage());
			
		Workspace work = new Workspace();
		work.setGroups(groupes);		
		try {
			work.save(selectedFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// piechart
		groupeCirculaire.clear();
		groupes.stream().forEach(g -> groupeCirculaire.add(new PieChart.Data(g.getName(), g.contacts.size())));
		
		// bar char vertical
		Map<String, Number> villes = new HashMap<String, Number>();
		groupes.stream().forEach(g -> g.contacts.stream().forEach(c -> mapAjout(villes, c.getVille())));
		XYChart.Series<String, Number> serie = new XYChart.Series<>();
		villes.forEach((v,n) -> serie.getData().add(new XYChart.Data<String, Number>(v,n)));
		histVertSerie.setValue(serie);
		
		// bar chart horizontal
		ArrayList<Contact> preSerie = new ArrayList<Contact>();
		ArrayList<Contact> deuSerie = new ArrayList<Contact>();
		ArrayList<Contact> troSerie = new ArrayList<Contact>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
		for(Group g : groupes) {
			for(Contact c : g.contacts) {
				try {
					if(formatter.parse(c.getNaissance()).before(formatter.parse("1990-01-01")))
					{	
						preSerie.add(c);
					}
					else if(formatter.parse(c.getNaissance()).after(formatter.parse("2000-01-01")))
					{
						troSerie.add(c);
					}
					else {
						deuSerie.add(c);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println(preSerie);
		System.out.println(deuSerie);
		System.out.println(troSerie);	
		ArrayList<XYChart.Series<Number, String>> seriesList = new ArrayList<XYChart.Series<Number, String>>();
		
		XYChart.Series<Number, String> seriesHors1 = new XYChart.Series<Number, String>();
		seriesHors1.setName("<1990-01-01");	
		Map<String, Number> grps1 = new HashMap<String, Number>();
		for (Contact c : preSerie) {
			mapAjout(grps1, c.getGroup().getName());
		}
		
	    for (Entry<String, Number> entry : grps1.entrySet()) {
	    	seriesHors1.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey()));
	    }
	    seriesList.add(seriesHors1);
	    
		XYChart.Series<Number, String> seriesHors2 = new XYChart.Series<Number, String>();
		seriesHors2.setName("entre");	
		Map<String, Number> grps2 = new HashMap<String, Number>();
		for (Contact c : deuSerie) {
			mapAjout(grps2, c.getGroup().getName());
		}

	    for (Entry<String, Number> entry : grps2.entrySet()) {
	    	seriesHors2.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey()));
	    }
	    seriesList.add(seriesHors2);
	    
	    
	    
		XYChart.Series<Number, String> seriesHors3 = new XYChart.Series<Number, String>();
		seriesHors3.setName(">2000-01-01");	
		Map<String, Number> grps3 = new HashMap<String, Number>();
		for (Contact c : troSerie) {
			mapAjout(grps3, c.getGroup().getName());
		}

	    for (Entry<String, Number> entry : grps3.entrySet()) {
	    	seriesHors3.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey()));
	    }
	    seriesList.add(seriesHors3);
	    
	    histHorSerie.setValue(seriesList);
	}
	
	public void load() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Load File");
		File selectedFile = fc.showOpenDialog(new Stage());
		
		Workspace work = new Workspace();
		work.groupes.clear();
		try {
			work.groupes = work.fromFile(selectedFile);
			for (GroupModele g : work.groupes) {
				groupes.add(new Group(g));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//pie chart
		groupeCirculaire.clear();
		groupes.stream().forEach(g -> groupeCirculaire.add(new PieChart.Data(g.getName(), g.contacts.size())));
		
		// bar chart vertical
		Map<String, Number> villes = new HashMap<String, Number>();
		groupes.stream().forEach(g -> g.contacts.stream().forEach(c -> mapAjout(villes, c.getVille())));		
		XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
		villes.forEach((v,n) -> serie.getData().add(new XYChart.Data<String, Number>(v,n)));
		histVertSerie.setValue(serie);
		
		
		// bar chart horizontal
		ArrayList<Contact> preSerie = new ArrayList<Contact>();
		ArrayList<Contact> deuSerie = new ArrayList<Contact>();
		ArrayList<Contact> troSerie = new ArrayList<Contact>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
		for(Group g : groupes) {
			for(Contact c : g.contacts) {
				try {
					if(formatter.parse(c.getNaissance()).before(formatter.parse("1990-01-01")))
					{	
						preSerie.add(c);
					}
					else if(formatter.parse(c.getNaissance()).after(formatter.parse("2000-01-01")))
					{
						troSerie.add(c);
					}
					else {
						deuSerie.add(c);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println(preSerie);
		System.out.println(deuSerie);
		System.out.println(troSerie);	
		ArrayList<XYChart.Series<Number, String>> seriesList = new ArrayList<XYChart.Series<Number, String>>();
		
		XYChart.Series<Number, String> seriesHors1 = new XYChart.Series<Number, String>();
		seriesHors1.setName("<1990-01-01");	
		Map<String, Number> grps1 = new HashMap<String, Number>();
		for (Contact c : preSerie) {
			mapAjout(grps1, c.getGroup().getName());
		}
		
	    for (Entry<String, Number> entry : grps1.entrySet()) {
	    	seriesHors1.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey()));
	    }
	    seriesList.add(seriesHors1);
	    
		XYChart.Series<Number, String> seriesHors2 = new XYChart.Series<Number, String>();
		seriesHors2.setName("entre");	
		Map<String, Number> grps2 = new HashMap<String, Number>();
		for (Contact c : deuSerie) {
			mapAjout(grps2, c.getGroup().getName());
		}

	    for (Entry<String, Number> entry : grps2.entrySet()) {
	    	seriesHors2.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey()));
	    }
	    seriesList.add(seriesHors2);
	    
	    
	    
		XYChart.Series<Number, String> seriesHors3 = new XYChart.Series<Number, String>();
		seriesHors3.setName(">2000-01-01");	
		Map<String, Number> grps3 = new HashMap<String, Number>();
		for (Contact c : troSerie) {
			mapAjout(grps3, c.getGroup().getName());
		}

	    for (Entry<String, Number> entry : grps3.entrySet()) {
	    	seriesHors3.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey()));
	    }
	    seriesList.add(seriesHors3);
	    
	    histHorSerie.setValue(seriesList);

	}
	
	void mapAjout(Map<String, Number> map, String nom){
		if(map.containsKey(nom))
			map.put(nom, map.get(nom).intValue()+1);
		else
			map.put(nom, 1);
	}
}