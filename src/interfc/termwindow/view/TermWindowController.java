package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

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