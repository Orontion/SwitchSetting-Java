package interfc.settingswindow.view;

import java.util.List;

import interfc.settingswindow.SettingsWindowClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jssc.SerialPort;

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
	private ComboBox<Integer> cmbPortDataBits;
	
	@FXML
	private ComboBox<String> cmbPortStopBits;
	
	@FXML
	private ComboBox<String> cmbPortParity;
	
	@FXML
	private Button butConfirmSerialPortSettings;
	
	@FXML
	private void initialize(){
		serialPortObsList = FXCollections.observableArrayList();
		cmbSerialPort.setItems(serialPortObsList);
		
		portSpeedObsList = FXCollections.observableArrayList();
		portSpeedObsList.add(SerialPort.BAUDRATE_4800);
		portSpeedObsList.add(SerialPort.BAUDRATE_9600);
		portSpeedObsList.add(SerialPort.BAUDRATE_14400);
		portSpeedObsList.add(SerialPort.BAUDRATE_57600);
		portSpeedObsList.add(SerialPort.BAUDRATE_115200);
		cmbPortSpeed.setItems(portSpeedObsList);
		
		portDataBits = FXCollections.observableArrayList();
		portDataBits.add(SerialPort.DATABITS_5);
		portDataBits.add(SerialPort.DATABITS_6);
		portDataBits.add(SerialPort.DATABITS_7);
		portDataBits.add(SerialPort.DATABITS_8);
		cmbPortDataBits.setItems(portDataBits);
		cmbPortDataBits.setValue(SerialPort.DATABITS_8);
		
		//При выборе использовать индекс + 1
		portStopBits = FXCollections.observableArrayList();
		portStopBits.add("1");
		portStopBits.add("2");
		portStopBits.add("1,5");
		cmbPortStopBits.setItems(portStopBits);
		cmbPortStopBits.setValue("1");
		
		//При выборе использовать индекс
		portParity = FXCollections.observableArrayList();
		portParity.add("NONE");
		portParity.add("ODD");
		portParity.add("EVEN");
		portParity.add("MARK");
		portParity.add("SPACE");
		cmbPortParity.setItems(portParity);
		cmbPortParity.setValue("NONE");
		
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
	
	private ObservableList<Integer> portDataBits;
	
	private ObservableList<String> portStopBits;
	
	private ObservableList<String> portParity;
	
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
