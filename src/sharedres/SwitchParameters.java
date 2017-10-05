package sharedres;

//Класс-контейнер, содержащий информацию о параметрах свитча
public class SwitchParameters {
	
	//Параметры свитча по-умолчанию - если не установлены
	public static final int SWITCH_PORTS_NOT_SET = -1;
	public static final String SUCCESS_PHRASE_NOT_SET = "!NOTSET";
	public static final int PORT_SPEED_NOT_SET = -1;

	//Переменные для хранения параметров свитча
	private int SwitchPorts;
	private String SuccessPhrase;
	private int PortSpeed;
	
	
	
	//Конструктор для предовращения ошибок чтения данных из объекта ============================================================================================================
	public SwitchParameters() {
		SwitchPorts = SWITCH_PORTS_NOT_SET;
		SuccessPhrase = SUCCESS_PHRASE_NOT_SET;
		PortSpeed = PORT_SPEED_NOT_SET;
	}
	
	//Конструктор с параметрами ==================================================================================================================================================================
	public SwitchParameters(int SwitchPorts, String SuccessPhrase, int PortSpeed){
		this.SwitchPorts = SwitchPorts;
		this.SuccessPhrase = SuccessPhrase;
		this.PortSpeed = PortSpeed;
	}
	
	//TODO проверить поведение программы при перехвате этого исключения
	//Getter количества портов в свитче =======================================================================================================================================
	public int getSwitchPorts() {
		if (SwitchPorts == SWITCH_PORTS_NOT_SET){
			throw new RuntimeException("Number of switch ports is not set!");
		}
		return SwitchPorts;
	}

	//Setter количества портов в свитче =======================================================================================================================================
	public void setSwitchPorts(int switchPorts) {
		this.SwitchPorts = switchPorts;
	}

	//TODO проверить поведение программы при перехвате этого исключения
	//Getter фразы успешной настройки свитча =======================================================================================================================================
	public String getSuccessPhrase() {
		if (SuccessPhrase.equals(SUCCESS_PHRASE_NOT_SET)){
			throw new RuntimeException("Success phrase is not set!");
		}
		return SuccessPhrase;
	}

	//Setter фразы успешной настройки свитча =======================================================================================================================================
	public void setSuccessPhrase(String successPhrase) {
		this.SuccessPhrase = successPhrase;
	}
	
	//Getter скорости COM-порта для работы со свитчом =======================================================================================================================================
	public int getPortSpeed() {
		if (PortSpeed == PORT_SPEED_NOT_SET){
			throw new RuntimeException("Port speed is not set!");
		}
		return PortSpeed;
	}

	//Setter скорости COM-порта для работы со свитчом =======================================================================================================================================
	public void setPortSpeed(int portSpeed) {
		PortSpeed = portSpeed;
	}
	
	//TODO Переделать код на более гибкий
	//Специальный метод для чтения параметров свитча =================================================================================================================================================
	public void readParamLine(String params[]){
		//Если в функцию передаётся неверный массив - вылетает исключение
		if (params.length != 2){
			throw new RuntimeException("Wrong parameter line!");
		}
		
		//По первому элементу массива читаем, какой параметр сейчас вводится
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
			
		//Если не удалось найти такой параметр у свитча - вылетает исключение
		default:		
			throw new RuntimeException("Can't find such switch parameters category: " + params[0]);
		}
	}
	
	//Сброс всех параметров свитча на NOT_SET ====================================================================================================================
	public void resetAll(){
		SwitchPorts = SWITCH_PORTS_NOT_SET;
		SuccessPhrase = SUCCESS_PHRASE_NOT_SET;
		PortSpeed = PORT_SPEED_NOT_SET;
	}
}
