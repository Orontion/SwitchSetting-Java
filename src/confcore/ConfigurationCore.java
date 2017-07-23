package confcore;

import java.util.ArrayList;
import java.util.List;

import fileparcer.FileParcer;
import interfc.mainwindow.MainWindowClass;
import interfc.settingswindow.SettingsWindowClass;
import interfc.termwindow.TermWindowClass;
import serialportmodule.SerialPortClass;

public class ConfigurationCore {
	//Объекты окон
	private MainWindowClass mainWindow;
	private SettingsWindowClass settingsWindow;
	private TermWindowClass terminalWindow;
	private FileParcer mainFileParcer;
	private SerialPortClass serialPortInteractor;
	
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
	}
	
	//Метод для получения списка СОМ-портов в системе. Если портов нет - выдаёт исключение
	public List<String> getSerialPortList(){
		List<String> converterList = new ArrayList<>();
		for (String tmpStr : serialPortInteractor.getSerialPortList()){
			converterList.add(tmpStr);
		}
		if (converterList.isEmpty()){
			throw new RuntimeException("There is no serial ports in system!");
		}
		return converterList;
	}
	
	//Метод для получения списка имён конфигурационных файлов
	public List<String> getConfFilesList(){
		mainFileParcer.scanConfDir();
		return mainFileParcer.showFilesInConfDir();
	}
	
	//Метод получения текущего пути к файлам конфигурации
	public String getConFileDir(){
		//TODO Исключение в случае косячного пути
		return mainFileParcer.showCurrentConfDirPath();
	}
	
	public void setConFileDir(String newConfDirPath){
		mainFileParcer.setConfDirPath(newConfDirPath);
	}
	
	public static void main(String[] args) {
		
	}
}
