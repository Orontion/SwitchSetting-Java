package interfc.settingswindow.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SettingsWindowController {
	@FXML
	private ComboBox<String> cmbSerialPort;
	
	@FXML
	private ObservableList<String> serialPortObsList;
	
	@FXML
	private void initialize(){
		serialPortObsList = FXCollections.observableArrayList();
		
		cmbSerialPort.setItems(serialPortObsList);
	}
	
	public void fillSerialPortList(List<String> serialPortList){
		serialPortObsList.clear();
		serialPortObsList.addAll(serialPortList);
	}
}
