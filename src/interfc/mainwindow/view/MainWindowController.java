package interfc.mainwindow.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MainWindowController {
	@FXML
	private ComboBox<String> cmbConfFileSelect;
	
	private ObservableList<String> confFileObsList;
	
	
	public void startForm(List<String> confFileList){
		confFileObsList = FXCollections.observableArrayList();
		
		this.fillCmbConfFileSelect(confFileList);
		
		cmbConfFileSelect.setItems(confFileObsList);
	}
	
	
	public void fillCmbConfFileSelect(List<String> confFileList){
		confFileObsList.clear();
		confFileObsList.addAll(confFileList);
	}
}
