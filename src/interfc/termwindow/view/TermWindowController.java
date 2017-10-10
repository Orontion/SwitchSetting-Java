package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class TermWindowController {
	////���������� ��������, ������������� �� ����� � �������, ����������� � �����
	
	@FXML
	private Button butClose;
	
	@FXML
	private Button butOpenSerialPort;
	
	@FXML
	private TextArea txtAreaTerminal;
	
	//����� �������� ���� ========================================================================================================================
	@FXML
	private void closeForm(){
		termWindowRef.hideWindow();
	}
	
	//����� �������� ���������� ����� ==================================================================================================================================
	@FXML
	private void openSerialPort(){
		termWindowRef.openSerialPort();
	}
	
	//����� ������������ �� ������� ������ ========================================================================================================================
	@FXML
	private void typeKey(KeyEvent e){
		
		//���� ������������ ��� ������ �� ������� ������������ ����� - ��� ������������� ���������� � ����� ���� �����
		//TODO �� �������� ����������� Enter, ����� �������
		if (txtAreaTerminal.getCaretPosition() < cursPosition) txtAreaTerminal.positionCaret(txtAreaTerminal.getLength());
		
		//�� ������� Enter ������� � COM-���� ���� ���� �� ��������� ������� ������� �� ����� ���������� ����, � ��������� ������� ������� � ����� ������
		if (newLineChar.lastIndexOf(e.getCharacter()) != -1){
			System.out.println(txtAreaTerminal.getText(cursPosition, txtAreaTerminal.getLength()));
			termWindowRef.throwDataToSerial(txtAreaTerminal.getText(cursPosition, txtAreaTerminal.getLength()));
			cursPosition = txtAreaTerminal.getLength();
		}
	}
	
	//���������� ������� � ������ ������
	
	private String newLineChar = System.getProperty("line.separator");			//������, �������� ������� �������� �� ����� �����											
	private int cursPosition = 0;					//�������, ��� ��������� ������ ������, �������� �������������
	
	//������ �� �����-����
	public TermWindowClass termWindowRef;
	
	//������� ��������� ������ �� �����-���� ==================================================================================================================================
	public void setTermWindowRef(TermWindowClass termWindowRef){
		this.termWindowRef = termWindowRef;
	}
	
	//����� ���������� ������ � ��������� ���� ��������� ==================================================================================================================================
	public void addDataToTerminal(String newData){
		//TODO ��������, ���� �������� ����� ���� �� ������. ��������� ��� ������!
		if (newData != null){
			txtAreaTerminal.appendText(newData);
			cursPosition = txtAreaTerminal.getLength();
		}
	}
}