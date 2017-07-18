package sharedres;

public class SwitchParameters {
	
	public static final int SWITCH_PORTS_NOT_SET = -1;
	public static final String SUCCESS_PHRASE_NOT_SET = "!NOTSET";

	private int SwitchPorts;
	private String SuccessPhrase;
	
	
	//Конструктор для предовращения ошибок чтения данных из объекта. 
	public SwitchParameters() {
		SwitchPorts = SWITCH_PORTS_NOT_SET;
		SuccessPhrase = SUCCESS_PHRASE_NOT_SET;
	}
	
	public SwitchParameters(int SwitchPorts, String SuccessPhrase){
		this.SwitchPorts = SwitchPorts;
		this.SuccessPhrase = SuccessPhrase;
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
}
