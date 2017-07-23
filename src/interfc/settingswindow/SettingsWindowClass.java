package interfc.settingswindow;

import confcore.ConfigurationCore;
import interfc.mainwindow.view.MainWindowController;
import interfc.settingswindow.view.SettingsWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindowClass extends Application {
	//Loader для доступа к контроллеру
	FXMLLoader stgWinLoader;
	//Ссылка на контроллер интерфейса
	SettingsWindowController stgWinCtrl;
	//Stage для отображения окна настроек
	Stage stgWinStage;
	//Ссылка на ядро программы
	ConfigurationCore programCoreRef;
	
	public SettingsWindowClass(ConfigurationCore programCoreRef) {
		this.programCoreRef = programCoreRef;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stgWinLoader = new FXMLLoader(getClass().getResource("view/SettingsWindowView.fxml"));
		
		Parent stgView = stgWinLoader.load();
		
		stgWinCtrl = stgWinLoader.getController();
		
		
		stgWinCtrl.fillSerialPortList(programCoreRef.getSerialPortList());
		
		Scene stgScene = new Scene(stgView);
		
		stgWinStage = new Stage();
		stgWinStage.setScene(stgScene);
		stgWinStage.setTitle("Настройки приложения");
		stgWinStage.initModality(Modality.WINDOW_MODAL);
		stgWinStage.initOwner(primaryStage);
		stgWinStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
