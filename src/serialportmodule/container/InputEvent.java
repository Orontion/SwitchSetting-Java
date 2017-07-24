package serialportmodule.container;

import java.util.EventObject;

public class InputEvent extends EventObject {

	//TODO Разобраться с назначением переменной
	private static final long serialVersionUID = 1L;
	//Переменная, содержащая полученную из порта информацию
	private String inputContents;
	
	
	public InputEvent(Object source, String inputContents) {
		super(source);
		this.inputContents = inputContents;
	}
	
	public String getInputContents(){
		return inputContents;
	}
}
