package serialportmodule;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialPortClass {
	private class SerialPortListener implements SerialPortEventListener { 
	    public void serialEvent (SerialPortEvent event) {
	        if (event.isRXCHAR () && event.getEventValue () > 0){ 
	            try {
	                      String data = workingPort.readString(event.getEventValue()); 
	                      printOutData(data);
	                   }
	            catch (SerialPortException ex) {
	                      ex.printStackTrace();
	            }
	        }
	    }
	} 
	
	private String portList[];
	private String selectedPort;
	private SerialPort workingPort;
	private SerialPortListener innerPortListener = new SerialPortListener();
	int portSpeed = -1;
	int portDataBits = -1;
	int portStopBits = -1;
	int portParity = -1;
	
	//Открытие порта и запуск потока чтения через Listener
	public void startWork(){
		try {
			workingPort = new SerialPort(selectedPort);
			workingPort.openPort();
			workingPort.setParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			workingPort.addEventListener(innerPortListener);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Получение списка последовательных портов в системе
	public String[] getSerialPortList(){
		portList = SerialPortList.getPortNames();
		return portList;
	}
	
	//Выбор определённого порта	
	public void chooseSerialPort(String selectedPort){
		this.selectedPort = selectedPort;
		System.out.print(this.selectedPort);
	}
	
	public String getSelectedPort(){
		return selectedPort;
	}
	
	public void printOutData(String dataToPrint){
		System.out.print(dataToPrint);
	}
	
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


