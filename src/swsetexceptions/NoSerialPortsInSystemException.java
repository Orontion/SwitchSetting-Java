package swsetexceptions;

public class NoSerialPortsInSystemException extends SwitchSettingException {

	public NoSerialPortsInSystemException(String string) {
		super(string);
	}

	public NoSerialPortsInSystemException() {
	}

	public NoSerialPortsInSystemException(String string, Exception e) {
		super(string, e);
	}

}
