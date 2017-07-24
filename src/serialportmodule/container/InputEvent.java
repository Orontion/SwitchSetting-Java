package serialportmodule.container;

import java.util.EventObject;

public class InputEvent extends EventObject {

	//TODO ����������� � ����������� ����������
	private static final long serialVersionUID = 1L;
	//����������, ���������� ���������� �� ����� ����������
	private String inputContents;
	
	
	public InputEvent(Object source, String inputContents) {
		super(source);
		this.inputContents = inputContents;
	}
	
	public String getInputContents(){
		return inputContents;
	}
}
