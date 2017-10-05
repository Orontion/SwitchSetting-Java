package confcore;

import java.util.ArrayList;
import java.util.List;

import fileparcer.FileParcer;
import interfc.mainwindow.MainWindowClass;
import interfc.settingswindow.SettingsWindowClass;
import interfc.termwindow.TermWindowClass;
import serialportmodule.SerialPortClass;
import serialportmodule.container.InputContainer;
import serialportmodule.container.InputEventListener;
import swsetexceptions.NoSerialPortsInSystemException;
import swsetexceptions.SwitchSettingException;

public class ConfigurationCore {
	//Ссылки на объекты окон
	private MainWindowClass mainWindow;
	private SettingsWindowClass settingsWindow;
	private TermWindowClass terminalWindow;
	//Ссылка на объект-парсер конфигурационных файлов
	private FileParcer mainFileParcer;
	//Ссылка на класс для работы с последовательным портом
	private SerialPortClass serialPortInteractor;
	//Ссылка на контейнер, содержащий ввод информации в последовательный порт
	private InputContainer serialPortInputCont;
	
	//TODO Проверить работу конструктора, оптимизировать по возможности
	//Конструктор объекта ядра программы =============================================================================================================================
	public ConfigurationCore() {
		//Запуск метода подготовки ядра к работе
		this.initializeCode();
	}
	
	//Метод, подготавливающий программу к работе =============================================================================================================================
	public void initializeCode(){
		//TODO Загрузка настроек из файла
		
		//Создаём все необходимые объекты
		mainFileParcer = new FileParcer("e:/Saves");
		serialPortInteractor = new SerialPortClass();
		serialPortInputCont = new InputContainer();
		
		//Передаём ссылку на объект ввода данных из последовательного порта в класс работы с COM-портом
		serialPortInteractor.setInputContainerRef(serialPortInputCont);
	}

	//Получение ссылки на главное окно =============================================================================================================================
	public void setMainWindow(MainWindowClass mainWindow){
		this.mainWindow = mainWindow;
	}
	
	//Метод для получения списка СОМ-портов в системе. Если портов нет - выдаём исключение ===============================================================
	public List<String> getSerialPortList(){
		
		//Т.к. стандартная библиотека JSSC выдаёт список SerialPortList в виде массива String[], 
		//а остальная программа работает с List, производим преобразование через временный List 
		List<String> converterList = new ArrayList<>();
		
		//Построчно переносим записи из получаемого массива во временный
		for (String tmpStr : serialPortInteractor.getSerialPortList()){
			converterList.add(tmpStr);
		}
		
		//Если в итоговом List нет ни одной записи - выдаём исключение 
		if (converterList.isEmpty()){
			throw new NoSerialPortsInSystemException("There is no serial ports in system!");
		}
		
		return converterList;
	}
	
	//Получение списка имён конфигурационных файлов ==================================================================================================
	public List<String> getConfFilesList(){
		//Перед выдачей списка - просканировать директорию на предмет изменений
		//TODO Необходимость проверки корректности значения ConfDir?
		mainFileParcer.scanConfDir();

		return mainFileParcer.showFilesInConfDir();
	}
	
	//Получение текущего пути к файлам конфигурации ==================================================================================================
	public String getConFileDir(){
		//TODO Исключение в случае косячного пути
		return mainFileParcer.getCurrentConfDirPath();
	}
	
	//Setter для значения ConfFileDir ================================================================================================================
	public void setConFileDir(String newConfDirPath){
		mainFileParcer.setConfDirPath(newConfDirPath);
	}
	
	//Выбор COM-порта для работы =====================================================================================================================
	//TODO Возможно, стоит сделат привязку к SerialPortList, чтобы нельзя было выбрать кривой COM-порт
	public void chooseSerialPort(String serialPortName){
		serialPortInteractor.chooseSerialPort(serialPortName);
	}
	
	//Выбор скорости работы COM-порта ================================================================================================================
	public void chooseSerialPortSpeed(int chosedSpeed){
		serialPortInteractor.chooseSerialPortSpeed(chosedSpeed);
	}
	
	//Начало работы с COM-портом =====================================================================================================================
	public void openChosenPort(){
		serialPortInteractor.startWork();
	}
	
	//Добавление Listener-а для получения входящей информации с порта =============================================================================================================================
	public void addSerialPortInputListener(InputEventListener inputListener){
		serialPortInputCont.addListener(inputListener);
	}
	
	//Удаление Listener-а ===============================================================================================================================
	public void removeSerialPortInputListener(InputEventListener inputListener){
		serialPortInputCont.removeListener(inputListener);
	}
	
	//Получение всей информации, которая была принята из COM-порта ==================================================================================================
	public String getAllSerialPortData(){
		return serialPortInputCont.getAllInputData();
	}
	
	public static void main(String[] args) {
		
	}
}
