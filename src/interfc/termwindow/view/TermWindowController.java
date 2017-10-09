package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.event.Event;
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
		String Ent = System.getProperty("line.separator");
		if (Ent.lastIndexOf(e.getCharacter()) != -1){
			System.out.println("Enter was pressed!");
		}
	}
	
	//���������� ������� � ������ ������
	
	
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
		}
	}
}