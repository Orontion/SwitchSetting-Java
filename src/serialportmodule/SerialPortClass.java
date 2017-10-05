package serialportmodule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import serialportmodule.container.InputContainer;

public class SerialPortClass {
	
	//Inner class-listener ��� ��������� ������� ��������� ������ � �����
	private class SerialPortListener implements SerialPortEventListener { 
		
		//��������������� ����������� ������
	    public void serialEvent (SerialPortEvent event) {
	    	//���� ������� ������� ���������� ������, � ��� ������ � �������� ������������...
	        if (event.isRXCHAR () && event.getEventValue () > 0){ 
	            try {
	            	//...��������� �� ����� ��������� ������ � ������� �� ����� ����� prinOutData
	            	String data = workingPort.readString(event.getEventValue()); 
	            	printOutData(data);
	            } catch (SerialPortException ex) {
	                      ex.printStackTrace();
	            }
	        }
	    }
	} 
	
	
	private String portList[];													//������ ������ (�� �����)
	private String selectedPort;												//��� ���������� �����
	private SerialPort workingPort;												//������ ��� ������ � COM-������
	private SerialPortListener innerPortListener = new SerialPortListener();	//Listener ��� �������� �������� ������
	private InputContainer inputContRef;										//���������, ���������� ��� ����������, ������� ��������� � COM-�����
	
	//���������� � ����������� �����
	int portSpeed = -1;
	int portDataBits = -1;
	int portStopBits = -1;
	int portParity = -1;
	
	//�������� ����� � ������ ������ ������ ����� Listener
	public void startWork(){
		try {
			//TODO �������� �������� ����, ��� ��� ������ �����-���� ����
	
			workingPort = new SerialPort(selectedPort);		//������ ����� ������ ��� ������, ��������� ��� ���������� �����			
			workingPort.openPort();								
			System.out.println("port operned!"); //TODO ������
			
			//TODO �������� ��������� � ���������� �� ����������
			//TODO �������� ��������� ������ ����� ���������� �����
			workingPort.setParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			workingPort.addEventListener(innerPortListener);			//��������� � ���� Listener �������� ������
			System.out.println("listener added!");		 //TODO ������
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��������� ������ ���������������� ������ � ������� ====================================================================================================================
	//TODO ��������, ������� ��� ������������� �������� �� List<String>?
	public String[] getSerialPortList(){
		portList = SerialPortList.getPortNames();
		return portList;
	}
	
	//����� ������������ �����	====================================================================================================================
	public void chooseSerialPort(String chosedPort){
		this.selectedPort = chosedPort;
		System.out.println(this.selectedPort); //TODO ������
	}
	
	//����� �������� ����� ====================================================================================================================
	public void chooseSerialPortSpeed(int chosedSpeed){
		portSpeed = chosedSpeed;
		System.out.println(this.portSpeed); //TODO ������
	}
	
	//����� DataBits ====================================================================================================================
	public void chooseSerialPortDataBits(int chosedDataBits){
		portDataBits = chosedDataBits;
	}
	
	//����� StopBits ====================================================================================================================
	public void chooseSerialPortStopBits(int chosedStopBits){
		portStopBits = chosedStopBits;
	}
	
	//����� parity ====================================================================================================================
	public void chooseSerialPortParity(int chosedParity){
		portParity = chosedParity;
	}
	
	//Getter ����� ���������� �����  ====================================================================================================================
	public String getSelectedPort(){
		return selectedPort;
	}
	
	//����� ��������� ���������� Listener-�� ������ ====================================================================================================================
	public void printOutData(String dataToPrint){
		inputContRef.addData(dataToPrint);
	}
	
	//������� ���� ====================================================================================================================
	public void closeSerialPort(){
		if (workingPort.isOpened()){
			try {
				workingPort.closePort();
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//���� ������ � ���� ====================================================================================================================
	public void enterLine(String enteredLine){
		if (!workingPort.isOpened()){
			throw new RuntimeException("Serial port is not opened!");
		}
		
		try {
			workingPort.writeString(enteredLine);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//����� ��������� ������ �� ��������� ������ ====================================================================================================================
	public void setInputContainerRef(InputContainer inputCont){
		this.inputContRef = inputCont;
	}
	
	//����� main ��� ������ ������ ====================================================================================================================
	public static void main(String args[]){
		SerialPortClass testObj = new SerialPortClass();
		
		String testPortList[] = testObj.getSerialPortList();
		
		for (String iterStr : testPortList){
			System.out.println(iterStr);
		}
		
		testObj.chooseSerialPort(testPortList[0]);
		testObj.startWork();
		System.out.println("Selected port: " + testObj.getSelectedPort());
		
		
		try {
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			String tmpStr = "";
			while (!tmpStr.equals("END")){
				tmpStr = consoleReader.readLine();
				testObj.enterLine(tmpStr + "\n");
				if (tmpStr.equals("close")){
					testObj.closeSerialPort();
				}
			}
			System.out.println("Input cycle finished!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}