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
	
	//Inner class-listener для обработки событий получения данных с порта
	private class SerialPortListener implements SerialPortEventListener { 
		
		//Переопределение встроенного метода
	    public void serialEvent (SerialPortEvent event) {
	    	//Если событие вызвано получением данных, и эти данные в принципе присутствуют...
	        if (event.isRXCHAR () && event.getEventValue () > 0){ 
	            try {
	            	//...считываем из порта имеющиеся данные и выводим их через метод prinOutData
	            	String data = workingPort.readString(event.getEventValue()); 
	            	printOutData(data);
	            } catch (SerialPortException ex) {
	                      ex.printStackTrace();
	            }
	        }
	    }
	} 
	
	
	private String portList[];													//Список портов (их имена)
	private String selectedPort;												//Имя выбранного порта
	private SerialPort workingPort;												//Объект для работы с COM-портом
	private SerialPortListener innerPortListener = new SerialPortListener();	//Listener для контроля входящих данных
	private InputContainer inputContRef;										//Контейнер, содержащий всю информацию, которая приходила с COM-порта
	
	//Переменные с настройками порта
	int portSpeed = -1;
	int portDataBits = -1;
	int portStopBits = -1;
	int portParity = -1;
	
	//Открытие порта и запуск потока чтения через Listener
	public void startWork(){
		try {
			//TODO вставить проверку того, что был выбран какой-либо порт
	
			workingPort = new SerialPort(selectedPort);		//Создаём новый объект для работы, используя имя выбранного порта			
			workingPort.openPort();								
			System.out.println("port operned!"); //TODO Убрать
			
			//TODO Изменить параметры с хардкодных на изменяемые
			//TODO Вставить обработку ошибок ввода параметров порта
			workingPort.setParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			workingPort.addEventListener(innerPortListener);			//Добавляем в порт Listener входящих данных
			System.out.println("listener added!");		 //TODO Убрать
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Получение списка последовательных портов в системе ====================================================================================================================
	//TODO Возможно, сменить тип возвращаемого значения на List<String>?
	public String[] getSerialPortList(){
		portList = SerialPortList.getPortNames();
		return portList;
	}
	
	//Выбор определённого порта	====================================================================================================================
	public void chooseSerialPort(String chosedPort){
		this.selectedPort = chosedPort;
		System.out.println(this.selectedPort); //TODO Убрать
	}
	
	//Выбор скорости порта ====================================================================================================================
	public void chooseSerialPortSpeed(int chosedSpeed){
		portSpeed = chosedSpeed;
		System.out.println(this.portSpeed); //TODO Убрать
	}
	
	//Выбор DataBits ====================================================================================================================
	public void chooseSerialPortDataBits(int chosedDataBits){
		portDataBits = chosedDataBits;
	}
	
	//Выбор StopBits ====================================================================================================================
	public void chooseSerialPortStopBits(int chosedStopBits){
		portStopBits = chosedStopBits;
	}
	
	//Выбор parity ====================================================================================================================
	public void chooseSerialPortParity(int chosedParity){
		portParity = chosedParity;
	}
	
	//Getter имени выбранного порта  ====================================================================================================================
	public String getSelectedPort(){
		return selectedPort;
	}
	
	//Метод обработки полученных Listener-ом данных ====================================================================================================================
	public void printOutData(String dataToPrint){
		inputContRef.addData(dataToPrint);
	}
	
	//Закрыть порт ====================================================================================================================
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
	
	//Ввод строки в порт ====================================================================================================================
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
	
	//Метод получения ссылки на контейнер данных ====================================================================================================================
	public void setInputContainerRef(InputContainer inputCont){
		this.inputContRef = inputCont;
	}
	
	//Метод main для тестов модуля ====================================================================================================================
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