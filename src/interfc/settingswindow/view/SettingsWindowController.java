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
	private ComboBox<Integer> cmbPortSpeed;
	
	@FXML
	private Button butConfirmSerialPortSettings;
	
	@FXML
	private void initialize(){
		serialPortObsList = FXCollections.observableArrayList();
		portSpeedObsList = FXCollections.observableArrayList();
		
		portSpeedObsList.add(7200);
		portSpeedObsList.add(9600);
		portSpeedObsList.add(14400);
		portSpeedObsList.add(57600);
		portSpeedObsList.add(115200);
		
		cmbSerialPort.setItems(serialPortObsList);
		cmbPortSpeed.setItems(portSpeedObsList);
		
		//TODO Добавить загрузку текущих настроек COM-порта из ядра программы
	}
	
	
	//Щелчок по кнопке выбора папки с конфигурационными файлами
	@FXML
	private void chooseConfFileDir(){
		stgWinRef.chooseConfFileDir();
	}
	
	@FXML
	private void activateSerialPortSettingsButton(){
		butConfirmSerialPortSettings.setDisable(false);
	}
	
	@FXML
	private void confirmSerialPortSettings(){
		if (cmbSerialPort.getSelectionModel().getSelectedItem() == null){
			//TODO Вставить обработку ошибки не выбранного порта
			return;
		}		
		
		if (cmbPortSpeed.getSelectionModel().getSelectedItem() == null){
			//TODO Вставить обработку ошибки не выбранной скорости порта
			return;
		}
		
		stgWinRef.chooseSerialPort(cmbSerialPort.getSelectionModel().getSelectedItem());
		stgWinRef.chooseSerialPortSpeed(cmbPortSpeed.getSelectionModel().getSelectedItem());
		
		//TODO вставить передачу настроек COM-порта
		
		butConfirmSerialPortSettings.setDisable(true);
	}
	
	private SettingsWindowClass stgWinRef;
	
	private ObservableList<String> serialPortObsList;
	
	private ObservableList<Integer> portSpeedObsList;
	
	//Метод установки ссылки на объект окна настроек
	public void setSettingsWindowRef(SettingsWindowClass stgWinRef){
		this.stgWinRef = stgWinRef;
	}
	
	//Метод для заполнения связанного с ComboBox списка имён последовательных портов
	public void fillSerialPortList(List<String> serialPortList){
		serialPortObsList.clear();
		serialPortObsList.addAll(serialPortList);
	}
	
	//Метод для получения текущего пути к файлам конфигурации
	public void setConfDirPath(String confDirPath){
		txtConfDirPath.setText(confDirPath);
	}
}
