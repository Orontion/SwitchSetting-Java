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
	//������ �� ������� ����
	private MainWindowClass mainWindow;
	private SettingsWindowClass settingsWindow;
	private TermWindowClass terminalWindow;
	//������ �� ������-������ ���������������� ������
	private FileParcer mainFileParcer;
	//������ �� ����� ��� ������ � ���������������� ������
	private SerialPortClass serialPortInteractor;
	//������ �� ���������, ���������� ���� ���������� � ���������������� ����
	private InputContainer serialPortInputCont;
	
	//TODO ��������� ������ ������������, �������������� �� �����������
	//����������� ������� ���� ��������� =============================================================================================================================
	public ConfigurationCore() {
		//������ ������ ���������� ���� � ������
		this.initializeCode();
	}
	
	//�����, ���������������� ��������� � ������ =============================================================================================================================
	public void initializeCode(){
		//TODO �������� �������� �� �����
		
		//������ ��� ����������� �������
		mainFileParcer = new FileParcer("e:/Saves");
		serialPortInteractor = new SerialPortClass();
		serialPortInputCont = new InputContainer();
		
		//������� ������ �� ������ ����� ������ �� ����������������� ����� � ����� ������ � COM-������
		serialPortInteractor.setInputContainerRef(serialPortInputCont);
	}

	//��������� ������ �� ������� ���� =============================================================================================================================
	public void setMainWindow(MainWindowClass mainWindow){
		this.mainWindow = mainWindow;
	}
	
	//����� ��� ��������� ������ ���-������ � �������. ���� ������ ��� - ����� ���������� ===============================================================
	public List<String> getSerialPortList(){
		
		//�.�. ����������� ���������� JSSC ����� ������ SerialPortList � ���� ������� String[], 
		//� ��������� ��������� �������� � List, ���������� �������������� ����� ��������� List 
		List<String> converterList = new ArrayList<>();
		
		//��������� ��������� ������ �� ����������� ������� �� ���������
		for (String tmpStr : serialPortInteractor.getSerialPortList()){
			converterList.add(tmpStr);
		}
		
		//���� � �������� List ��� �� ����� ������ - ����� ���������� 
		if (converterList.isEmpty()){
			throw new NoSerialPortsInSystemException("There is no serial ports in system!");
		}
		
		return converterList;
	}
	
	//��������� ������ ��� ���������������� ������ ==================================================================================================
	public List<String> getConfFilesList(){
		//����� ������� ������ - �������������� ���������� �� ������� ���������
		//TODO ������������� �������� ������������ �������� ConfDir?
		mainFileParcer.scanConfDir();

		return mainFileParcer.showFilesInConfDir();
	}
	
	//��������� �������� ���� � ������ ������������ ==================================================================================================
	public String getConFileDir(){
		//TODO ���������� � ������ ��������� ����
		return mainFileParcer.getCurrentConfDirPath();
	}
	
	//Setter ��� �������� ConfFileDir ================================================================================================================
	public void setConFileDir(String newConfDirPath){
		mainFileParcer.setConfDirPath(newConfDirPath);
	}
	
	//����� COM-����� ��� ������ =====================================================================================================================
	//TODO ��������, ����� ������ �������� � SerialPortList, ����� ������ ���� ������� ������ COM-����
	public void chooseSerialPort(String serialPortName){
		serialPortInteractor.chooseSerialPort(serialPortName);
	}
	
	//����� �������� ������ COM-����� ================================================================================================================
	public void chooseSerialPortSpeed(int chosedSpeed){
		serialPortInteractor.chooseSerialPortSpeed(chosedSpeed);
	}
	
	//������ ������ � COM-������ =====================================================================================================================
	public void openChosenPort(){
		serialPortInteractor.startWork();
	}
	
	//���������� Listener-� ��� ��������� �������� ���������� � ����� =============================================================================================================================
	public void addSerialPortInputListener(InputEventListener inputListener){
		serialPortInputCont.addListener(inputListener);
	}
	
	//�������� Listener-� ===============================================================================================================================
	public void removeSerialPortInputListener(InputEventListener inputListener){
		serialPortInputCont.removeListener(inputListener);
	}
	
	//��������� ���� ����������, ������� ���� ������� �� COM-����� ==================================================================================================
	public String getAllSerialPortData(){
		return serialPortInputCont.getAllInputData();
	}
	
	public static void main(String[] args) {
		
	}
}
