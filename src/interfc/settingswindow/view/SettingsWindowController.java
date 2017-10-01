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
	//���������� ��������, ������������� �� �����
	
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
	
	//������-�������, ������������ �� �����
	
	@FXML
	private void initialize(){
		//������ ObservableList ��� ������ ������ COM-������ � combobox, � ��������� ��� � ���� combobox-��
		serialPortObsList = FXCollections.observableArrayList();
		cmbSerialPort.setItems(serialPortObsList);
		
		//������ ObservableList � �������� ������� ��������� COM-�����, � ��������� ��� � ��������������� combobox-��
		portSpeedObsList = FXCollections.observableArrayList();
		portSpeedObsList.add(SerialPort.BAUDRATE_4800);
		portSpeedObsList.add(SerialPort.BAUDRATE_9600);
		portSpeedObsList.add(SerialPort.BAUDRATE_14400);
		portSpeedObsList.add(SerialPort.BAUDRATE_57600);
		portSpeedObsList.add(SerialPort.BAUDRATE_115200);
		cmbPortSpeed.setItems(portSpeedObsList);
		
		//������ ObservableList � �������� ������� ��������� COM-�����, � ��������� ��� � ��������������� combobox-��
		portDataBits = FXCollections.observableArrayList();
		portDataBits.add(SerialPort.DATABITS_5);
		portDataBits.add(SerialPort.DATABITS_6);
		portDataBits.add(SerialPort.DATABITS_7);
		portDataBits.add(SerialPort.DATABITS_8);
		cmbPortDataBits.setItems(portDataBits);
		cmbPortDataBits.setValue(SerialPort.DATABITS_8);	//������������� ����������� �������� � 8 ���
		
		//������ ObservableList � �������� ������� ����-����� COM-�����, � ��������� ��� � ��������������� combobox-��
		//��� ������ ������������ ������ + 1, �.�. � ������ SerialPort ���������� JSSC ��������� ������������� �������� int 1, 2 � 3 
		portStopBits = FXCollections.observableArrayList();
		portStopBits.add("1");
		portStopBits.add("2");
		portStopBits.add("1,5");
		cmbPortStopBits.setItems(portStopBits);
		cmbPortStopBits.setValue("1");		//������������� ����������� ��������
		
		//������ ObservableList � �������� ������� parity COM-�����, � ��������� ��� � ��������������� combobox-��
		//��� ������ ������������ ������, �.�. � ������ SerialPort ���������� JSSC ��������� ������������� �������� int �� 0 �� 4
		portParity = FXCollections.observableArrayList();
		portParity.add("NONE");
		portParity.add("ODD");
		portParity.add("EVEN");
		portParity.add("MARK");
		portParity.add("SPACE");
		cmbPortParity.setItems(portParity);
		cmbPortParity.setValue("NONE");		//������������� ����������� ��������
		
		//TODO �������� �������� ������� �������� COM-����� �� ���� ���������
	}
	
	
	//������ �� ������ ������ ����� � ����������������� ������� ======================================================================================
	@FXML
	private void chooseConfFileDir(){
		stgWinRef.chooseConfFileDir();
	}
	
	//��������� ������ ���������� �������� ��� ��������� �����-���� ����������, ���� � ������� ���� ����, ������� ����� ��������� ======================================================================================
	@FXML
	private void activateSerialPortSettingsButton(){
		if (canSetupSerialPort)	butConfirmSerialPortSettings.setDisable(false);
	}
	
	//������ �� ������ ���������� �������� =============================================================================================================================================
	@FXML
	private void confirmSerialPortSettings(){
		if (cmbSerialPort.getSelectionModel().getSelectedItem() == null){
			//TODO �������� ��������� ������ �� ���������� �����
			return;
		}		
		
		if (cmbPortSpeed.getSelectionModel().getSelectedItem() == null){
			//TODO �������� ��������� ������ �� ��������� �������� �����
			return;
		}
		
		//��������� ���������, ��������� �� � ����� ���� ��������
		stgWinRef.chooseSerialPort(cmbSerialPort.getSelectionModel().getSelectedItem());
		stgWinRef.chooseSerialPortSpeed(cmbPortSpeed.getSelectionModel().getSelectedItem());
		
		//TODO �������� �������� �������� COM-�����
	
		//��������� ������ ���������� �������� �� ��������� �����-���� ����������
		butConfirmSerialPortSettings.setDisable(true);
	}
	
	//���������� ������� � ������� ������
	
	private SettingsWindowClass stgWinRef;
	private ObservableList<String> serialPortObsList;
	private ObservableList<Integer> portSpeedObsList;
	private ObservableList<Integer> portDataBits;
	private ObservableList<String> portStopBits;
	private ObservableList<String> portParity;
	
	//����, ������������ ������� COM-������ � �������, ������� ����� �����������.
	//���� � ������� ��� �� ������ COM-����� - ��������� ������ ����� ���������, �.�. ������ ���������� �� ������������
	private boolean canSetupSerialPort = true;
	
	//����� ��������� ������ �� ������ ���� �������� ================================================================================================
	public void setSettingsWindowRef(SettingsWindowClass stgWinRef){
		this.stgWinRef = stgWinRef;
	}
	
	//����� ��� ���������� ���������� � ComboBox ������ ��� ���������������� ������ ================================================================
	public void fillSerialPortList(List<String> serialPortList){
		serialPortObsList.clear();
		serialPortObsList.addAll(serialPortList);
		canSetupSerialPort = true;
	}
	
	//����� ��-��������� - ����� ����������� =======================================================================================================
	public void fillSerialPortList(){
		serialPortObsList.clear();
		serialPortObsList.add("��� ��������� ������!");
		cmbSerialPort.setValue("��� ��������� ������!");
		canSetupSerialPort = false;
	}
	
	//����� ��� ��������� �������� ���� � ������ ������������ ======================================================================================
	public void setConfDirPath(String confDirPath){
		txtConfDirPath.setText(confDirPath);
	}
}
