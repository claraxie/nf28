package modele;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.ObservableList;

public class Workspace implements Externalizable {
	private static final ObjectMapper mapper = new ObjectMapper();
	public ArrayList<GroupModele> groupes = new ArrayList<GroupModele>();
	
	
	public Workspace() {
		
	}
	
	public Workspace(ObservableList<Group> g) {
		for(Group gg : g) {
			groupes.add(new GroupModele(gg));
		}
	}
	
	public void addGroup(Group g) {
		GroupModele gg = new GroupModele(g);
		groupes.add(gg);
	}
	
	
	public void rmGroup(Group g) {
		groupes.remove(new GroupModele(g));
	}
	
	public ArrayList<GroupModele> getGroupes(){
		return groupes;
	}
	
	
	
	public ArrayList<GroupModele> fromFile(File file) throws Exception{

		String iStream = new String(Files.readAllBytes(file.toPath()));
		System.out.println(iStream);
		
		WorkspaceModele work = new WorkspaceModele();
		work = ModelJson.deserialize(iStream, WorkspaceModele.class);
		groupes = work.groupes;
		System.out.println(groupes);
		return groupes;
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		/*String iStream;
	
		iStream = in.readUTF();
		
		groupes = getMapper().readValue(iStream, Workspace.class).groupes; */
	}
	
	public void save(File f) throws IOException{
		FileWriter file;
		file = new FileWriter(f);
		String oStream = getMapper().writeValueAsString(this);
		System.out.println(oStream);
		file.write(oStream);
		file.flush();
		file.close();
	}
		
		
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		//String oStream = getMapper().writeValueAsString(this);	
		//out.writeUTF(oStream);

	}
	
	private static ObjectMapper getMapper() {
		return mapper;
	}
	
	public void setGroups(ObservableList<Group> grps) {
		for (Group g : grps) {
			groupes.add(new GroupModele(g));
		}		
	}
}
