package interfc.termwindow;

import confcore.ConfigurationCore;
import interfc.termwindow.view.TermWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TermWindowClass extends Application {
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
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		termWinLoader = new FXMLLoader(getClass().getResource("view/TermWindowView.fxml"));
		
		Parent termView = termWinLoader.load();
		
		//Получение ссылки на объект-контроллер окна, и передача этому объекту ссылки на текущий объект-окно
		termWinCtrl = termWinLoader.getController();
		termWinCtrl.setTermWindowRef(this);
		
		Scene termScene = new Scene(termView);
		
		termWinStage = new Stage();
		termWinStage.setScene(termScene);
		termWinStage.setTitle("Терминал");
		termWinStage.initOwner(primaryStage);
		termWinStage.show();
	}

	public void closeWindow(){
		termWinStage.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
