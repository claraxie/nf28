package modele;

import java.util.ArrayList;


public class WorkspaceModele extends ModelJson {
	public ArrayList<GroupModele> groupes = new ArrayList<GroupModele>();
	
	public WorkspaceModele() {
		
	}
	
	public WorkspaceModele(ArrayList<GroupModele> grps) {
		groupes = grps;
			
	}
	
	public ArrayList<GroupModele> getWorkspaceModele() {
		return groupes;
		
	}
	
	public void setWorkspaceModele(ArrayList<GroupModele> grps) {
		groupes = grps;
	}
	
	

}
