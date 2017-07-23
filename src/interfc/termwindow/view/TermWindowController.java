package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TermWindowController {
	@FXML
	private Button butClose;
	
	//����� �������� ���� ==========================================================
	@FXML
	private void closeForm(){
		termWindowRef.closeWindow();
	}
	
	//������ �� �����-����
	public TermWindowClass termWindowRef;
	
	//������� ��������� ������ �� �����-����
	public void setTermWindowRef(TermWindowClass termWindowRef){
		this.termWindowRef = termWindowRef;
	}
	
}