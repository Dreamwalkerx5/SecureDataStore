package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



class DataBase {
	private Password password;
	private LinkedList<Model> dataBase;
	private DerbyInterface derbyInterface;
	
	DataBase(DerbyInterface derbyInterface) {
		
		// create an Derby interface
		this.derbyInterface = derbyInterface;
		
		// create an empty data base
		dataBase = new LinkedList<Model>();
		
		// get a password from user
		password = new Password();
		//System.out.println(password.getPassword() + "  " + password.getPassword().length());
	}
	
	void addModel(String text) {
		Model newModel;
		// Encrypt data
		newModel = new Model(password, text);
		
		// Add it to the derby database
		derbyInterface.addModel(newModel);
		
		//System.out.println(newModel.getCryptoText());
		
		// Reload derby dataBase to local database
		//load();
	}

	void deleteModel(int index) {
		
		derbyInterface.deleteModel(dataBase.get(index));
		//load();
	}
	
	void updateModel(int index, String newText) {
		Model newModel;
		
		// Create new Model with the updated text
		newModel = new Model(password, newText);
		
		// Give the new Model the key of the old one
		newModel.setKey(dataBase.get(index).getKey());
		
		derbyInterface.updateModel(newModel);
	}

	void load() {
		
		// Clear the data base
		dataBase.clear();
		derbyInterface.getDataBase(dataBase);
		
	}
	
	void save() {
		
		derbyInterface.saveDataBase(dataBase);
		
	}
	
	boolean deleteDataBase() {
		return derbyInterface.deleteDataBase();
	}
	
	void shutdownDerby() {
		
		derbyInterface.shutdown();
		
	}
	
	@SuppressWarnings("unused")
	private void testDump() {
		
		for (Model element : dataBase) {
			System.out.println(element.getText(password));
		}
		
	}

	int getSize() {
		
		return dataBase.size();
		
	}

	List<Model> getModel() {
		
		// Return an unmodifiable List of Model's in our database so other Classes cannot modify it
		return Collections.unmodifiableList(dataBase);
		
	}
	
	String getKey(Model model) {
		
		return Integer.toString(model.getKey());
		
	}
	
	String getText(Model model) {
		
		return model.getText(password);
	}
	
}
