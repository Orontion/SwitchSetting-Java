package interfc.mainwindow;

import confcore.ConfigurationCore;
import interfc.mainwindow.view.MainWindowController;
import interfc.settingswindow.SettingsWindowClass;
import interfc.termwindow.TermWindowClass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import swsetexceptions.NoSerialPortsInSystemException;
import swsetexceptions.SwitchSettingException;

public class MainWindowClass extends Application {
	//Loader для доступа к контроллеру
	FXMLLoader mainWindowLoader;
	//Ссылка на контроллер интерфейса
	MainWindowController mainWindowCtrl;
	//Объект-ядро, создаётся в объекте главного окна 
	ConfigurationCore programCore = new ConfigurationCore();
	//Stage для отображения окна
	Stage mainWindowStage;
	//Ссылка на объект окна терминала
	TermWindowClass termWindow;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//TODO Запилить нормальную обработку исключений
		
		//Передача ссылки на окно в ядро
		programCore.setMainWindow(this);
		
		//Получаем FXML-файл формы
		mainWindowLoader = new FXMLLoader();
		mainWindowLoader.setLocation(getClass().getResource("view/MainWindowView.fxml"));
			
		//Загружаем полученный файл в объект Parent (родительский объект для всей остальной формы)
		Parent mainView = mainWindowLoader.load();
		
		//Получение ссылки на объект-контроллер окна, и передача этому объекту ссылки на текущий объект-окно
		mainWindowCtrl = mainWindowLoader.getController();
		mainWindowCtrl.setMainWindowRef(this);
		
		//Действия при загрузке формы
		refreshFileListCmb();
		
		//Создаём сцену на основе формы в mainView
		Scene mainScene = new Scene(mainView);
		
		//Запускаем сцену и показываем её на экране
		mainWindowStage = new Stage();
		mainWindowStage.setScene(mainScene);
		mainWindowStage.setTitle("Настройка коммутаторов");
		mainWindowStage.setMinHeight(mainScene.getHeight());
		mainWindowStage.setMinWidth(mainScene.getWidth());
		mainWindowStage.show();
		
		termWindow = new TermWindowClass(programCore);
		//Создаём окно терминала, если не удаётся - вылетает исключение
		try {
			termWindow.start(mainWindowStage);
		} catch (Exception e) {
			throw new RuntimeException("Cannot start terminal window", e);
		}
	}
	
	//Метод отображения окна с настройками =======================================================================
	public void showSettingsWindow(){
		//Новый объект окна настроек
		SettingsWindowClass stgWindow = new SettingsWindowClass(programCore, this);

		//Показываем созданное окно
		try {
			stgWindow.start(mainWindowStage);
		} catch (Exception e) {
			throw new SwitchSettingException("Cannot start settings window", e);
		}
	}
	
	//Метод отображения окна терминала ===========================================================================
	public void showTermWindow(){
		termWindow.showWindow();
	}
	
	//Метод обновления содержимого ComboBox-а со списком конфигурационных файлов =================================
	public void refreshFileListCmb(){
		mainWindowCtrl.fillCmbConfFileSelect(programCore.getConfFilesList());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
