package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class TermWindowController {
	////Объявления объектов, расположенных на форме и методов, запускаемых с формы
	
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
	
	//Метод открытия выбранного порта ==================================================================================================================================
	@FXML
	private void openSerialPort(){
		termWindowRef.openSerialPort();
	}
	
	//Метод реагирования на нажатия клавиш ========================================================================================================================
	@FXML
	private void typeKey(KeyEvent e){
		String Ent = System.getProperty("line.separator");
		if (Ent.lastIndexOf(e.getCharacter()) != -1){
			System.out.println("Enter was pressed!");
		}
	}
	
	//Внутренние объекты и методы класса
	
	
	//Ссылка на класс-окно
	public TermWindowClass termWindowRef;
	
	//Функция получения ссылки на класс-окно ==================================================================================================================================
	public void setTermWindowRef(TermWindowClass termWindowRef){
		this.termWindowRef = termWindowRef;
	}
	
	//Метод добавления данных в текстовое поле терминала ==================================================================================================================================
	public void addDataToTerminal(String newData){
		//TODO Возможно, этой проверки здесь быть не должно. Добавлена для тестов!
		if (newData != null){
			txtAreaTerminal.appendText(newData);
		}
	}
}