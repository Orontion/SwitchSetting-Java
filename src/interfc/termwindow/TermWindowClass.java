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
	
	public TermWindowClass(ConfigurationCore programCoreRef) {
		this.programCoreRef = programCoreRef;
		programCoreRef.addSerialPortInputListener(this);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		termWinLoader = new FXMLLoader(getClass().getResource("view/TermWindowView.fxml"));
		
		Parent termView = termWinLoader.load();
		
		//Получение ссылки на объект-контроллер окна, и передача этому объекту ссылки на текущий объект-окно
		termWinCtrl = termWinLoader.getController();
		termWinCtrl.setTermWindowRef(this);
		
		termWinCtrl.addDataToTerminal(programCoreRef.getAllSerialPortData());
		
		Scene termScene = new Scene(termView);
		
		termWinStage = new Stage();
		
		//Обработка закрытия окна
		termWinStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				programCoreRef.removeSerialPortInputListener(TermWindowClass.this); //Убрать ссылку на уничтожаемый объект из списка Listener-ов
			}
		});
		termWinStage.setScene(termScene);
		termWinStage.setTitle("Терминал");
		termWinStage.initOwner(primaryStage);
		termWinStage.show();
	}
	
	public void closeWindow(){
		//Возможна утечка памяти. Метод .close() просто скрывает окно, а не закрывает.
		termWinStage.close();
		programCoreRef.removeSerialPortInputListener(this); //Костыль, т.к. при вызове .close() event не генерируется
	}
	
	@Override
	public void dataArrived(InputEvent inputE) {
		termWinCtrl.addDataToTerminal(inputE.getInputContents());
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
