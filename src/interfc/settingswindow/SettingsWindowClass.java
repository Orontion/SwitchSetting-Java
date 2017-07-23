package interfc.settingswindow;

import java.io.File;

import confcore.ConfigurationCore;
import interfc.mainwindow.MainWindowClass;
import interfc.settingswindow.view.SettingsWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
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
	
	//TODO Придумать передачу информации о смене директории, не требующую ссылки на главное окно
	//Ссылка на главное окно
	MainWindowClass mainWindowRef;
	
	public SettingsWindowClass(ConfigurationCore programCoreRef, MainWindowClass mainWindowRef) {
		this.programCoreRef = programCoreRef;
		this.mainWindowRef = mainWindowRef;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stgWinLoader = new FXMLLoader(getClass().getResource("view/SettingsWindowView.fxml"));
		
		Parent stgView = stgWinLoader.load();
		
		stgWinCtrl = stgWinLoader.getController();
		stgWinCtrl.setSettingsWindowRef(this);
		
		stgWinCtrl.fillSerialPortList(programCoreRef.getSerialPortList());
		stgWinCtrl.setConfDirPath(programCoreRef.getConFileDir());
		
		Scene stgScene = new Scene(stgView);
		
		stgWinStage = new Stage();
		stgWinStage.setScene(stgScene);
		stgWinStage.setTitle("Настройки приложения");
		stgWinStage.initModality(Modality.WINDOW_MODAL);
		stgWinStage.initOwner(primaryStage);
		stgWinStage.show();
	}

	//Вызов диалога выбора папки с конфигурационными файлами
	public void chooseConfFileDir(){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File newConfDir;
		
		newConfDir = dirChooser.showDialog(stgWinStage);
		
		if (newConfDir != null){
			programCoreRef.setConFileDir(newConfDir.getAbsolutePath());
			stgWinCtrl.setConfDirPath(newConfDir.getAbsolutePath());
			mainWindowRef.refreshFileListCmb();
		}
	}

	public void chooseSerialPort(String serialPortName){
		programCoreRef.chooseSerialPort(serialPortName);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
