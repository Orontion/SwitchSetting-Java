package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class TermWindowController {
	@FXML
	private Button butClose;
	
	@FXML
	private Button butOpenSerialPort;
	
	@FXML
	private TextArea txtAreaTerminal;
	
	//Метод закрытия окна ========================================================================================================================
	@FXML
	private void closeForm(){
		termWindowRef.hideWindow();
	}
	
	@FXML
	private void openSerialPort(){
		termWindowRef.openSerialPort();
	}
	
	//Ссылка на класс-окно
	public TermWindowClass termWindowRef;
	
	//Функция получения ссылки на класс-окно
	public void setTermWindowRef(TermWindowClass termWindowRef){
		this.termWindowRef = termWindowRef;
	}
	
	//Метод добавления данных в окно терминала
	public void addDataToTerminal(String newData){
		//TODO Возможно, этой проверки здесь быть не должно. Добавлена для тестов!
		if (newData != null){
			txtAreaTerminal.appendText(newData);
		}
	}
	
}