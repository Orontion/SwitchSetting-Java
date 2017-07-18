package sharedres;

public class SwitchParameters {
	
	public static final int SWITCH_PORTS_NOT_SET = -1;
	public static final String SUCCESS_PHRASE_NOT_SET = "!NOTSET";
	public static final int PORT_SPEED_NOT_SET = -1;

	private int SwitchPorts;
	private String SuccessPhrase;
	private int PortSpeed;
	
	
	
	//Конструктор для предовращения ошибок чтения данных из объекта. 
	public SwitchParameters() {
		SwitchPorts = SWITCH_PORTS_NOT_SET;
		SuccessPhrase = SUCCESS_PHRASE_NOT_SET;
		PortSpeed = PORT_SPEED_NOT_SET;
	}
	
	public SwitchParameters(int SwitchPorts, String SuccessPhrase, int PortSpeed){
		this.SwitchPorts = SwitchPorts;
		this.SuccessPhrase = SuccessPhrase;
		this.PortSpeed = PortSpeed;
	}
	
	//TODO проверить поведение программы при перехвате этого исключения
	public int getSwitchPorts() {
		if (SwitchPorts == SWITCH_PORTS_NOT_SET){
			throw new RuntimeException("Number of switch ports is not set!");
		}
		return SwitchPorts;
	}

	public void setSwitchPorts(int switchPorts) {
		this.SwitchPorts = switchPorts;
	}

	//TODO проверить поведение программы при перехвате этого исключения
	public String getSuccessPhrase() {
		if (SuccessPhrase.equals(SUCCESS_PHRASE_NOT_SET)){
			throw new RuntimeException("Success phrase is not set!");
		}
		return SuccessPhrase;
	}

	public void setSuccessPhrase(String successPhrase) {
		this.SuccessPhrase = successPhrase;
	}
	
	public int getPortSpeed() {
		if (PortSpeed == PORT_SPEED_NOT_SET){
			throw new RuntimeException("Port speed is not set!");
		}
		return PortSpeed;
	}

	public void setPortSpeed(int portSpeed) {
		PortSpeed = portSpeed;
	}

	public void readParamLine(String params[]){
		//Если в функцию передаётся неверный массив - вылетает исключение
		if (params.length != 2){
			throw new RuntimeException("Wrong parameter line!");
		}
		
		switch (params[0]) {
		case "PORTS":
			try{
				int tmpPorts = Integer.parseInt(params[1]);
				this.setPortSpeed(tmpPorts);
			}
			catch (NumberFormatException e){
				throw new RuntimeException("Wrong number of ports input!", e);
			}
			break;
		case "SUCCESS":
			this.setSuccessPhrase(params[1]);
			break;
		case "SPEED":
			try{
				int tmpSpeed = Integer.parseInt(params[1]);
				this.setPortSpeed(tmpSpeed);
			}
			catch (NumberFormatException e){
				throw new RuntimeException("Wrong serial port speed input!", e);
			}
			break;
		default:
			throw new RuntimeException("Can't find such switch parameters category: " + params[0]);
		}
	}
	
	public void resetAll(){
		SwitchPorts = SWITCH_PORTS_NOT_SET;
		SuccessPhrase = SUCCESS_PHRASE_NOT_SET;
		PortSpeed = PORT_SPEED_NOT_SET;
	}
}
