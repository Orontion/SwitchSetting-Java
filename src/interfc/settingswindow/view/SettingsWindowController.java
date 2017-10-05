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
	//Объявления объектов, расположенных на форме
	
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
	
	//Методы-события, активируемые из формы
	
	@FXML
	private void initialize(){
		//Создаём ObservableList для вывода списка COM-портов в combobox, и связываем его с этим combobox-ом
		serialPortObsList = FXCollections.observableArrayList();
		cmbSerialPort.setItems(serialPortObsList);
		
		//Создаём ObservableList с заданным списком скоростей COM-порта, и связываем его с соответствующим combobox-ом
		portSpeedObsList = FXCollections.observableArrayList();
		portSpeedObsList.add(SerialPort.BAUDRATE_4800);
		portSpeedObsList.add(SerialPort.BAUDRATE_9600);
		portSpeedObsList.add(SerialPort.BAUDRATE_14400);
		portSpeedObsList.add(SerialPort.BAUDRATE_57600);
		portSpeedObsList.add(SerialPort.BAUDRATE_115200);
		cmbPortSpeed.setItems(portSpeedObsList);
		
		//Создаём ObservableList с заданным списком битностей COM-порта, и связываем его с соответствующим combobox-ом
		portDataBits = FXCollections.observableArrayList();
		portDataBits.add(SerialPort.DATABITS_5);
		portDataBits.add(SerialPort.DATABITS_6);
		portDataBits.add(SerialPort.DATABITS_7);
		portDataBits.add(SerialPort.DATABITS_8);
		cmbPortDataBits.setItems(portDataBits);
		cmbPortDataBits.setValue(SerialPort.DATABITS_8);	//Устанавливаем стандартное значение в 8 бит
		
		//Создаём ObservableList с заданным списком стоп-битов COM-порта, и связываем его с соответствующим combobox-ом
		//При выборе использовать индекс + 1, т.к. в классе SerialPort библиотеки JSSC вариантам соответствуют значения int 1, 2 и 3 
		portStopBits = FXCollections.observableArrayList();
		portStopBits.add("1");
		portStopBits.add("2");
		portStopBits.add("1,5");
		cmbPortStopBits.setItems(portStopBits);
		cmbPortStopBits.setValue("1");		//Устанавливаем стандартное значение
		
		//Создаём ObservableList с заданным списком parity COM-порта, и связываем его с соответствующим combobox-ом
		//При выборе использовать индекс, т.к. в классе SerialPort библиотеки JSSC вариантам соответствуют значения int от 0 до 4
		portParity = FXCollections.observableArrayList();
		portParity.add("NONE");
		portParity.add("ODD");
		portParity.add("EVEN");
		portParity.add("MARK");
		portParity.add("SPACE");
		cmbPortParity.setItems(portParity);
		cmbPortParity.setValue("NONE");		//Устанавливаем стандартное значение
		
		//TODO Добавить загрузку текущих настроек COM-порта из ядра программы
	}
	
	
	//Щелчок по кнопке выбора папки с конфигурационными файлами ======================================================================================
	@FXML
	private void chooseConfFileDir(){
		stgWinRef.chooseConfFileDir();
	}
	
	//Активация кнопки сохранения настроек при изменении каких-либо параметров, если в системе есть порт, который можно настроить ======================================================================================
	@FXML
	private void activateSerialPortSettingsButton(){
		if (canSetupSerialPort)	butConfirmSerialPortSettings.setDisable(false);
	}
	
	//Щелчок по кнопке сохранения настроек =============================================================================================================================================
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
		
		//Сохраняем настройки, передавая их в класс окна настроек
		stgWinRef.chooseSerialPort(cmbSerialPort.getSelectionModel().getSelectedItem());
		stgWinRef.chooseSerialPortSpeed(cmbPortSpeed.getSelectionModel().getSelectedItem());
		
		//TODO вставить передачу настроек COM-порта
	
		//Отключаем кнопку сохранения настроек до изменения каких-либо параметров
		butConfirmSerialPortSettings.setDisable(true);
	}
	
	//Внутренние объекты и функции класса
	
	private SettingsWindowClass stgWinRef;
	private ObservableList<String> serialPortObsList;
	private ObservableList<Integer> portSpeedObsList;
	private ObservableList<Integer> portDataBits;
	private ObservableList<String> portStopBits;
	private ObservableList<String> portParity;
	
	//Флаг, показывающий наличие COM-портов в системе, которые можно настраивать.
	//Если в системе нет ни одного COM-порта - настройки нельзя будет сохранить, т.к. кнопка сохранения не активируется
	private boolean canSetupSerialPort = true;
	
	//Метод установки ссылки на объект окна настроек ================================================================================================
	public void setSettingsWindowRef(SettingsWindowClass stgWinRef){
		this.stgWinRef = stgWinRef;
	}
	
	//Метод для заполнения связанного с ComboBox списка имён последовательных портов ================================================================
	public void fillSerialPortList(List<String> serialPortList){
		serialPortObsList.clear();
		serialPortObsList.addAll(serialPortList);
		canSetupSerialPort = true;
	}
	
	//Метод по-умолчанию - порты отсутствуют =======================================================================================================
	public void fillSerialPortList(){
		serialPortObsList.clear();
		serialPortObsList.add("НЕТ ДОСТУПНЫХ ПОРТОВ!");
		cmbSerialPort.setValue("НЕТ ДОСТУПНЫХ ПОРТОВ!");
		canSetupSerialPort = false;
	}
	
	//Метод для получения текущего пути к файлам конфигурации ======================================================================================
	public void setConfDirPath(String confDirPath){
		txtConfDirPath.setText(confDirPath);
	}
}
