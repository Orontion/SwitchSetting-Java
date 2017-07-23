package interfc.settingswindow.view;

import java.util.List;

import interfc.settingswindow.SettingsWindowClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SettingsWindowController {
	@FXML
	private TextField txtConfDirPath;

	@FXML
	private Button butChooseDir;

	@FXML
	private ComboBox<String> cmbSerialPort;
	
	@FXML
	private void initialize(){
		serialPortObsList = FXCollections.observableArrayList();
		
		cmbSerialPort.setItems(serialPortObsList);
	}
	
	@FXML
	private void chooseConfFileDir(){
		stgWinRef.chooseConfFileDir();
	}
	
	private SettingsWindowClass stgWinRef;
	
	private ObservableList<String> serialPortObsList;
	
	//����� ��������� ������ �� ������ ���� ��������
	public void setSettingsWindowRef(SettingsWindowClass stgWinRef){
		this.stgWinRef = stgWinRef;
	}
	
	//����� ��� ���������� ���������� � ComboBox ������ ��� ���������������� ������
	public void fillSerialPortList(List<String> serialPortList){
		serialPortObsList.clear();
		serialPortObsList.addAll(serialPortList);
	}
	
	//����� ��� ��������� �������� ���� � ������ ������������
	public void setConfDirPath(String confDirPath){
		txtConfDirPath.setText(confDirPath);
	}
}
