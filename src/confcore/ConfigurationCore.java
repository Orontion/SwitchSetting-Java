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
	//Объекты окон
	private MainWindowClass mainWindow;
	private SettingsWindowClass settingsWindow;
	private TermWindowClass terminalWindow;
	private FileParcer mainFileParcer;
	private SerialPortClass serialPortInteractor;
	private InputContainer serialPortInputCont;
	
	//TODO Проверить работу конструктора, оптимизировать по возможности
	public ConfigurationCore() {
		this.initializeCode();
	}
	
	public void setMainWindow(MainWindowClass mainWindow){
		this.mainWindow = mainWindow;
	}
	
	//Метод, подготавливающий программу к работе
	public void initializeCode(){
		//TODO Загрузка настроек из файла
		mainFileParcer = new FileParcer("e:/Saves"); //
		serialPortInteractor = new SerialPortClass();
		serialPortInputCont = new InputContainer();
	}
	
	//Метод для получения списка СОМ-портов в системе. Если портов нет - выдаёт исключение ===============================================================
	public List<String> getSerialPortList(){
		List<String> converterList = new ArrayList<>();
		for (String tmpStr : serialPortInteractor.getSerialPortList()){
			converterList.add(tmpStr);
		}
		
		if (converterList.isEmpty()){
			throw new NoSerialPortsInSystemException("There is no serial ports in system!");
		}
		return converterList;
	}
	
	//Получение списка имён конфигурационных файлов ==================================================================================================
	public List<String> getConfFilesList(){
		mainFileParcer.scanConfDir();
		return mainFileParcer.showFilesInConfDir();
	}
	
	//Получение текущего пути к файлам конфигурации ==================================================================================================
	public String getConFileDir(){
		//TODO Исключение в случае косячного пути
		return mainFileParcer.showCurrentConfDirPath();
	}
	
	public void setConFileDir(String newConfDirPath){
		mainFileParcer.setConfDirPath(newConfDirPath);
	}
	
	//Выбор COM-порта для работы ==================================================================================================
	public void chooseSerialPort(String serialPortName){
		serialPortInteractor.chooseSerialPort(serialPortName);
	}
	
	//Выбор скорости работы COM-порта ==================================================================================================
	public void chooseSerialPortSpeed(int chosedSpeed){
		serialPortInteractor.chooseSerialPortSpeed(chosedSpeed);
	}
	
	public void addSerialPortInputListener(InputEventListener inputListener){
		serialPortInputCont.addListener(inputListener);
	}
	
	public void removeSerialPortInputListener(InputEventListener inputListener){
		serialPortInputCont.removeListener(inputListener);
	}
	
	public String getAllSerialPortData(){
		return serialPortInputCont.getAllInputData();
	}
	
	public static void main(String[] args) {
		
	}
}
