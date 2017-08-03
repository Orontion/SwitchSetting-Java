package swsetexceptions;

public class SwitchSettingException extends RuntimeException {

	public SwitchSettingException(String string) {
		super(string);
	}
	
	public SwitchSettingException(){
		super();
	}

	public SwitchSettingException(String string, Exception e) {
		super(string, e);
	}

	private static final long serialVersionUID = 1L;

}
