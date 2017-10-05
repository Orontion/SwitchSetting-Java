package interfc.termwindow;

import confcore.ConfigurationCore;
import interfc.termwindow.view.TermWindowController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import serialportmodule.container.InputEvent;
import serialportmodule.container.InputEventListener;

public class TermWindowClass extends Application implements InputEventListener {
	//Loader для доступа к контроллеру
	FXMLLoader termWinLoader;
	//Ссылка на контроллер интерфейса
	TermWindowController termWinCtrl;
	//Stage для отображения окна терминала
	Stage termWinStage;
	//Ссылка на ядро программы
	ConfigurationCore programCoreRef;
	
	//Конструктор, вынуждающий передавать ссылку на объект ядра программы =====================================================================================
	public TermWindowClass(ConfigurationCore programCoreRef) {
		this.programCoreRef = programCoreRef;
		programCoreRef.addSerialPortInputListener(this);
	}
	
	//Старт окна терминала ==========================================================================================================================================================================
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Загрузка view-файла формы
		termWinLoader = new FXMLLoader(getClass().getResource("view/TermWindowView.fxml"));
		
		//Загружаем полученный файл в объект Parent (родительский объект для всей остальной формы)
		Parent termView = termWinLoader.load();
		
		
		//Получение ссылки на объект-контроллер окна, и передача этому объекту ссылки на текущий объект-окно
		termWinCtrl = termWinLoader.getController();
		termWinCtrl.setTermWindowRef(this);
		
//		termWinCtrl.addDataToTerminal(programCoreRef.getAllSerialPortData());
		
		//Создаём сцену на основе формы в termView
		Scene termScene = new Scene(termView);
		
		//Запускаем сцену, но на экран её не выводим, т.к. мы используем текстовое поле окна терминала как лог общения с COM-портом
		termWinStage = new Stage();
		termWinStage.setScene(termScene);
		termWinStage.setTitle("Терминал");
		termWinStage.initOwner(primaryStage);
//		termWinStage.show();
		
		//Переназначение действия при закрытии окна - просто скрываем его
		termWinStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				termWinStage.hide();
			}
		});

	}
	
	//Метод скрытия окна ==========================================================================================================================================================================
	public void hideWindow(){
		termWinStage.hide();
	}
	
	//Метод отображения окна ==========================================================================================================================================================================
	public void showWindow(){
		termWinStage.show();
	}
	
	//Т.к. класс использует интерфейс InputEventListener, то мы определяем метод, вызываемый при получении данных из COM-порта ==========================
	@Override
	public void dataArrived(InputEvent inputE) {
		termWinCtrl.addDataToTerminal(inputE.getInputContents());
	}

	//Метод открытия выбранного порта ==================================================================================================================================
	public void openSerialPort(){
		programCoreRef.openChosenPort();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
