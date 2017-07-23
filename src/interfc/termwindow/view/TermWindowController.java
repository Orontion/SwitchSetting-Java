package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TermWindowController {
	@FXML
	private Button butClose;
	
	//Метод закрытия окна ==========================================================
	@FXML
	private void closeForm(){
		termWindowRef.closeWindow();
	}
	
	//Ссылка на класс-окно
	public TermWindowClass termWindowRef;
	
	//Функция получения ссылки на класс-окно
	public void setTermWindowRef(TermWindowClass termWindowRef){
		this.termWindowRef = termWindowRef;
	}
	
}