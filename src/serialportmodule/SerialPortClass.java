package serialportmodule;


import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialPortClass {
	private static class MyEventListener implements SerialPortEventListener { 
	    public void serialEvent (SerialPortEvent event) {
	        if (event.isRXCHAR () && event.getEventValue () > 0){ 
	            try {
	                      String data = workingPort.readString(event.getEventValue()); 
	                      System.out.print (data);
	                   }
	            catch (SerialPortException ex) {
	                      ex.printStackTrace();
	            }
	        }
	    }
	} 
	
	private String portList[];
	private String selectedPort;
	private static SerialPort workingPort;
	
	public void startWork(){
		try {
			workingPort = new SerialPort(selectedPort);
			workingPort.openPort();
			workingPort.setParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			workingPort.addEventListener(new MyEventListener());
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String[] getSerialPortList(){
		portList = SerialPortList.getPortNames();
		return portList;
	}
	
	public void selectPort(String selectedPort){
		this.selectedPort = selectedPort;
	}
	
	public String getSelectedPort(){
		return selectedPort;
	}
	
	
	
	public static void main(String args[]){
		SerialPortClass testObj = new SerialPortClass();
		
		String testPortList[] = testObj.getSerialPortList();
		
		for (String iterStr : testPortList){
			System.out.println(iterStr);
		}
		
		testObj.selectPort(testPortList[0]);
		testObj.startWork();
		System.out.println("Selected port: " + testObj.getSelectedPort());
		
		
	}
	

	
//	public void finalize(){
//		try {
//			System.out.println("Finalizing...");
//			workingPort.closePort();
//		} catch (SerialPortException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
}


