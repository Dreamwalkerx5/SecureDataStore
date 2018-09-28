package model;

import java.util.List;

public class Controller {
	private DataBase dataBase;
	private DerbyInterface derbyInterface;
	
	public Controller() {
		derbyInterface = new DerbyInterface();
		dataBase = new DataBase(derbyInterface);
		
	}
	
	public boolean firstRun() {
		return derbyInterface.createNewDataBase();
	}
	
	public List<Model> getDataBase() {
		return dataBase.getModel();
	}
	
	public void addModel(String text) {
		dataBase.addModel(text);
	}
	
	public void deleteModel(int index) {
		dataBase.deleteModel(index);
	}
	
	public void updateModel(int index, String newText) {
		dataBase.updateModel(index, newText);
	}
	
	public void saveDataBase() {
		dataBase.save();
	}
	
	public void loadDataBase() {
		dataBase.load();
	}
	
	public boolean deleteDataBase() {
		return dataBase.deleteDataBase();
	}
	
	public void shutdownDataBase() {
		dataBase.shutdownDerby();
	}
	
	public int getSize() {
		return dataBase.getSize();
	}
	
	public String getKey(Model model) {
		return dataBase.getKey(model);
	}
	
	public String getText(Model model) {
		return dataBase.getText(model);
	}
}
