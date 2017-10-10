package interfc.termwindow.view;

import interfc.termwindow.TermWindowClass;
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
		
		//Если пользователь увёл курсор за пределы собственного ввода - его принудительно возвращают в конец поля ввода
		//TODO Не успевает перехватить Enter, текст уезжает
		if (txtAreaTerminal.getCaretPosition() < cursPosition) txtAreaTerminal.positionCaret(txtAreaTerminal.getLength());
		
		//По нажатию Enter выводим в COM-порт весь ввод от последней позиции курсора до конца текстового поля, и переводим позицию курсора в конец строки
		if (newLineChar.lastIndexOf(e.getCharacter()) != -1){
			System.out.println(txtAreaTerminal.getText(cursPosition, txtAreaTerminal.getLength()));
			termWindowRef.throwDataToSerial(txtAreaTerminal.getText(cursPosition, txtAreaTerminal.getLength()));
			cursPosition = txtAreaTerminal.getLength();
		}
	}
	
	//Внутренние объекты и методы класса
	
	private String newLineChar = System.getProperty("line.separator");			//Строка, хранящая символы перехода на новую строк											
	private int cursPosition = 0;					//Пометка, где находится начало команд, вводимых пользователем
	
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
			cursPosition = txtAreaTerminal.getLength();
		}
	}
}