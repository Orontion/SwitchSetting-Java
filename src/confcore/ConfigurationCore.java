package confcore;

import java.util.ArrayList;
import java.util.List;

import fileparcer.FileParcer;
import interfc.mainwindow.MainWindowClass;
import interfc.settingswindow.SettingsWindowClass;
import interfc.termwindow.TermWindowClass;
import serialportmodule.SerialPortClass;

public class ConfigurationCore {
	//������� ����
	private MainWindowClass mainWindow;
	private SettingsWindowClass settingsWindow;
	private TermWindowClass terminalWindow;
	private FileParcer mainFileParcer;
	private SerialPortClass serialPortInteractor;
	
	//TODO ��������� ������ ������������, �������������� �� �����������
	public ConfigurationCore() {
		this.initializeCode();
	}
	
	public void setMainWindow(MainWindowClass mainWindow){
		this.mainWindow = mainWindow;
	}
	
	//�����, ���������������� ��������� � ������
	public void initializeCode(){
		//TODO �������� �������� �� �����
		mainFileParcer = new FileParcer("e:/Saves"); //
		serialPortInteractor = new SerialPortClass();
	}
	
	//����� ��� ��������� ������ ���-������ � �������. ���� ������ ��� - ����� ����������
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
	
	//����� ��� ��������� ������ ��� ���������������� ������
	public List<String> getConfFilesList(){
		mainFileParcer.scanConfDir();
		return mainFileParcer.showFilesInConfDir();
	}
	
	//����� ��������� �������� ���� � ������ ������������
	public String getConFileDir(){
		//TODO ���������� � ������ ��������� ����
		return mainFileParcer.showCurrentConfDirPath();
	}
	
	public void setConFileDir(String newConfDirPath){
		mainFileParcer.setConfDirPath(newConfDirPath);
	}
	
	public static void main(String[] args) {
		
	}
}
